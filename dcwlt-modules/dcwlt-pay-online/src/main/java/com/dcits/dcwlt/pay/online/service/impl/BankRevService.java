package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.bankcore998889.BankCore998889Rsp;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.service.IEventService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhanguohai
 * @Time 2020/11/4 17:07
 * @Version 1.0
 * Description:
 */
@Service
public class BankRevService implements IEventService {
    private static final Logger logger = LoggerFactory.getLogger(BankRevService.class);

//    @Autowired
//    private BankCoreImplDubboService bankCoreImplDubboService;

    @Autowired
    private BankCoreAccTxnService bankCoreAccTxnService;

//    @Autowired
//    private SernoService sernoService;
//
//    @Autowired
//    private CoreQryService coreQryService;

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

    @Autowired
    private IPayTransDtlInfoRepository payTransDtlInfoRepository;

//    @Autowired
//    private BankCoreDubboService bankCoreDubboService;

//    @Override
//    @ParamLog
//    public EventDealRspMsg runFlow(EventDealReqMsg eventDealReqMsg) {
//        // 获取异常事件配置
//        String callBackClassName = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("callBackCanonicalName");
//        String payDate = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("payDate");
//        String paySerno = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("paySerno");
//
//        // 初始化返回信息
//        EventDealRspMsg eventDealRspMsg = initEventRspMsg(eventDealReqMsg);
//
//        // 回查交易
//        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoRepository.query(payDate, paySerno);
//        // 查询原交易不存在，直接抛出异常结束
//        if (null == payTransDtlInfoDO) {
//            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
//            eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
//            eventDealRspMsg.setRespMsg("原交易不存在");
//            return eventDealRspMsg;
//        }
//
//        if (Constant.CORESTATUS_ABEND.equals(payTransDtlInfoDO.getCoreProcStatus())) {
//            logger.info("核心状态异常,需回查后,判断状态冲正");
//            String coreReqDate = payTransDtlInfoDO.getCoreReqDate();
//            String coreReqSerno = payTransDtlInfoDO.getCoreReqSerno();
//            BankCore996666Rsp bankCore996666Rsp = bankCoreImplDubboService.queryCoreStatus(coreReqDate, coreReqSerno);
//            String coreProcStatus = bankCoreAccTxnService.getCoreStatusMap(bankCore996666Rsp.getTxnSts());
//            payTransDtlInfoDO.setCoreProcStatus(coreProcStatus);
//            if (Constant.CORESTATUS_SUCCESS.equals(coreProcStatus)) {
//                logger.info("回查核心成功允许冲正");
//                payTransDtlInfoRepository.update(payTransDtlInfoDO);
//                bankCoreAccTxnService.updateQryTradeRet(coreReqDate, coreReqSerno, bankCore996666Rsp);
//            } else if (Constant.CORESTATUS_ABEND.equals(coreProcStatus)) {
//                logger.info("回查核心异常，继续登记异常事件");
//                payTransDtlInfoRepository.update(payTransDtlInfoDO);
//                bankCoreAccTxnService.updateQryTradeRet(coreReqDate, coreReqSerno, bankCore996666Rsp);
//                eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_Y);
//                eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
//                eventDealRspMsg.setRespMsg("回查核心异常，继续回查");
//                return eventDealRspMsg;
//            } else if (Constant.CORESTATUS_FAILED.equals(coreProcStatus)) {
//                logger.info("回查核心失败，不再冲正");
//                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
//                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
//                payTransDtlInfoRepository.update(payTransDtlInfoDO);
//                bankCoreAccTxnService.updateQryTradeRet(coreReqDate, coreReqSerno, bankCore996666Rsp);
//                eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
//                eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
//                eventDealRspMsg.setRespMsg("回查核心失败，不再冲正");
//                return eventDealRspMsg;
//            } else {
//                logger.info("回查核心已冲正，不再冲正");
//                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_REVERSED);
//                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
//                payTransDtlInfoRepository.update(payTransDtlInfoDO);
//                bankCoreAccTxnService.updateQryTradeRet(coreReqDate, coreReqSerno, bankCore996666Rsp);
//                eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
//                eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
//                eventDealRspMsg.setRespMsg("回查核心已冲正，不再冲正");
//                return eventDealRspMsg;
//            }
//        } else if (Constant.CORESTATUS_SUCCESS.equals(payTransDtlInfoDO.getCoreProcStatus())) {
//            logger.info("核心成功允许冲正");
//        } else {
//            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
//            eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
//            eventDealRspMsg.setRespMsg("核心失败或者已冲正，不再冲正");
//            return eventDealRspMsg;
//        }
//
//        //4、上核心冲正
//        AccFlowDO accFlowDO = bankCoreAccTxnService.selectByOrigInfo(payTransDtlInfoDO.getCoreReqDate(), payTransDtlInfoDO.getCoreReqSerno());
//        BankCore998889Rsp bankCore998889Rsp = bankRev(eventDealReqMsg, accFlowDO, payTransDtlInfoDO);
//
//        //5、拼包响应
//        packEventRspMsg(eventDealRspMsg, bankCore998889Rsp);
//
//        //6、执行回调程序
//        eventDealRspMsg = runCallBack(eventDealRspMsg, bankCore998889Rsp, callBackClassName, JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()));
//        logger.info("eventDealRspMsg:" + eventDealRspMsg);
//
//        return eventDealRspMsg;
//    }

