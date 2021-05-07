package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.pay.api.model.SettleTaskInfoDO;
import com.dcits.dcwlt.pay.batch.mapper.SettleTaskInfoMapper;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskInfoService;
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
public class SettleTaskInfoServiceImpl implements ISettleTaskInfoService {

    @Autowired
    private SettleTaskInfoMapper settleTaskInfoMapper;


    @Override
    public List<SettleTaskInfoDO> queryTaskInfoList(SettleTaskInfoDO settleTaskInfoDO) {
        return settleTaskInfoMapper.select(settleTaskInfoDO);
    }

    @Override
    public int addTaskInfo(SettleTaskInfoDO settleTaskInfoDO) {
        return settleTaskInfoMapper.insertSelective(settleTaskInfoDO);
    }

    @Override
    public int updateTaskInfo(SettleTaskInfoDO settleTaskInfoDO) {
        return settleTaskInfoMapper.updateByPrimaryKeySelective(settleTaskInfoDO);
    }

    @Override
    public int deleteTaskInfo(String[] taskCodes) {
        return settleTaskInfoMapper.deleteByPrimaryKey(taskCodes);
    }


}
