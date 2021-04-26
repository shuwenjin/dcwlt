package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.pay.api.model.MonitorDO;
import com.dcits.dcwlt.pay.online.mapper.MonitorMapper;
import com.dcits.dcwlt.pay.online.mapper.ReconsummaryChkMapper;
import com.dcits.dcwlt.pay.online.service.IMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @desc 监控数据组件
 */
@Repository
public class MonitorServiceImpl implements IMonitorService {

    @Autowired
    private ReconsummaryChkMapper reconsummaryChkMapper;

    @Autowired
    private MonitorMapper monitorMapper;


    @Override
    public List<MonitorDO> queryMonitorList(MonitorDO monitorDO) {
        return monitorMapper.selectExMonitorInfo(monitorDO);
    }

    @Override
    public int insertMonitorData(MonitorDO monitorDO) {
        return reconsummaryChkMapper.insertMonitorData(monitorDO);
    }

    @Override
    public int updateMonitorData(MonitorDO monitorDO) {
        return reconsummaryChkMapper.updateMonitorData(monitorDO);
    }
}
