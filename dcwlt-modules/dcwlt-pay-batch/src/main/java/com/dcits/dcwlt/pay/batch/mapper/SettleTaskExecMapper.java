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

    public int insert(SettleTaskExecDO taskExec);

    public SettleTaskExecDO queryTaskExecByCode(@Param("settleDate")String settleDate, @Param("taskGroupCode")String taskGroupCode, @Param("taskCode")String taskCode, @Param("batchId")String batchId);


    public int updateTaskExecState(SettleTaskExecDO taskExec);

    public int updateTaskExecDone(SettleTaskExecDO taskExec);

    public List<SettleTaskExecDO> queryTaskExecListByCode(@Param("settleDate")String settleDate, @Param("taskGroupCode")String taskGroupCode, @Param("batchId")String batchId);

    public List<SettleTaskExecDO> queryTaskExecFailure(@Param("settleDate")String settleDate);

    public int deleteExecTaskByGroupId(@Param("settleDate")String settleDate, @Param("taskGroupCode")String taskGroupCode, @Param("batchId")String batchId);
}
