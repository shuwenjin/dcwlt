package com.dcits.dcwlt.pay.online.flow.receive;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.enums.*;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.util.AmountUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.RspsnInf;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.DsptInf;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.DsptReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.DsptRspDTO;
import com.dcits.dcwlt.pay.api.model.AccFlowDO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.baffle.dcep.impl.BankCoreImplDubboService;
import com.dcits.dcwlt.pay.online.event.callback.DisputeCoreQryCallBack;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.service.IAuthInfoService;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IPartyService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import com.dcits.dcwlt.pay.online.service.impl.BankCoreAccTxnServiceImpl;
import com.dcits.dcwlt.pay.online.service.impl.CoreEventServiceImpl;
import com.dcits.dcwlt.pay.online.task.ParamConfigCheckTask;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;


/**
 * ????????????????????????????????????
 */
@Configuration
public class DisputeInfo801RTradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(DisputeInfo801RTradeFlow.class);

    public static final String DSPT_TRADE_FLOW = "Dspt801RTradeFlow";

    public static final String DSPT_BIZ_TP = "E100";

    public static final String DSPT_CTGYP_URP_08001 = "08001";

    public static final String DSPT_CTGYP_URP_08002 = "08002";

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoService;

    @Autowired
    private IPartyService partyService;

    @Autowired
    private IAuthInfoService authInfoService;

    @Autowired
    private ICoreProcessService bankCoreProcessService;

    @Autowired
    private BankCoreAccTxnServiceImpl bankCoreAccTxnService;

    @Autowired
    private BankCoreImplDubboService bankCoreImplDubboService;



    @Autowired
    private CoreEventServiceImpl coreEventService;


    private static final String BUSINESS_TYPE = "BIZTP";

    @Bean(name = DSPT_TRADE_FLOW)
    public TradeFlow dsptTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .initRspMsg(this::initRspMsg)                // ?????????????????????
                .initTxn(this::initTxn)                      // ????????????????????????
                .checkMsg(this::checkMsg)                    // ????????????
                .process(this::coreProcess)                  // ?????????
                .response(this::packRspMsg)                  // ????????????
                .errHandler(this::convertTradeErrHandler)
                .build();
    }

    /**
     * ?????????????????????
     */
    public void initRspMsg(TradeContext<?, ?> context) {
        // ??????????????????
        DCEPReqDTO<DsptReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);

        //???????????????
        GrpHdr grpHdr = GrpHdr.getInstance(reqMsg.getBody().getDsptReq().getGrpHdr());

        //?????????????????????
        OrgnlGrpHdr orgnlGrpHdr = OrgnlGrpHdr.getInstance(reqMsg.getBody().getDsptReq().getGrpHdr(), reqMsg);

        //????????????
        RspsnInf rspsnInf = new RspsnInf();
        rspsnInf.setBatchId(reqMsg.getBody().getDsptReq().getDsptInf().getBatchId());
        rspsnInf.setRspsnSts(ProcessStsCdEnum.PR02.getCode());

        DsptRspDTO rspDTO = DsptRspDTO.getInstance(grpHdr, orgnlGrpHdr, rspsnInf);

        //??????????????????
        DCEPRspDTO<DsptRspDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP802.getCode(), rspDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
    }

    /**
     * ?????????????????????
     */
    public void initTxn(TradeContext<?, ?> tradeContext) {
        logger.info("initTxn????????????????????????????????? ");
        // ????????????
        DCEPReqDTO<DsptReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        // ?????????
        GrpHdr grpHdr = reqMsg.getBody().getDsptReq().getGrpHdr();
        // ???????????????
        OrgnlGrpHdr orgnlGrpHdr = reqMsg.getBody().getDsptReq().getOrgnlGrpHdr();
        // ????????????
        DsptInf dsptInf = reqMsg.getBody().getDsptReq().getDsptInf();

        PayTransDtlInfoDO payTransDtlInfoDO = new PayTransDtlInfoDO();

        String generateFlowNo = generateCodeService.generatePlatformFlowNo();
        payTransDtlInfoDO.setPayDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setPaySerno(generateFlowNo);
        payTransDtlInfoDO.setPayTime(DateUtil.getDefaultTime());
        payTransDtlInfoDO.setDirect(AppConstant.DIRECT_RECV);
        payTransDtlInfoDO.setPayFlag(AppConstant.IDENTIFICATION_PAYEE);
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_CRDT);
        payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
        payTransDtlInfoDO.setSummary(SummaryCdEnum.XSJ.getCode());

        // ????????????2
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
        // ????????????9
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
        // ????????????7
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);

        // ???????????????????????????
        payTransDtlInfoDO.setPayPathSerno(grpHdr.getMsgId());
        payTransDtlInfoDO.setPayPathRetSerno(grpHdr.getMsgId());
        payTransDtlInfoDO.setPayPathDateTime(grpHdr.getCreDtTm());
        payTransDtlInfoDO.setInstgPty(grpHdr.getInstgPty().getInstgDrctPty());
        payTransDtlInfoDO.setInstdPty(grpHdr.getInstdPty().getInstdDrctPty());
        payTransDtlInfoDO.setRemark(grpHdr.getRmk());
        payTransDtlInfoDO.setMsgType(MsgTpEnum.DCEP801.getCode());

        // ?????????????????????
        // ??????????????????
        payTransDtlInfoDO.setOrigPayPathSerno(orgnlGrpHdr.getOrgnlMsgId());
        // ???????????????
        payTransDtlInfoDO.setOrigMsgType(orgnlGrpHdr.getOrgnlMT());

        // ??????????????????????????????
        // ??????????????????
        payTransDtlInfoDO.setBusiType(dsptInf.getDsptBizTp());
        // ??????????????????
        payTransDtlInfoDO.setBusiKind(dsptInf.getDsptCtgyPurpCd());
        // ??????????????? + ??????????????????
        payTransDtlInfoDO.setNarraTive(dsptInf.getDsptRsnCd() + "_" + dsptInf.getDsptRsnDesc());
        // ????????????
        payTransDtlInfoDO.setAmount(AmountUtil.yuanToFen(dsptInf.getDsptAmt().getValue()));
        // ??????
        payTransDtlInfoDO.setCcy(dsptInf.getDsptAmt().getCcy());
        // ???????????????
        payTransDtlInfoDO.setBatchId(dsptInf.getBatchId());

        payTransDtlInfoDO.setZoneNo(Constant.MASTERBANK);
        payTransDtlInfoDO.setBrno(Constant.MASTERBANK);
        payTransDtlInfoDO.setAcctBrno(Constant.MASTERBANK);
        payTransDtlInfoDO.setLastUpJrnno(generateFlowNo);
        payTransDtlInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setLastUpTime(DateUtil.getDefaultTime());

        try {
            payTransDtlInfoService.insert(payTransDtlInfoDO);
        } catch (Exception e) {
            logger.error("???????????????????????????:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.UPDATE_INFO_ERROR);
        }
        //?????????context???
        EcnyTradeContext.getTempContext(tradeContext).put("PAY_TRANS_DTL", payTransDtlInfoDO);
        logger.info("initTxn????????????????????????????????? ");
    }

    /**
     * ????????????
     */
    public void checkMsg(TradeContext<?, ?> tradeContext) {
        // ????????????
        DCEPReqDTO<DsptReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        DsptReqDTO reqMsgBody = reqMsg.getBody();

        // ????????????
        checkInstgPty(reqMsgBody);

        String orgnlMsgId = reqMsg.getBody().getDsptReq().getOrgnlGrpHdr().getOrgnlMsgId();
        // ??????????????????????????????
        PayTransDtlInfoDO payTransDtlInfo_orig = payTransDtlInfoService.query(orgnlMsgId);

        if (null == payTransDtlInfo_orig) {
            logger.error("??????????????????");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.OLD_PAY_INFO_UNFOUND);
        }

        PayTransDtlInfoDO payTransDtlInfo_dspt = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("PAY_TRANS_DTL");

        // ??????????????????????????????????????????
        // ?????????????????????
        payTransDtlInfo_dspt.setPayerPtyId(payTransDtlInfo_orig.getPayeePtyId());
        payTransDtlInfo_dspt.setPayerName(payTransDtlInfo_orig.getPayeeName());
        payTransDtlInfo_dspt.setPayerAcctType(payTransDtlInfo_orig.getPayeeAcctType());
        payTransDtlInfo_dspt.setPayerAcct(payTransDtlInfo_orig.getPayeeAcct());
        payTransDtlInfo_dspt.setPayerWalletId(payTransDtlInfo_orig.getPayeeWalletId());
        payTransDtlInfo_dspt.setPayerWalletName(payTransDtlInfo_orig.getPayeeWalletName());
        payTransDtlInfo_dspt.setPayerWalletLv(payTransDtlInfo_orig.getPayeeWalletLv());
        payTransDtlInfo_dspt.setPayerWalletType(payTransDtlInfo_orig.getPayeeWalletType());

        // ?????????????????????
        payTransDtlInfo_dspt.setPayeePtyId(payTransDtlInfo_orig.getPayerPtyId());
        payTransDtlInfo_dspt.setPayeeName(payTransDtlInfo_orig.getPayerName());
        payTransDtlInfo_dspt.setPayeeAcctType(payTransDtlInfo_orig.getPayerAcctType());
        payTransDtlInfo_dspt.setPayeeAcct(payTransDtlInfo_orig.getPayerAcct());
        payTransDtlInfo_dspt.setPayeeWalletId(payTransDtlInfo_orig.getPayerWalletId());
        payTransDtlInfo_dspt.setPayeeWalletName(payTransDtlInfo_orig.getPayerWalletName());
        payTransDtlInfo_dspt.setPayeeWalletLv(payTransDtlInfo_orig.getPayerWalletLv());
        payTransDtlInfo_dspt.setPayeeWalletType(payTransDtlInfo_orig.getPayerAcctType());

        payTransDtlInfo_dspt.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlInfo_dspt.setLastUpTime(DateUtil.getDefaultTime());

        try {
            payTransDtlInfoService.update(payTransDtlInfo_dspt);
        } catch (Exception e) {
            logger.error("???????????????????????????:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }

        //?????????context???
        EcnyTradeContext.getTempContext(tradeContext).put("PAY_TRANS_DTL", payTransDtlInfo_dspt);
    }

    /**
     * ????????????
     */
    private void checkInstgPty(DsptReqDTO reqMsgBody) {
        logger.info("????????????????????????");
        GrpHdr grpHdr = reqMsgBody.getDsptReq().getGrpHdr();
        // ????????????
        String instgPty = grpHdr.getInstgPty().getInstgDrctPty();
        // ????????????
        String instdPty = grpHdr.getInstdPty().getInstdDrctPty();

        // ????????????
        String dsptBizTp = reqMsgBody.getDsptReq().getDsptInf().getDsptBizTp();

        String dsptCtgyPurpCd = reqMsgBody.getDsptReq().getDsptInf().getDsptCtgyPurpCd();

        //?????????????????????????????????
        if (!ParamConfigCheckTask.checkConfigValue(BUSINESS_TYPE, dsptBizTp, dsptCtgyPurpCd)
                || !AppConstant.BUSINESS_TYPE_DSPT.equals(dsptBizTp)) {
            logger.error("??????????????????????????????????????????");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.ECNY_BUSINESS_TYPE_INVALID);
        }

        //????????????????????????
        boolean sendPartyFlag = partyService.isAvailable(instgPty);
        if (!sendPartyFlag) {
            logger.info("????????????????????????,{}", instgPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTG_STATUS_UNSUPPORT);
        }

        //????????????????????????
        boolean recvPartyFlag = partyService.isAvailable(instdPty);
        if (!recvPartyFlag) {
            logger.info("????????????????????????,{}", instdPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTD_STATUS_UNSUPPORT);
        }

        // ????????????????????????,????????????801????????????
        Boolean sendAuth = authInfoService.validateAuthInfo(instgPty, MsgTpEnum.DCEP801.getCode(), DSPT_BIZ_TP, AuthInfoDrctEnum.sendAuth);
        if (!sendAuth) {
            logger.error("????????????????????????,{}", instgPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.ORGAN_POWER_ERROR);
        }

        // ???????????????????????????????????????801????????????
        Boolean recvAuth = authInfoService.validateAuthInfo(instdPty, MsgTpEnum.DCEP801.getCode(), DSPT_BIZ_TP, AuthInfoDrctEnum.recvAuth);
        if (!recvAuth) {
            logger.error("????????????????????????,{}", instdPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.ORGAN_POWER_ERROR);
        }

        logger.info("????????????????????????");
    }

    /**
     * ????????????
     */
    private void coreProcess(TradeContext<?, ?> context) {
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(context).get("PAY_TRANS_DTL");

        // ???????????????????????????
        BankCore351100InnerReq bankCore351100InnerReq = sendCoreInit(payTransDtlInfoDO);

        // ???????????????
        sendCorePre(payTransDtlInfoDO, bankCore351100InnerReq);

        // ????????????
        BankCore351100InnerRsp bankCore351100InnerRsp = sendToCore(bankCore351100InnerReq, payTransDtlInfoDO);

        // ???????????????
        sendCoreDone(payTransDtlInfoDO, bankCore351100InnerRsp, bankCore351100InnerReq);

        EcnyTradeContext.getTempContext(context).put("MAP_KEY_351100_INNER_RSP", bankCore351100InnerRsp);
    }

    /**
     * ??????????????????
     * ????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param payTransDtlInfoDO
     */
    private BankCore351100InnerReq sendCoreInit(PayTransDtlInfoDO payTransDtlInfoDO) {
        //??????????????????
        logger.info("sendCoreInit??????????????????????????? ");
        BankCore351100InnerReq bankCore351100InnerReq = bankCoreProcessService.initBankCore351100InnerReq(payTransDtlInfoDO);
        bankCore351100InnerReq.setAcctBrno("1");
        logger.info("sendCoreInit??????????????????????????? ");
        return bankCore351100InnerReq;
    }

    /**
     * ???????????????
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendCorePre(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerReq bankCore351100InnerReq) {
        logger.info("sendCorePre: ???????????????????????????????????????????????????????????????????????????{}??????????????????{} ",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
        String coreReqDate = generateCodeService.getCoreReqDate(payTransDtlInfoDO.getBatchId());
        String coreReqSerno = generateCodeService.generateCoreReqSerno();

        bankCore351100InnerReq.setCoreReqDate(coreReqDate);
        bankCore351100InnerReq.setCoreReqSerno(coreReqSerno);

        // ????????????????????????
        payTransDtlInfoDO.setCoreReqSerno(coreReqSerno);
        payTransDtlInfoDO.setCoreReqDate(coreReqDate);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_CRDT);
        payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);

        // 297 - 227
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_INIT);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);

        try {
            // ?????????????????????
            bankCoreAccTxnService.insertCoreFlow(bankCore351100InnerReq, coreReqSerno, coreReqDate);
            // ?????????????????????
            int retCount = payTransDtlInfoService.update(payTransDtlInfoDO, stateMachine);
            if (retCount != 1) {
                logger.info("???????????????????????????");
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
            }
        } catch (Exception e) {
            // ???????????????????????????9
            payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
            logger.error("????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ???351100???????????????
     */
    public BankCore351100InnerRsp sendToCore(BankCore351100InnerReq bankCore351100InnerReq, PayTransDtlInfoDO payTransDtlInfoDO) {
        logger.info("???????????????????????????????????????{}????????????????????????{}",
                bankCore351100InnerReq.getCoreReqDate(), bankCore351100InnerReq.getCoreReqSerno());
        BankCore351100InnerRsp bankCore351100InnerRsp = new BankCore351100InnerRsp();
        try {
            // TODO ??????????????????
            bankCore351100InnerRsp = bankCoreImplDubboService.coreServer(bankCore351100InnerReq);
        } catch (Exception e) {
            logger.error("?????????????????????{}-{}-{}", LogMonitorLevelCdEnum.ECNY_LOG_MONITOR_NORMAL.getCode(), e.getMessage(), e);
            logger.info("??????????????????,???????????????{},???????????????{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
            AccFlowDO accFlowDO = bankCoreAccTxnService.selectByPayInfo(payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
            logger.info("??????????????????,???????????????{},???????????????{}", accFlowDO.getCoreReqDate(), accFlowDO.getCoreReqSerno());
            coreEventService.registerCoreQry(accFlowDO.getCoreReqDate(), accFlowDO.getCoreReqSerno(), payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), DisputeCoreQryCallBack.class);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.PAY_TIME_OUT);
        }
        logger.info("sendToCore????????????????????????");
        return bankCore351100InnerRsp;

    }

    /**
     * ???????????????
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendCoreDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp, BankCore351100InnerReq bankCore351100InnerReq) {
        logger.info("??????????????????????????????{}?????????????????????{}????????????????????????{}",
                bankCore351100InnerRsp.getCoreStatus(), bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        // ??????????????????
        setTradeResult(payTransDtlInfoDO, bankCore351100InnerRsp);

        try {
            bankCore351100InnerRsp.setCoreReqDate(bankCore351100InnerReq.getCoreReqDate());
            bankCore351100InnerRsp.setCoreReqSerno(bankCore351100InnerReq.getCoreReqSerno());
            // ?????????????????????
            int retCount = bankCoreAccTxnService.updateCoreAccFlow(bankCore351100InnerRsp);
            if (retCount != 1) {
                logger.info("???????????????????????????");
                throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
            }
            // ?????????????????????
            retCount = payTransDtlInfoService.update(payTransDtlInfoDO, stateMachine);
            if (retCount != 1) {
                logger.info("???????????????????????????");
                throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
            }
        } catch (Exception e) {
            logger.error("????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ??????????????????
     */
    private void setTradeResult(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {
        logger.info("??????????????????????????????:{}, ????????????:{}", bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
        String coreStatus = bankCore351100InnerRsp.getCoreStatus();

        if (StringUtils.isBlank(bankCore351100InnerRsp.getCoreReqDate())) {
            bankCore351100InnerRsp.setCoreReqDate(payTransDtlInfoDO.getCoreReqDate());
        }
        if (StringUtils.isBlank(bankCore351100InnerRsp.getCoreReqSerno())) {
            bankCore351100InnerRsp.setCoreReqSerno(payTransDtlInfoDO.getCoreReqSerno());
        }

        payTransDtlInfoDO.setCoreProcStatus(coreStatus);
        payTransDtlInfoDO.setCoreAcctDate(bankCore351100InnerRsp.getCoreRspDate());
        payTransDtlInfoDO.setCoreSerno(bankCore351100InnerRsp.getCoreRspSerno());
        payTransDtlInfoDO.setCoreRetCode(bankCore351100InnerRsp.getErrorCode());
        payTransDtlInfoDO.setCoreRetMsg(bankCore351100InnerRsp.getErrorMsg());
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_CRDT);
        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(Constant.CORE_SYS_ID, AppConstant.DCEP_SYS_ID, bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());

        if (AppConstant.CORESTATUS_SUCCESS.equals(coreStatus)) {
            logger.info("?????????????????????");
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
            payTransDtlInfoDO.setTrxRetCode(EcnyTransError.SUCCESS.getErrorCode());
            payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
            payTransDtlInfoDO.setPayPathRspStatus(ProcessStsCdEnum.PR00.getCode());
            payTransDtlInfoDO.setPayPathRetCode(RejectCdEnum.SUCCESS.getCode());
            payTransDtlInfoDO.setPayPathRetMsg(RejectCdEnum.SUCCESS.getDesc());
        } else if (AppConstant.CORESTATUS_FAILED.equals(coreStatus)) {
            logger.info("?????????????????????");
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_FAIL);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
            payTransDtlInfoDO.setTrxRetCode(bankCore351100InnerRsp.getErrorCode());
            payTransDtlInfoDO.setTrxRetMsg(bankCore351100InnerRsp.getErrorMsg());
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            payTransDtlInfoDO.setPayPathRspStatus(ProcessStsCdEnum.PR01.getCode());
            payTransDtlInfoDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
            payTransDtlInfoDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
        } else {
            logger.info("????????????");
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
            payTransDtlInfoDO.setTrxRetCode(bankCore351100InnerRsp.getErrorCode());
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
            payTransDtlInfoDO.setPayPathRspStatus(ProcessStsCdEnum.PR02.getCode());
            payTransDtlInfoDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
            payTransDtlInfoDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
        }
    }

    /**
     * ??????????????????
     *
     * @param context
     */
    private void packRspMsg(TradeContext<?, ?> context) {
        DCEPRspDTO<DsptRspDTO> dcepRspDTO = EcnyTradeContext.getRspMsg(context);
        RspsnInf rspsnInf = dcepRspDTO.getBody().getDsptRsp().getRspsnInf();
        BankCore351100InnerRsp bankCore351100InnerRsp = (BankCore351100InnerRsp) EcnyTradeContext.getTempContext(context).get("MAP_KEY_351100_INNER_RSP");
        String coreStatus = bankCore351100InnerRsp.getCoreStatus();
        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(Constant.CORE_SYS_ID, AppConstant.DCEP_SYS_ID, bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
        switch (coreStatus) {
            case AppConstant.CORESTATUS_SUCCESS:
                logger.info("?????????????????????,????????????");
                rspsnInf.setRspsnSts(ProcessStsCdEnum.PR00.getCode());
                break;
            case AppConstant.CORESTATUS_FAILED:
                logger.info("????????????????????????????????????");
                rspsnInf.setRspsnSts(ProcessStsCdEnum.PR01.getCode());
                rspsnInf.setRjctCd(rspCodeMapDO.getDestRspCode());
                rspsnInf.setRjctInf(rspCodeMapDO.getRspCodeDsp());
                break;
            default:
                logger.error("???????????????????????????");
                rspsnInf.setRspsnSts(ProcessStsCdEnum.PR02.getCode());
                rspsnInf.setRjctCd(rspCodeMapDO.getDestRspCode());
                rspsnInf.setRjctInf(rspCodeMapDO.getRspCodeDsp());
                break;
        }
        logger.info("????????????801?????????????????????????????????:{}", ProcessStsCdEnum.PR00.getCode());
    }

    /**
     * ????????????
     */
    public void convertTradeErrHandler(TradeContext<?, ?> tradeContext, Throwable exception) {
        logger.error("?????????????????????????????????");
        // ??????????????????
        DCEPRspDTO<DsptRspDTO> dcepRspDTO = EcnyTradeContext.getRspMsg(tradeContext);
        RspsnInf rspsnInf = dcepRspDTO.getBody().getDsptRsp().getRspsnInf();
        // ???????????????
        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(exception);
        rspsnInf.setRjctCd(rspCodeMapDO.getDestRspCode());
        rspsnInf.setRjctInf(rspCodeMapDO.getRspCodeDsp());

        // ?????????????????????
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("PAY_TRANS_DTL");

        if (null == payTransDtlInfoDO) {
            // ????????????????????????
            rspsnInf.setRspsnSts(ProcessStsCdEnum.PR01.getCode());
            logger.info("802?????????????????????????????????:{},?????????:{},????????????:{}", rspsnInf.getRspsnSts(), rspsnInf.getRjctCd(), rspsnInf.getRjctInf());
            return;
        }

        String status = AppConstant.TRXSTATUS_ABEND;
        String code = EcnyTransError.OTHER_TECH_ERROR.getErrorCode();
        String msg = EcnyTransError.OTHER_TECH_ERROR.getErrorMsg();
        if (exception instanceof EcnyTransException) {
            code = ((EcnyTransException) exception).getErrorCode();
            msg = ((EcnyTransException) exception).getErrorMsg();
            status = ((EcnyTransException) exception).getStatus();
        }

        // ?????????????????????
        if (AppConstant.TRXSTATUS_FAILED.equals(status)) {
            rspsnInf.setRspsnSts(ProcessStsCdEnum.PR01.getCode());
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        }
        // ?????????????????????
        if (AppConstant.TRXSTATUS_ABEND.equals(status)) {
            rspsnInf.setRspsnSts(ProcessStsCdEnum.PR02.getCode());
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        }
        // ???????????????
        payTransDtlInfoDO.setTrxStatus(status);
        payTransDtlInfoDO.setTrxRetCode(code);
        payTransDtlInfoDO.setTrxRetMsg(msg);

        if (StringUtil.isEmpty(payTransDtlInfoDO.getPayPathRetMsg())) {
            payTransDtlInfoDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
        }
        if (StringUtil.isEmpty(payTransDtlInfoDO.getPayPathRetCode())) {
            payTransDtlInfoDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
        }
        payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setPayPathRspStatus(rspsnInf.getRspsnSts());
        payTransDtlInfoService.update(payTransDtlInfoDO);

        rspsnInf.setRjctCd(rspCodeMapDO.getDestRspCode());
        rspsnInf.setRjctInf(rspCodeMapDO.getRspCodeDsp());

        logger.info("802?????????????????????????????????:{},?????????:{},????????????:{}", rspsnInf.getRspsnSts(), rspsnInf.getRjctCd(), rspsnInf.getRjctInf());
    }
}