    /**
     * 核心冲正
     *
     * @param eventDealReqMsg
     * @param accFlowDO
     * @return
     */
//    public BankCore998889Rsp bankRev(EventDealReqMsg eventDealReqMsg, AccFlowDO accFlowDO, PayTransDtlInfoDO payTransDtlInfoDO) {
//        //1)、获取核心请求日期流水
//        String coreReqDate = DateTimeUtil.getCurrentDateStr();
//        String coreReqSerno = generateCodeService.generateCoreReqSerno();
//        String origCoreReqDate = payTransDtlInfoDO.getCoreReqDate();
//        String origCoreReqSerno = payTransDtlInfoDO.getCoreReqSerno();
//        String canResn = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("canResn");
//
//        //2）、登记账务流水
//        bankCoreAccTxnService.insertReverseCoreFlow(accFlowDO, coreReqDate, coreReqSerno);
//
//        //3）、更新业务表核心状态为异常
//        StateMachine stateMachine = new StateMachine();
//        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
//        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
//        try {
//            payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
//        } catch (Exception e) {
//            logger.error("冲正前处理异常：{}-{}", e.getMessage(), e);
//            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
//        }
//
//        //4)、填充核心头
//        BankCoreReqHeader coreHead = bankCoreDubboService.buildDefaultBankCoreHeader(Constant.BANKCORE_BANKREV_CODE, Constant.MASTERBANK);
//        if(StringUtil.isNotBlank(payTransDtlInfoDO.getOrigChnl())) {
//            coreHead.setReqChnl(payTransDtlInfoDO.getOrigChnl());
//        }
//        if(StringUtil.isNotBlank(payTransDtlInfoDO.getOrigChnl2())) {
//            coreHead.setReqChnl2(payTransDtlInfoDO.getOrigChnl2());
//        }
//
//        //5）、上核心冲正
//        BankCore998889Rsp bankCore998889Rsp = bankCoreImplDubboService.bankRev(coreHead, coreReqSerno, origCoreReqDate, origCoreReqSerno, canResn);
//
//        return bankCore998889Rsp;
//    }


