package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.common.pay.channel.event.msg.EventDealRspMsg;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.pay.api.model.PayNotifyDO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IPayNotifyService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoRepository;
import com.dcits.dcwlt.pay.online.service.ICoreQryCallBackService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liuyong
 * @Time 2021/01/08 08:52
 * @Version 1.0
 * Description:终态通知差错贷记调账核心回查回调
 */

public class BankCoreQryCallBackServiceImpl implements ICoreQryCallBackService {

    @Autowired
    private ICoreProcessService bankCoreProcessService;

    @Autowired
    private IPayTransDtlInfoRepository payTransDtlInfoRepository;

    @Autowired
    private BankCoreAccTxnServiceImpl bankCoreAccTxnServiceImpl;

    @Autowired
    private IPayNotifyService payTxEndMsgRepository;

    private static final Logger logger = LoggerFactory.getLogger(BankCoreQryCallBackServiceImpl.class);

   // private static final ICoreProcessService bankCoreProcessService = RtpUtil.getInstance().getBean("bankCoreProcessService");

    //private static final IPayTransDtlInfoRepository payTransDtlInfoRepository = RtpUtil.getInstance().getBean("payTransDtlInfoRepository") ;

    //private static final BankCoreAccTxnService bankCoreAccTxnService = RtpUtil.getInstance().getBean("bankCoreAccTxnService");

    //private static final IPayNotifyRepository payTxEndMsgRepository = RtpUtil.getInstance().getBean("payNotifyRepository");


    @Override
    public EventDealRspMsg coreSucc(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {
        logger.info("交易状态查询-核心回查成功回调");
        String payDate =  eventParam.getString("payDate");
        String paySerno = eventParam.getString("paySerno");
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoRepository.query(payDate,paySerno);
        StateMachine stateMachine = new StateMachine();
        boolean isSucc = true;
        if(null == payTransDtlInfoDO) {
            logger.error("终态通知差错贷记调账回调查询登记簿信息不存在！平台日期：{},平台流水：{}",payDate,paySerno);
            return eventDealRspMsg;
        }
        //查终态通知请求登记表获取当前推定状态是否成功
        PayNotifyDO payNotifyDO =  payTxEndMsgRepository.queryByMsgId(payTransDtlInfoDO.getPayPathSerno());
        if(null != payNotifyDO){
            isSucc= (StringUtils.equalsAny(payNotifyDO.getProcessStatus(), ProcessStsCdEnum.PR00.getCode(), ProcessStsCdEnum.PR03.getCode()));
        }else{
            logger.info("终态通知909请求登记表未查询到报文标识号{}的记录",payTransDtlInfoDO.getPayPathSerno());
            isSucc = StringUtils.equals(payTransDtlInfoDO.getPathProcStatus(),"1");
        }
        if(isSucc){
            //推定状态成功，核心应答成功，登记簿更新为111
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
            payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
            //状态机221
            stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
            stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
            stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
        }else{
            //推定状态失败，核心应答成功，登记簿更新为210
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
            //状态机220
            stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
            stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
            stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        }

        bankCoreProcessService.qryBankCoreStsRetDone(payTransDtlInfoDO,bankCore996666Rsp,new StateMachine());
        return eventDealRspMsg;
    }

    @Override
    public EventDealRspMsg coreFail(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, JSONObject eventParam) {
        logger.info("交易状态查询-核心回查成功回调");
        String payDate =  eventParam.getString("payDate");
        String paySerno = eventParam.getString("paySerno");
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoRepository.query(payDate,paySerno);
        StateMachine stateMachine = new StateMachine();
        boolean isSucc = true;
        if(null == payTransDtlInfoDO) {
            logger.error("终态通知差错贷记调账回调查询登记簿信息不存在！平台日期：{},平台流水：{}",payDate,paySerno);
            return eventDealRspMsg;
        }
        //查终态通知请求登记表获取当前推定状态是否成功
        PayNotifyDO payNotifyDO =  payTxEndMsgRepository.queryByMsgId(payTransDtlInfoDO.getPayPathSerno());
        if(null != payNotifyDO){
            isSucc= (StringUtils.equalsAny(payNotifyDO.getProcessStatus(), ProcessStsCdEnum.PR00.getCode(), ProcessStsCdEnum.PR03.getCode()));
        }else{
            logger.info("终态通知909请求登记表未查询到报文标识号{}的记录",payTransDtlInfoDO.getPayPathSerno());
            isSucc = StringUtils.equals(payTransDtlInfoDO.getPathProcStatus(),"1");
        }
        if(isSucc){
            //推定状态成功，核心应答失败，登记簿更新为201
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_FAILED);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_FAIL);

            //状态机221
            stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
            stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
            stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
        }else{
            //推定状态失败，核心应答失败，登记簿更新为000
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
            payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_FAILED);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_FAIL);
            //状态机220
            stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
            stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
            stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        }

        bankCoreProcessService.qryBankCoreStsRetDone(payTransDtlInfoDO,bankCore996666Rsp,stateMachine);
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
