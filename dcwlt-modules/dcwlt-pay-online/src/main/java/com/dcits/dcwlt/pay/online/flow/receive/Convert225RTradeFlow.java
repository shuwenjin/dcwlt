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
 * 兑出交易处理配置
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
                .initRspMsg(this::initRspMsg)// 响应报文初始化
                .initTxn(this::initTxn) //交易流水初始化
                .process(this::checkMsg) //业务检查
                .process(this::coreProcess)// 上核心
                .response(this::response) //响应报文
                .errHandler(this::convertTradeErrHandler)// 异常响应
                .build();
    }

    /**
     * 初始化响应报文
     *
     * @param tradeContext
     */
    public void initRspMsg(TradeContext<?, ?> tradeContext) {

        DCEPReqDTO<ConvertReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        // 业务组件头
        GrpHdr grpHdr = GrpHdr.getInstance(reqMsg.getBody().getConvertReq().getGrpHdr());
        // 原报文组件
        OrgnlGrpHdr orgnlGrpHdr = OrgnlGrpHdr.getInstance(reqMsg.getBody().getConvertReq().getGrpHdr(), reqMsg);
        // 应答信息组件
        RspsnInf rspsnInf = new RspsnInf();
        rspsnInf.setBatchId(reqMsg.getBody().getConvertReq().getTrxInf().getBatchId());

        ConvertRspDTO convertRsp = new ConvertRspDTO(grpHdr, orgnlGrpHdr, rspsnInf);
        // 设置初始化响应报文
        DCEPRspDTO<ConvertRspDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP226.getCode(), convertRsp);
        EcnyTradeContext.setRspMsg(tradeContext, dcepRspDTO);

    }

    /**
     * 初始化流水入库
     *
     * @param tradeContext
     */
    public void initTxn(TradeContext<?, ?> tradeContext) {

        DCEPReqDTO<ConvertReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        // 交易流水入库
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
            logger.info("金融流水表入库失败:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }

        //保存最新的数据库交易状态、核心状态、通道状态
        StateMachine oldStatus = new StateMachine();
        oldStatus.setPreTrxStatus(payTransDtlInfoDO.getTrxStatus());
        oldStatus.setPreCoreProcStatus(payTransDtlInfoDO.getCoreProcStatus());
        oldStatus.setPrePathProcStatus(payTransDtlInfoDO.getPathProcStatus());
        EcnyTradeContext.getTempContext(tradeContext).put("oldStatus", oldStatus);

        //保存到context中
        EcnyTradeContext.getTempContext(tradeContext).put("payTransDtlInfoDO", payTransDtlInfoDO);

    }

    /**
     * 业务检查
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
        // 仅支持个人钱包
        if (!WalletTpCdEnum.WT01.getCode().equals(walletType)) {
            logger.info("钱包类型不支持:{}", walletType);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.WALLET_TYPE_ERROR);
        }
        // 仅支持一二类钱包
        if (!StringUtils.equalsAny(walletLevel, WalletLevelCdEnum.WL01.getCode(), WalletLevelCdEnum.WL02.getCode())) {
            logger.info("钱包等级不支持:{}", walletLevel);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.WALLET_LEVEL_ERROR);
        }
        //业务种类、业务类型校验
        if (!AppConstant.BUSINESS_TYPE_RECONVERT.equals(busiType) || !ParamConfigCheckTask.checkConfigValue(BUSINESS_TYPE,
                busiType, busiKind)
        ) {
            logger.error("业务类型:{},业务种类:{}校验不通过,", busiType, busiKind);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.BUSINESS_TYPE_INVALID);
        }
        // 接收机构状态校验
        boolean partyFlag = partyService.isAvailable(instdPty);
        if (!partyFlag) {
            logger.info("接收机构状态异常,{}", instdPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_STATUS_ERROR);
        }
        // 发起机构权限校验 根据：发起机构号+报文编号+业务类型+发送标识
        Boolean sendAuth = authInfoService.validateAuthInfo(instgPty, MsgTpEnum.DCEP225.getCode(), busiType, AuthInfoDrctEnum.sendAuth);
        if (!sendAuth) {
            logger.error("发送机构权限不足,{}", instgPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_POWER_ERROR);
        }
        // 协议校验
        checkSignInfo(tradeContext);

        String payerAcct = reqMsg.getBody().getConvertReq().getDbtrInf().getDbtrAcct();
        //已累计交易金额
        BigDecimal payerAmount = redisService.getCacheObject(payerAcct+AppConstant.PAYER_AMOUNT);;
        //当前交易金额
        BigDecimal amount = new BigDecimal(reqMsg.getBody().getConvertReq().getTrxInf().getTrxAmt().getValue());
        //单笔交易限额
        String perLimitPayerAmount = PayCommParamTask.getPayCommParamVal(com.dcits.dcwlt.common.pay.constant.Constant.PARAM_TYPE_DATA, com.dcits.dcwlt.common.pay.constant.Constant.PER_LIMIT_PAYER_AMOUNT);
        //判断当前交易金额是否超过单笔限额
        if(StringUtil.isNotEmpty(perLimitPayerAmount)){
            BigDecimal perLimitAmount = new BigDecimal(perLimitPayerAmount);
            //判断当前交易金额是否超过单笔限额
            if(amount.compareTo(perLimitAmount) > 0){
                logger.error("当前账户{}单笔兑出超过限额{}", payerAcct.substring(payerAcct.length()-4),perLimitAmount.toString());
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PER_LIMIT_AMOUNT_EXPIRED);
            }
        }

        //当前交易金额加上累计交易金额，判断是否大于日累计限额
        //日交易限额
        String dayLimitAmountStr = PayCommParamTask.getPayCommParamVal(com.dcits.dcwlt.common.pay.constant.Constant.PARAM_TYPE_DATA, com.dcits.dcwlt.common.pay.constant.Constant.DAY_LIMIT_PAYER_AMOUNT);
        if(StringUtil.isNotEmpty(dayLimitAmountStr)){
            BigDecimal dayLimitAmount = new BigDecimal(dayLimitAmountStr);
            //当前交易金额加上累计交易金额，判断是否大于日累计限额
            if(null == payerAmount){
                payerAmount = new BigDecimal("0.00");
            }
            if((amount.add(payerAmount)).compareTo(dayLimitAmount) > 0){
                logger.error("当前账户{}日累计兑出超过限额{}", payerAcct.substring(payerAcct.length()-4),dayLimitAmount.toString());
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DAY_LIMIT_AMOUNT_EXPIRED);
            }
        }
    }


    /**
     * 检查协议信息
     *
     * @param tradeContext
     */
    public void checkSignInfo(TradeContext<?, ?> tradeContext) {
        DCEPReqDTO<ConvertReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        logger.info("检查协议信息，协议号:{}", reqMsg.getBody().getConvertReq().getDbtrInf().getSgnNo());
        // 查询协议
        SignInfoDO signInfoDO = signInfoMapper.selectBySignNo(reqMsg.getBody().getConvertReq().getDbtrInf().getSgnNo());
        // 协议存在
        if (null != signInfoDO) {
            // 检查协议是否已失效
            if (AppConstant.SIGN_STATUS_CLOSE.equals(signInfoDO.getSignStatus())) {
                logger.error("当前协议状态已失效");
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.SIGN_STATUS_UNVALID);
            }
            // 检查与协议中的付款信息是否匹配
            if (!reqMsg.getBody().getConvertReq().getDbtrInf().getDbtrAcct().equals(signInfoDO.getAcctId())) {
                logger.error("上送付款人账号与协议不匹配");
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PAYER_INFO_UNEQUAL);
            }
            if (!reqMsg.getBody().getConvertReq().getDbtrInf().getDbtrNm().equals(signInfoDO.getAcctName())) {
                logger.error("上送付款人名称与协议不匹配");
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PAYER_INFO_UNEQUAL);
            }

        } else {
            logger.error("协议信息不存在");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.SIGN_INFO_UN_FOUND);
        }
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

        //更新上核心前处理成功后的登记簿状态
        StateMachine oldStatus = new StateMachine();
        oldStatus.setPreTrxStatus(payTransDtlInfoDO.getTrxStatus());
        oldStatus.setPreCoreProcStatus(payTransDtlInfoDO.getCoreProcStatus());
        oldStatus.setPrePathProcStatus(payTransDtlInfoDO.getPathProcStatus());
        EcnyTradeContext.getTempContext(tradeContext).put("oldStatus", oldStatus);

        // 发送核心
        BankCore351100InnerRsp bankCore351100InnerRsp = sendToCore(bankCore351100InnerReq);
        // 核心后处理
        sendCoreDone(payTransDtlInfoDO, bankCore351100InnerRsp);

        //更新上核心后处理成功后的登记簿状态
        oldStatus.setPreTrxStatus(payTransDtlInfoDO.getTrxStatus());
        oldStatus.setPreCoreProcStatus(payTransDtlInfoDO.getCoreProcStatus());
        oldStatus.setPrePathProcStatus(payTransDtlInfoDO.getPathProcStatus());
        EcnyTradeContext.getTempContext(tradeContext).put("oldStatus", oldStatus);
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
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_DRDT);
        payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);
        // 核心请求实体更新
        bankCore351100InnerReq.setCoreReqDate(coreReqDate);
        bankCore351100InnerReq.setCoreReqSerno(coreReqSerno);

        // 获取当前交易状态
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_INIT);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
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
    public BankCore351100InnerRsp sendToCore(BankCore351100InnerReq bankCore351100InnerReq) {
        logger.info("发送核心系统进行账务处理,核心请求日期:{},流水:{}", bankCore351100InnerReq.getCoreReqDate(), bankCore351100InnerReq.getCoreReqSerno());
        BankCore351100InnerRsp bankCore351100InnerRsp;
        try {
            // todo 请求核心的代码
            bankCore351100InnerRsp = bankCoreImplDubboService.coreServer(bankCore351100InnerReq);
        } catch (Exception e) {
            //核心记账异常，将异常存入MQ
            PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(bankCore351100InnerReq.getPayDate(),bankCore351100InnerReq.getPaySerno());
            coreEventServiceImpl.registerReCredit(payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), ReCreditCallBack.class);

            logger.error("核心通讯异常：{}-{}-{}", LogMonitorLevelCdEnum.ECNY_LOG_MONITOR_NORMAL.getCode(), e.getMessage(), e);
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
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        // 设置核心结果
        setTradeResult(payTransDtlInfoDO, bankCore351100InnerRsp);

        try {
            bankCoreProcessService.sendCoreDone(payTransDtlInfoDO,bankCore351100InnerRsp,stateMachine);
        } catch (Exception e) {
            logger.error("核心后处理异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 设置核心结果
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     */
    public void setTradeResult(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {
        logger.info("核心返回信息：返回码:{}, 返回信息:{}", bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
        // 设置核心返回信息
        String coreProcStatus = bankCore351100InnerRsp.getCoreStatus();
        payTransDtlInfoDO.setCoreProcStatus(bankCore351100InnerRsp.getCoreStatus());
        payTransDtlInfoDO.setCoreAcctDate(bankCore351100InnerRsp.getCoreRspDate());
        payTransDtlInfoDO.setCoreSerno(bankCore351100InnerRsp.getCoreRspSerno());
        payTransDtlInfoDO.setCoreRetCode(bankCore351100InnerRsp.getErrorCode());
        payTransDtlInfoDO.setCoreRetMsg(bankCore351100InnerRsp.getErrorMsg());
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_DRDT);
        payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());
        // 核心成功
        if (AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus)) {
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
            payTransDtlInfoDO.setTrxRetCode(EcnyTransError.SUCCESS.getErrorCode());
            payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
            payTransDtlInfoDO.setPayPathRspStatus(ProcessStsCdEnum.PR00.getCode());
            payTransDtlInfoDO.setPayPathRetCode(RejectCdEnum.SUCCESS.getCode());
            payTransDtlInfoDO.setPayPathRetMsg(RejectCdEnum.SUCCESS.getDesc());
            //记录当前账户日累计支付金额
            bankCoreProcessService.cacheLimitAmount(payTransDtlInfoDO.getPayerAcct()+AppConstant.PAYER_AMOUNT,payTransDtlInfoDO.getAmount(),true);
        } else if (AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)) {
            // 核心失败
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
            // 核心异常
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
     * 响应报文
     *
     * @param tradeContext
     */
    public void response(TradeContext<?, ?> tradeContext) {

        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("payTransDtlInfoDO");

        DCEPRspDTO<ConvertRspDTO> dcepRspDTO = EcnyTradeContext.getRspMsg(tradeContext);
        RspsnInf rspsnInf = dcepRspDTO.getBody().getConvertRsp().getRspsnInf();
        rspsnInf.setRspsnSts(payTransDtlInfoDO.getPayPathRspStatus());
        // 核心非成功，回拒绝信息
        if (!AppConstant.CORESTATUS_SUCCESS.equals(payTransDtlInfoDO.getCoreProcStatus())) {
            rspsnInf.setRjctCd(payTransDtlInfoDO.getPayPathRetCode());
            rspsnInf.setRjctInf(payTransDtlInfoDO.getPayPathRetMsg());
        }

        logger.info("应答报文的业务回执状态:{},错误码:{},错误信息:{}", rspsnInf.getRspsnSts(), rspsnInf.getRjctCd(), rspsnInf.getRjctInf());
    }

    /**
     * 异常处理
     *
     * @param tradeContext 交易上下文
     * @param exception    异常
     */
    public void convertTradeErrHandler(TradeContext<?, ?> tradeContext, Throwable exception) {
        logger.error("兑出交易异常处理！");
        // 获取响应报文
        DCEPRspDTO<ConvertRspDTO> dcepRspDTO = EcnyTradeContext.getRspMsg(tradeContext);
        RspsnInf rspsnInf = dcepRspDTO.getBody().getConvertRsp().getRspsnInf();
        // 错误码映射
        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(exception);
        // 获取流水表实体
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("payTransDtlInfoDO");
        if (null != payTransDtlInfoDO) {

            StateMachine oldStatus = (StateMachine)EcnyTradeContext.getTempContext(tradeContext).get("oldStatus");

            PayTransDtlInfoDO updateDO = new PayTransDtlInfoDO();
            updateDO.setPayDate(payTransDtlInfoDO.getPayDate());
            updateDO.setPaySerno(payTransDtlInfoDO.getPaySerno());
            // 获取交易状态，即发生异常时，交易是失败还是异常，默认是异常
            String rspStatus = ProcessStsCdEnum.PR02.getCode();
            String status = AppConstant.TRXSTATUS_ABEND;
            String code = EcnyTransError.OTHER_TECH_ERROR.getErrorCode();
            String msg = EcnyTransError.OTHER_TECH_ERROR.getErrorMsg();
            if (exception instanceof EcnyTransException) {
                code = ((EcnyTransException) exception).getErrorCode();
                msg = ((EcnyTransException) exception).getErrorMsg();
                status = ((EcnyTransException) exception).getStatus();
            }
            // 更新流水表
            updateDO.setTrxStatus(status);
            updateDO.setTrxRetCode(code);
            updateDO.setTrxRetMsg(msg);
            updateDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_ABEND);
            updateDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
            updateDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
            updateDO.setPayPathRetDate(DateUtil.getDefaultDate());
            // 交易失败
            if (AppConstant.TRXSTATUS_FAILED.equals(status)) {
                rspStatus = ProcessStsCdEnum.PR01.getCode();
                updateDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            }
            updateDO.setPayPathRspStatus(rspStatus);

            try {
                // 更新金融交易表
                iPayTransDtlInfo1Service.update(updateDO,oldStatus);
            } catch (Exception e) {
                logger.error("兑回异常处理时更新交易状态异常：{}-{}", e.getMessage(), e);
                throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
            }

            // 返回信息
            rspsnInf.setRspsnSts(rspStatus);
        } else {
            // 未入库，返回失败
            rspsnInf.setRspsnSts(ProcessStsCdEnum.PR01.getCode());
        }
        rspsnInf.setRjctCd(rspCodeMapDO.getDestRspCode());
        rspsnInf.setRjctInf(rspCodeMapDO.getRspCodeDsp());

        logger.info("应答报文的业务回执状态:{},错误码:{},错误信息:{}", rspsnInf.getRspsnSts(), rspsnInf.getRjctCd(), rspsnInf.getRjctInf());
    }
}
