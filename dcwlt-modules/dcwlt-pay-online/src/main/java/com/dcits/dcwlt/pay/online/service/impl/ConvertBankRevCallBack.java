package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore998889.BankCore998889Rsp;
import com.dcits.dcwlt.common.pay.channel.event.msg.EventDealRspMsg;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.pay.online.service.IBankRevCallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wuguofeng01
 * @Time 2021/01/02 15:29
 * @Version 1.0
 * Description:兑出交易冲正回调
 */
public class ConvertBankRevCallBack implements IBankRevCallBack {

    private static final Logger logger = LoggerFactory.getLogger(ConvertBankRevCallBack.class);
    @Autowired
    private BankRevService bankRevService;

   // private static final BankRevService bankRevService = RtpUtil.getInstance().getBean(BankRevService.class);


    @Override
    public EventDealRspMsg bankRevSucc(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, JSONObject eventParam) {
        return bankRevService.bankRevProcess(eventDealRspMsg, bankCore998889Rsp, eventParam, AppConstant.TRXSTATUS_REVERSED, AppConstant.OPERSTATUS_SUCC);
    }
    @Override
    public EventDealRspMsg bankRevHadSucc(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, JSONObject eventParam) {
        return bankRevService.bankRevProcess(eventDealRspMsg, bankCore998889Rsp, eventParam, AppConstant.TRXSTATUS_REVERSED, AppConstant.OPERSTATUS_SUCC);
    }
    @Override
    public EventDealRspMsg bankRevFail(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, JSONObject eventParam) {
        return bankRevService.bankRevProcess(eventDealRspMsg, bankCore998889Rsp, eventParam, AppConstant.TRXSTATUS_ABEND, AppConstant.OPERSTATUS_FAIL);
    }
    @Override
    public EventDealRspMsg bankRevException(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, JSONObject eventParam) {
        return bankRevService.bankRevProcess(eventDealRspMsg, bankCore998889Rsp, eventParam, AppConstant.TRXSTATUS_ABEND, AppConstant.OPERSTATUS_EXPT);
    }
}
