package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.common.pay.channel.event.msg.EventDealRspMsg;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoRepository;
import com.dcits.dcwlt.pay.online.service.ICoreQryCallBackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 补入账核心回查
 */
public class ReCreditCoreQryCallBackServiceImpl implements ICoreQryCallBackService {

    @Autowired
    private  IPayTransDtlInfoRepository payTransDtlInfoRepository;

    @Autowired
    private ICoreProcessService bankCoreProcessService;

    @Autowired
    private TxEndNtfcntHandleServiceImpl handleService;

    private static final Logger logger = LoggerFactory.getLogger(ReCreditCoreQryCallBackServiceImpl.class);

    //private static final IPayTransDtlInfoRepository payTransDtlInfoRepository = RtpUtil.getInstance().getBean("payTransDtlInfoRepository");

   // private static final ICoreProcessService bankCoreProcessService = RtpUtil.getInstance().getBean("bankCoreProcessService");

    @Override
    public EventDealRspMsg coreSucc(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {
        logger.info("补入账异常回查核心成功");

     //   TxEndNtfcntHandleService handleService = RtpUtil.getInstance().getBean("txEndNtfcntHandleService");

        //221 --> 211 ，调推断
        String payDate =  eventParam.getString("payDate");
        String paySerno = eventParam.getString("paySerno");

        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoRepository.query(payDate,paySerno);
        boolean status221 = (AppConstant.TRXSTATUS_ABEND.equals(payTransDtlInfoDO.getTrxStatus())
                && AppConstant.CORESTATUS_ABEND.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

        if(status221){
            StateMachine stateMachine = new StateMachine();
            stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
            stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
            stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);

            bankCoreProcessService.qryCoreStsRetDone(payTransDtlInfoDO,bankCore996666Rsp,stateMachine);
            //调用兑回推断
            handleService.reconvertInferenceHandle(payDate,paySerno);
        }

        return eventDealRspMsg;
    }

    @Override
    public EventDealRspMsg coreFail(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {
        logger.info("补入账异常回查核心失败");

        //221 --> 201
        String payDate =  eventParam.getString("payDate");
        String paySerno = eventParam.getString("paySerno");

        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoRepository.query(payDate,paySerno);
        boolean status221 = (AppConstant.TRXSTATUS_ABEND.equals(payTransDtlInfoDO.getTrxStatus())
                && AppConstant.CORESTATUS_ABEND.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

        if(status221){
            StateMachine stateMachine = new StateMachine();
            stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
            stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
            stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);

            bankCoreProcessService.qryCoreStsRetDone(payTransDtlInfoDO,bankCore996666Rsp,stateMachine);
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
