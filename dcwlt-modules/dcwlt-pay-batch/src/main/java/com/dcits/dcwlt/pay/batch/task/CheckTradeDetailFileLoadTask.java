package com.dcits.dcwlt.pay.batch.task;


import com.dcits.dcwlt.common.pay.enums.DtlFileProcStatusEnum;
import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.batch.service.IDtlFileInfoBatchService;

import com.dcits.dcwlt.pay.batch.service.IDtlFileParseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 从明细文件中导数据到数据库表中
 */
@Service
public class CheckTradeDetailFileLoadTask implements ISettleTask {
    private static final Logger logger = LoggerFactory.getLogger(CheckTradeDetailFileLoadTask.class);
    @Autowired
    private IDtlFileParseService dtlFileParseService;
    @Autowired
    private IDtlFileInfoBatchService dtlFileInfoBatchService;

    /**
     * 任务执行前初始化
     */
    @Override
    public void initTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        //任务初始化
        //检查抽取交易数据是否完成
        if (taskExec.getBatchId() == null) {
            logger.warn("从明细文件中导数据到数据库表任务入参中缺少必要参数，批次号不能为null");
            throw new SettleTaskException(SettleTaskErrorEnum.BC0104.getCode(), SettleTaskErrorEnum.BC0104.getDesc());
        }
    }

    /**
     * 执行任务
     */
    @Override
    public void runTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        try {
            //执行文件解析入库任务
            dtlFileParseService.parse(taskExec.getBatchId());
        } catch (Exception e) {
            logger.warn("执行从明细文件中导数据到数据库表中失败，本次执行批次号:{}，失败原因{}", taskExec.getBatchId(), e.getMessage());
            throw new SettleTaskException(SettleTaskErrorEnum.BC0103.getCode(), SettleTaskErrorEnum.BC0103.getDesc());
        }

        List<DtlFileInfDO> dtlFileInfDOS =
                dtlFileInfoBatchService.queryDtlFileInfoByBatchId(taskExec.getBatchId());

        //检查当前任务是否执行成功，保证所有的文件都解析
        //判断是否所有的文件状态为SUCC
        if (dtlFileInfDOS != null && !dtlFileInfDOS.isEmpty()) {
            
            int moved = (int) dtlFileInfDOS.stream()
                    .filter(dtlFileInfDO -> DtlFileProcStatusEnum.SUCC.getCode().equals(dtlFileInfDO.getFileProcStatus())).count();

            if (moved != dtlFileInfDOS.size()) {
                logger.warn("从明细文件中导数据到数据库表未完成，批次号：{}，本批次号需要解析{}条，目前已经解析{}条，尚未解析{}条", taskExec.getBatchId(),
                        dtlFileInfDOS.size(), moved, dtlFileInfDOS.size() - moved);
                throw new SettleTaskException(SettleTaskErrorEnum.BC0103.getCode(), SettleTaskErrorEnum.BC0103.getDesc());
            }
        }
    }

}