    /**
     * 核心即时冲正
     * @param payTransDtlInfoDO
     * @return
     */
    public BankCore998889Rsp bankRevOnTime(PayTransDtlInfoDO payTransDtlInfoDO) {
        // 获取核心请求日期流水
        String coreReqDate = DateUtil.getDefaultDate();
        String coreReqSerno = generateCodeService.generateCoreReqSerno();
        String origCoreReqDate = payTransDtlInfoDO.getCoreReqDate();
        String origCoreReqSerno = payTransDtlInfoDO.getCoreReqSerno();

        // 登记账务流水
        bankCoreAccTxnService.insertReverseCoreFlow(bankCoreAccTxnService.selectByOrigInfo(origCoreReqDate, origCoreReqSerno), coreReqDate, coreReqSerno);

        // 更新业务表核心状态为异常
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        try {
            int updateNum =  payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
            if (updateNum != 1) {
                //logger.error("更新交易登记簿失败");
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
        } catch (Exception e) {
            //logger.error("冲正前处理异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }

        //填充核心头
//        BankCoreReqHeader coreHead = bankCoreDubboService.buildDefaultBankCoreHeader(Constant.BANKCORE_BANKREV_CODE, Constant.MASTERBANK);
//        if(StringUtil.isNotBlank(payTransDtlInfoDO.getOrigChnl())) {
//            coreHead.setReqChnl(payTransDtlInfoDO.getOrigChnl());
//        }
//        if(StringUtil.isNotBlank(payTransDtlInfoDO.getOrigChnl2())) {
//            coreHead.setReqChnl2(payTransDtlInfoDO.getOrigChnl2());
//        }

//        // 上核心冲正
//        BankCore998889Rsp bankCore998889Rsp = bankCoreImplDubboService.bankRev(coreHead, coreReqSerno, origCoreReqDate, origCoreReqSerno, "交易冲正");
        BankCore998889Rsp bankCore998889Rsp = null;

        // 更新账务流水表
        bankCoreAccTxnService.updateReverseCoreFlow(bankCore998889Rsp);

        // 更新金融信息流水表
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

        // 状态机
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        try {
            // 更新金融交易表
            payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
//            logger.error("冲正后处理异常：{}-{}", e.getMessage(), e);
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
//    private EventDealRspMsg runCallBack(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, String callBackClassName, JSONObject eventParam) {
//        try {
//            //应用不存在回调处理
//            if (StringUtil.isBlank(callBackClassName)) {
//                return eventDealRspMsg;
//            }
//            //存在回调处理，调用应用的冲正回调
//            String coreProcStatus = getCoreStatusMap(bankCore998889Rsp.getCoreStatus());
//            IBankRevCallBack callBack = (IBankRevCallBack) Class.forName(callBackClassName).newInstance();
//            if (Constant.CORESTATUS_SUCCESS.equals(coreProcStatus)) {
//                eventDealRspMsg.setRespCode(Constant.CORESTATUS_SUCCESS);
//                return callBack.bankRevSucc(eventDealRspMsg, bankCore998889Rsp, eventParam);
//            } else if ("TC1003".equals(bankCore998889Rsp.getErrorCode())) {
//                eventDealRspMsg.setRespCode(Constant.CORESTATUS_SUCCESS);
//                return callBack.bankRevHadSucc(eventDealRspMsg, bankCore998889Rsp, eventParam);
//            } else if (Constant.CORESTATUS_ABEND.equals(coreProcStatus)) {
//                eventDealRspMsg.setRespCode(Constant.CORESTATUS_ABEND);
//                return callBack.bankRevException(eventDealRspMsg, bankCore998889Rsp, eventParam);
//            } else if (Constant.CORESTATUS_FAILED.equals(coreProcStatus)) {
//                eventDealRspMsg.setRespCode(Constant.CORESTATUS_FAILED);
//                return callBack.bankRevFail(eventDealRspMsg, bankCore998889Rsp, eventParam);
//            } else {
//                eventDealRspMsg.setRespCode(PlatformError.OTHER_BUSI_ERROR.getErrorCode());
//            }
//            eventDealRspMsg.setRespMsg(bankCore998889Rsp.getErrorMsg());
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            if (e instanceof RtpCoreException) {
//                RtpCoreException error = (RtpCoreException) e;
//                eventDealRspMsg.setRespCode(error.getErrorCode());
//                eventDealRspMsg.setRespMsg(error.getErrorMsg());
//            } else {
//                eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
//                eventDealRspMsg.setRespMsg(e.getMessage());
//            }
//        }
//        return eventDealRspMsg;
//    }

    /**
     * 获取核心状态映射
     *
     * @param status
     * @return
     */
    private String getCoreStatusMap(String status) {
//        0-处理中
//        1-成功
//        2-被拒纳
//        3-已冲正
//        N-记录不存在
//        5-返回授权 （交易没有成功）
//        6-半自动收费（交易没有成功）
        if (status.equals("3")) {
            return com.dcits.dcwlt.pay.online.base.Constant.CORESTATUS_REVERSED;
        } else if (StringUtils.equalsAny(status, "1")) {
            return com.dcits.dcwlt.pay.online.base.Constant.CORESTATUS_SUCCESS;
        } else if (StringUtils.equalsAny(status, "2", "5", "6")) {
            return com.dcits.dcwlt.pay.online.base.Constant.CORESTATUS_FAILED;
        } else {
            return com.dcits.dcwlt.pay.online.base.Constant.CORESTATUS_ABEND;
        }
    }

    /**
     * 异常事件拼包响应
     *
     * @param eventDealRspMsg
     * @param bankCore998889Rsp
     * @return
     */
//    private EventDealRspMsg packEventRspMsg(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp) {
//        if (StringUtil.equalsAny(bankCore998889Rsp.getCoreStatus(), Constant.CORESTATUS_SUCCESS) || "TC1003".equals(bankCore998889Rsp.getErrorCode())) {
//            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
//            eventDealRspMsg.setRespCode(PlatformError.SUCCESS.getErrorCode());
//            eventDealRspMsg.setRespMsg(PlatformError.SUCCESS.getErrorMsg());
//        } else {
//            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_Y);
//            eventDealRspMsg.setRespCode(bankCore998889Rsp.getErrorCode());
//            eventDealRspMsg.setRespMsg(bankCore998889Rsp.getErrorMsg());
//        }
//        return eventDealRspMsg;
//    }

    /**
     * 冲正后处理
     *
     * @param eventDealReqMsg
     * @param bankCore998889Rsp
     */
//    @Transactional(rollbackFor = Exception.class)
//    private void dealBankRevRet(EventDealReqMsg eventDealReqMsg, AccFlowDO accFlowDO, BankCore998889Rsp bankCore998889Rsp) {
//        JSONObject eventJson = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext());
//        String trxSqlId = eventJson.getString("trxSqlId");
//
//        //更新账务流水表
//        bankCoreAccTxnService.updateReverseCoreFlow(bankCore998889Rsp);
//
//        //SQL_ID为空，核心不是冲正成功且核心不是已冲正，则不关联更新登记簿
//        if ((!Constant.CORESTATUS_SUCCESS.equals(bankCore998889Rsp.getCoreStatus()) && !"TC1003".equals(bankCore998889Rsp.getErrorCode())) || StringUtil.isBlank(trxSqlId)) {
//            return;
//        }
//        //设置更新字段
//        Map<String, String> updMap = new HashMap<>();
//        updMap.put("coreRetCode", bankCore998889Rsp.getErrorCode());
//        updMap.put("coreRetMsg", bankCore998889Rsp.getErrorMsg());
//        updMap.put("coreAcctDate", bankCore998889Rsp.getRspCoreDate());
//        updMap.put("coreSerno", bankCore998889Rsp.getRspCoreSerno());
//        updMap.put("coreProcStatus", Constant.CORESTATUS_REVERSED);
//        updMap.put("lastUpDate", DateTimeUtil.getCurrentDateStr());
//        updMap.put("lastUpTime", DateTimeUtil.getCurrentTimeStr());
//        updMap.put("trxStatus", Constant.CORESTATUS_REVERSED);
//        //设置更新条件
//        updMap.put("payDate", accFlowDO.getPayDate());
//        updMap.put("paySerno", accFlowDO.getPaySerno());
//        DBUtil.update(trxSqlId, updMap);
//    }

    /**
     * 初始化异常事件响应信息
     *
     * @param eventDealReqMsg
     * @return
     */
//    private EventDealRspMsg initEventRspMsg(EventDealReqMsg eventDealReqMsg) {
//        EventDealRspMsg eventDealRspMsg = new EventDealRspMsg();
//        eventDealRspMsg.setExceptEventCode(eventDealReqMsg.getExceptEventCode());
//        eventDealRspMsg.setExceptEventSeqNo(eventDealReqMsg.getExceptEventSeqNo());
//        return eventDealRspMsg;
//    }


//    @Transactional(rollbackFor = Exception.class)
//    public EventDealRspMsg bankRevProcess(EventDealRspMsg eventDealRspMsg, BankCore998889Rsp bankCore998889Rsp, JSONObject eventParam, String trxStatus, String operStatus) {
//
//        // 更新账户流水表
//        BankCoreAccTxnService bankCoreAccTxnService = RtpUtil.getInstance().getBean("bankCoreAccTxnService");
//        bankCoreAccTxnService.updateReverseCoreFlow(bankCore998889Rsp);
//        // 更新金融信息流水表
//        PayTransDtlInfoRepository payTransDtlInfoRepository = RtpUtil.getInstance().getBean("payTransDtlInfoRepository");
//        PayTransDtlInfoDO payTransDtlInfoDO = new PayTransDtlInfoDO();
//        payTransDtlInfoDO.setPayDate(eventParam.getString("payDate"));
//        payTransDtlInfoDO.setPaySerno(eventParam.getString("paySerno"));
//        payTransDtlInfoDO.setCoreRetCode(bankCore998889Rsp.getErrorCode());
//        payTransDtlInfoDO.setCoreRetMsg(bankCore998889Rsp.getErrorMsg());
//        payTransDtlInfoDO.setCoreAcctDate(bankCore998889Rsp.getRspCoreDate());
//        payTransDtlInfoDO.setCoreSerno(bankCore998889Rsp.getRspCoreSerno());
//        if (AppConstant.TRXSTATUS_REVERSED.equals(trxStatus)) {
//            payTransDtlInfoDO.setTrxStatus(trxStatus);
//            payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_REVERSED);
//            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
//        } else {
//            payTransDtlInfoDO.setTrxStatus(trxStatus);
//            payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
//            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
//        }
//        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_REVR);
//        payTransDtlInfoDO.setOperStatus(operStatus);
//
//        // 状态机
//        StateMachine stateMachine = new StateMachine();
//        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
//
//        try {
//            // 更新金融交易表
//            payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
//        } catch (Exception e) {
//            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_Y);
//            logger.error("冲正后处理异常：{}-{}", e.getMessage(), e);
//            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
//        }
//        return eventDealRspMsg;
//    }
}
