package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.common.pay.channel.event.msg.EventDealRspMsg;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoRepository;
import com.dcits.dcwlt.pay.online.service.TxEndNtfcntHandleService;
import com.dcits.dcwlt.pay.online.service.ICoreQryCallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 终态通知 220 或 221 回查核心后回调处理
 */
public class TxEndNTCoreQryCallBack implements ICoreQryCallBack {

    @Autowired
    private TxEndNtfcntHandleService handleService;

    @Autowired
    private IPayTransDtlInfoRepository payTransDtlInfoRepository;

    @Autowired
    private ICoreProcessService bankCoreProcessService;

    private static final Logger logger = LoggerFactory.getLogger(ReCreditCoreQryCallBack.class);


    @Override
    public EventDealRspMsg coreSucc(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {

        logger.info("交易兑回终态通知-核心回查-核心状态为成功");
        // 220--> 210 或 221--> 211,调推断
        txEndNTQryCoreDone(bankCore996666Rsp, eventParam);
        return eventDealRspMsg;
    }

    @Override
    public EventDealRspMsg coreFail(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {

        logger.info("交易兑回终态通知-核心回查-核心状态为失败");
        // 220--> 200 或 221--> 201,调推断
        txEndNTQryCoreDone(bankCore996666Rsp, eventParam);
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

    private void txEndNTQryCoreDone(BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {



    //    TxEndNtfcntHandleService handleService = RtpUtil.getInstance().getBean("txEndNtfcntHandleService");
     //   IPayTransDtlInfoRepository payTransDtlInfoRepository = RtpUtil.getInstance().getBean("payTransDtlInfoRepository");
      //  ICoreProcessService bankCoreProcessService = RtpUtil.getInstance().getBean("bankCoreProcessService");

        String payDate =  eventParam.getString("payDate");
        String paySerno = eventParam.getString("paySerno");

        //查询原交易状态
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoRepository.query(payDate,paySerno);
        String trxStatus = payTransDtlInfoDO.getTrxStatus();
        String coreProcStatus = payTransDtlInfoDO.getCoreProcStatus();
        String pathProcStatus = payTransDtlInfoDO.getPathProcStatus();

        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(trxStatus);
        stateMachine.setPreCoreProcStatus(coreProcStatus);
        stateMachine.setPrePathProcStatus(pathProcStatus);

        boolean status221 = (AppConstant.TRXSTATUS_ABEND.equals(trxStatus)
                && AppConstant.CORESTATUS_ABEND.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(pathProcStatus));

        boolean status220 = (AppConstant.TRXSTATUS_ABEND.equals(trxStatus)
                && AppConstant.CORESTATUS_ABEND.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_FAILED.equals(pathProcStatus));

        if(status221 || status220){
            //核心状态修改
            bankCoreProcessService.qryCoreStsRetDone(payTransDtlInfoDO,bankCore996666Rsp,stateMachine);
            //调用兑回推断
            handleService.reconvertInferenceHandle(payDate,paySerno);
        }
    }

}
