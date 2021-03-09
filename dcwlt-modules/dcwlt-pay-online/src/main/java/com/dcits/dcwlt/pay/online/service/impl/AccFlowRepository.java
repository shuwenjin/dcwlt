package com.dcits.dcwlt.pay.online.service.impl;


import ch.qos.logback.core.db.dialect.DBUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.model.AccFlowDO;
import com.dcits.dcwlt.pay.online.mapper.AccflowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


/**
 * @author zhanguohai
 * @Time 2020/10/14 9:39
 * @Version 1.0
 * Description:账务流水表数据访问组件
 */
@Repository
public class AccFlowRepository {

    @Autowired
    private AccflowMapper accflowMapper;

    /**
     * 账务信息登记
     * @param accFlowDO 账务流水表实体
     * @return
     */
    public int insert(AccFlowDO accFlowDO) {
        accFlowDO.setLastUpDate(DateUtil.getDefaultDate());
        accFlowDO.setLastUpTime(DateUtil.getDefaultTime());
        accFlowDO.setLastMicroSecond(String.valueOf(System.currentTimeMillis()));
        return accflowMapper.insert(accFlowDO);
    }

    /**
     * 根据核心请求日期和请求流水获取 记账实体
     * @param coreReqDate
     * @param coreReqSerno
     * @return
     */
    public AccFlowDO query(String coreReqDate, String coreReqSerno) {
        Map<String, String> param = new HashMap<>();
        param.put("coreReqDate", coreReqDate);
        param.put("coreReqSerno", coreReqSerno);
        return accflowMapper.query(param);
    }


    /**
     * 更新账务流水信息
     * @param coreReqDate   核心请求日期
     * @param coreReqSerno  核心请求流水
     * @param updAccFlowDO  更新实体
     * @return
     */
    public int update(String coreReqDate, String coreReqSerno, AccFlowDO updAccFlowDO) {
        //补充更新字段
        updAccFlowDO.setLastUpDate(DateUtil.getDefaultDate());
        updAccFlowDO.setLastUpTime(DateUtil.getDefaultTime());
        updAccFlowDO.setLastMicroSecond(String.valueOf(System.currentTimeMillis()));

        //设置更新条件
        updAccFlowDO.setCoreReqDate(coreReqDate);
        updAccFlowDO.setCoreReqSerno(coreReqSerno);
        return accflowMapper.update(updAccFlowDO);
    }


}
