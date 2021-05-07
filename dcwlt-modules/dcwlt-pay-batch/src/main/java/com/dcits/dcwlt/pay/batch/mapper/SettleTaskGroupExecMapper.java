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

    List<SettleTaskGroupExecDO> select(SettleTaskGroupExecDO settleTaskGroupExecDO);

    int insert(SettleTaskGroupExecDO taskGroupExec);

    int updateTaskGroupExecState(SettleTaskGroupExecDO taskGroupExec);

    SettleTaskGroupExecDO queryTaskGroupExec(@Param("settleDate") String settleDate, @Param("batchId") String batchId, @Param("taskGroupCode") String taskGroupCode);

    List<SettleTaskGroupExecDO> queryTaskGroupExecFailure(@Param("settleDate") String settleDate);

    int deleteTaskGroupByGroupId(@Param("settleDate") String settleDate, @Param("taskGroupCode") String taskGroupCode, @Param("batchId") String batchId);

}
