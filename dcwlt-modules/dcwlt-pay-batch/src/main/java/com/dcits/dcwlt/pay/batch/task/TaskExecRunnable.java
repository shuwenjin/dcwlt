package com.dcits.dcwlt.pay.batch.task;

import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskExecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskExecRunnable implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(TaskExecRunnable.class);
    private String settleDate = null;
    private String taskGroupCode = null;
    private String batchId = null;

    private ISettleTaskExecService settleTaskExecService;

    public TaskExecRunnable(String settleDate, String batchId, String taskGroupCode) {
        this.settleDate = settleDate;
        this.taskGroupCode = taskGroupCode;
        this.batchId = batchId;
    }

    @Override
    public void run() {
        logger.info("execute task: taskGroupCode=" + taskGroupCode + ", batchId=" + batchId);
        try {
            logger.info("任务组调起：settleDate=" + settleDate + ", batchId=" + batchId + ", taskGroupCode=" + taskGroupCode);
            settleTaskExecService.runTaskGroup(settleDate, batchId, taskGroupCode, null);
        } catch (SettleTaskException e) {
            logger.error("执行定时任抛出异常：{}-{}", e.getErrorCode(), e.getErrorMsg());
        } catch (Exception e) {
            logger.error("执行定时任抛出异常：{}", e.getMessage());
        }
        logger.info("execute done.");
    }
   
}
