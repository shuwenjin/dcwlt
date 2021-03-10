package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.SettleTaskGroupExecDO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @description: some desc
 * @author: zhangp
 * @date: 2021/3/9 17:29
 */

@Mapper
public interface SettleTaskGroupExecMapper {


    public int insert(SettleTaskGroupExecDO taskGroupExec);

    public int updateTaskGroupExecState(SettleTaskGroupExecDO taskGroupExec);

    public SettleTaskGroupExecDO queryTaskGroupExec(String settleDate, String batchId, String taskGroupCode);

    public List<SettleTaskGroupExecDO> queryTaskGroupExecFailure(String settleDate);

    public int deleteTaskGroupByGroupId(String settleDate, String taskGroupCode, String batchId);

}
