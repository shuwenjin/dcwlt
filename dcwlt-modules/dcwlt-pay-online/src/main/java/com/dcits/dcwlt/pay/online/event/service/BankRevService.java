package com.dcits.dcwlt.pay.online.event.service;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.utils.SpringUtils;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCoreReqHeader;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore998889.BankCore998889Rsp;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealRspMsg;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.constant.EventConst;
import com.dcits.dcwlt.common.pay.exception.PlatformError;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.api.model.AccFlowDO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.baffle.dcep.impl.BankCoreDubboServiceImpl;
import com.dcits.dcwlt.pay.online.base.Constant;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.service.IEventService;
import com.dcits.dcwlt.pay.online.event.coreqry.IBankRevCallBack;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import com.dcits.dcwlt.pay.online.service.impl.BankCoreAccTxnServiceImpl;
import com.dcits.dcwlt.pay.online.baffle.core.impl.BankCoreImplDubboServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * @author
 * @Time 2020/11/4 17:07
 * @Version 1.0
 * Description:
 */
@Service
public class BankRevService implements IEventService {

    private static final Logger logger = LoggerFactory.getLogger(BankRevService.class);

    @Autowired
    private BankCoreImplDubboServiceImpl bankCoreImplDubboServiceImpl;

    @Autowired
    private BankCoreAccTxnServiceImpl bankCoreAccTxnServiceImpl;


    @Autowired
    private CoreQryService coreQryServiceImpl;

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoRepository;

    @Autowired
    private BankCoreDubboServiceImpl bankCoreDubboServiceImpl;

