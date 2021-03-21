package com.dcits.dcwlt.pay.online.event.coreqry;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore998889.BankCore998889Rsp;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealRspMsg;

/**
 * 核心冲正异常事件回调
 */
public interface IBankRevCallBack {
    public EventDealRspMsg bankRevSucc(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, JSONObject eventParam);

    public EventDealRspMsg bankRevHadSucc(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, JSONObject eventParam);

    public EventDealRspMsg bankRevFail(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, JSONObject eventParam);

    public EventDealRspMsg bankRevException(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, JSONObject eventParam);

}
