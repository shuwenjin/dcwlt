package com.dcits.dcwlt.pay.batch.mapper;


import com.dcits.dcwlt.pay.api.model.SettleTaskExecDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @description: some desc
 * @author: zhangp
 * @date: 2021/3/9 17:18
 */

@Mapper
public interface SettleTaskExecMapper {

    public int insert(SettleTaskExecDO taskExec);

    public SettleTaskExecDO queryTaskExecByCode(String settleDate, String taskGroupCode, String taskCode, String batchId);


    public int updateTaskExecState(SettleTaskExecDO taskExec);

    public int updateTaskExecDone(SettleTaskExecDO taskExec);

    public List<SettleTaskExecDO> queryTaskExecListByCode(String settleDate, String taskGroupCode, String batchId);

    public List<SettleTaskExecDO> queryTaskExecFailure(String settleDate);

    public int deleteExecTaskByGroupId(String settleDate, String taskGroupCode, String batchId);
}
