package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore998889.BankCore998889Rsp;
import com.dcits.dcwlt.common.pay.channel.event.msg.EventDealRspMsg;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.pay.online.service.IBankRevCallBackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wuguofeng01
 * @Time 2021/01/02 15:29
 * @Version 1.0
 * Description:兑出交易冲正回调
 */
public class ConvertBankRevCallBackServiceImpl implements IBankRevCallBackService {

    private static final Logger logger = LoggerFactory.getLogger(ConvertBankRevCallBackServiceImpl.class);
    @Autowired
    private BankRevServiceImpl bankRevServiceImpl;

   // private static final BankRevService bankRevService = RtpUtil.getInstance().getBean(BankRevService.class);


    @Override
    public EventDealRspMsg bankRevSucc(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, JSONObject eventParam) {
        return bankRevServiceImpl.bankRevProcess(eventDealRspMsg, bankCore998889Rsp, eventParam, AppConstant.TRXSTATUS_REVERSED, AppConstant.OPERSTATUS_SUCC);
    }
    @Override
    public EventDealRspMsg bankRevHadSucc(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, JSONObject eventParam) {
        return bankRevServiceImpl.bankRevProcess(eventDealRspMsg, bankCore998889Rsp, eventParam, AppConstant.TRXSTATUS_REVERSED, AppConstant.OPERSTATUS_SUCC);
    }
    @Override
    public EventDealRspMsg bankRevFail(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, JSONObject eventParam) {
        return bankRevServiceImpl.bankRevProcess(eventDealRspMsg, bankCore998889Rsp, eventParam, AppConstant.TRXSTATUS_ABEND, AppConstant.OPERSTATUS_FAIL);
    }
    @Override
    public EventDealRspMsg bankRevException(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, JSONObject eventParam) {
        return bankRevServiceImpl.bankRevProcess(eventDealRspMsg, bankCore998889Rsp, eventParam, AppConstant.TRXSTATUS_ABEND, AppConstant.OPERSTATUS_EXPT);
    }
}
