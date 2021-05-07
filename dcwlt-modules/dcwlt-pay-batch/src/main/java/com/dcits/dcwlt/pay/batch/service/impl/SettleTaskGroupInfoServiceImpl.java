package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.common.core.exception.BaseException;
import com.dcits.dcwlt.pay.api.model.SettleTaskGroupInfoDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskInfoDO;
import com.dcits.dcwlt.pay.batch.mapper.SettleTaskGroupInfoMapper;
import com.dcits.dcwlt.pay.batch.mapper.SettleTaskInfoMapper;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskGroupInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Ashin
 * @Date: 2021/5/6
 * @Description: com.dcits.dcwlt.pay.batch.service
 * @Version: 1.0.0
 */
@Service
public class SettleTaskGroupInfoServiceImpl implements ISettleTaskGroupInfoService {

    @Autowired
    private SettleTaskGroupInfoMapper settleTaskGroupInfoMapper;

    @Autowired
    private SettleTaskInfoMapper settleTaskInfoMapper;

    @Override
    public int addTaskGroupInfo(SettleTaskGroupInfoDO taskGroupInfoDO) {
        return settleTaskGroupInfoMapper.insert(taskGroupInfoDO);
    }

    @Override
    public List<SettleTaskGroupInfoDO> queryTaskGroupInfoList(SettleTaskGroupInfoDO taskGroupInfoDO) {
        return settleTaskGroupInfoMapper.select(taskGroupInfoDO);
    }

    @Override
    public int updateTaskGroupInfo(SettleTaskGroupInfoDO settleTaskGroupInfoDO) {
        return settleTaskGroupInfoMapper.updateByPrimaryKeySelective(settleTaskGroupInfoDO);
    }


    @Override
    public int deleteTaskGroupInfo(String[] taskGroupCodes) {
        for (String taskGroupCode : taskGroupCodes) {
            List<SettleTaskInfoDO> settleTaskInfoDOS = settleTaskInfoMapper.queryTaskInfoListToExec(taskGroupCode);
            if (!settleTaskInfoDOS.isEmpty()) {
                throw new BaseException(String.format("任务组%s关联的任务信息列表不为空,请先删除任务信息列表!", taskGroupCode));
            }
        }

        return settleTaskGroupInfoMapper.deleteByPrimaryKey(taskGroupCodes);
    }
}
