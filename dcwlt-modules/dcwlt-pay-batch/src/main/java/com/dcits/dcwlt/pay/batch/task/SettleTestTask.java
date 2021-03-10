package com.dcits.dcwlt.pay.batch.task;

import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettleTestTask implements ISettleTask {
	private static final Logger logger = LoggerFactory.getLogger(SettleTestTask.class);


	@Override
	public void initTask(SettleTaskExecDO taskExec) throws SettleTaskException {
		logger.info("开始执行init：" + taskExec.toString());
		//检查抽取交易数据是否完成

	}

	@Override
	public void runTask(SettleTaskExecDO taskExec) throws SettleTaskException {
		logger.info("开始执行run：" + taskExec.toString());
		
	}

}
