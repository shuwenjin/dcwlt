package com.dcits.dcwlt.pay.batch.task;


import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.common.pay.enums.TaskCodeEnum;
import com.dcits.dcwlt.common.pay.enums.TaskExecStatusEnum;
import com.dcits.dcwlt.common.pay.enums.TaskGroupEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskExecService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 抽数前置任务检查
 */
@Service
public class ImportTradeDataCheckTask implements ISettleTask {
    private static final Logger logger = LoggerFactory.getLogger(ImportTradeDataCheckTask.class);
   
    @Autowired
    private ISettleTaskExecService settleTaskExecService;
    
    /**
     * 任务执行前初始化
     */
    @Override
    public void initTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        //检查抽取交易数据是否完成
        
    }
    
    /**
     * 执行任务
     */
    @Override
    public void runTask(SettleTaskExecDO taskExec) throws SettleTaskException {
        logger.info("开始执行任务，对账日期:{},对账批次:{}", taskExec.getSettleDate(), taskExec.getBatchId());
        SettleTaskExecDO execTask = settleTaskExecService.queryTaskExecByCode(taskExec.getSettleDate(), TaskGroupEnum.CHK_DATA.getCode(),
                TaskCodeEnum.CD001.getCode(), taskExec.getBatchId());
        if (execTask == null) {
        	throw new SettleTaskException(SettleTaskErrorEnum.BC0016.getCode(), SettleTaskErrorEnum.BC0016.getDesc());
        }
        if (!execTask.getExecState().equals(TaskExecStatusEnum.SUCC.getCode())) {
        	throw new SettleTaskException(SettleTaskErrorEnum.BC0017.getCode(), SettleTaskErrorEnum.BC0017.getDesc());
        }
    }
    
}
