package com.dcits.dcwlt.pay.online.service;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.common.pay.channel.event.msg.EventDealRspMsg;

/**
 * 核心回查回调
 */
public interface ICoreQryCallBackService {

    //回查成功
    public EventDealRspMsg coreSucc(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam);

    //回查失败
    public EventDealRspMsg coreFail(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam);

    //回查异常
    public EventDealRspMsg coreAbend(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam);

    //回查冲正
    public EventDealRspMsg coreReversed(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam);


}
