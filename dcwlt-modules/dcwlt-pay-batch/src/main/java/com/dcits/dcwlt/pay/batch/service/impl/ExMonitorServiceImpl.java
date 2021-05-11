package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.pay.api.model.MonitorDO;
import com.dcits.dcwlt.pay.batch.mapper.MonitorMapper;
import com.dcits.dcwlt.pay.batch.service.IExMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Ashin
 * @Date: 2021/5/11
 * @Description: com.dcits.dcwlt.pay.batch.service.impl
 * @Version: 1.0.0
 */
@Service
public class ExMonitorServiceImpl implements IExMonitorService {


    @Autowired
    private MonitorMapper monitorMapper;

    @Override
    public List<MonitorDO> queryMonitorList(MonitorDO monitorDO) {
        return monitorMapper.selectExMonitorInfo(monitorDO);
    }
}
