package com.dcits.dcwlt.pay.online.fun.event;

import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealRspMsg;
import com.dcits.dcwlt.pay.online.service.PathQryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 通道状态回查事件入口
 */

@Service
@Component("pathQry")
public class PathQryEventFunService {

    @Autowired
    private PathQryService pathQryService;

    public EventDealRspMsg runPathQry(EventDealReqMsg eventDealReqMsg){
        return pathQryService.runFlow(eventDealReqMsg);
    }
}