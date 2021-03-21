package com.dcits.dcwlt.pay.online.event;


import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;

public interface IEventDealAppService {

    void eventDeal(EventDealReqMsg eventInfo);
}
