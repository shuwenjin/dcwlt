package com.dcits.dcwlt.pay.online.event.callback;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealRspMsg;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.event.coreqry.ICoreQryCallBack;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import com.dcits.dcwlt.pay.online.service.impl.AuthInfoServiceimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Description:核心回查
 */
@Component
public class DisputeCoreQryCallBack implements ICoreQryCallBack {
    private static final Logger logger = LoggerFactory.getLogger(DisputeCoreQryCallBack.class);

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoService;

    @Autowired
    private ICoreProcessService coreProcessService;

    @Override
    public EventDealRspMsg coreSucc(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {
        logger.info("差错贷记调账查询-核心回查成功回调");
        String payDate =  eventParam.getString("payDate");
        String paySerno = eventParam.getString("paySerno");
        // 227
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(payDate,paySerno);
        coreProcessService.dsptCoreStsRetDone(payTransDtlInfoDO,bankCore996666Rsp,stateMachine);
        return eventDealRspMsg;
    }

    @Override
    public EventDealRspMsg coreFail(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {
        logger.info("差错贷记调账查询-核心回查失败回调");
        String payDate =  eventParam.getString("payDate");
        String paySerno = eventParam.getString("paySerno");
        // 227
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(payDate,paySerno);
        coreProcessService.dsptCoreStsRetDone(payTransDtlInfoDO,bankCore996666Rsp,stateMachine);
        return eventDealRspMsg;
    }

    @Override
    public EventDealRspMsg coreAbend(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {
        // 核心回查异常，保持状态为227不变
        return eventDealRspMsg;
    }

    @Override
    public EventDealRspMsg coreReversed(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {
        return eventDealRspMsg;
    }
}
