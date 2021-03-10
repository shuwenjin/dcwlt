package com.dcits.dcwlt.pay.batch.service;

import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskGroupExecDO;
import com.dcits.dcwlt.pay.batch.task.ISettleTask;


/**
 * @description: some desc
 * @author: zhangp
 * @date: 2021/3/9 16:41
 */
public interface ISettleTaskExecService {
    public void runTaskGroup(String settleDate, String batchId, String taskGroupCode, String digitalEnvelope);
 
    public void runTask(String settleDate, String taskGroupCode, String taskCode, String batchId);

    public void runTaskGroup(SettleTaskGroupExecDO taskGroupExec);

    public void updateTaskGroupExecDoing(SettleTaskGroupExecDO taskGroupExec);

    public void updateTaskGroupExecDone(SettleTaskGroupExecDO taskGroupExec);

    public SettleTaskGroupExecDO initTaskExecByCfg(String settleDate, String batchId, String taskGroupCode,
                                                    String digitalEnvelope);

    public void genTaskExec(SettleTaskGroupExecDO taskGroupExec);

    public void checkTaskExec(SettleTaskExecDO taskExec);

    public ISettleTask getTaskExecInstance(String taskClassName);

    public void run(SettleTaskExecDO taskExec);

    public int updateTaskExecState(SettleTaskExecDO taskExec);
    public int updateTaskExecDone(SettleTaskExecDO taskExec);
}
