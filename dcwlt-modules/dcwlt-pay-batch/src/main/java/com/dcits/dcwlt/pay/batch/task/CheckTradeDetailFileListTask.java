package com.dcits.dcwlt.pay.batch.task;


import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.batch.service.ICopyOnlineDtlFileToBatchService;
import com.dcits.dcwlt.pay.batch.service.IDtlFileInfoBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 对账明细文件转存任务
 */
@Service
public class CheckTradeDetailFileListTask implements ISettleTask {
    private static final Logger logger = LoggerFactory.getLogger(CheckTradeDetailFileListTask.class);
    @Autowired
    private ICopyOnlineDtlFileToBatchService iCopyOnlineDtlFileToBatchService;
    @Autowired
    private IDtlFileInfoBatchService dtlFileToBatchService;

    /**
     * 任务执行前初始化
     */
    @Override
    public void initTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        //检查抽取交易数据是否完成
        if (taskExec.getBatchId() == null) {
            logger.warn("对账明细文件转存任务入参中缺少必要参数，批次号不能为null");
            throw new SettleTaskException(SettleTaskErrorEnum.BC0104.getCode(), SettleTaskErrorEnum.BC0104.getDesc());
        }
    }

    /**
     * 执行任务
     */
    @Override
    public void runTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        logger.info("开始执行任务：对账明细文件转存，对账日期:{},对账批次:{}", taskExec.getSettleDate(), taskExec.getBatchId());

        //首先清除批量库中批次号对应的明细对账文件列表
        dtlFileToBatchService.deleteByBatchId(taskExec.getBatchId());

        //开始执行从联机库导报文文件数据到批量库
        boolean copyFinish = iCopyOnlineDtlFileToBatchService.copyDtlFile(taskExec.getBatchId());

        //判断任务正常执行结束
        if (!copyFinish) {
            logger.warn("执行对账明细文件转存任务失败，对账日期:{},对账批次:{}", taskExec.getSettleDate(), taskExec.getBatchId());
            throw new SettleTaskException(SettleTaskErrorEnum.BC0100.getCode(), SettleTaskErrorEnum.BC0100.getDesc());

        }
        logger.info("执行任务:对账明细文件转存结束，对账日期:{},对账批次:{}", taskExec.getSettleDate(), taskExec.getBatchId());
    }
}
