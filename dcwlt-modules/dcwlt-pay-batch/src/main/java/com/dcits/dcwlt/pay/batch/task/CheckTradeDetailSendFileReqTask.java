package com.dcits.dcwlt.pay.batch.task;


import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.batch.service.IGwf008Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 向文件服务器申请文件
 */
@Service
public class CheckTradeDetailSendFileReqTask implements ISettleTask {
    private static final Logger logger = LoggerFactory.getLogger(CheckTradeDetailSendFileReqTask.class);
    @Autowired
    private IGwf008Service gwf008Service;

    /**
     * 任务执行前初始化
     */
    @Override
    public void initTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        if (taskExec.getBatchId() == null) {
            logger.warn("从向文件服务器申请文件任务入参中缺少必要参数，批次号不能为null");
            throw new SettleTaskException(SettleTaskErrorEnum.BC0104.getCode(), SettleTaskErrorEnum.BC0104.getDesc());
        }
    }

    /**
     * 执行任务
     */
    @Override
    public void runTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        logger.info("开始执行对账明细文件下载申请任务:{}，对账日期:{},对账批次:{}", taskExec.getTaskName(), taskExec.getSettleDate(), taskExec.getBatchId());
        try {
            gwf008Service.fileDownloadApply(taskExec.getBatchId(), taskExec.getExecParam());
        } catch (SettleTaskException e) {
            logger.warn("执行对账明细文件下载申请失败，原因{}", e.getMessage(), e);
            throw e;
        }
    }

}
