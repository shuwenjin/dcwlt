package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventInfoDO;
import com.dcits.dcwlt.pay.online.mapper.ReconsummaryChkMapper;
import com.dcits.dcwlt.pay.online.service.IEventInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @Time 2020/10/29 10:58
 * @Version 1.0
 * Description:异常事件流水表数据库操作组件
 */
@Repository
public class EventInfoServiceimpl implements IEventInfoService {

    @Autowired
    private ReconsummaryChkMapper reconsummaryChkMapper;


    /**
     * 异常事件入库，MQ--->DB
     *
     * @param eventInfoDO
     * @return
     */
    @Override
    public int insertEventInfo(EventInfoDO eventInfoDO) {
        eventInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        eventInfoDO.setLastUpTime(DateUtil.getDefaultTime());
        return reconsummaryChkMapper.insertEventInfo(eventInfoDO);
    }

    /**
     * 更新异常事件处理结果
     *
     * @param eventInfoDO
     * @return
     */
    @Override
    public int updateEventInfo(EventInfoDO eventInfoDO) {
        //设置更新字段
        eventInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        eventInfoDO.setLastUpTime(DateUtil.getDefaultTime());
        return reconsummaryChkMapper.updateEventInfo( eventInfoDO);
    }

    /**
     * 更新异常事件处理次数
     * @param exceptEventCode
     * @param exceptEventSeqNo
     * @param maxDealNum
     * @return
     */
    @Override
    public int updateEventDealNum(String exceptEventCode, String exceptEventSeqNo, String maxDealNum) {
        Map<String, Object> param = new HashMap<>();
        param.put("exceptEventCode", exceptEventCode);
        param.put("exceptEventSeqNo", exceptEventSeqNo);
        param.put("maxDealNum", maxDealNum);
        return reconsummaryChkMapper.updateEventDealNum(exceptEventCode,exceptEventSeqNo,maxDealNum);
    }

}
