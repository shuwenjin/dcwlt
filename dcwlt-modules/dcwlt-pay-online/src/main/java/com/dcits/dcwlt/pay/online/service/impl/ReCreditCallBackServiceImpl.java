package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.common.pay.channel.event.msg.EventDealRspMsg;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import com.dcits.dcwlt.pay.online.service.IReCreditCallBackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 调用补入账事件回调
 */
public class ReCreditCallBackServiceImpl implements IReCreditCallBackService {

    private static final Logger logger = LoggerFactory.getLogger(ReCreditCallBackServiceImpl.class);

    @Autowired
    private PayTransDtlInfoServiceImpl payTransDtlInfoRepository;

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoService;




    @Override
    public EventDealRspMsg reCreditSucc(EventDealRspMsg eventDealRspMsg, BankCore351100InnerRsp bankCore351100InnerRsp, JSONObject eventParam) {
        logger.info("补入账成功开始回调处理");


        //查询最新状态是否为 211--> 111
        String payDate = eventParam.getString("payDate");
        String paySerno = eventParam.getString("paySerno");

        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoRepository.query(payDate,paySerno);
        boolean status211 = (AppConstant.TRXSTATUS_ABEND.equals(payTransDtlInfoDO.getTrxStatus())
                && AppConstant.CORESTATUS_SUCCESS.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

        if(status211){
            payTransDtlInfoService.updateFinalStatusSucc(payTransDtlInfoDO);
            eventDealRspMsg.setRespMsg("补入账成功，登记簿状态为211，更新为111");
        }
        return eventDealRspMsg;
    }

    @Override
    public EventDealRspMsg reCreditFail(EventDealRspMsg eventDealRspMsg, BankCore351100InnerRsp bankCore351100InnerRsp, JSONObject eventParam) {
        logger.error("补入账失败");
        return eventDealRspMsg;
    }

    @Override
    public EventDealRspMsg reCreditException(EventDealRspMsg eventDealRspMsg, BankCore351100InnerRsp bankCore351100InnerRsp, JSONObject eventParam) {
        return eventDealRspMsg;
    }
}
