package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.model.MonitorDO;

/**
 * @author
 * @desc 监控组件
 */
public interface IMonitorService {
    /*
     * 新增监控数据
     * */
    int insertMonitorData(MonitorDO monitorDO);

    /*
     * 更新监控数据
     * */
    int updateMonitorData(MonitorDO monitorDO);
}
