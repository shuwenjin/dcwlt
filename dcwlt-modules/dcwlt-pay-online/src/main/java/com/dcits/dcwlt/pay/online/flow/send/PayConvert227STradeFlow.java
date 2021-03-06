package com.dcits.dcwlt.pay.online.flow.send;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore358040.BankCore358040Req;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore358040.BankCore358040Rsp;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.*;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.type.OutOrgTypeEnum;
import com.dcits.dcwlt.common.pay.util.BigDecimalUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.common.redis.service.RedisService;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.cmonconf.CmonConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.cmonconf.CmonConfInf;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.RspsnInf;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.Fault;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.FaultDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.payconvert.*;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqHead;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspHead;
import com.dcits.dcwlt.pay.api.domain.ecny.payconvert.PayConvertChnlReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.payconvert.PayConvertChnlRspDTO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.baffle.dcep.DcepService;
import com.dcits.dcwlt.pay.online.baffle.dcep.impl.BankCoreImplDubboService;
import com.dcits.dcwlt.pay.online.base.Constant;
import com.dcits.dcwlt.pay.online.event.callback.ConvertBankRevCallBack;
import com.dcits.dcwlt.pay.online.event.service.BankRevService;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.mapper.PayTransDtlInfoMapper;
import com.dcits.dcwlt.pay.online.mapper.SignInfoMapper;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.ITxStsQryNetPartyService;
import com.dcits.dcwlt.pay.online.service.impl.*;
import com.dcits.dcwlt.pay.online.task.PayCommParamTask;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.util.Map;

/**
 * ??????????????????????????????
 *
 * @author
 */
