package com.dcits.dcwlt.pay.batch.mapper;


import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: some desc
 * @author: zhangp
 * @date: 2021/3/9 17:18
 */

@Mapper
public interface SettleTaskExecMapper {


    List<SettleTaskExecDO> select(SettleTaskExecDO settleTaskExecDO);

    int insert(SettleTaskExecDO taskExec);

    SettleTaskExecDO queryTaskExecByCode(@Param("settleDate") String settleDate, @Param("taskGroupCode") String taskGroupCode, @Param("taskCode") String taskCode, @Param("batchId") String batchId);


    int updateTaskExecState(SettleTaskExecDO taskExec);

    int updateTaskExecDone(SettleTaskExecDO taskExec);

    List<SettleTaskExecDO> queryTaskExecListByCode(@Param("settleDate") String settleDate, @Param("taskGroupCode") String taskGroupCode, @Param("batchId") String batchId);

    List<SettleTaskExecDO> queryTaskExecFailure(@Param("settleDate") String settleDate);

    int deleteExecTaskByGroupId(@Param("settleDate") String settleDate, @Param("taskGroupCode") String taskGroupCode, @Param("batchId") String batchId);
}
