package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventInfoDO;
import com.dcits.dcwlt.pay.online.mapper.EventMapper;
import com.dcits.dcwlt.pay.online.service.IEventInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanguohai
 * @Time 2020/10/29 10:58
 * @Version 1.0
 * Description:异常事件流水表数据库操作组件
 */
@Repository
public class EventInfoRepository implements IEventInfoRepository {

    @Autowired
    private EventMapper eventMapper;


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
        return eventMapper.insertEventInfo(eventInfoDO);
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
        return eventMapper.updateEventInfo(eventInfoDO);
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
        return eventMapper.updateEventDealNum(param);
    }

}