@Configuration
public class PayConvert227STradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(PayConvert227STradeFlow.class);

    public static final String PAY_CONVERT_TRADE_FLOW = "PayConvertTradeFlow";
    private static final String AMOUNT_LIMIT = "500000";
    private static final String LSFK43_SUCCESS_CODE = "0000";
    private static final String ERRORCODE = "errorCode";
    private static final String ERRORMSG = "errorMsg";
    private static final String LIMIT_CONFIG_KEY = "ecny.payCounvert.limit.amount";

    private static final String PAYER_AMOUNT = "_payerAmount";

    @Autowired
    private SignInfoMapper signInfoMapper;

    @Autowired
    private PayTransDtlInfoMapper payTransDtlInfoMapper;

    @Autowired
    private PayTransDtlInfoServiceImpl payTransDtlInfoService;

    @Autowired
    private BankCoreAccTxnServiceImpl bankCoreAccTxnService;

    @Autowired
    private BankCoreImplDubboService bankCoreImplDubboService;

    @Autowired
    private BankAccountVerifyService bankAccountVerifyService;

    @Autowired
    private CoreEventServiceImpl coreEventService;

    @Autowired
    private DcepSendService dcepSendService;

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

    @Autowired
    private ITxStsQryNetPartyService txStsQryNetPartyService;


    @Autowired
    private BankRevService bankRevService;

    @Autowired
    private PartyService partyService;

    @Autowired
    private AuthInfoServiceimpl authInfoService;

    @Autowired
    private ICoreProcessService bankCoreProcessService;

    @Autowired
    private DcepService dcepService;

    @Autowired
    private RedisService redisService;


    @Bean(name = PAY_CONVERT_TRADE_FLOW)
    public TradeFlow payConvertTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .initTxn(this::initTxn) //?????????????????????
                .process(this::businessCheck) //????????????
                .process(this::coreProcess)// ?????????
                .process(this::sendToDcep)// ??????????????????????????????
                .response(this::response) //????????????
                .errHandler(this::convertTradeErrHandler)// ??????????????????
                .build();
    }

    /**
     * ?????????????????????
     *
     * @param tradeContext
     */
    public void initTxn(TradeContext<?, ?> tradeContext) {

        ECNYReqDTO<PayConvertChnlReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        // ??????????????????
        ECNYReqHead ecnyHead = reqMsg.getEcnyHead();
        PayConvertChnlReqDTO reqBody = reqMsg.getBody();
        PayTransDtlInfoDO payTransDtlInfoDO = new PayTransDtlInfoDO();

        payTransDtlInfoDO.setPayDate(DateUtil.getDefaultDate());
        String paySerno = generateCodeService.generatePlatformFlowNo();
        payTransDtlInfoDO.setPaySerno(paySerno);
        payTransDtlInfoDO.setPayTime(DateUtil.getDefaultTime());
        payTransDtlInfoDO.setDirect(AppConstant.DIRECT_SEND);
        payTransDtlInfoDO.setPayFlag(AppConstant.IDENTIFICATION_PAYER);
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_INIT);
        payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
        payTransDtlInfoDO.setPayPathSerno(generateCodeService.generateMsgId(OutOrgTypeEnum.OutOrg, MsgTpEnum.DCEP227.getCode().substring(5, 8), ""));
        payTransDtlInfoDO.setPayPathDateTime(DateUtil.getISODateTime());
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_INIT);
        payTransDtlInfoDO.setBatchId(generateCodeService.getBachNo());
        payTransDtlInfoDO.setBusiChnl(ecnyHead.getBusiChnl());
        payTransDtlInfoDO.setBusiChnl2(ecnyHead.getBusiChnl2());
        payTransDtlInfoDO.setBusiSysDate(reqMsg.getHead().getTranDate());
        payTransDtlInfoDO.setBusiSysTime(reqMsg.getHead().getTranTime());
        payTransDtlInfoDO.setBusiSysSerno(reqMsg.getHead().getSeqNo());

        payTransDtlInfoDO.setBusiSysDate(DateUtil.getCurDay());
        payTransDtlInfoDO.setBusiSysTime(DateUtil.getDefaultTime());
        payTransDtlInfoDO.setBusiSysSerno(generateCodeService.getBachNo());

        payTransDtlInfoDO.setMsgType(MsgTpEnum.DCEP227.getCode());
        payTransDtlInfoDO.setBusiType(reqBody.getBusiType());
        payTransDtlInfoDO.setBusiKind(reqBody.getBusiKind());
        payTransDtlInfoDO.setInstgPty(reqBody.getPayerBank());
        payTransDtlInfoDO.setInstdPty(reqBody.getPayeeBank());
        payTransDtlInfoDO.setCcy(AppConstant.CURRENCY_SYMBOL);
        payTransDtlInfoDO.setAmount(reqBody.getAmount());
        payTransDtlInfoDO.setNarraTive(reqBody.getNarraTive());
        payTransDtlInfoDO.setPayerPtyId(reqBody.getPayerBank());
        payTransDtlInfoDO.setPayerName(reqBody.getPayerName());
        payTransDtlInfoDO.setPayerAcct(reqBody.getPayerAcct());
        payTransDtlInfoDO.setPayeePtyId(reqBody.getPayeeBank());
        payTransDtlInfoDO.setPayeeWalletId(reqBody.getPayeeAcct());
        payTransDtlInfoDO.setPayeeWalletName(reqBody.getWalletName());
        payTransDtlInfoDO.setPayeeWalletLv(reqBody.getWalletLevel());
        payTransDtlInfoDO.setPayeeWalletType(reqBody.getWalletType());
        payTransDtlInfoDO.setTellerNo(ecnyHead.getTellerno());
        payTransDtlInfoDO.setZoneNo(ecnyHead.getZoneno());
        payTransDtlInfoDO.setBrno(ecnyHead.getBrno());
        payTransDtlInfoDO.setAcctBrno(ecnyHead.getBrno());
        payTransDtlInfoDO.setOrigChnl(ecnyHead.getOrigChnl());
        payTransDtlInfoDO.setOrigChnl2(ecnyHead.getOrigChnl2());
        payTransDtlInfoDO.setOrigChnlDtl(ecnyHead.getOrigChnlDtl());
        payTransDtlInfoDO.setSummary(SummaryCdEnum.XSG.getCode());
        payTransDtlInfoDO.setLastUpJrnno(paySerno);
        payTransDtlInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setLastUpTime(DateUtil.getDefaultTime());
        payTransDtlInfoDO.setRemark(reqBody.getRemark());
        try {
            payTransDtlInfoMapper.insert(payTransDtlInfoDO);
        } catch (Exception e) {
            logger.info("???????????????????????????:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }

        //?????????context???
        EcnyTradeContext.getTempContext(tradeContext).put("payTransDtlInfoDO", payTransDtlInfoDO);

    }

    /**
     * ????????????
     *
     * @param tradeContext
     */
    public void businessCheck(TradeContext<?, ?> tradeContext) {
        logger.info("????????????");
        ECNYReqDTO<PayConvertChnlReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("payTransDtlInfoDO");

        String instdPtyId = reqMsg.getBody().getPayeeBank();
        String instgPtyId = reqMsg.getBody().getPayerBank();
        String busiType = reqMsg.getBody().getBusiType();

        //?????????????????????????????????
        if (!AppConstant.BANK_FINANCIAL_INSTITUTION_CD.equals(instgPtyId)) {
            logger.info("????????????????????????,{}", instgPtyId);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTG_ERROR);
        }

        //????????????????????????
        boolean sendPartyFlag = partyService.isAvailable(instgPtyId);
        if (!sendPartyFlag) {
            logger.info("????????????????????????,{}", instgPtyId);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTG_STATUS_UNSUPPORT);
        }

        //????????????????????????
        boolean recvPartyFlag = partyService.isAvailable(instdPtyId);
        if (!recvPartyFlag) {
            logger.info("????????????????????????,{}", instdPtyId);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTD_STATUS_UNSUPPORT);
        }

        // ???????????????????????? ????????????????????????+????????????+????????????+????????????
        Boolean sendAuth = authInfoService.validateAuthInfo(instdPtyId, MsgTpEnum.DCEP227.getCode(), busiType, AuthInfoDrctEnum.recvAuth);
        if (!sendAuth) {
            logger.error("????????????????????????,{}", instdPtyId);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTD_POWER_UNSUPPORT);
        }

        // ????????????
        String payerAcct = reqMsg.getBody().getPayerAcct();
        //?????????????????????
        BigDecimal payerAmount = redisService.getCacheObject(payerAcct+PAYER_AMOUNT);
        //??????????????????
        BigDecimal amount = new BigDecimal(reqMsg.getBody().getAmount());

        //??????????????????
        String perLimitPayerAmount = PayCommParamTask.getPayCommParamVal(com.dcits.dcwlt.common.pay.constant.Constant.PARAM_TYPE_DATA, com.dcits.dcwlt.common.pay.constant.Constant.PER_LIMIT_PAYER_AMOUNT);
        if(StringUtil.isNotEmpty(perLimitPayerAmount)){
            BigDecimal perLimitAmount = new BigDecimal(perLimitPayerAmount);
            //????????????????????????????????????????????????
            if(amount.compareTo(perLimitAmount) > 0){
                logger.error("????????????{}????????????????????????{}", payerAcct.substring(payerAcct.length()-4),perLimitAmount.toString());
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PER_LIMIT_AMOUNT_EXPIRED);
            }
        }

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


        // ????????????
        BankCore358040Req bankCore358040Req = new BankCore358040Req();
        bankCore358040Req.setAc(reqMsg.getBody().getPayerAcct());
        BankCore358040Rsp bankCore358040Rsp = bankAccountVerifyService.queryAccount(MsgTpEnum.DCEP227.getCode(), bankCore358040Req);

        AccTpCdEnum accTpCdEnum = bankAccountVerifyService.transAcctTp(bankCore358040Rsp);
        if(!AccTpCdEnum.AT00.getCode().equals(accTpCdEnum.getCode())){
            logger.error("???????????????????????????--???????????????????????????");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.UN_SUPPORTED_ACC_CLASS_TP);
        }

        payTransDtlInfoDO.setPayerAcctType(accTpCdEnum.getCode());

        // ?????????
        Map<String, String> errMap = bankAccountVerifyService.invokeLSFK43(bankCore358040Rsp.getIdNo(), bankCore358040Rsp.getType(), bankCore358040Rsp.getCiName(), payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPayTime(), payTransDtlInfoDO.getPaySerno());
        String errorCode = errMap.get(ERRORCODE);
        String errorMsg = errMap.get(ERRORMSG);
        if (!LSFK43_SUCCESS_CODE.equals(errorCode)) {
            logger.error("????????????????????????????????????{}??????????????????{}", errorCode, errorMsg);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.INVALID_ACCT_STATUS);
        }

        EcnyTradeContext.getTempContext(tradeContext).put("payTransDtlInfoDO", payTransDtlInfoDO);
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
        // ????????????
        BankCore351100InnerRsp bankCore351100InnerRsp = sendToCore(payTransDtlInfoDO, bankCore351100InnerReq);
        // ???????????????
        sendCoreDone(payTransDtlInfoDO, bankCore351100InnerRsp);
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
        bankCore351100InnerReq.setPayPath(Constant.ECNY_SYS_ID);
        bankCore351100InnerReq.setReqChnl(payTransDtlInfoDO.getOrigChnl());
        bankCore351100InnerReq.setReqChnl2(payTransDtlInfoDO.getOrigChnl2());
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
        bankCore351100InnerReq.setNarrative(payTransDtlInfoDO.getRemark());
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
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_INIT);
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_DRDT);
        payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);
        // ????????????????????????
        bankCore351100InnerReq.setCoreReqDate(coreReqDate);
        bankCore351100InnerReq.setCoreReqSerno(coreReqSerno);
        // ????????????????????????
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_INIT);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_INIT);
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
    public BankCore351100InnerRsp sendToCore(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerReq bankCore351100InnerReq) {
        logger.info("????????????????????????????????????,??????????????????:{},??????:{}", bankCore351100InnerReq.getCoreReqDate(), bankCore351100InnerReq.getCoreReqSerno());
        BankCore351100InnerRsp bankCore351100InnerRsp;
        try {
            bankCore351100InnerRsp = bankCoreImplDubboService.coreServer(bankCore351100InnerReq);
        } catch (Exception e) {
            logger.error("?????????????????????{}-{}-{}", LogMonitorLevelCdEnum.ECNY_LOG_MONITOR_NORMAL.getCode(), e.getMessage(), e);
            // ????????????????????????
            bankRev(payTransDtlInfoDO);
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
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_INIT);
        // ??????????????????
        setTradeResult(payTransDtlInfoDO, bankCore351100InnerRsp);
        try {
            bankCoreProcessService.sendCoreDone(payTransDtlInfoDO,bankCore351100InnerRsp,stateMachine);
        } catch (Exception e) {
            // ????????????????????????
            bankRev(payTransDtlInfoDO);
            logger.error("????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
        }
        // ?????????????????????????????????????????????????????????
        if (AppConstant.CORESTATUS_FAILED.equals(bankCore351100InnerRsp.getCoreStatus())) {
            logger.error("????????????");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, Constant.CORE_SYS_ID, bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
        } else if (AppConstant.CORESTATUS_ABEND.equals(bankCore351100InnerRsp.getCoreStatus())) {
            logger.error("????????????");
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, Constant.CORE_SYS_ID, bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
        }
    }

    /**
     * ??????????????????
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     */
    public void setTradeResult(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {
        logger.info("?????????????????????{}", bankCore351100InnerRsp.getErrorMsg());

        // ????????????????????????
        String coreProcStatus = bankCore351100InnerRsp.getCoreStatus();
        payTransDtlInfoDO.setCoreProcStatus(bankCore351100InnerRsp.getCoreStatus());
        payTransDtlInfoDO.setCoreAcctDate(bankCore351100InnerRsp.getCoreRspDate());
        payTransDtlInfoDO.setCoreSerno(bankCore351100InnerRsp.getCoreRspSerno());
        payTransDtlInfoDO.setCoreRetCode(bankCore351100InnerRsp.getErrorCode());
        payTransDtlInfoDO.setCoreRetMsg(bankCore351100InnerRsp.getErrorMsg());
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_DRDT);
        // ????????????
        if (AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus)) {
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
            //????????????????????????????????????
            bankCoreProcessService.cacheLimitAmount(payTransDtlInfoDO.getPayerAcct()+PAYER_AMOUNT,payTransDtlInfoDO.getAmount(),true);
        } else if (AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)) {
            // ????????????
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_FAIL);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        } else {
            // ????????????
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_INIT);
            // ????????????????????????
            bankRev(payTransDtlInfoDO);
        }
    }

    /**
     * ????????????????????????
     *
     * @param tradeContext
     */
    public void sendToDcep(TradeContext<?, ?> tradeContext) {
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("payTransDtlInfoDO");
        // ?????????????????????????????????
        DCEPReqDTO<PayConvertReqDTO> dcepReqDTO = sendDcepInit(payTransDtlInfoDO);
        // ????????????????????????
        JSONObject rspObj = sendDcep(dcepReqDTO, payTransDtlInfoDO);
        // ???????????????????????????
        sendDcepDone(rspObj, payTransDtlInfoDO);
    }

    /**
     * ??????????????????????????????
     *
     * @param payTransDtlInfoDO
     */
    public DCEPReqDTO<PayConvertReqDTO> sendDcepInit(PayTransDtlInfoDO payTransDtlInfoDO) {

        // ???????????????
        GrpHdr grpHdr = GrpHdr.getInstance(payTransDtlInfoDO.getPayPathSerno(), payTransDtlInfoDO.getPayeePtyId());
        // ??????????????????
        TrxInf trxInf = TrxInf.getInstance(payTransDtlInfoDO);
        // ???????????????
        DbtrInf dbtrInf = DbtrInf.getInstance(payTransDtlInfoDO);
        // ??????????????????
        CdtrInf cdtrInf = CdtrInf.getInstance(payTransDtlInfoDO);
        // ??????????????????
        PayConvertReqDTO payConvertReqDTO = new PayConvertReqDTO(grpHdr, trxInf, dbtrInf, cdtrInf);
        // ????????????????????????
        String msgSn = generateCodeService.generateMsgSN(payTransDtlInfoDO.getPayPathSerno());
        return DCEPReqDTO.newInstance(MsgTpEnum.DCEP227.getCode(),msgSn, payTransDtlInfoDO.getPayeePtyId(), payConvertReqDTO);
    }

    /**
     * ??????227?????????????????????
     *
     * @param dcepReqDTO
     */
    public JSONObject sendDcep(DCEPReqDTO<PayConvertReqDTO> dcepReqDTO, PayTransDtlInfoDO payTransDtlInfoDO) {
        try {
            JSONObject jsonObject = dcepSendService.sendDcep(dcepReqDTO);
            //JSONObject jsonObject = dcepService.receive228(dcepReqDTO);
            return jsonObject;
        } catch (Exception e) {
            logger.error("?????????????????????????????????{}-{}-{}", LogMonitorLevelCdEnum.ECNY_LOG_MONITOR_NORMAL.getCode(), e.getMessage(), e);
            // ?????????????????????????????????????????????????????????
            txStsQryNetPartyService.registerTrxStsQry(payTransDtlInfoDO);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.SEND_DCEP_ERROR);
        }
    }

    /**
     * ???????????????????????????
     *
     * @param rspObj
     * @param payTransDtlInfoDO
     */
    public void sendDcepDone(JSONObject rspObj, PayTransDtlInfoDO payTransDtlInfoDO) {

        if (null == rspObj.getJSONObject("ecnyHead")) {
            logger.error("??????????????????????????????");
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.PARAMS_INVALID);
        }
        String msgType = rspObj.getJSONObject("ecnyHead").getString("MsgTp");
        logger.info("?????????????????????:{}", msgType);
        // ????????????????????????????????????????????????????????????????????????
        Boolean isFailed = false;
        // ?????????????????????228
        if (MsgTpEnum.DCEP228.getCode().equals(msgType)) {
            DCEPRspDTO<PayConvertRspDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, PayConvertRspDTO.class);
            String processStatus = dcepRspDTO.getBody().getConvertRsp().getRspsnInf().getPrcSts();
            RspsnInf rspsnInf = dcepRspDTO.getBody().getConvertRsp().getRspsnInf();
            payTransDtlInfoDO.setPayPathRetSerno(dcepRspDTO.getBody().getConvertRsp().getGrpHdr().getMsgId());
            payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());
            payTransDtlInfoDO.setPayPathRspStatus(rspsnInf.getRspsnSts());
            payTransDtlInfoDO.setPayPathRetCode(rspsnInf.getRjctCd());
            payTransDtlInfoDO.setPayPathRetMsg(rspsnInf.getRjctInf());

            // ????????????
            if (StringUtils.equalsAny(processStatus, ProcessStsCdEnum.PR00.getCode(), ProcessStsCdEnum.PR03.getCode())) {
                payTransDtlInfoDO.setTrxRetCode(Constant.SERVER_SUCC_RSPCODE);
                payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
                payTransDtlInfoDO.setPayPathRetCode(RejectCdEnum.SUCCESS.getCode());
                payTransDtlInfoDO.setPayPathRetMsg(RejectCdEnum.SUCCESS.getDesc());
            } else if (StringUtils.equalsAny(processStatus, ProcessStsCdEnum.PR01.getCode(), ProcessStsCdEnum.PR04.getCode())) {
                // ????????????
                payTransDtlInfoDO.setTrxRetCode(rspsnInf.getRjctCd());
                payTransDtlInfoDO.setTrxRetMsg(rspsnInf.getRjctInf());
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
                isFailed = true;
            } else {
                // ???????????????
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_ABEND);
                // ?????????????????????????????????????????????????????????
                txStsQryNetPartyService.registerTrxStsQry(payTransDtlInfoDO);
            }

        } else if (MsgTpEnum.DCEP900.getCode().equals(msgType)) {
            // ?????????????????????900???????????????,??????
            DCEPRspDTO<CmonConfDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, CmonConfDTO.class);
            CmonConfInf cmonConfInf = dcepRspDTO.getBody().getCmonConf().getCmonConfInf();
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            payTransDtlInfoDO.setTrxRetMsg(cmonConfInf.getPrcCd());
            payTransDtlInfoDO.setTrxRetCode(cmonConfInf.getRjctInf());
            payTransDtlInfoDO.setPayPathRetSerno(dcepRspDTO.getBody().getCmonConf().getGrpHdr().getMsgId());
            payTransDtlInfoDO.setPayPathRetDate(DateUtil.getCurDay());
            payTransDtlInfoDO.setPayPathRspStatus(cmonConfInf.getPrcSts());
            payTransDtlInfoDO.setPayPathRetCode(cmonConfInf.getPrcCd());
            payTransDtlInfoDO.setPayPathRetMsg(cmonConfInf.getRjctInf());
            isFailed = true;
        } else if (MsgTpEnum.DCEP911.getCode().equals(msgType))  {
            // ???????????????911??????????????????????????????????????????
            DCEPRspDTO<FaultDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, FaultDTO.class);
            Fault fault = dcepRspDTO.getBody().getFault();
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_ABEND);
            payTransDtlInfoDO.setTrxRetMsg(fault.getFaultString());
            payTransDtlInfoDO.setTrxRetCode(fault.getFaultCode());
            payTransDtlInfoDO.setPayPathRetSerno(dcepRspDTO.getDcepHead().getMsgSn());
            payTransDtlInfoDO.setPayPathRetDate(DateUtil.getCurDay());
            payTransDtlInfoDO.setPayPathRetCode(fault.getFaultCode());
            payTransDtlInfoDO.setPayPathRetMsg(fault.getFaultString());
            // ?????????????????????????????????????????????????????????
            txStsQryNetPartyService.registerTrxStsQry(payTransDtlInfoDO);
        } else {
            // ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            txStsQryNetPartyService.registerTrxStsQry(payTransDtlInfoDO);
        }
        // ????????????????????????
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        try {
            // ?????????????????????
            int retCount = payTransDtlInfoService.update(payTransDtlInfoDO, stateMachine);
            if (retCount != 1) {
                // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
                if (isFailed) {
                    bankRevService.bankRevOnTime(payTransDtlInfoDO);
                }
                throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
            }
        } catch (Exception e) {
            logger.error("????????????????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
        }
        // ???????????????????????????
        if (isFailed) {
            // ????????????
            bankRevService.bankRevOnTime(payTransDtlInfoDO);
        }
    }

    /**
     * ????????????
     */
    public void response(TradeContext<?, ?> tradeContext) {

        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("payTransDtlInfoDO");
        ECNYReqDTO<PayConvertChnlReqDTO> ecnyReqDTO = EcnyTradeContext.getReqMsg(tradeContext);
        String trxStatus = payTransDtlInfoDO.getTrxStatus();
        // ?????????????????????????????????
        if (AppConstant.CORESTATUS_SUCCESS.equals(payTransDtlInfoDO.getCoreProcStatus()) && AppConstant.PAYPATHSTATUS_ABEND.equals(payTransDtlInfoDO.getPathProcStatus())) {
            // ??????????????????????????????????????????
            trxStatus = AppConstant.TRXSTATUS_INIT;
        } else if (AppConstant.CORESTATUS_REVERSED.equals(payTransDtlInfoDO.getCoreProcStatus()) && AppConstant.PAYPATHSTATUS_FAILED.equals(payTransDtlInfoDO.getPathProcStatus())) {
            // ??????????????????????????????????????????
            trxStatus = AppConstant.TRXSTATUS_FAILED;
        }

        // ?????????
        ECNYRspHead head = new ECNYRspHead(trxStatus);
        // ?????????
        PayConvertChnlRspDTO rspDTO = new PayConvertChnlRspDTO();
        rspDTO.setCoreAcctDate(payTransDtlInfoDO.getCoreAcctDate());
        rspDTO.setCoreSerno(payTransDtlInfoDO.getCoreSerno());
        rspDTO.setPayPathSerno(payTransDtlInfoDO.getPayPathSerno());
        ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(ecnyReqDTO, head, rspDTO, payTransDtlInfoDO.getTrxRetCode(), payTransDtlInfoDO.getTrxRetMsg());
        EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
    }

    /**
     * ????????????
     *
     * @param tradeContext
     * @param exception
     */
    public void convertTradeErrHandler(TradeContext<?, ?> tradeContext, Throwable exception) {
        logger.error("??????????????????????????????");
        ECNYReqDTO<PayConvertChnlReqDTO> ecnyReqDTO = EcnyTradeContext.getReqMsg(tradeContext);
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("payTransDtlInfoDO");

        // ???????????????????????????????????????????????????????????????????????????????????????
        String status = AppConstant.TRXSTATUS_ABEND;
        String code = EcnyTransError.OTHER_TECH_ERROR.getErrorCode();
        String msg = EcnyTransError.OTHER_TECH_ERROR.getErrorMsg();
        if (exception instanceof EcnyTransException) {
            status = ((EcnyTransException) exception).getStatus();
            code = ((EcnyTransException) exception).getErrorCode();
            msg = ((EcnyTransException) exception).getErrorMsg();
        }
        if (null != payTransDtlInfoDO) {
            // ???????????????
            payTransDtlInfoDO.setTrxStatus(status);
            if (StringUtil.isEmpty(payTransDtlInfoDO.getTrxRetCode())) {
                payTransDtlInfoDO.setTrxRetCode(code);
            }
            if (StringUtil.isEmpty(payTransDtlInfoDO.getTrxRetMsg())) {
                payTransDtlInfoDO.setTrxRetMsg(msg);
            }
            payTransDtlInfoMapper.update((Map<String, String>) payTransDtlInfoDO);
            // ????????????
            ECNYRspHead ecnyRspHead = new ECNYRspHead(payTransDtlInfoDO.getTrxStatus());
            ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(ecnyReqDTO, ecnyRspHead, null, payTransDtlInfoDO.getTrxRetCode(), payTransDtlInfoDO.getTrxRetMsg());
            EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
        } else {
            // ????????????
            ECNYRspHead ecnyRspHead = new ECNYRspHead(status);
            ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(ecnyReqDTO, ecnyRspHead, null, code, msg);
            EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
        }
    }

    /**
     * ??????????????????bankCore351100InnerReq.setPayPath
     *
     * @param payTransDtlInfoDO
     */
    public void bankRev(PayTransDtlInfoDO payTransDtlInfoDO) {
        coreEventService.registerBankRev(payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), ConvertBankRevCallBack.class);
    }

}
