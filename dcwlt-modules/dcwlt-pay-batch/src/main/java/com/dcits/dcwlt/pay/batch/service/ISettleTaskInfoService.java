package com.dcits.dcwlt.pay.batch.service;

import com.dcits.dcwlt.pay.api.model.SettleTaskInfoDO;

import java.util.List;

/***
 * 任务信息列表
 * @author Ashin
 */
public interface ISettleTaskInfoService {


    List<SettleTaskInfoDO> queryTaskInfoList(SettleTaskInfoDO settleTaskInfoDO);

    int addTaskInfo(SettleTaskInfoDO settleTaskInfoDO);

    int updateTaskInfo(SettleTaskInfoDO settleTaskInfoDO);

    int deleteTaskInfo(String[] taskCodes);
}
