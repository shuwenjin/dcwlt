package com.dcits.dcwlt.pay.online.flow.receive;

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
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement.BankAttRspsnInf;
import com.dcits.dcwlt.pay.api.domain.dcep.cmonconf.CmonConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.cmonconf.CmonConfInf;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.Fault;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.FaultDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.payconvert.*;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqHead;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspHead;
import com.dcits.dcwlt.pay.api.domain.ecny.payconvert.PayConvertChnlReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.payconvert.PayConvertStsQryRspDTO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.base.Constant;
import com.dcits.dcwlt.pay.online.event.service.BankRevService;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.mapper.SignInfoMapper;
import com.dcits.dcwlt.pay.online.service.IAuthInfoService;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import com.dcits.dcwlt.pay.online.service.impl.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


/**
 * 兑出交易处理配置
 *
 * @author liuyuanhui，wuguofeng01
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

    @Autowired
    private SignInfoMapper signInfoRepository;

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoRepository;

    @Autowired
    private BankCoreAccTxnServiceImpl bankCoreAccTxnService;

//    @Autowired
//    private BankCoreImplDubboService bankCoreImplDubboService;

    @Autowired
    private BankAccountVerifyService bankAccountVerifyService;

    @Autowired
    private CoreEventServiceImpl coreEventService;

    @Autowired
    private DcepSendService dcepSendService;

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

//    @Autowired
//    private ITxStsQryNetPartyService txStsQryNetPartyService;

    @Autowired
    private BankRevService bankRevService;

    @Autowired
    private PartyService partyService;

    @Autowired
    private IAuthInfoService authInfoService;

    @Autowired
    private ICoreProcessService bankCoreProcessService;

    @Bean(name = PAY_CONVERT_TRADE_FLOW)
    public TradeFlow payConvertTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .initTxn(this::initTxn) //交易流水初始化
                .process(this::businessCheck) //业务检查
                .process(this::coreProcess)// 上核心
                .process(this::sendToDcep)// 发送汇款兑出请求报文
                .response(this::response) //响应报文
                .errHandler(this::convertTradeErrHandler)// 异常响应报文
                .build();
    }

    /**
     * 初始化交易流水
     *
     * @param tradeContext
     */
    public void initTxn(TradeContext<?, ?> tradeContext) {

        ECNYReqDTO<PayConvertChnlReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        // 交易流水入库
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
//        payTransDtlInfoDO.setBusiSysDate(reqMsg.getHead().getTranDate());
//        payTransDtlInfoDO.setBusiSysTime(reqMsg.getHead().getTranTime());
//        payTransDtlInfoDO.setBusiSysSerno(reqMsg.getHead().getSeqNo());
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
            payTransDtlInfoRepository.insert(payTransDtlInfoDO);
        } catch (Exception e) {
            logger.info("金融流水表入库失败:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }

        //保存到context中
        EcnyTradeContext.getTempContext(tradeContext).put("payTransDtlInfoDO", payTransDtlInfoDO);

    }

    /**
     * 业务检查
     *
     * @param tradeContext
     */
    public void businessCheck(TradeContext<?, ?> tradeContext) {
        logger.info("业务检查");
        ECNYReqDTO<PayConvertChnlReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("payTransDtlInfoDO");

        String instdPtyId = reqMsg.getBody().getPayeeBank();
        String instgPtyId = reqMsg.getBody().getPayerBank();
        String busiType = reqMsg.getBody().getBusiType();

        //判断发起机构是否广发银行
        if (!AppConstant.CGB_FINANCIAL_INSTITUTION_CD.equals(instgPtyId)) {
            logger.info("发起机构传输有误,{}", instgPtyId);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTG_ERROR);
        }

        //判断发起机构状态
        boolean sendPartyFlag = partyService.isAvailable(instgPtyId);
        if (!sendPartyFlag) {
            logger.info("发起机构状态异常,{}", instgPtyId);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTG_STATUS_UNSUPPORT);
        }

        //判断接收机构状态
        boolean recvPartyFlag = partyService.isAvailable(instdPtyId);
        if (!recvPartyFlag) {
            logger.info("接收机构状态异常,{}", instdPtyId);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTD_STATUS_UNSUPPORT);
        }

        // 接收机构权限校验 根据：接收机构号+报文编号+业务类型+发送标识
        Boolean sendAuth = authInfoService.validateAuthInfo(instdPtyId, MsgTpEnum.DCEP227.getCode(), busiType, AuthInfoDrctEnum.recvAuth);
        if (!sendAuth) {
            logger.error("接收机构权限不足,{}", instdPtyId);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTD_POWER_UNSUPPORT);
        }

        // 限额校验
