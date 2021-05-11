package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.MonitorDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MonitorMapper {


    /**
     * 新增监控数据
     */
    int insertMonitorData(MonitorDO monitorDO);

    /**
     * 更新监控数据
     */
    int updateMonitorData(MonitorDO monitorDO);
}
