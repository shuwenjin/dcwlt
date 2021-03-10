package com.dcits.dcwlt.pay.batch.service;


import com.dcits.dcwlt.pay.api.model.SettleTaskGroupExecDO;

import java.util.List;

public interface ISettleTaskGroupExecService {

    public int insert(SettleTaskGroupExecDO taskGroupExec);

    public int updateTaskGroupExecState(SettleTaskGroupExecDO taskGroupExec);

    public SettleTaskGroupExecDO queryTaskGroupExec(String settleDate, String batchId, String taskGroupCode);
    
    public List<SettleTaskGroupExecDO> queryTaskExecFailure(String settleDate);
    
    public int deleteExecTaskByGroupId(String settleDate, String taskGroupCode, String batchId);
}
