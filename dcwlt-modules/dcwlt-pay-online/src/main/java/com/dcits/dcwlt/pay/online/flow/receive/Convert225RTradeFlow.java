package com.dcits.dcwlt.pay.online.flow.receive;


import com.alibaba.csp.sentinel.util.StringUtil;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.*;
import com.dcits.dcwlt.common.pay.sequence.service.IGenerateCodeService;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.util.AmountUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.common.redis.service.RedisService;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.RspsnInf;
import com.dcits.dcwlt.pay.api.domain.dcep.convert.*;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.api.model.SignInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.baffle.dcep.impl.BankCoreImplDubboService;
import com.dcits.dcwlt.pay.online.base.Constant;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.event.callback.ReCreditCallBack;
import com.dcits.dcwlt.pay.online.event.callback.ReCreditCoreQryCallBack;
import com.dcits.dcwlt.pay.online.event.callback.TxEndNTCoreQryCallBack;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.mapper.SignInfoMapper;
import com.dcits.dcwlt.pay.online.service.IAuthInfoService;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import com.dcits.dcwlt.pay.online.service.impl.CoreEventServiceImpl;
import com.dcits.dcwlt.pay.online.service.impl.PartyService;
import com.dcits.dcwlt.pay.online.task.ParamConfigCheckTask;
import com.dcits.dcwlt.pay.online.task.PayCommParamTask;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * ????????????????????????
 */
