package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.MonitorDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: Ashin
 * @Date: 2021/5/11
 * @Description: com.dcits.dcwlt.pay.batch.mapper
 * @Version: 1.0.0
 */

@Mapper
public interface MonitorMapper {
    /**
     * 查询异常监控数据
     * @param monitorDO
     * @return
     */
    List<MonitorDO> selectExMonitorInfo(MonitorDO monitorDO);
}
