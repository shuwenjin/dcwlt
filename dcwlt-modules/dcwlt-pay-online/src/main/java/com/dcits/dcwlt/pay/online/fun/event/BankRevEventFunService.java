package com.dcits.dcwlt.pay.online.fun.event;

import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealRspMsg;
import com.dcits.dcwlt.pay.online.event.service.BankRevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 冲正事件入库
 */
@Service
@Component("bankRev")
public class BankRevEventFunService {

    @Autowired
    private BankRevService bankRevService;

    public EventDealRspMsg runBankRev(EventDealReqMsg eventDealReqMsg){
        return bankRevService.runFlow(eventDealReqMsg);
    }
}
