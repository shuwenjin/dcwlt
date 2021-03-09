package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventConfigDO;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;

public interface IEventRegisterAppService {
    void registerEvent(EventDealReqMsg reqMsg);

    void registerEvent(EventDealReqMsg reqMsg, String msgTag);

    EventConfigDO loadEventConfig(String eventCode);

    String getMaxDealCount(String eventCode);
}
