package com.dcits.dcwlt.pay.batch.service;


import com.dcits.dcwlt.pay.api.model.SettleTaskGroupExecDO;

import java.util.List;

public interface ISettleTaskGroupExecService {

    List<SettleTaskGroupExecDO> querySettleTaskExec(SettleTaskGroupExecDO settleTaskGroupExecDO);

    int insert(SettleTaskGroupExecDO taskGroupExec);

    int updateTaskGroupExecState(SettleTaskGroupExecDO taskGroupExec);

    SettleTaskGroupExecDO queryTaskGroupExec(String settleDate, String batchId, String taskGroupCode);

    List<SettleTaskGroupExecDO> queryTaskExecFailure(String settleDate);

    int deleteExecTaskByGroupId(String settleDate, String taskGroupCode, String batchId);
}