    @Override
    public EventDealRspMsg runFlow(EventDealReqMsg eventDealReqMsg) {
        // ????????????????????????
        String callBackClassName = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("callBackCanonicalName");
        String payDate = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("payDate");
        String paySerno = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("paySerno");

        // ?????????????????????
        EventDealRspMsg eventDealRspMsg = initEventRspMsg(eventDealReqMsg);

        // ????????????
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoRepository.query(payDate, paySerno);
        // ???????????????????????????????????????????????????
        if (null == payTransDtlInfoDO) {
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
            eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
            eventDealRspMsg.setRespMsg("??????????????????");
            return eventDealRspMsg;
        }

        if (Constant.CORESTATUS_ABEND.equals(payTransDtlInfoDO.getCoreProcStatus())) {
            logger.info("??????????????????,????????????,??????????????????");
            String coreReqDate = payTransDtlInfoDO.getCoreReqDate();
            String coreReqSerno = payTransDtlInfoDO.getCoreReqSerno();
            BankCore996666Rsp bankCore996666Rsp = bankCoreImplDubboServiceImpl.queryCoreStatus(coreReqDate, coreReqSerno);
            String coreProcStatus = bankCoreAccTxnServiceImpl.getCoreStatusMap(bankCore996666Rsp.getTxnSts());
            payTransDtlInfoDO.setCoreProcStatus(coreProcStatus);
            if (Constant.CORESTATUS_SUCCESS.equals(coreProcStatus)) {
                logger.info("??????????????????????????????");
                payTransDtlInfoRepository.update(payTransDtlInfoDO);
                bankCoreAccTxnServiceImpl.updateQryTradeRet(coreReqDate, coreReqSerno, bankCore996666Rsp);
            } else if (Constant.CORESTATUS_ABEND.equals(coreProcStatus)) {
                logger.info("?????????????????????????????????????????????");
                payTransDtlInfoRepository.update(payTransDtlInfoDO);
                bankCoreAccTxnServiceImpl.updateQryTradeRet(coreReqDate, coreReqSerno, bankCore996666Rsp);
                eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_Y);
                eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
                eventDealRspMsg.setRespMsg("?????????????????????????????????");
                return eventDealRspMsg;
            } else if (Constant.CORESTATUS_FAILED.equals(coreProcStatus)) {
                logger.info("?????????????????????????????????");
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
                payTransDtlInfoRepository.update(payTransDtlInfoDO);
                bankCoreAccTxnServiceImpl.updateQryTradeRet(coreReqDate, coreReqSerno, bankCore996666Rsp);
                eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
                eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
                eventDealRspMsg.setRespMsg("?????????????????????????????????");
                return eventDealRspMsg;
            } else {
                logger.info("????????????????????????????????????");
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_REVERSED);
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
                payTransDtlInfoRepository.update(payTransDtlInfoDO);
                bankCoreAccTxnServiceImpl.updateQryTradeRet(coreReqDate, coreReqSerno, bankCore996666Rsp);
                eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
                eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
                eventDealRspMsg.setRespMsg("????????????????????????????????????");
                return eventDealRspMsg;
            }
        } else if (Constant.CORESTATUS_SUCCESS.equals(payTransDtlInfoDO.getCoreProcStatus())) {
            logger.info("????????????????????????");
        } else {
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
            eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
            eventDealRspMsg.setRespMsg("??????????????????????????????????????????");
            return eventDealRspMsg;
        }

        //4??????????????????
        AccFlowDO accFlowDO = bankCoreAccTxnServiceImpl.selectByOrigInfo(payTransDtlInfoDO.getCoreReqDate(), payTransDtlInfoDO.getCoreReqSerno());
        BankCore998889Rsp bankCore998889Rsp = bankRev(eventDealReqMsg, accFlowDO, payTransDtlInfoDO);

        //5???????????????
        packEventRspMsg(eventDealRspMsg, bankCore998889Rsp);

        //6?????????????????????
        eventDealRspMsg = runCallBack(eventDealRspMsg, bankCore998889Rsp, callBackClassName, JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()));
        logger.info("eventDealRspMsg:" + eventDealRspMsg);

        return eventDealRspMsg;
    }

    /**
     * ????????????
     *
     * @param eventDealReqMsg
     * @param accFlowDO
     * @return
     */
    public BankCore998889Rsp bankRev(EventDealReqMsg eventDealReqMsg, AccFlowDO accFlowDO, PayTransDtlInfoDO payTransDtlInfoDO) {
        //1)?????????????????????????????????
        String coreReqDate = DateUtil.getDefaultDate();
        String coreReqSerno = generateCodeService.generateCoreReqSerno();
        String origCoreReqDate = payTransDtlInfoDO.getCoreReqDate();
        String origCoreReqSerno = payTransDtlInfoDO.getCoreReqSerno();
        String canResn = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("canResn");

        //2????????????????????????
        bankCoreAccTxnServiceImpl.insertReverseCoreFlow(accFlowDO, coreReqDate, coreReqSerno);

        //3??????????????????????????????????????????
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        try {
            payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }

        //4)??????????????????
        BankCoreReqHeader coreHead = bankCoreDubboServiceImpl.buildDefaultBankCoreHeader(Constant.BANKCORE_BANKREV_CODE, Constant.MASTERBANK);
        if(StringUtil.isNotBlank(payTransDtlInfoDO.getOrigChnl())) {
            coreHead.setReqChnl(payTransDtlInfoDO.getOrigChnl());
        }
        if(StringUtil.isNotBlank(payTransDtlInfoDO.getOrigChnl2())) {
            coreHead.setReqChnl2(payTransDtlInfoDO.getOrigChnl2());
        }

        //5?????????????????????
        BankCore998889Rsp bankCore998889Rsp = bankCoreImplDubboServiceImpl.bankRev(coreHead, coreReqSerno, origCoreReqDate, origCoreReqSerno, canResn);

        return bankCore998889Rsp;
    }


    /**
     * ??????????????????
     * @param payTransDtlInfoDO
     * @return
     */
    public BankCore998889Rsp bankRevOnTime(PayTransDtlInfoDO payTransDtlInfoDO) {
        // ??????????????????????????????
        String coreReqDate = DateUtil.getDefaultDate();
        String coreReqSerno = generateCodeService.generateCoreReqSerno();
        String origCoreReqDate = payTransDtlInfoDO.getCoreReqDate();
        String origCoreReqSerno = payTransDtlInfoDO.getCoreReqSerno();

        // ??????????????????
        bankCoreAccTxnServiceImpl.insertReverseCoreFlow(bankCoreAccTxnServiceImpl.selectByOrigInfo(origCoreReqDate, origCoreReqSerno), coreReqDate, coreReqSerno);

        // ????????????????????????????????????
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        try {
            int updateNum =  payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
            if (updateNum != 1) {
                logger.error("???????????????????????????");
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
        } catch (Exception e) {
            logger.error("????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }

        //???????????????
        BankCoreReqHeader coreHead = bankCoreDubboServiceImpl.buildDefaultBankCoreHeader(Constant.BANKCORE_BANKREV_CODE, Constant.MASTERBANK);
        if(StringUtil.isNotBlank(payTransDtlInfoDO.getOrigChnl())) {
            coreHead.setReqChnl(payTransDtlInfoDO.getOrigChnl());
        }
        if(StringUtil.isNotBlank(payTransDtlInfoDO.getOrigChnl2())) {
            coreHead.setReqChnl2(payTransDtlInfoDO.getOrigChnl2());
        }

        // ???????????????
        BankCore998889Rsp bankCore998889Rsp = bankCoreImplDubboServiceImpl.bankRev(coreHead, coreReqSerno, origCoreReqDate, origCoreReqSerno, "????????????");

        // ?????????????????????
        bankCoreAccTxnServiceImpl.updateReverseCoreFlow(bankCore998889Rsp);

        // ???????????????????????????
        String coreProcStatus = getCoreStatusMap(bankCore998889Rsp.getCoreStatus());
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_REVR);
        if (AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus) || "TC1003".equals(bankCore998889Rsp.getErrorCode())) {
            payTransDtlInfoDO.setTrxStatus(AppConstant.CORESTATUS_REVERSED);
            payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_REVERSED);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
        } else {
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);
        }

        // ?????????
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        try {
            // ?????????????????????
            payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }

        return bankCore998889Rsp;
    }


    /**
     * @param eventDealRspMsg
     * @param bankCore998889Rsp
     * @param callBackClassName
     * @param eventParam
     * @return
     * @throws Exception
     */
    private EventDealRspMsg runCallBack(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, String callBackClassName, JSONObject eventParam) {
        try {
            //???????????????????????????
            if (StringUtil.isBlank(callBackClassName)) {
                return eventDealRspMsg;
            }
            //????????????????????????????????????????????????
            String coreProcStatus = getCoreStatusMap(bankCore998889Rsp.getCoreStatus());
            //IBankRevCallBack callBack = (IBankRevCallBack) Class.forName(callBackClassName).newInstance();
            Object bean = SpringUtils.getBean(callBackClassName.substring(callBackClassName.lastIndexOf(".")+1));
            if (Constant.CORESTATUS_SUCCESS.equals(coreProcStatus)) {
                eventDealRspMsg.setRespCode(Constant.CORESTATUS_SUCCESS);
                //return callBack.bankRevSucc(eventDealRspMsg, bankCore998889Rsp, eventParam);
                Method coreSucc = bean.getClass().getDeclaredMethod("bankRevSucc", EventDealRspMsg.class, BankCore998889Rsp.class, JSONObject.class);
                return (EventDealRspMsg)coreSucc.invoke(bean,eventDealRspMsg, bankCore998889Rsp, eventParam);
            } else if ("TC1003".equals(bankCore998889Rsp.getErrorCode())) {
                eventDealRspMsg.setRespCode(Constant.CORESTATUS_SUCCESS);
                //return callBack.bankRevHadSucc(eventDealRspMsg, bankCore998889Rsp, eventParam);
                Method coreSucc = bean.getClass().getDeclaredMethod("bankRevHadSucc", EventDealRspMsg.class, BankCore998889Rsp.class, JSONObject.class);
                return (EventDealRspMsg)coreSucc.invoke(bean,eventDealRspMsg, bankCore998889Rsp, eventParam);
            } else if (Constant.CORESTATUS_ABEND.equals(coreProcStatus)) {
                eventDealRspMsg.setRespCode(Constant.CORESTATUS_ABEND);
                //return callBack.bankRevException(eventDealRspMsg, bankCore998889Rsp, eventParam);
                Method coreSucc = bean.getClass().getDeclaredMethod("bankRevException", EventDealRspMsg.class, BankCore998889Rsp.class, JSONObject.class);
                return (EventDealRspMsg)coreSucc.invoke(bean,eventDealRspMsg, bankCore998889Rsp, eventParam);
            } else if (Constant.CORESTATUS_FAILED.equals(coreProcStatus)) {
                eventDealRspMsg.setRespCode(Constant.CORESTATUS_FAILED);
                //return callBack.bankRevFail(eventDealRspMsg, bankCore998889Rsp, eventParam);
                Method coreSucc = bean.getClass().getDeclaredMethod("bankRevFail", EventDealRspMsg.class, BankCore998889Rsp.class, JSONObject.class);
                return (EventDealRspMsg)coreSucc.invoke(bean,eventDealRspMsg, bankCore998889Rsp, eventParam);
            } else {
                eventDealRspMsg.setRespCode(PlatformError.OTHER_BUSI_ERROR.getErrorCode());
            }
            eventDealRspMsg.setRespMsg(bankCore998889Rsp.getErrorMsg());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (e instanceof Exception) {
                Exception error =  e;
                eventDealRspMsg.setRespCode(error.getLocalizedMessage());
                eventDealRspMsg.setRespMsg(error.getMessage());
            } else {
                eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
                eventDealRspMsg.setRespMsg(e.getMessage());
            }
        }
        return eventDealRspMsg;
    }

    /**
     * ????????????????????????
     *
     * @param status
     * @return
     */
    private String getCoreStatusMap(String status) {
//        0-?????????
//        1-??????
//        2-?????????
//        3-?????????
//        N-???????????????
//        5-???????????? ????????????????????????
//        6-???????????????????????????????????????
        if (status.equals("3")) {
            return Constant.CORESTATUS_REVERSED;
        } else if (StringUtils.equalsAny(status, "1")) {
            return Constant.CORESTATUS_SUCCESS;
        } else if (StringUtils.equalsAny(status, "2", "5", "6")) {
            return Constant.CORESTATUS_FAILED;
        } else {
            return Constant.CORESTATUS_ABEND;
        }
    }

    /**
     * ????????????????????????
     *
     * @param eventDealRspMsg
     * @param bankCore998889Rsp
     * @return
     */
    private EventDealRspMsg packEventRspMsg(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp) {
        if (StringUtils.equalsAny(bankCore998889Rsp.getCoreStatus(), Constant.CORESTATUS_SUCCESS) || "TC1003".equals(bankCore998889Rsp.getErrorCode())) {
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
            eventDealRspMsg.setRespCode(PlatformError.SUCCESS.getErrorCode());
            eventDealRspMsg.setRespMsg(PlatformError.SUCCESS.getErrorMsg());
        } else {
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_Y);
            eventDealRspMsg.setRespCode(bankCore998889Rsp.getErrorCode());
            eventDealRspMsg.setRespMsg(bankCore998889Rsp.getErrorMsg());
        }
        return eventDealRspMsg;
    }

    /**
     * ???????????????
     *
     * @param eventDealReqMsg
     * @param bankCore998889Rsp
     */
