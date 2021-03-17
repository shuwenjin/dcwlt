package com.dcits.dcwlt.pay.batch.task;


import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.batch.service.ICheckCollectService;
import com.dcits.dcwlt.pay.batch.service.ICheckPathDetailService;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskExecService;
import com.dcits.dcwlt.pay.batch.service.impl.CheckWrongResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 对总账结果处理
 */
@Service
public class CheckTradeSummaryResultHandleTask implements ISettleTask {
    private static final Logger logger = LoggerFactory.getLogger(CheckTradeSummaryResultHandleTask.class);
    @Autowired
    private ICheckCollectService checkCollectService;
    @Autowired
    private ISettleTaskExecService taskExecService;
    @Autowired
    private CheckWrongResultService basicService;
    @Autowired
    private ICheckPathDetailService checkPathDetailService;
    
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
        
    }
    
}