//        BigDecimal trxAmt = BigDecimalUtil.parse(reqMsg.getBody().getAmount());
//        String amountLimit = RtpUtil.getInstance().getProperty(LIMIT_CONFIG_KEY, AMOUNT_LIMIT);
//        if (trxAmt.compareTo(BigDecimalUtil.parse(amountLimit)) > 0) {
//            logger.error("交易金额超过上限");
//            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.AMOUNT_LIMIT_OUT);
//        }


        // 账户校验
        BankCore358040Req bankCore358040Req = new BankCore358040Req();
        bankCore358040Req.setAc(reqMsg.getBody().getPayerAcct());
        BankCore358040Rsp bankCore358040Rsp = bankAccountVerifyService.queryAccount(MsgTpEnum.DCEP227.getCode(), bankCore358040Req);

        AccTpCdEnum accTpCdEnum = bankAccountVerifyService.transAcctTp(bankCore358040Rsp);
        if(!AccTpCdEnum.AT00.getCode().equals(accTpCdEnum.getCode())){
            logger.error("校验账户状态不通过--该账户为二、三类户");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.UN_SUPPORTED_ACC_CLASS_TP);
        }

        payTransDtlInfoDO.setPayerAcctType(accTpCdEnum.getCode());

        // 反洗钱
        Map<String, String> errMap = bankAccountVerifyService.invokeLSFK43(bankCore358040Rsp.getIdNo(), bankCore358040Rsp.getType(), bankCore358040Rsp.getCiName(), payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPayTime(), payTransDtlInfoDO.getPaySerno());
        String errorCode = errMap.get(ERRORCODE);
        String errorMsg = errMap.get(ERRORMSG);
        if (!LSFK43_SUCCESS_CODE.equals(errorCode)) {
            logger.error("调用反洗钱失败，错误码：{}，错误信息：{}", errorCode, errorMsg);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.INVALID_ACCT_STATUS);
        }

        EcnyTradeContext.getTempContext(tradeContext).put("payTransDtlInfoDO", payTransDtlInfoDO);
    }

    /**
     * 上核心
     *
     * @param tradeContext
     */
    public void coreProcess(TradeContext<?, ?> tradeContext) {
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("payTransDtlInfoDO");
        // 初始化核心请求报文
        BankCore351100InnerReq bankCore351100InnerReq = sendCoreInit(payTransDtlInfoDO);
        // 核心前处理
        sendCorePre(payTransDtlInfoDO, bankCore351100InnerReq);
        // 发送核心
        BankCore351100InnerRsp bankCore351100InnerRsp = sendToCore(payTransDtlInfoDO, bankCore351100InnerReq);
        // 核心后处理
        sendCoreDone(payTransDtlInfoDO, bankCore351100InnerRsp);
    }

    /**
     * 初始化核心请求报文
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
     * 核心前处理
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerReq
     */
    public void sendCorePre(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerReq bankCore351100InnerReq) {
        logger.info("核心前处理");
        String coreReqDate = generateCodeService.getCoreReqDate(payTransDtlInfoDO.getBatchId());
        String coreReqSerno = generateCodeService.generateCoreReqSerno();
        // 交易实体更新
        payTransDtlInfoDO.setCoreReqDate(coreReqDate);
        payTransDtlInfoDO.setCoreReqSerno(coreReqSerno);
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_INIT);
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_DRDT);
        payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);
        // 核心请求实体更新
        bankCore351100InnerReq.setCoreReqDate(coreReqDate);
        bankCore351100InnerReq.setCoreReqSerno(coreReqSerno);
        // 获取当前交易状态
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_INIT);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_INIT);
        try {
            bankCoreProcessService.sendCorePre(payTransDtlInfoDO, bankCore351100InnerReq, stateMachine);
        } catch (Exception e) {
            logger.error("核心前处理异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 发送核心
     *
     * @param bankCore351100InnerReq
     * @return
     */
    public BankCore351100InnerRsp sendToCore(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerReq bankCore351100InnerReq) {
        logger.info("发送核心系统进行账务处理,核心请求日期:{},流水:{}", bankCore351100InnerReq.getCoreReqDate(), bankCore351100InnerReq.getCoreReqSerno());
        BankCore351100InnerRsp bankCore351100InnerRsp = null;
        try {
//            bankCore351100InnerRsp = bankCoreImplDubboService.coreServer(bankCore351100InnerReq);
        } catch (Exception e) {
            logger.error("核心通讯异常：{}-{}-{}", LogMonitorLevelCdEnum.ECNY_LOG_MONITOR_NORMAL.getCode(), e.getMessage(), e);
            // 登记冲正异常事件
//            bankRev(payTransDtlInfoDO);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.GATEWAY_REQUEST_ERROR);
        }
        return bankCore351100InnerRsp;
    }

    /**
     * 核心后处理
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     */
    public void sendCoreDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {
        logger.info("核心后处理");
        // 获取当前状态对象
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_INIT);
        // 设置核心结果
        setTradeResult(payTransDtlInfoDO, bankCore351100InnerRsp);
        try {
            bankCoreProcessService.sendCoreDone(payTransDtlInfoDO,bankCore351100InnerRsp,stateMachine);
        } catch (Exception e) {
            // 登记冲正异常事件
//            bankRev(payTransDtlInfoDO);
            logger.error("核心后处理异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
        }
        // 核心非成功，抛出异常，走到异常响应报文
        if (AppConstant.CORESTATUS_FAILED.equals(bankCore351100InnerRsp.getCoreStatus())) {
            logger.error("核心失败");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, Constant.CORE_SYS_ID, bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
        } else if (AppConstant.CORESTATUS_ABEND.equals(bankCore351100InnerRsp.getCoreStatus())) {
            logger.error("核心异常");
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, Constant.CORE_SYS_ID, bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
        }
    }

    /**
     * 设置核心结果
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     */
    public void setTradeResult(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {
        logger.info("核心返回信息：{}", bankCore351100InnerRsp.getErrorMsg());

        // 设置核心返回信息
        String coreProcStatus = bankCore351100InnerRsp.getCoreStatus();
        payTransDtlInfoDO.setCoreProcStatus(bankCore351100InnerRsp.getCoreStatus());
        payTransDtlInfoDO.setCoreAcctDate(bankCore351100InnerRsp.getCoreRspDate());
        payTransDtlInfoDO.setCoreSerno(bankCore351100InnerRsp.getCoreRspSerno());
        payTransDtlInfoDO.setCoreRetCode(bankCore351100InnerRsp.getErrorCode());
        payTransDtlInfoDO.setCoreRetMsg(bankCore351100InnerRsp.getErrorMsg());
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_DRDT);
        // 核心成功
        if (AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus)) {
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        } else if (AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)) {
            // 核心失败
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_FAIL);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        } else {
            // 核心异常
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_INIT);
            // 登记冲正异常事件
