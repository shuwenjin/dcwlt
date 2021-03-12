package com.dcits.dcwlt.pay.batch.service.impl;


import com.dcits.dcwlt.common.pay.enums.DtlFileProcStatusEnum;
import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;
import com.dcits.dcwlt.pay.batch.service.ICopyOnlineDtlFileToBatchService;
import com.dcits.dcwlt.pay.batch.service.IDtlFileInfoBatchService;
import com.dcits.dcwlt.pay.batch.service.IDtlFileInfoOnlineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 转移联机数据库对账文件清单数据到批量数据库
 *
 * @author majun
 * @date 2021/1/7
 */
@Service
public class CopyOnlineDtlFileToBatchService implements ICopyOnlineDtlFileToBatchService {
    private static final Logger logger = LoggerFactory.getLogger(CopyOnlineDtlFileToBatchService.class);
    @Autowired
    private IDtlFileInfoOnlineService dtlFileInfoOnlineService;

    @Autowired
    private IDtlFileInfoBatchService dtlFileInfoBatchService;

    /**
     * 从联机库同步指定批次的对账文件清单到批量库中
     *
     * @param batchId
     * @return
     */
    @Override
    public boolean copyDtlFile(String batchId) {
        List<DtlFileInfDO> dtlFileInfDOS = dtlFileInfoOnlineService.queryDtlFileInfoByBatchId(batchId);
        //如果批次号对应没有对账明细文件数据需要同步， 执行响应成功，任务完成
        if (dtlFileInfDOS.isEmpty()) {
            logger.info("对账明细文件转存任务， 批次id:{}, 本次没有需要同步的数据", batchId);
            return true;
        }
        logger.info("对账明细文件转存任务， 批次id:{}, 本次需要同步的数据{}条", batchId, dtlFileInfDOS.size());

        //在联机库中没有文件处理状态， 统一将数据设置为未下载状态
        dtlFileInfDOS.parallelStream().forEach(dtlFileInfDO -> {
            dtlFileInfDO.setFileProcStatus(DtlFileProcStatusEnum.INIT.getCode());
            dtlFileInfDO.setLastUpDate(DateUtil.getDefaultDate());
            dtlFileInfDO.setLastUpTime(DateUtil.getDefaultTime());
            String fileName = dtlFileInfDO.getFileName();

            //去掉文件.sec后缀，方便后续文件移到和解压处理
            if (fileName.endsWith(".sec")) {
                fileName = fileName.substring(0, fileName.length() - 4);
            }
            dtlFileInfDO.setFileName(fileName);
        });

        //执行同步操作
        try {
            int sum = dtlFileInfoBatchService.replaceDtlFileInfo(dtlFileInfDOS);

            //判断是全量同步完成并返回
            if (sum != dtlFileInfDOS.size()) {
                logger.warn("对账明细文件转存任务， 批次号:{}, 本次需要同步的数据{}条, 实际完成同步{}条",batchId, dtlFileInfDOS.size(), sum);
                throw new SettleTaskException(SettleTaskErrorEnum.BC0109.getCode(), SettleTaskErrorEnum.BC0109.getDesc());
            }

            logger.info("完成对账明细文件转存任务， 批次号:{}, 本次需要同步的数据{}条，完成同步{}条", batchId, dtlFileInfDOS.size(), sum);
            return true;
        } catch (Exception e) {
            logger.warn("对账明细文件转存任务， 批次号:{}, 本次需要同步的数据{}条, 错误信息{}", batchId, dtlFileInfDOS.size(), e.getMessage());
        }
        //失败返回，表示导数失败
        return false;
    }
}