@Configuration
public class Convert225RTradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(Convert225RTradeFlow.class);

    public static final String CONVER_TRADE_FLOW = "Convert225RTradeFlow";
    private static final String BUSINESS_TYPE = "BIZTP";

    @Autowired
    private SignInfoMapper signInfoMapper;

    @Autowired
    private IPayTransDtlInfoService iPayTransDtlInfo1Service;

    @Autowired
    private BankCoreImplDubboService bankCoreImplDubboService;

    @Autowired
    private PartyService partyService;

    @Autowired
    private IGenerateCodeService generateCodeService;

    @Autowired
    private IAuthInfoService authInfoService;


    @Autowired
    private ICoreProcessService bankCoreProcessService;
    @Autowired
    private CoreEventServiceImpl coreEventServiceImpl;
    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoService;

    @Autowired
    private RedisService redisService;


    @Bean(name = CONVER_TRADE_FLOW)
    public TradeFlow convertTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .initRspMsg(this::initRspMsg)// ?????????????????????
                .initTxn(this::initTxn) //?????????????????????
                .process(this::checkMsg) //????????????
                .process(this::coreProcess)// ?????????
                .response(this::response) //????????????
                .errHandler(this::convertTradeErrHandler)// ????????????
                .build();
    }

    /**
     * ?????????????????????
     *
     * @param tradeContext
     */
    public void initRspMsg(TradeContext<?, ?> tradeContext) {

        DCEPReqDTO<ConvertReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        // ???????????????
        GrpHdr grpHdr = GrpHdr.getInstance(reqMsg.getBody().getConvertReq().getGrpHdr());
        // ???????????????
        OrgnlGrpHdr orgnlGrpHdr = OrgnlGrpHdr.getInstance(reqMsg.getBody().getConvertReq().getGrpHdr(), reqMsg);
        // ??????????????????
        RspsnInf rspsnInf = new RspsnInf();
        rspsnInf.setBatchId(reqMsg.getBody().getConvertReq().getTrxInf().getBatchId());

        ConvertRspDTO convertRsp = new ConvertRspDTO(grpHdr, orgnlGrpHdr, rspsnInf);
        // ???????????????????????????
        DCEPRspDTO<ConvertRspDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP226.getCode(), convertRsp);
        EcnyTradeContext.setRspMsg(tradeContext, dcepRspDTO);

    }

    /**
     * ?????????????????????
     *
     * @param tradeContext
     */
    public void initTxn(TradeContext<?, ?> tradeContext) {

        DCEPReqDTO<ConvertReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        // ??????????????????
        GrpHdr grpHdr = reqMsg.getBody().getConvertReq().getGrpHdr();
        TrxInfConvert trxInf = reqMsg.getBody().getConvertReq().getTrxInf();
        ConvertCdtrInf cdtrInf = reqMsg.getBody().getConvertReq().getCdtrInf();
        ConvertDbtrInf dbtrInf = reqMsg.getBody().getConvertReq().getDbtrInf();

        PayTransDtlInfoDO payTransDtlInfoDO = new PayTransDtlInfoDO();
        payTransDtlInfoDO.setPayDate(DateUtil.getDefaultDate());
        String paySerno = generateCodeService.generatePlatformFlowNo();
        payTransDtlInfoDO.setPaySerno(paySerno);
        payTransDtlInfoDO.setPayTime(DateUtil.getDefaultTime());
        payTransDtlInfoDO.setDirect(AppConstant.DIRECT_RECV);
        payTransDtlInfoDO.setPayFlag(AppConstant.IDENTIFICATION_PAYER);
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_INIT);
        payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
        payTransDtlInfoDO.setPayPathDateTime(grpHdr.getCreDtTm());
        payTransDtlInfoDO.setPayPathSerno(grpHdr.getMsgId());
        payTransDtlInfoDO.setPayPathRetSerno(grpHdr.getMsgId());
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        payTransDtlInfoDO.setBatchId(trxInf.getBatchId());
        payTransDtlInfoDO.setMsgType(MsgTpEnum.DCEP225.getCode());
        payTransDtlInfoDO.setBusiType(trxInf.getTrxBizTp());
        payTransDtlInfoDO.setBusiKind(trxInf.getTrxCtgyPurpCd());
        payTransDtlInfoDO.setInstgPty(grpHdr.getInstgPty().getInstgDrctPty());
        payTransDtlInfoDO.setInstdPty(grpHdr.getInstdPty().getInstdDrctPty());
        payTransDtlInfoDO.setCcy(trxInf.getTrxAmt().getCcy());
        payTransDtlInfoDO.setAmount(AmountUtil.yuanToFen(trxInf.getTrxAmt().getValue()));
        payTransDtlInfoDO.setTradePurpose(trxInf.getTrxPrps());
        payTransDtlInfoDO.setPayerPtyId(dbtrInf.getDbtrPtyId());
        payTransDtlInfoDO.setPayerName(dbtrInf.getDbtrNm());
        payTransDtlInfoDO.setPayerAcctType(dbtrInf.getDbtAcctTp());
        payTransDtlInfoDO.setPayerAcct(dbtrInf.getDbtrAcct());
        payTransDtlInfoDO.setPayeePtyId(cdtrInf.getCdtrPtyId());
        payTransDtlInfoDO.setPayeeName(cdtrInf.getCdtrNm());
        payTransDtlInfoDO.setPayeeWalletId(cdtrInf.getCdtrWltId());
        payTransDtlInfoDO.setPayeeWalletName(cdtrInf.getCdtrWltNm());
        payTransDtlInfoDO.setPayeeWalletLv(cdtrInf.getCdtrWltLvl());
        payTransDtlInfoDO.setPayeeWalletType(cdtrInf.getCdtrWltTp());
        payTransDtlInfoDO.setProtocolNum(dbtrInf.getSgnNo());
        payTransDtlInfoDO.setZoneNo(Constant.MASTERBANK);
        payTransDtlInfoDO.setBrno(Constant.MASTERBANK);
        payTransDtlInfoDO.setAcctBrno(Constant.MASTERBANK);
        payTransDtlInfoDO.setSummary(SummaryCdEnum.XSF.getCode());
        payTransDtlInfoDO.setLastUpJrnno(paySerno);
        payTransDtlInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setLastUpTime(DateUtil.getDefaultTime());
        payTransDtlInfoDO.setRemark(grpHdr.getRmk());
        try {
            iPayTransDtlInfo1Service.insert(payTransDtlInfoDO);
        } catch (Exception e) {
            logger.info("???????????????????????????:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }

        //??????????????????????????????????????????????????????????????????
        StateMachine oldStatus = new StateMachine();
        oldStatus.setPreTrxStatus(payTransDtlInfoDO.getTrxStatus());
        oldStatus.setPreCoreProcStatus(payTransDtlInfoDO.getCoreProcStatus());
        oldStatus.setPrePathProcStatus(payTransDtlInfoDO.getPathProcStatus());
        EcnyTradeContext.getTempContext(tradeContext).put("oldStatus", oldStatus);

        //?????????context???
        EcnyTradeContext.getTempContext(tradeContext).put("payTransDtlInfoDO", payTransDtlInfoDO);

    }

    /**
     * ????????????
     *
     * @param tradeContext
     */
    public void checkMsg(TradeContext<?, ?> tradeContext) {
        DCEPReqDTO<ConvertReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        String instdPty = reqMsg.getBody().getConvertReq().getGrpHdr().getInstdPty().getInstdDrctPty();
        String instgPty = reqMsg.getBody().getConvertReq().getGrpHdr().getInstgPty().getInstgDrctPty();
        String busiType = reqMsg.getBody().getConvertReq().getTrxInf().getTrxBizTp();
        String busiKind = reqMsg.getBody().getConvertReq().getTrxInf().getTrxCtgyPurpCd();
        String walletType = reqMsg.getBody().getConvertReq().getCdtrInf().getCdtrWltTp();
        String walletLevel = reqMsg.getBody().getConvertReq().getCdtrInf().getCdtrWltLvl();
        // ?????????????????????
        if (!WalletTpCdEnum.WT01.getCode().equals(walletType)) {
            logger.info("?????????????????????:{}", walletType);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.WALLET_TYPE_ERROR);
        }
        // ????????????????????????
        if (!StringUtils.equalsAny(walletLevel, WalletLevelCdEnum.WL01.getCode(), WalletLevelCdEnum.WL02.getCode())) {
            logger.info("?????????????????????:{}", walletLevel);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.WALLET_LEVEL_ERROR);
        }
        //?????????????????????????????????
        if (!AppConstant.BUSINESS_TYPE_RECONVERT.equals(busiType) || !ParamConfigCheckTask.checkConfigValue(BUSINESS_TYPE,
                busiType, busiKind)
        ) {
            logger.error("????????????:{},????????????:{}???????????????,", busiType, busiKind);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.BUSINESS_TYPE_INVALID);
        }
        // ????????????????????????
        boolean partyFlag = partyService.isAvailable(instdPty);
        if (!partyFlag) {
            logger.info("????????????????????????,{}", instdPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_STATUS_ERROR);
        }
        // ???????????????????????? ????????????????????????+????????????+????????????+????????????
        Boolean sendAuth = authInfoService.validateAuthInfo(instgPty, MsgTpEnum.DCEP225.getCode(), busiType, AuthInfoDrctEnum.sendAuth);
        if (!sendAuth) {
            logger.error("????????????????????????,{}", instgPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_POWER_ERROR);
        }
        // ????????????
        checkSignInfo(tradeContext);

        String payerAcct = reqMsg.getBody().getConvertReq().getDbtrInf().getDbtrAcct();
        //?????????????????????
        BigDecimal payerAmount = redisService.getCacheObject(payerAcct+AppConstant.PAYER_AMOUNT);;
        //??????????????????
        BigDecimal amount = new BigDecimal(reqMsg.getBody().getConvertReq().getTrxInf().getTrxAmt().getValue());
        //??????????????????
        String perLimitPayerAmount = PayCommParamTask.getPayCommParamVal(com.dcits.dcwlt.common.pay.constant.Constant.PARAM_TYPE_DATA, com.dcits.dcwlt.common.pay.constant.Constant.PER_LIMIT_PAYER_AMOUNT);
        //????????????????????????????????????????????????
        if(StringUtil.isNotEmpty(perLimitPayerAmount)){
            BigDecimal perLimitAmount = new BigDecimal(perLimitPayerAmount);
            //????????????????????????????????????????????????
            if(amount.compareTo(perLimitAmount) > 0){
                logger.error("????????????{}????????????????????????{}", payerAcct.substring(payerAcct.length()-4),perLimitAmount.toString());
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PER_LIMIT_AMOUNT_EXPIRED);
            }
        }

        //??????????????????????????????????????????????????????????????????????????????
        //???????????????
        String dayLimitAmountStr = PayCommParamTask.getPayCommParamVal(com.dcits.dcwlt.common.pay.constant.Constant.PARAM_TYPE_DATA, com.dcits.dcwlt.common.pay.constant.Constant.DAY_LIMIT_PAYER_AMOUNT);
        if(StringUtil.isNotEmpty(dayLimitAmountStr)){
            BigDecimal dayLimitAmount = new BigDecimal(dayLimitAmountStr);
            //??????????????????????????????????????????????????????????????????????????????
            if(null == payerAmount){
                payerAmount = new BigDecimal("0.00");
            }
            if((amount.add(payerAmount)).compareTo(dayLimitAmount) > 0){
                logger.error("????????????{}???????????????????????????{}", payerAcct.substring(payerAcct.length()-4),dayLimitAmount.toString());
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DAY_LIMIT_AMOUNT_EXPIRED);
            }
        }
    }


    /**
     * ??????????????????
     *
     * @param tradeContext
     */
    public void checkSignInfo(TradeContext<?, ?> tradeContext) {
        DCEPReqDTO<ConvertReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        logger.info("??????????????????????????????:{}", reqMsg.getBody().getConvertReq().getDbtrInf().getSgnNo());
        // ????????????
        SignInfoDO signInfoDO = signInfoMapper.selectBySignNo(reqMsg.getBody().getConvertReq().getDbtrInf().getSgnNo());
        // ????????????
        if (null != signInfoDO) {
            // ???????????????????????????
            if (AppConstant.SIGN_STATUS_CLOSE.equals(signInfoDO.getSignStatus())) {
                logger.error("???????????????????????????");
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.SIGN_STATUS_UNVALID);
            }
            // ?????????????????????????????????????????????
            if (!reqMsg.getBody().getConvertReq().getDbtrInf().getDbtrAcct().equals(signInfoDO.getAcctId())) {
                logger.error("???????????????????????????????????????");
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PAYER_INFO_UNEQUAL);
            }
            if (!reqMsg.getBody().getConvertReq().getDbtrInf().getDbtrNm().equals(signInfoDO.getAcctName())) {
                logger.error("???????????????????????????????????????");
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PAYER_INFO_UNEQUAL);
            }

        } else {
            logger.error("?????????????????????");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.SIGN_INFO_UN_FOUND);
        }
    }

    /**
     * ?????????
     *
     * @param tradeContext
     */
    public void coreProcess(TradeContext<?, ?> tradeContext) {
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("payTransDtlInfoDO");
        // ???????????????????????????
        BankCore351100InnerReq bankCore351100InnerReq = sendCoreInit(payTransDtlInfoDO);
        // ???????????????
        sendCorePre(payTransDtlInfoDO, bankCore351100InnerReq);

        //???????????????????????????????????????????????????
        StateMachine oldStatus = new StateMachine();
        oldStatus.setPreTrxStatus(payTransDtlInfoDO.getTrxStatus());
        oldStatus.setPreCoreProcStatus(payTransDtlInfoDO.getCoreProcStatus());
        oldStatus.setPrePathProcStatus(payTransDtlInfoDO.getPathProcStatus());
        EcnyTradeContext.getTempContext(tradeContext).put("oldStatus", oldStatus);

        // ????????????
        BankCore351100InnerRsp bankCore351100InnerRsp = sendToCore(bankCore351100InnerReq);
        // ???????????????
        sendCoreDone(payTransDtlInfoDO, bankCore351100InnerRsp);

        //???????????????????????????????????????????????????
        oldStatus.setPreTrxStatus(payTransDtlInfoDO.getTrxStatus());
        oldStatus.setPreCoreProcStatus(payTransDtlInfoDO.getCoreProcStatus());
        oldStatus.setPrePathProcStatus(payTransDtlInfoDO.getPathProcStatus());
        EcnyTradeContext.getTempContext(tradeContext).put("oldStatus", oldStatus);
    }

    /**
     * ???????????????????????????
     *
     * @param payTransDtlInfoDO
     * @return
     */
    public BankCore351100InnerReq sendCoreInit(PayTransDtlInfoDO payTransDtlInfoDO) {

        BankCore351100InnerReq bankCore351100InnerReq = new BankCore351100InnerReq();
        bankCore351100InnerReq.setPaySerno(payTransDtlInfoDO.getPaySerno());
        bankCore351100InnerReq.setPayDate(payTransDtlInfoDO.getPayDate());
        bankCore351100InnerReq.setPayerAcct(payTransDtlInfoDO.getPayerAcct());
        bankCore351100InnerReq.setPayerName(payTransDtlInfoDO.getPayerName());
        bankCore351100InnerReq.setPayeeAcct(payTransDtlInfoDO.getPayeeWalletId());
        bankCore351100InnerReq.setPayeeName(payTransDtlInfoDO.getPayeeWalletName());
        bankCore351100InnerReq.setPayeeBank(payTransDtlInfoDO.getPayeePtyId());
        bankCore351100InnerReq.setPayPath(com.dcits.dcwlt.pay.online.base.Constant.ECNY_SYS_ID);
        bankCore351100InnerReq.setAcctMeth("DJ010011");
        bankCore351100InnerReq.setServerId(Constant.P_BANKCORE_DEBIT);
        bankCore351100InnerReq.setAmount(payTransDtlInfoDO.getAmount());
        bankCore351100InnerReq.setCurrency(Constant.CURRENCY_CODE_156);
        bankCore351100InnerReq.setBookType(Constant.BANKCORE_DEBIT);
        bankCore351100InnerReq.setCoreSysId(Constant.DEFAULT_BANKSYSID);
        bankCore351100InnerReq.setRevTranFlag(Constant.REVTRANFLAG_POSITIVE);
        bankCore351100InnerReq.setCoreTrxType(Constant.BANKTRXTYPE_DEBIT);
        bankCore351100InnerReq.setAcctBrno(payTransDtlInfoDO.getAcctBrno());
        bankCore351100InnerReq.setBrno(payTransDtlInfoDO.getBrno());
        bankCore351100InnerReq.setClearDate(payTransDtlInfoDO.getPayDate());
        bankCore351100InnerReq.setSummary(payTransDtlInfoDO.getSummary());
        bankCore351100InnerReq.setChkNameFlg1("Y");
        return bankCore351100InnerReq;
    }

    /**
     * ???????????????
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerReq
     */
    public void sendCorePre(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerReq bankCore351100InnerReq) {
        logger.info("???????????????");
        String coreReqDate = generateCodeService.getCoreReqDate(payTransDtlInfoDO.getBatchId());
        String coreReqSerno = generateCodeService.generateCoreReqSerno();
        // ??????????????????
        payTransDtlInfoDO.setCoreReqDate(coreReqDate);
        payTransDtlInfoDO.setCoreReqSerno(coreReqSerno);
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_DRDT);
        payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);
        // ????????????????????????
        bankCore351100InnerReq.setCoreReqDate(coreReqDate);
        bankCore351100InnerReq.setCoreReqSerno(coreReqSerno);

        // ????????????????????????
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_INIT);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        try {
            bankCoreProcessService.sendCorePre(payTransDtlInfoDO, bankCore351100InnerReq, stateMachine);
        } catch (Exception e) {
            logger.error("????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ????????????
     *
     * @param bankCore351100InnerReq
     * @return
     */
    public BankCore351100InnerRsp sendToCore(BankCore351100InnerReq bankCore351100InnerReq) {
        logger.info("????????????????????????????????????,??????????????????:{},??????:{}", bankCore351100InnerReq.getCoreReqDate(), bankCore351100InnerReq.getCoreReqSerno());
        BankCore351100InnerRsp bankCore351100InnerRsp;
        try {
            // todo ?????????????????????
            bankCore351100InnerRsp = bankCoreImplDubboService.coreServer(bankCore351100InnerReq);
        } catch (Exception e) {
            //????????????????????????????????????MQ
            PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(bankCore351100InnerReq.getPayDate(),bankCore351100InnerReq.getPaySerno());
            coreEventServiceImpl.registerReCredit(payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), ReCreditCallBack.class);

            logger.error("?????????????????????{}-{}-{}", LogMonitorLevelCdEnum.ECNY_LOG_MONITOR_NORMAL.getCode(), e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.GATEWAY_REQUEST_ERROR);
        }
        return bankCore351100InnerRsp;
    }

    /**
     * ???????????????
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     */
    public void sendCoreDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {
        logger.info("???????????????");
        // ????????????????????????
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        // ??????????????????
        setTradeResult(payTransDtlInfoDO, bankCore351100InnerRsp);

        try {
            bankCoreProcessService.sendCoreDone(payTransDtlInfoDO,bankCore351100InnerRsp,stateMachine);
        } catch (Exception e) {
            logger.error("????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ??????????????????
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     */
    public void setTradeResult(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {
        logger.info("??????????????????????????????:{}, ????????????:{}", bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
        // ????????????????????????
        String coreProcStatus = bankCore351100InnerRsp.getCoreStatus();
        payTransDtlInfoDO.setCoreProcStatus(bankCore351100InnerRsp.getCoreStatus());
        payTransDtlInfoDO.setCoreAcctDate(bankCore351100InnerRsp.getCoreRspDate());
        payTransDtlInfoDO.setCoreSerno(bankCore351100InnerRsp.getCoreRspSerno());
        payTransDtlInfoDO.setCoreRetCode(bankCore351100InnerRsp.getErrorCode());
        payTransDtlInfoDO.setCoreRetMsg(bankCore351100InnerRsp.getErrorMsg());
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_DRDT);
        payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());
        // ????????????
        if (AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus)) {
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
            payTransDtlInfoDO.setTrxRetCode(EcnyTransError.SUCCESS.getErrorCode());
            payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
            payTransDtlInfoDO.setPayPathRspStatus(ProcessStsCdEnum.PR00.getCode());
            payTransDtlInfoDO.setPayPathRetCode(RejectCdEnum.SUCCESS.getCode());
            payTransDtlInfoDO.setPayPathRetMsg(RejectCdEnum.SUCCESS.getDesc());
            //???????????????????????????????????????
            bankCoreProcessService.cacheLimitAmount(payTransDtlInfoDO.getPayerAcct()+AppConstant.PAYER_AMOUNT,payTransDtlInfoDO.getAmount(),true);
        } else if (AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)) {
            // ????????????
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_FAIL);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
            payTransDtlInfoDO.setTrxRetCode(bankCore351100InnerRsp.getErrorCode());
            payTransDtlInfoDO.setTrxRetMsg(bankCore351100InnerRsp.getErrorMsg());
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            payTransDtlInfoDO.setPayPathRspStatus(ProcessStsCdEnum.PR01.getCode());
            RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(Constant.CORE_SYS_ID, Constant.DCEP_SYS_ID, bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
            payTransDtlInfoDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
            payTransDtlInfoDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
        } else {
            // ????????????
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setTrxRetCode(bankCore351100InnerRsp.getErrorCode());
            payTransDtlInfoDO.setTrxRetMsg(bankCore351100InnerRsp.getErrorMsg());
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
            payTransDtlInfoDO.setPayPathRspStatus(ProcessStsCdEnum.PR02.getCode());
            RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(Constant.CORE_SYS_ID, Constant.DCEP_SYS_ID, bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
            payTransDtlInfoDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
            payTransDtlInfoDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
        }
    }

    /**
     * ????????????
     *
     * @param tradeContext
     */
    public void response(TradeContext<?, ?> tradeContext) {

        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("payTransDtlInfoDO");

        DCEPRspDTO<ConvertRspDTO> dcepRspDTO = EcnyTradeContext.getRspMsg(tradeContext);
        RspsnInf rspsnInf = dcepRspDTO.getBody().getConvertRsp().getRspsnInf();
        rspsnInf.setRspsnSts(payTransDtlInfoDO.getPayPathRspStatus());
        // ?????????????????????????????????
        if (!AppConstant.CORESTATUS_SUCCESS.equals(payTransDtlInfoDO.getCoreProcStatus())) {
            rspsnInf.setRjctCd(payTransDtlInfoDO.getPayPathRetCode());
            rspsnInf.setRjctInf(payTransDtlInfoDO.getPayPathRetMsg());
        }

        logger.info("?????????????????????????????????:{},?????????:{},????????????:{}", rspsnInf.getRspsnSts(), rspsnInf.getRjctCd(), rspsnInf.getRjctInf());
    }

    /**
     * ????????????
     *
     * @param tradeContext ???????????????
     * @param exception    ??????
     */
    public void convertTradeErrHandler(TradeContext<?, ?> tradeContext, Throwable exception) {
        logger.error("???????????????????????????");
        // ??????????????????
        DCEPRspDTO<ConvertRspDTO> dcepRspDTO = EcnyTradeContext.getRspMsg(tradeContext);
        RspsnInf rspsnInf = dcepRspDTO.getBody().getConvertRsp().getRspsnInf();
        // ???????????????
        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(exception);
        // ?????????????????????
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("payTransDtlInfoDO");
        if (null != payTransDtlInfoDO) {

            StateMachine oldStatus = (StateMachine)EcnyTradeContext.getTempContext(tradeContext).get("oldStatus");

            PayTransDtlInfoDO updateDO = new PayTransDtlInfoDO();
            updateDO.setPayDate(payTransDtlInfoDO.getPayDate());
            updateDO.setPaySerno(payTransDtlInfoDO.getPaySerno());
            // ???????????????????????????????????????????????????????????????????????????????????????
            String rspStatus = ProcessStsCdEnum.PR02.getCode();
            String status = AppConstant.TRXSTATUS_ABEND;
            String code = EcnyTransError.OTHER_TECH_ERROR.getErrorCode();
            String msg = EcnyTransError.OTHER_TECH_ERROR.getErrorMsg();
            if (exception instanceof EcnyTransException) {
                code = ((EcnyTransException) exception).getErrorCode();
                msg = ((EcnyTransException) exception).getErrorMsg();
                status = ((EcnyTransException) exception).getStatus();
            }
            // ???????????????
            updateDO.setTrxStatus(status);
            updateDO.setTrxRetCode(code);
            updateDO.setTrxRetMsg(msg);
            updateDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_ABEND);
            updateDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
            updateDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
            updateDO.setPayPathRetDate(DateUtil.getDefaultDate());
            // ????????????
            if (AppConstant.TRXSTATUS_FAILED.equals(status)) {
                rspStatus = ProcessStsCdEnum.PR01.getCode();
                updateDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            }
            updateDO.setPayPathRspStatus(rspStatus);

            try {
                // ?????????????????????
                iPayTransDtlInfo1Service.update(updateDO,oldStatus);
            } catch (Exception e) {
                logger.error("????????????????????????????????????????????????{}-{}", e.getMessage(), e);
                throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
            }

            // ????????????
            rspsnInf.setRspsnSts(rspStatus);
        } else {
            // ????????????????????????
            rspsnInf.setRspsnSts(ProcessStsCdEnum.PR01.getCode());
        }
        rspsnInf.setRjctCd(rspCodeMapDO.getDestRspCode());
        rspsnInf.setRjctInf(rspCodeMapDO.getRspCodeDsp());

        logger.info("?????????????????????????????????:{},?????????:{},????????????:{}", rspsnInf.getRspsnSts(), rspsnInf.getRjctCd(), rspsnInf.getRjctInf());
    }
}