//    @Transactional(rollbackFor = Exception.class)
//    public void dealBankRevRet(EventDealReqMsg eventDealReqMsg, AccFlowDO accFlowDO, BankCore998889Rsp bankCore998889Rsp) {
//        JSONObject eventJson = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext());
//        String trxSqlId = eventJson.getString("trxSqlId");
//
//        //?????????????????????
//        bankCoreAccTxnService.updateReverseCoreFlow(bankCore998889Rsp);
//
//        //SQL_ID???????????????????????????????????????????????????????????????????????????????????????
//        if ((!Constant.CORESTATUS_SUCCESS.equals(bankCore998889Rsp.getCoreStatus()) && !"TC1003".equals(bankCore998889Rsp.getErrorCode())) || StringUtil.isBlank(trxSqlId)) {
//            return;
//        }
//        //??????????????????
//        Map<String, String> updMap = new HashMap<>();
//        updMap.put("coreRetCode", bankCore998889Rsp.getErrorCode());
//        updMap.put("coreRetMsg", bankCore998889Rsp.getErrorMsg());
//        updMap.put("coreAcctDate", bankCore998889Rsp.getRspCoreDate());
//        updMap.put("coreSerno", bankCore998889Rsp.getRspCoreSerno());
//        updMap.put("coreProcStatus", Constant.CORESTATUS_REVERSED);
//        updMap.put("lastUpDate", DateUtil.getDefaultDate());
//        updMap.put("lastUpTime", DateUtil.getDefaultTime());
//        updMap.put("trxStatus", Constant.CORESTATUS_REVERSED);
//        //??????????????????
//        updMap.put("payDate", accFlowDO.getPayDate());
//        updMap.put("paySerno", accFlowDO.getPaySerno());
//        DBUtil.update(trxSqlId, updMap);
//    }

    /**
     * ?????????????????????????????????
     *
     * @param eventDealReqMsg
     * @return
     */
    private EventDealRspMsg initEventRspMsg(EventDealReqMsg eventDealReqMsg) {
        EventDealRspMsg eventDealRspMsg = new EventDealRspMsg();
        eventDealRspMsg.setExceptEventCode(eventDealReqMsg.getExceptEventCode());
        eventDealRspMsg.setExceptEventSeqNo(eventDealReqMsg.getExceptEventSeqNo());
        return eventDealRspMsg;
    }



    @Transactional(rollbackFor = Exception.class)

    public EventDealRspMsg bankRevProcess(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, JSONObject eventParam, String trxStatus, String operStatus) {

        // ?????????????????????
       // BankCoreAccTxnService bankCoreAccTxnService = RtpUtil.getInstance().getBean("bankCoreAccTxnService");
        bankCoreAccTxnServiceImpl.updateReverseCoreFlow(bankCore998889Rsp);
        // ???????????????????????????
      //  PayTransDtlInfoRepository payTransDtlInfoRepository = RtpUtil.getInstance().getBean("payTransDtlInfoRepository");
        PayTransDtlInfoDO payTransDtlInfoDO = new PayTransDtlInfoDO();
        payTransDtlInfoDO.setPayDate(eventParam.getString("payDate"));
        payTransDtlInfoDO.setPaySerno(eventParam.getString("paySerno"));
        payTransDtlInfoDO.setCoreRetCode(bankCore998889Rsp.getErrorCode());
        payTransDtlInfoDO.setCoreRetMsg(bankCore998889Rsp.getErrorMsg());
        payTransDtlInfoDO.setCoreAcctDate(bankCore998889Rsp.getRspCoreDate());
        payTransDtlInfoDO.setCoreSerno(bankCore998889Rsp.getRspCoreSerno());
        if (AppConstant.TRXSTATUS_REVERSED.equals(trxStatus)) {
            payTransDtlInfoDO.setTrxStatus(trxStatus);
            payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_REVERSED);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        } else {
            payTransDtlInfoDO.setTrxStatus(trxStatus);
            payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        }
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_REVR);
        payTransDtlInfoDO.setOperStatus(operStatus);

        // ?????????
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);

        try {
            // ?????????????????????
            payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_Y);
            logger.error("????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
        return eventDealRspMsg;
    }
}
