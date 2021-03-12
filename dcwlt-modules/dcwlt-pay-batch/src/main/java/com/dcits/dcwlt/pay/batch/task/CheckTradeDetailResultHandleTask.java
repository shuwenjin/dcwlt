package com.dcits.dcwlt.pay.batch.task;


import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.api.model.CheckResultDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.batch.service.ICheckResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 明细对账结果处理
 */
@Service
public class CheckTradeDetailResultHandleTask implements ISettleTask {
    private static final Logger logger = LoggerFactory.getLogger(CheckTradeDetailResultHandleTask.class);

    @Autowired
    private ICheckResultService checkResultService;
    
    
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
        List<CheckResultDO> list = checkResultService.selectCheckResultByStatus(taskExec.getSettleDate(), taskExec.getBatchId());
        if (list != null && !list.isEmpty()) {
        	//String onlineServerUrl = RtpUtil.getInstance().getProperty("ecny.online.service.url");
        	for (CheckResultDO crt : list) {
        		//使用http方使调用联机的方法进行差错处理
//				HttpClientUtil.doPost()
			}
        }
    }
    
}
