package com.dcits.dcwlt.pay.batch.service;


import com.dcits.dcwlt.pay.api.model.SettleTaskGroupInfoDO;

import java.util.List;

/**
 * 任务信息组服务
 *
 * @author Ashin
 */
public interface ISettleTaskGroupInfoService {

    int addTaskGroupInfo(SettleTaskGroupInfoDO taskGroupInfoDO);

    int updateTaskGroupInfo(SettleTaskGroupInfoDO taskGroupExec);

    List<SettleTaskGroupInfoDO> queryTaskGroupInfoList(SettleTaskGroupInfoDO taskGroupInfoDO);

    int deleteTaskGroupInfo(String[] taskGroupCode);
}
