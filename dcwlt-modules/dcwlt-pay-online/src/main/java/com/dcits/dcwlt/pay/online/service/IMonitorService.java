package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.model.MonitorDO;

import java.util.List;

/**
 * @author
 * @desc 监控组件
 */
public interface IMonitorService {

    /**
     * 查询监控数据
     *
     * @param monitorDO
     * @return
     */
    List<MonitorDO> queryMonitorList(MonitorDO monitorDO);

    /**
     * 新增监控数据
     */
    int insertMonitorData(MonitorDO monitorDO);

    /**
     * 更新监控数据
     */
    int updateMonitorData(MonitorDO monitorDO);
}
