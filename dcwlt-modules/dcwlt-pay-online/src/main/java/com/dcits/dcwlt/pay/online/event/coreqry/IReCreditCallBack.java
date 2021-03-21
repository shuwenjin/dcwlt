package com.dcits.dcwlt.pay.online.event.coreqry;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealRspMsg;

public interface IReCreditCallBack {

    //补入账成功
    EventDealRspMsg reCreditSucc(EventDealRspMsg eventDealRspMsg, BankCore351100InnerRsp bankCore351100InnerRsp, JSONObject eventParam);

    //补入账失败
    EventDealRspMsg reCreditFail(EventDealRspMsg eventDealRspMsg, BankCore351100InnerRsp bankCore351100InnerRsp, JSONObject eventParam);

    //补入账异常
    EventDealRspMsg reCreditException(EventDealRspMsg eventDealRspMsg, BankCore351100InnerRsp bankCore351100InnerRsp, JSONObject eventParam);

}
