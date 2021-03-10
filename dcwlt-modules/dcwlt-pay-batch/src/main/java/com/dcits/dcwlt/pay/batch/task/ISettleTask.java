package com.dcits.dcwlt.pay.batch.task;


import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;

/**
 * 清算任务接口
 * @author liuyuanhui
 *
 */
public interface ISettleTask {

	/**
	 * 清算任务初始化
	 * @throws SettleTaskException
	 */
	public void initTask(SettleTaskExecDO taskExec) throws SettleTaskException;
	
	/**
	 * 执行清算任务
	 * @throws SettleTaskException
	 */
	public void runTask(SettleTaskExecDO taskExec) throws SettleTaskException;
}
