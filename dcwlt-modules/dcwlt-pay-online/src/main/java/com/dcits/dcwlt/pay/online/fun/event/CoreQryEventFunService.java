package com.dcits.dcwlt.pay.online.fun.event;

import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealRspMsg;
import com.dcits.dcwlt.pay.online.event.service.CoreQryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 核心回查事件入口
 */

@Service
@Component("coreQry")
public class CoreQryEventFunService {

    @Autowired
    private CoreQryService coreQryService;

    public EventDealRspMsg runCoreQry(EventDealReqMsg eventDealReqMsg){
        return coreQryService.runFlow(eventDealReqMsg);
    }
}
