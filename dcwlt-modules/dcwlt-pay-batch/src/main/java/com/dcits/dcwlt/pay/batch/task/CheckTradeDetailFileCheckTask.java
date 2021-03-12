package com.dcits.dcwlt.pay.batch.task;


import com.dcits.dcwlt.common.pay.constant.FileConst;
import com.dcits.dcwlt.common.pay.enums.DtlFileProcStatusEnum;
import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.common.pay.util.FileUtil;
import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.batch.service.IDtlFileInfoBatchService;
import com.dcits.dcwlt.pay.batch.service.file.service.FileScanningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 移动对账明细文件，检查对账明细文件是否收齐
 */
@Service
public class CheckTradeDetailFileCheckTask implements ISettleTask {
    private static final Logger logger = LoggerFactory.getLogger(CheckTradeDetailFileCheckTask.class);
    @Autowired
    private FileScanningService fileScanningService;
    @Autowired
    private IDtlFileInfoBatchService dtlFileInfoBatchService;

    /**
     * 任务执行前初始化
     */
    @Override
    public void initTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        //检查抽取交易数据是否完成
        if (taskExec.getBatchId() == null) {
            logger.warn("移动对账明细文件，检查对账明细文件是否收齐，缺少必要的参数，批次号不能为null");
            throw new SettleTaskException(SettleTaskErrorEnum.BC0104.getCode(), SettleTaskErrorEnum.BC0104.getDesc());
        }
    }

    /**
     * 执行任务
     */
    @Override
    public void runTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        logger.info("开始执行任务，对账日期:{},对账批次:{}", taskExec.getSettleDate(), taskExec.getBatchId());
        try {
            fileScanningService.runFlow(taskExec.getBatchId());
        } catch (Exception e) {
            logger.warn("移动文件服务推送的文件到规定目录失败，批次号：{}，错误信息：{}", taskExec.getBatchId(), e.getMessage());
            throw new SettleTaskException(SettleTaskErrorEnum.BC0102.getCode(), SettleTaskErrorEnum.BC0102.getDesc());
        }

        //兜底检查，防止文件已经移动，但是状态未更新， 这个时候反向扫描目标文件夹，找到已经移动的文件但是状态的为修改的明细文件，修复状态为move
        fixMove(taskExec.getBatchId());
        logger.info("对账明细文件移动任务执行完毕，对账日期:{},对账批次:{}", taskExec.getSettleDate(), taskExec.getBatchId());
    }

    /**
     * 修复移动，并检查文件是否收齐，如果检查到对账明细文件没有收齐， 抛出异常
     *
     * @param batchId
     * @throws SettleTaskException
     */
    private void fixMove(String batchId) throws SettleTaskException {
        List<DtlFileInfDO> dtlFileInfDOS =
                dtlFileInfoBatchService.queryDtlFileInfoByBatchId(batchId);

        if (dtlFileInfDOS != null && !dtlFileInfDOS.isEmpty()) {
            //查出所有已经移动的明细文件
            int moved = (int) dtlFileInfDOS.stream()
                    .filter(dtlFileInfDO -> DtlFileProcStatusEnum.MOVE.getCode().equals(dtlFileInfDO.getFileProcStatus())).count();

            //判断是否存在没有移动的文件， 说明文件尚未移动完成
            if (moved != dtlFileInfDOS.size()) {
                logger.warn("移动文件服务推送的文件到规定目录未完成，批次号：{}，本批次号需要移动{}条，目前已经移动{}条，尚未移动{}条",
                        batchId, dtlFileInfDOS.size(), moved, dtlFileInfDOS.size() - moved);

                //拿到所有处理状态不是move状态的数据，做兜底检查，避免文件已经移动， 但是状态未更新，最后没办法完成该项任务
                List<DtlFileInfDO> unMoveDtlFileInfos = dtlFileInfDOS.stream().filter(dtlFileInfDO -> {
                    return !DtlFileProcStatusEnum.MOVE.getCode().equals(dtlFileInfDO.getFileProcStatus());
                }).collect(Collectors.toList());

                //检查这些不是move状态的文件是否已经移动
                int fixMove = 0;
                for (DtlFileInfDO dtlFileInfDO : unMoveDtlFileInfos) {
                    //拼装文件目标路径
                    //String destPath = RtpUtil.getInstance().getProperty(FileConst.CONFIG_PROPERTY_SAVEBAKPATH);
                    String destPath = FileConst.CONFIG_PROPERTY_SAVEBAKPATH;

                    //目标文件路径中会自动添加批次号
                    destPath = destPath + File.separator + dtlFileInfDO.getBatchId();

                    String filePath = destPath + File.separator + dtlFileInfDO.getFileName();
                    //调用rtp文件工具类， 判断文件是否存在，如果已经存在， 那么修复文件处理状态
                    if (FileUtil.isFileExist(filePath)) {
                        dtlFileInfDO.setLocalFilePath(filePath);
                        dtlFileInfDO.setFileProcStatus(DtlFileProcStatusEnum.MOVE.getCode());
                        dtlFileInfDO.setLastUpDate(DateUtil.getDefaultDate());
                        dtlFileInfDO.setLastUpTime(DateUtil.getDefaultTime());
                        dtlFileInfoBatchService.updateDtlFileInfo(dtlFileInfDO);

                        //统计修复文件条数
                        fixMove++;
                    }
                }
                //修复后的移动文件数量
                moved += fixMove;
                logger.info("明细对账文件检查移动兜底执行完毕，修复移动{}条", fixMove);
            }
            //兜底检查结束后依旧存在没有移动的文件， 抛出异常，该任务需要下次再次拉起
            if (moved != dtlFileInfDOS.size()) {
                logger.warn("对账明细文件修复检查执行完毕，任然有{}条未完成移动的文件", dtlFileInfDOS.size() - moved);
                throw new SettleTaskException(SettleTaskErrorEnum.BC0102.getCode(), SettleTaskErrorEnum.BC0102.getDesc());
            }
        }
    }
}
