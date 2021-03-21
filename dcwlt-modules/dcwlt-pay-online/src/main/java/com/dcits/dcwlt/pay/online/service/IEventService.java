package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealRspMsg;

public interface IEventService {
    EventDealRspMsg runFlow(EventDealReqMsg eventDealReqMsg);
}
