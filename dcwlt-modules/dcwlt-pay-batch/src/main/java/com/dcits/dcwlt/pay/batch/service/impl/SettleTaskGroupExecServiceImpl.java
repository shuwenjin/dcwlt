package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.pay.api.model.SettleTaskGroupExecDO;
import com.dcits.dcwlt.pay.batch.mapper.SettleTaskGroupExecMapper;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskGroupExecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 任务组执行信息
 */

@Service
public class SettleTaskGroupExecServiceImpl implements ISettleTaskGroupExecService {

    @Autowired
    private SettleTaskGroupExecMapper settleTaskGroupExecMapper;


    @Override
    public List<SettleTaskGroupExecDO> querySettleTaskExec(SettleTaskGroupExecDO settleTaskGroupExecDO) {
        return settleTaskGroupExecMapper.select(settleTaskGroupExecDO);
    }

    @Override
    public int insert(SettleTaskGroupExecDO taskGroupExec) {
        return settleTaskGroupExecMapper.insert(taskGroupExec);
    }

    @Override
    public int updateTaskGroupExecState(SettleTaskGroupExecDO taskGroupExec) {
        return settleTaskGroupExecMapper.updateTaskGroupExecState(taskGroupExec);
    }

    @Override
    public SettleTaskGroupExecDO queryTaskGroupExec(String settleDate, String batchId, String taskGroupCode) {
        return settleTaskGroupExecMapper.queryTaskGroupExec( settleDate,batchId,taskGroupCode);
    }

    @Override
    public List<SettleTaskGroupExecDO> queryTaskExecFailure(String settleDate) {
        return settleTaskGroupExecMapper.queryTaskGroupExecFailure(settleDate);
    }

    @Override
    public int deleteExecTaskByGroupId(String settleDate, String taskGroupCode, String batchId) {
        return settleTaskGroupExecMapper.deleteTaskGroupByGroupId(settleDate,taskGroupCode,batchId);
    }
}