//            bankRev(payTransDtlInfoDO);
        }
    }

    /**
     * 发送互联互通平台
     *
     * @param tradeContext
     */
    public void sendToDcep(TradeContext<?, ?> tradeContext) {
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("payTransDtlInfoDO");
        // 初始化互联互通请求报文
        DCEPReqDTO<PayConvertReqDTO> dcepReqDTO = sendDcepInit(payTransDtlInfoDO);
        // 发送互联互通平台
        JSONObject rspObj = sendDcep(dcepReqDTO, payTransDtlInfoDO);
        // 发送互联互通后处理
        sendDcepDone(rspObj, payTransDtlInfoDO);
    }

    /**
     * 构建请求互联互通报文
     *
     * @param payTransDtlInfoDO
     */
    public DCEPReqDTO<PayConvertReqDTO> sendDcepInit(PayTransDtlInfoDO payTransDtlInfoDO) {

        // 业务头组件
        GrpHdr grpHdr = GrpHdr.getInstance(payTransDtlInfoDO.getPayPathSerno(), payTransDtlInfoDO.getPayeePtyId());
        // 业务信息组件
        TrxInf trxInf = TrxInf.getInstance(payTransDtlInfoDO);
        // 付款人信息
        DbtrInf dbtrInf = DbtrInf.getInstance(payTransDtlInfoDO);
        // 收款钱包信息
        CdtrInf cdtrInf = CdtrInf.getInstance(payTransDtlInfoDO);
        // 组装请求报文
        PayConvertReqDTO payConvertReqDTO = new PayConvertReqDTO(grpHdr, trxInf, dbtrInf, cdtrInf);
        // 请求报文放到容器
        String msgSn = generateCodeService.generateMsgSN(payTransDtlInfoDO.getPayPathSerno());
        return DCEPReqDTO.newInstance(MsgTpEnum.DCEP227.getCode(),msgSn, payTransDtlInfoDO.getPayeePtyId(), payConvertReqDTO);
    }

    /**
     * 发送227到互联互通平台
     *
     * @param dcepReqDTO
     */
    public JSONObject sendDcep(DCEPReqDTO<PayConvertReqDTO> dcepReqDTO, PayTransDtlInfoDO payTransDtlInfoDO) {
        try {
            JSONObject rspObj = dcepSendService.sendDcep(dcepReqDTO);
            return rspObj;
        } catch (Exception e) {
            logger.error("发送互联互通平台异常：{}-{}-{}", LogMonitorLevelCdEnum.ECNY_LOG_MONITOR_NORMAL.getCode(), e.getMessage(), e);
            // 登记异常事件，发送互联互通交易状态查询
//            txStsQryNetPartyService.registerTrxStsQry(payTransDtlInfoDO);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.SEND_DCEP_ERROR);
        }
    }

    /**
     * 发送互联互通后处理
     *
     * @param rspObj
     * @param payTransDtlInfoDO
     */
    public void sendDcepDone(JSONObject rspObj, PayTransDtlInfoDO payTransDtlInfoDO) {

        if (null == rspObj.getJSONObject("ecnyHead")) {
            logger.error("互联互通应答后出异常");
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.PARAMS_INVALID);
        }
        String msgType = rspObj.getJSONObject("ecnyHead").getString("MsgTp");
        logger.info("返回的报文类型:{}", msgType);
        // 判断是否交易失败，若失败，更新通道信息后需要冲正
        Boolean isFailed = false;
        // 返回报文编号为228
        if (MsgTpEnum.DCEP228.getCode().equals(msgType)) {
            DCEPRspDTO<PayConvertRspDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, PayConvertRspDTO.class);
            String processStatus = dcepRspDTO.getBody().getConvertRsp().getRspsnInf().getPrcSts();
            BankAttRspsnInf rspsnInf = dcepRspDTO.getBody().getConvertRsp().getRspsnInf();
            payTransDtlInfoDO.setPayPathRetSerno(dcepRspDTO.getBody().getConvertRsp().getGrpHdr().getMsgId());
            payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());
            payTransDtlInfoDO.setPayPathRspStatus(rspsnInf.getRspsnSts());
            payTransDtlInfoDO.setPayPathRetCode(rspsnInf.getRjctCd());
            payTransDtlInfoDO.setPayPathRetMsg(rspsnInf.getRjctInf());

            // 交易成功
            if (StringUtils.equalsAny(processStatus, ProcessStsCdEnum.PR00.getCode(), ProcessStsCdEnum.PR03.getCode())) {
                payTransDtlInfoDO.setTrxRetCode(Constant.SERVER_SUCC_RSPCODE);
                payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
                payTransDtlInfoDO.setPayPathRetCode(RejectCdEnum.SUCCESS.getCode());
                payTransDtlInfoDO.setPayPathRetMsg(RejectCdEnum.SUCCESS.getDesc());
            } else if (StringUtils.equalsAny(processStatus, ProcessStsCdEnum.PR01.getCode(), ProcessStsCdEnum.PR04.getCode())) {
                // 交易失败
                payTransDtlInfoDO.setTrxRetCode(rspsnInf.getRjctCd());
                payTransDtlInfoDO.setTrxRetMsg(rspsnInf.getRjctInf());
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
                isFailed = true;
            } else {
                // 交易处理中
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_ABEND);
                // 登记异常事件，发送互联互通交易状态查询
