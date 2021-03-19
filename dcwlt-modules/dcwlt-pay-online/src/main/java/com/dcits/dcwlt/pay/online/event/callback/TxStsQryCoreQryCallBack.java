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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 交易状态查询回查核心回调
 */
public class TxStsQryCoreQryCallBack implements ICoreQryCallBack {
    private static final Logger logger = LoggerFactory.getLogger(TxStsQryCoreQryCallBack.class);

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoService;

    @Autowired
    private ICoreProcessService coreProcessService;

    @Override
    public EventDealRspMsg coreSucc(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {
        logger.info("交易状态查询-核心回查成功回调");

        String payDate =  eventParam.getString("payDate");
        String paySerno = eventParam.getString("paySerno");
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(payDate,paySerno);


        boolean status227 = (AppConstant.TRXSTATUS_ABEND.equals(payTransDtlInfoDO.getTrxStatus())
                && AppConstant.CORESTATUS_ABEND.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_RECIPE.equals(payTransDtlInfoDO.getPathProcStatus()));

        boolean status221 = (AppConstant.TRXSTATUS_ABEND.equals(payTransDtlInfoDO.getTrxStatus())
                && AppConstant.CORESTATUS_ABEND.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

        //227 --> 217
        if(status227){
            StateMachine stateMachine = new StateMachine();
            stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
            stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
            stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
            coreProcessService.qryCoreStsRetDone(payTransDtlInfoDO,bankCore996666Rsp,stateMachine);
        }

        //221 --> 111
        if(status221){
            StateMachine stateMachine = new StateMachine();
            stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
            stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
            stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
            coreProcessService.qryCoreStsRetDone(payTransDtlInfoDO,bankCore996666Rsp,stateMachine);

            payTransDtlInfoDO = payTransDtlInfoService.query(payDate,paySerno);
            payTransDtlInfoService.updateFinalStatusSucc(payTransDtlInfoDO);
        }

        return eventDealRspMsg;
    }

    @Override
    public EventDealRspMsg coreFail(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {

        logger.info("交易状态查询-核心回查失败回调");
        String payDate =  eventParam.getString("payDate");
        String paySerno = eventParam.getString("paySerno");
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(payDate,paySerno);

        boolean status227 = (AppConstant.TRXSTATUS_ABEND.equals(payTransDtlInfoDO.getTrxStatus())
                && AppConstant.CORESTATUS_ABEND.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_RECIPE.equals(payTransDtlInfoDO.getPathProcStatus()));

        boolean status220 = (AppConstant.TRXSTATUS_ABEND.equals(payTransDtlInfoDO.getTrxStatus())
                && AppConstant.CORESTATUS_ABEND.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_FAILED.equals(payTransDtlInfoDO.getPathProcStatus()));

        //227 --> 207
        if(status227){
            StateMachine stateMachine = new StateMachine();
            stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
            stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
            stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
            coreProcessService.qryCoreStsRetDone(payTransDtlInfoDO,bankCore996666Rsp,stateMachine);
        }

        //220 --> 000
        if(status220){
            StateMachine stateMachine = new StateMachine();
            stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
            stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
            stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            coreProcessService.qryCoreStsRetDone(payTransDtlInfoDO,bankCore996666Rsp,stateMachine);

            payTransDtlInfoDO = payTransDtlInfoService.query(payDate,paySerno);
            payTransDtlInfoService.updateFinalStatusFail(payTransDtlInfoDO);
        }

        return eventDealRspMsg;
    }

    @Override
    public EventDealRspMsg coreAbend(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {
        return eventDealRspMsg;
    }

    @Override
    public EventDealRspMsg coreReversed(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {
        return eventDealRspMsg;
    }
}
