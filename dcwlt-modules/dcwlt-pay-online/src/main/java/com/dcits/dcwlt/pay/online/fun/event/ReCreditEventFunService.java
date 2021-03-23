package com.dcits.dcwlt.pay.online.fun.event;

import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealRspMsg;
import com.dcits.dcwlt.pay.online.event.service.ReCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 补入账事件入口
 */
@Service
@Component("reCredit")
public class ReCreditEventFunService {

    @Autowired
    private ReCreditService reCreditService;

    public EventDealRspMsg runReCredit(EventDealReqMsg eventDealReqMsg){
        return reCreditService.runFlow(eventDealReqMsg);
    }
}