//                txStsQryNetPartyService.registerTrxStsQry(payTransDtlInfoDO);
            }

        } else if (MsgTpEnum.DCEP900.getCode().equals(msgType)) {
            // 返回报文编号为900，交易失败,冲正
            DCEPRspDTO<CmonConfDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, CmonConfDTO.class);
            CmonConfInf cmonConfInf = dcepRspDTO.getBody().getCmonConf().getCmonConfInf();
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            payTransDtlInfoDO.setTrxRetMsg(cmonConfInf.getPrcCd());
            payTransDtlInfoDO.setTrxRetCode(cmonConfInf.getRjctInf());
            payTransDtlInfoDO.setPayPathRetSerno(dcepRspDTO.getBody().getCmonConf().getGrpHdr().getMsgId());
            payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());
            payTransDtlInfoDO.setPayPathRspStatus(cmonConfInf.getPrcSts());
            payTransDtlInfoDO.setPayPathRetCode(cmonConfInf.getPrcCd());
            payTransDtlInfoDO.setPayPathRetMsg(cmonConfInf.getRjctInf());
            isFailed = true;
        } else if (MsgTpEnum.DCEP911.getCode().equals(msgType))  {
            // 返回报文为911，登记信息，发起交易状态查询
            DCEPRspDTO<FaultDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, FaultDTO.class);
            Fault fault = dcepRspDTO.getBody().getFault();
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_ABEND);
            payTransDtlInfoDO.setTrxRetMsg(fault.getFaultString());
            payTransDtlInfoDO.setTrxRetCode(fault.getFaultCode());
            payTransDtlInfoDO.setPayPathRetSerno(dcepRspDTO.getDcepHead().getMsgSn());
            payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());
            payTransDtlInfoDO.setPayPathRetCode(fault.getFaultCode());
            payTransDtlInfoDO.setPayPathRetMsg(fault.getFaultString());
            // 登记异常事件，发送互联互通交易状态查询
