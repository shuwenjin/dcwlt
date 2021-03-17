package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.SettleTaskGroupExecDO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    public SettleTaskGroupExecDO queryTaskGroupExec(@Param("settleDate")String settleDate, @Param("batchId")String batchId, @Param("taskGroupCode")String taskGroupCode);

    public List<SettleTaskGroupExecDO> queryTaskGroupExecFailure(@Param("settleDate")String settleDate);

    public int deleteTaskGroupByGroupId(@Param("settleDate")String settleDate, @Param("taskGroupCode")String taskGroupCode, @Param("batchId")String batchId);

}
