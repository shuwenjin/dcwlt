package com.dcits.dcwlt.pay.batch.service;

import com.dcits.dcwlt.pay.api.model.MonitorDO;

import java.util.List;

/**
 * @Author: Ashin
 * @Date: 2021/5/11
 * @Description: com.dcits.dcwlt.pay.batch.service
 * @Version: 1.0.0
 */
public interface IExMonitorService {

    /**
     * 查询监控数据
     *
     * @param monitorDO
     * @return
     */
    List<MonitorDO> queryMonitorList(MonitorDO monitorDO);

}