//            txStsQryNetPartyService.registerTrxStsQry(payTransDtlInfoDO);
        } else {
            // 其他的金融开放平台的异常（一般不会出现），登记异常事件，发送互联互通交易状态查询
//            txStsQryNetPartyService.registerTrxStsQry(payTransDtlInfoDO);
        }
        // 获取当前状态对象
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        try {
            // 更新金融交易表
            int retCount = payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
            if (retCount != 1) {
                // 更新失败，若此时交易是失败的，也要即时冲正，回复手机银行异常（一般不会）
                if (isFailed) {
                    bankRevService.bankRevOnTime(payTransDtlInfoDO);
                }
                throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
            }
        } catch (Exception e) {
            logger.error("发送互联互通后处理异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
        }
        // 交易失败，即时冲正
        if (isFailed) {
            // 即时冲正
            bankRevService.bankRevOnTime(payTransDtlInfoDO);
        }
    }

    /**
     * 响应报文
     */
    public void response(TradeContext<?, ?> tradeContext) {

        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("payTransDtlInfoDO");
        ECNYReqDTO<PayConvertChnlReqDTO> ecnyReqDTO = EcnyTradeContext.getReqMsg(tradeContext);
        String trxStatus = payTransDtlInfoDO.getTrxStatus();
        // 推断状态，回执手机银行
        if (AppConstant.CORESTATUS_SUCCESS.equals(payTransDtlInfoDO.getCoreProcStatus()) && AppConstant.PAYPATHSTATUS_ABEND.equals(payTransDtlInfoDO.getPathProcStatus())) {
            // 核心成功，通道异常，回处理中
            trxStatus = AppConstant.TRXSTATUS_INIT;
        } else if (AppConstant.CORESTATUS_REVERSED.equals(payTransDtlInfoDO.getCoreProcStatus()) && AppConstant.PAYPATHSTATUS_FAILED.equals(payTransDtlInfoDO.getPathProcStatus())) {
            // 核心已冲正，通道失败，回失败
            trxStatus = AppConstant.TRXSTATUS_FAILED;
        }

        // 响应头
        ECNYRspHead head = new ECNYRspHead(trxStatus);
        // 响应体
        PayConvertStsQryRspDTO rspDTO = new PayConvertStsQryRspDTO();
        rspDTO.setCoreAcctDate(payTransDtlInfoDO.getCoreAcctDate());
        rspDTO.setCoreSerno(payTransDtlInfoDO.getCoreSerno());
        rspDTO.setPayPathSerno(payTransDtlInfoDO.getPayPathSerno());

        ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(ecnyReqDTO, head, null, payTransDtlInfoDO.getTrxRetCode(), payTransDtlInfoDO.getTrxRetMsg());
        EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
    }

    /**
     * 异常处理
     *
     * @param tradeContext
     * @param exception
     */
    public void convertTradeErrHandler(TradeContext<?, ?> tradeContext, Throwable exception) {
        logger.error("汇款兑出交易异常处理");
        ECNYReqDTO<PayConvertChnlReqDTO> ecnyReqDTO = EcnyTradeContext.getReqMsg(tradeContext);
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("payTransDtlInfoDO");

        // 获取交易状态，即发生异常时，交易是失败还是异常，默认是异常
        String status = AppConstant.TRXSTATUS_ABEND;
        String code = EcnyTransError.OTHER_TECH_ERROR.getErrorCode();
        String msg = EcnyTransError.OTHER_TECH_ERROR.getErrorMsg();
        if (exception instanceof EcnyTransException) {
            status = ((EcnyTransException) exception).getStatus();
            code = ((EcnyTransException) exception).getErrorCode();
            msg = ((EcnyTransException) exception).getErrorMsg();
        }
        if (null != payTransDtlInfoDO) {
            // 更新流水表
            payTransDtlInfoDO.setTrxStatus(status);
            if (StringUtils.isEmpty(payTransDtlInfoDO.getTrxRetCode())) {
                payTransDtlInfoDO.setTrxRetCode(code);
            }
            if (StringUtils.isEmpty(payTransDtlInfoDO.getTrxRetMsg())) {
                payTransDtlInfoDO.setTrxRetMsg(msg);
            }
            payTransDtlInfoRepository.update(payTransDtlInfoDO);
            // 响应报文
            ECNYRspHead ecnyRspHead = new ECNYRspHead(payTransDtlInfoDO.getTrxStatus());
            ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(ecnyReqDTO, ecnyRspHead, null, payTransDtlInfoDO.getTrxRetCode(), payTransDtlInfoDO.getTrxRetMsg());
            EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
        } else {
            // 响应报文
            ECNYRspHead ecnyRspHead = new ECNYRspHead(status);
            ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(ecnyReqDTO, ecnyRspHead, null, code, msg);
            EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
        }
    }

    /**
     * 登记冲正事件bankCore351100InnerReq.setPayPath
     *
     * @param payTransDtlInfoDO
     */
//    public void bankRev(PayTransDtlInfoDO payTransDtlInfoDO) {
//        coreEventService.registerBankRev(payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), ConvertBankRevCallBack.class);
//    }

}
