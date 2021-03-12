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
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.service.*;
import com.dcits.dcwlt.pay.online.service.impl.BankCoreAccTxnService;
import com.dcits.dcwlt.pay.online.service.impl.CoreEventService;
import com.dcits.dcwlt.pay.online.service.impl.ECNYSerNoService;
import com.dcits.dcwlt.pay.online.service.impl.ParamConfigCheckService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;


/**
 * 差错贷记调账来账处理配置
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
    private IPayTransDtlInfoService payTransDtlInfoRepository;

    @Autowired
    private IPartyService partyService;

    @Autowired
    private ParamConfigCheckService paramConfigCheckService;

    @Autowired
    private IAuthInfoService authInfoService;

    @Autowired
    private ICoreProcessService bankCoreProcessService;


    @Autowired
    private ECNYSerNoService ecnySerNoService;

    @Autowired
    private BankCoreAccTxnService bankCoreAccTxnService;

//    @Autowired
//    private BankCoreImplDubboService bankCoreImplDubboService;



    @Autowired
    private CoreEventService coreEventService;


    private static final String BUSINESS_TYPE = "BIZTP";

    @Bean(name = DSPT_TRADE_FLOW)
    public TradeFlow dsptTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .initRspMsg(this::initRspMsg)                // 初始化响应报文
                .initTxn(this::initTxn)                      // 初始化交易登记簿
                .checkMsg(this::checkMsg)                    // 校验数据
                .process(this::coreProcess)                  // 上核心
                .response(this::packRspMsg)                  // 响应报文
                .errHandler(this::convertTradeErrHandler)
                .build();
    }

    /**
     * 初始化响应报文
     */
    public void initRspMsg(TradeContext<?, ?> context) {
        // 获取请求参数
        DCEPReqDTO<DsptReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);

        //业务头组件
        GrpHdr grpHdr = GrpHdr.getInstance(reqMsg.getBody().getDsptReq().getGrpHdr());

        //原报文主键组件
        OrgnlGrpHdr orgnlGrpHdr = OrgnlGrpHdr.getInstance(reqMsg.getBody().getDsptReq().getGrpHdr(), reqMsg);

        //响应信息
        RspsnInf rspsnInf = new RspsnInf();
        rspsnInf.setBatchId(reqMsg.getBody().getDsptReq().getDsptInf().getBatchId());
        rspsnInf.setRspsnSts(ProcessStsCdEnum.PR02.getCode());

        DsptRspDTO rspDTO = DsptRspDTO.getInstance(grpHdr, orgnlGrpHdr, rspsnInf);

        //封装响应报文
        DCEPRspDTO<DsptRspDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP802.getCode(), rspDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
    }

    /**
     * 初始化流水入库
     */
    public void initTxn(TradeContext<?, ?> tradeContext) {
        logger.info("initTxn：初始化金融登记簿开始 ");
        // 参数接收
        DCEPReqDTO<DsptReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        // 业务头
        GrpHdr grpHdr = reqMsg.getBody().getDsptReq().getGrpHdr();
        // 原报文组件
        OrgnlGrpHdr orgnlGrpHdr = reqMsg.getBody().getDsptReq().getOrgnlGrpHdr();
        // 差错信息
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

        // 业务状态2
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
        // 核心状态9
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
        // 通道状态7
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);

        // 组装业务头组件信息
        payTransDtlInfoDO.setPayPathSerno(grpHdr.getMsgId());
        payTransDtlInfoDO.setPayPathRetSerno(grpHdr.getMsgId());
        payTransDtlInfoDO.setPayPathDateTime(grpHdr.getCreDtTm());
        payTransDtlInfoDO.setInstgPty(grpHdr.getInstgPty().getInstgDrctPty());
        payTransDtlInfoDO.setInstdPty(grpHdr.getInstdPty().getInstdDrctPty());
        payTransDtlInfoDO.setRemark(grpHdr.getRmk());
        payTransDtlInfoDO.setMsgType(MsgTpEnum.DCEP801.getCode());

        // 组装原报文信息
        // 原报文标识号
        payTransDtlInfoDO.setOrigPayPathSerno(orgnlGrpHdr.getOrgnlMsgId());
        // 原报文编号
        payTransDtlInfoDO.setOrigMsgType(orgnlGrpHdr.getOrgnlMT());

        // 组装差错贷记调账信息
        // 业务类型编码
        payTransDtlInfoDO.setBusiType(dsptInf.getDsptBizTp());
        // 业务种类编码
        payTransDtlInfoDO.setBusiKind(dsptInf.getDsptCtgyPurpCd());
        // 差错原因码 + 差错原因说明
        payTransDtlInfoDO.setNarraTive(dsptInf.getDsptRsnCd() + "_" + dsptInf.getDsptRsnDesc());
        // 调账金额
        payTransDtlInfoDO.setAmount(AmountUtil.yuanToFen(dsptInf.getDsptAmt().getValue()));
        // 币种
        payTransDtlInfoDO.setCcy(dsptInf.getDsptAmt().getCcy());
        // 交易批次号
        payTransDtlInfoDO.setBatchId(dsptInf.getBatchId());

        payTransDtlInfoDO.setZoneNo(Constant.MASTERBANK);
        payTransDtlInfoDO.setBrno(Constant.MASTERBANK);
        payTransDtlInfoDO.setAcctBrno(Constant.MASTERBANK);
        payTransDtlInfoDO.setLastUpJrnno(generateFlowNo);
        payTransDtlInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setLastUpTime(DateUtil.getDefaultTime());

        try {
            payTransDtlInfoRepository.insert(payTransDtlInfoDO);
        } catch (Exception e) {
            logger.error("金融流水表入库失败:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.UPDATE_INFO_ERROR);
        }
        //保存到context中
        EcnyTradeContext.getTempContext(tradeContext).put("PAY_TRANS_DTL", payTransDtlInfoDO);
        logger.info("initTxn：初始化金融登记簿成功 ");
    }

    /**
     * 数据校验
     */
    public void checkMsg(TradeContext<?, ?> tradeContext) {
        // 参数接收
        DCEPReqDTO<DsptReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        DsptReqDTO reqMsgBody = reqMsg.getBody();

        // 参数校验
        checkInstgPty(reqMsgBody);

        String orgnlMsgId = reqMsg.getBody().getDsptReq().getOrgnlGrpHdr().getOrgnlMsgId();
        // 获取原金融登记簿信息
        PayTransDtlInfoDO payTransDtlInfo_orig = payTransDtlInfoRepository.query(orgnlMsgId);

        if (null == payTransDtlInfo_orig) {
            logger.error("原交易不存在");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.OLD_PAY_INFO_UNFOUND);
        }

        PayTransDtlInfoDO payTransDtlInfo_dspt = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("PAY_TRANS_DTL");

        // 原交易与现交易收付款方向调整
        // 组装付款人信息
        payTransDtlInfo_dspt.setPayerPtyId(payTransDtlInfo_orig.getPayeePtyId());
        payTransDtlInfo_dspt.setPayerName(payTransDtlInfo_orig.getPayeeName());
        payTransDtlInfo_dspt.setPayerAcctType(payTransDtlInfo_orig.getPayeeAcctType());
        payTransDtlInfo_dspt.setPayerAcct(payTransDtlInfo_orig.getPayeeAcct());
        payTransDtlInfo_dspt.setPayerWalletId(payTransDtlInfo_orig.getPayeeWalletId());
        payTransDtlInfo_dspt.setPayerWalletName(payTransDtlInfo_orig.getPayeeWalletName());
        payTransDtlInfo_dspt.setPayerWalletLv(payTransDtlInfo_orig.getPayeeWalletLv());
        payTransDtlInfo_dspt.setPayerWalletType(payTransDtlInfo_orig.getPayeeWalletType());

        // 组装收款人信息
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
            payTransDtlInfoRepository.update(payTransDtlInfo_dspt);
        } catch (Exception e) {
            logger.error("金融流水表更新失败:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }

        //保存到context中
        EcnyTradeContext.getTempContext(tradeContext).put("PAY_TRANS_DTL", payTransDtlInfo_dspt);
    }

    /**
     * 参数校验
     */
    private void checkInstgPty(DsptReqDTO reqMsgBody) {
        logger.info("机构代码校验开始");
        GrpHdr grpHdr = reqMsgBody.getDsptReq().getGrpHdr();
        // 发起机构
        String instgPty = grpHdr.getInstgPty().getInstgDrctPty();
        // 接收机构
        String instdPty = grpHdr.getInstdPty().getInstdDrctPty();

        // 差错业务
        String dsptBizTp = reqMsgBody.getDsptReq().getDsptInf().getDsptBizTp();

        String dsptCtgyPurpCd = reqMsgBody.getDsptReq().getDsptInf().getDsptCtgyPurpCd();

        //业务种类、业务类型校验
        if (!paramConfigCheckService.checkConfigValue(BUSINESS_TYPE, dsptBizTp, dsptCtgyPurpCd)
                || !AppConstant.BUSINESS_TYPE_DSPT.equals(dsptBizTp)) {
            logger.error("业务种类、业务类型校验不通过");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.ECNY_BUSINESS_TYPE_INVALID);
        }

        //判断发起机构状态
        boolean sendPartyFlag = partyService.isAvailable(instgPty);
        if (!sendPartyFlag) {
            logger.info("发起机构状态异常,{}", instgPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTG_STATUS_UNSUPPORT);
        }

        //判断接收机构状态
        boolean recvPartyFlag = partyService.isAvailable(instdPty);
        if (!recvPartyFlag) {
            logger.info("接收机构状态异常,{}", instdPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTD_STATUS_UNSUPPORT);
        }

        // 判断发送机构状态,发起机构801权限校验
        Boolean sendAuth = authInfoService.validateAuthInfo(instgPty, MsgTpEnum.DCEP801.getCode(), DSPT_BIZ_TP, AuthInfoDrctEnum.sendAuth);
        if (!sendAuth) {
            logger.error("发送机构权限不足,{}", instgPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.ORGAN_POWER_ERROR);
        }

        // 判断接收机构状态，接收机构801权限判断
        Boolean recvAuth = authInfoService.validateAuthInfo(instdPty, MsgTpEnum.DCEP801.getCode(), DSPT_BIZ_TP, AuthInfoDrctEnum.recvAuth);
        if (!recvAuth) {
            logger.error("接收机构权限不足,{}", instdPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.ORGAN_POWER_ERROR);
        }

        logger.info("机构代码校验完成");
    }

    /**
     * 入账处理
     */
    private void coreProcess(TradeContext<?, ?> context) {
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(context).get("PAY_TRANS_DTL");

        // 初始化核心请求报文
        BankCore351100InnerReq bankCore351100InnerReq = sendCoreInit(payTransDtlInfoDO);

        // 核心前处理
        sendCorePre(payTransDtlInfoDO, bankCore351100InnerReq);

        // 发送核心
        BankCore351100InnerRsp bankCore351100InnerRsp = sendToCore(bankCore351100InnerReq, payTransDtlInfoDO);

        // 核心后处理
        sendCoreDone(payTransDtlInfoDO, bankCore351100InnerRsp, bankCore351100InnerReq);

        EcnyTradeContext.getTempContext(context).put("MAP_KEY_351100_INNER_RSP", bankCore351100InnerRsp);
    }

    /**
     * 上核心前处理
     * 初始化核心报文、插入核心流水表、更新金融登记簿核心流水及核心状态
     *
     * @param payTransDtlInfoDO
     */
    private BankCore351100InnerReq sendCoreInit(PayTransDtlInfoDO payTransDtlInfoDO) {
        //打印关键参数
        logger.info("sendCoreInit：上核心前处理开始 ");
//        BankCore351100InnerReq bankCore351100InnerReq = bankCoreProcessService.initBankCore351100InnerReq(payTransDtlInfoDO);
        BankCore351100InnerReq bankCore351100InnerReq = new BankCore351100InnerReq();
        bankCore351100InnerReq.setAcctBrno("1");
        logger.info("sendCoreInit：上核心前处理结束 ");
        return bankCore351100InnerReq;
    }

    /**
     * 核心前处理
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendCorePre(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerReq bankCore351100InnerReq) {
        logger.info("sendCorePre: 入账务流水表，更新登记簿为状态为处理中，平台日期：{}，平台流水：{} ",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
        String coreReqDate = generateCodeService.getCoreReqDate(payTransDtlInfoDO.getBatchId());
        String coreReqSerno = generateCodeService.generateCoreReqSerno();

        bankCore351100InnerReq.setCoreReqDate(coreReqDate);
        bankCore351100InnerReq.setCoreReqSerno(coreReqSerno);

        // 设置核心日期流水
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
            // 更新账户流水表
            bankCoreAccTxnService.insertCoreFlow(bankCore351100InnerReq, coreReqSerno, coreReqDate);
            // 更新金融交易表
            int retCount = payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
            if (retCount != 1) {
                logger.info("更新金融信息表失败");
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
            }
        } catch (Exception e) {
            // 异常保持核心状态为9
            payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
            logger.error("核心前处理异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 调351100上核心入账
     */
    public BankCore351100InnerRsp sendToCore(BankCore351100InnerReq bankCore351100InnerReq, PayTransDtlInfoDO payTransDtlInfoDO) {
        logger.info("上核心入账开始，核心日期：{}，核心请求流水：{}",
                bankCore351100InnerReq.getCoreReqDate(), bankCore351100InnerReq.getCoreReqSerno());
        BankCore351100InnerRsp bankCore351100InnerRsp = new BankCore351100InnerRsp();
        bankCore351100InnerReq.setAmount("测试");
//        try {
//            bankCore351100InnerRsp = bankCoreImplDubboService.coreServer(bankCore351100InnerReq);
//        } catch (Exception e) {
//            logger.error("核心通讯异常：{}-{}-{}", LogMonitorLevelCdEnum.ECNY_LOG_MONITOR_NORMAL.getCode(), e.getMessage(), e);
//            logger.info("调用核心回查,平台日期：{},平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
//            AccFlowDO accFlowDO = bankCoreAccTxnService.selectByPayInfo(payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
//            logger.info("调用核心回查,核心日期：{},核心流水：{}", accFlowDO.getCoreReqDate(), accFlowDO.getCoreReqSerno());
//            coreEventService.registerCoreQry(accFlowDO.getCoreReqDate(), accFlowDO.getCoreReqSerno(), payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), DisputeCoreQryCallBack.class);
//            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.PAY_TIME_OUT);
//        }
        logger.info("sendToCore：上核心入账结束");
        return bankCore351100InnerRsp;
    }

    /**
     * 核心后处理
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendCoreDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp, BankCore351100InnerReq bankCore351100InnerReq) {
        logger.info("核心返回，核心状态：{}，核心返回码：{}，核心返回信息：{}",
                bankCore351100InnerRsp.getCoreStatus(), bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        // 设置核心结果
        setTradeResult(payTransDtlInfoDO, bankCore351100InnerRsp);

        try {
            bankCore351100InnerRsp.setCoreReqDate(bankCore351100InnerReq.getCoreReqDate());
            bankCore351100InnerRsp.setCoreReqSerno(bankCore351100InnerReq.getCoreReqSerno());
            // 更新账户流水表
            int retCount = bankCoreAccTxnService.updateCoreAccFlow(bankCore351100InnerRsp);
            if (retCount != 1) {
                logger.info("更新账务流水表失败");
                throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
            }
            // 更新金融交易表
            retCount = payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
            if (retCount != 1) {
                logger.info("更新金融信息表失败");
                throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
            }
        } catch (Exception e) {
            logger.error("核心后处理异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 设置核心结果
     */
    private void setTradeResult(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {
        logger.info("核心返回信息：返回码:{}, 返回信息:{}", bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
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
            logger.info("上核心入账成功");
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
            payTransDtlInfoDO.setTrxRetCode(EcnyTransError.SUCCESS.getErrorCode());
            payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
            payTransDtlInfoDO.setPayPathRspStatus(ProcessStsCdEnum.PR00.getCode());
            payTransDtlInfoDO.setPayPathRetCode(RejectCdEnum.SUCCESS.getCode());
            payTransDtlInfoDO.setPayPathRetMsg(RejectCdEnum.SUCCESS.getDesc());
        } else if (AppConstant.CORESTATUS_FAILED.equals(coreStatus)) {
            logger.info("上核心入账失败");
            payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_FAIL);
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
            payTransDtlInfoDO.setTrxRetCode(bankCore351100InnerRsp.getErrorCode());
            payTransDtlInfoDO.setTrxRetMsg(bankCore351100InnerRsp.getErrorMsg());
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            payTransDtlInfoDO.setPayPathRspStatus(ProcessStsCdEnum.PR01.getCode());
            payTransDtlInfoDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
            payTransDtlInfoDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
        } else {
            logger.info("核心异常");
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
     * 响应报文封装
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
                logger.info("上核心入账成功,返回成功");
                rspsnInf.setRspsnSts(ProcessStsCdEnum.PR00.getCode());
                break;
            case AppConstant.CORESTATUS_FAILED:
                logger.info("上核心入账失败，返回失败");
                rspsnInf.setRspsnSts(ProcessStsCdEnum.PR01.getCode());
                rspsnInf.setRjctCd(rspCodeMapDO.getDestRspCode());
                rspsnInf.setRjctInf(rspCodeMapDO.getRspCodeDsp());
                break;
            default:
                logger.error("核心异常，返回异常");
                rspsnInf.setRspsnSts(ProcessStsCdEnum.PR02.getCode());
                rspsnInf.setRjctCd(rspCodeMapDO.getDestRspCode());
                rspsnInf.setRjctInf(rspCodeMapDO.getRspCodeDsp());
                break;
        }
        logger.info("差错调账801应答报文的业务回执状态:{}", ProcessStsCdEnum.PR00.getCode());
    }

    /**
     * 异常处理
     */
    public void convertTradeErrHandler(TradeContext<?, ?> tradeContext, Throwable exception) {
        logger.error("差错贷记调账异常处理！");
        // 获取响应报文
        DCEPRspDTO<DsptRspDTO> dcepRspDTO = EcnyTradeContext.getRspMsg(tradeContext);
        RspsnInf rspsnInf = dcepRspDTO.getBody().getDsptRsp().getRspsnInf();
        // 错误码映射
        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(exception);
        rspsnInf.setRjctCd(rspCodeMapDO.getDestRspCode());
        rspsnInf.setRjctInf(rspCodeMapDO.getRspCodeDsp());

        // 获取流水表实体
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("PAY_TRANS_DTL");

        if (null == payTransDtlInfoDO) {
            // 未入库，返回失败
            rspsnInf.setRspsnSts(ProcessStsCdEnum.PR01.getCode());
            logger.info("802应答报文的业务回执状态:{},错误码:{},错误信息:{}", rspsnInf.getRspsnSts(), rspsnInf.getRjctCd(), rspsnInf.getRjctInf());
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

        // 核心前异常处理
        if (AppConstant.TRXSTATUS_FAILED.equals(status)) {
            rspsnInf.setRspsnSts(ProcessStsCdEnum.PR01.getCode());
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        }
        // 核心后异常处理
        if (AppConstant.TRXSTATUS_ABEND.equals(status)) {
            rspsnInf.setRspsnSts(ProcessStsCdEnum.PR02.getCode());
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        }
        // 更新流水表
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
        payTransDtlInfoRepository.update(payTransDtlInfoDO);

        rspsnInf.setRjctCd(rspCodeMapDO.getDestRspCode());
        rspsnInf.setRjctInf(rspCodeMapDO.getRspCodeDsp());

        logger.info("802应答报文的业务回执状态:{},错误码:{},错误信息:{}", rspsnInf.getRspsnSts(), rspsnInf.getRjctCd(), rspsnInf.getRjctInf());
    }
}
