package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.pay.api.model.MonitorDO;
import com.dcits.dcwlt.pay.online.mapper.ReconsummaryChkMapper;
import com.dcits.dcwlt.pay.online.service.IMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @desc 监控数据组件
 *
 * @author
 *
 */
@Repository
public class MonitorServiceImpl implements IMonitorService {

    @Autowired
    private ReconsummaryChkMapper reconsummaryChkMapper;

    private static final String INSERT_MONITOR="monitorMapper.insertMonitorData";
    private static final String UPDATE_MONITOR="monitorMapper.updateMonitorData";
    @Override
    public int insertMonitorData(MonitorDO monitorDO) {
        return reconsummaryChkMapper.insertMonitorData(monitorDO);
    }

    @Override
    public int updateMonitorData(MonitorDO monitorDO) {
        return reconsummaryChkMapper.updateMonitorData(monitorDO);
    }
}
