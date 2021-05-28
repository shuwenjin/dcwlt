package com.dcits.dcwlt.pay.online.flow.receive;

import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.enums.AuthInfoDrctEnum;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.common.pay.enums.RejectCdEnum;
import com.dcits.dcwlt.common.pay.enums.WalletTpCdEnum;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.RspsnInf;
import com.dcits.dcwlt.pay.api.domain.dcep.reconvert.ReconvertReq;
import com.dcits.dcwlt.pay.api.domain.dcep.reconvert.ReconvertReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.reconvert.ReconvertRspDTO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.service.IAuthInfoService;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IPartyService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import com.dcits.dcwlt.pay.online.task.ParamConfigCheckTask;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 兑回交易处理配置
 */
@Configuration
public class Reconvert221RTradeFlow {
    private static final Logger logger = LoggerFactory.getLogger(FreeFrmt401RTradeFlow.class);

    private static final String RECONVERT_TRADE_FLOW = "Reconvert221RTradeFlow";

    private static final String BUSINESS_TYPE = "BIZTP";

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoService;

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;


    @Autowired
    private ICoreProcessService bankCoreProcessService;

    @Autowired
    private IPartyService partyService;

    @Autowired
    private IAuthInfoService authInfoService;


    @Bean(name = RECONVERT_TRADE_FLOW)
    public TradeFlow reconvertTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .initRspMsg(this::initRspMsg)      //初始化响应报文
                .initTxn(this::initTxn)            //初始化和保存交易登记簿
                .checkMsg(this::checkMsg)          //校验数据
                .process(this::coreProcess)        // 上核心
                .response(this::packRspMsg)        //响应保存封装
                .errHandler(this::reconverErrHandler)
                .build();
    }

    /**
     * 初始化返回报文
     *
     * @param context
     */
    public void initRspMsg(TradeContext<?, ?> context) {

        DCEPReqDTO<ReconvertReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);

        //初始化返回报文
        ReconvertReqDTO reqBody = reqMsg.getBody();
        ReconvertReq reqDTO = reqBody.getReconvertReq();

        //业务头组件
        GrpHdr grpHdr = GrpHdr.getInstance(reqMsg.getBody().getReconvertReq().getGrpHdr());

        //原报文主键组件
        OrgnlGrpHdr orgnlGrpHdr = OrgnlGrpHdr.getInstance(reqMsg.getBody().getReconvertReq().getGrpHdr(), reqMsg);

        //响应信息
        RspsnInf rspsnInf = new RspsnInf();
        rspsnInf.setBatchId(reqDTO.getTrxInf().getBatchId());
        rspsnInf.setRspsnSts(ProcessStsCdEnum.PR02.getCode());

        ReconvertRspDTO rspDTO = new ReconvertRspDTO(grpHdr, orgnlGrpHdr, rspsnInf);

        //封装响应报文
        DCEPRspDTO<ReconvertRspDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP222.getCode(), rspDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
    }

    /**
     * 初始化核心登记簿
     *
     * @param context
     */
    private void initTxn(TradeContext<?, ?> context) {
        DCEPReqDTO<ReconvertReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        ReconvertReq reqDTO = reqMsg.getBody().getReconvertReq();

        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.init(reqDTO);
        try {
            payTransDtlInfoService.insert(payTransDtlInfoDO);
        } catch (Exception e) {
            logger.info("金融登记簿入库失败:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }

        //保存最新的数据库交易状态、核心状态、通道状态
        StateMachine oldStatus = new StateMachine();
        oldStatus.setPreTrxStatus(payTransDtlInfoDO.getTrxStatus());
        oldStatus.setPreCoreProcStatus(payTransDtlInfoDO.getCoreProcStatus());
        oldStatus.setPrePathProcStatus(payTransDtlInfoDO.getPathProcStatus());
        EcnyTradeContext.getTempContext(context).put("oldStatus", oldStatus);

        EcnyTradeContext.setTxn(context, payTransDtlInfoDO);
        logger.info("initTxn：初始化金融登记簿成功，平台日期：{}，平台流水：{}",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
    }


    /**
     * 基础校验
     *
     * @param context
     */
    private void checkMsg(TradeContext<?, ?> context) {
        DCEPReqDTO<ReconvertReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        ReconvertReq reconvertReq = reqMsg.getBody().getReconvertReq();
        String trxBizTp = reconvertReq.getTrxInf().getTrxBizTp();
        String instdPtyId = reconvertReq.getGrpHdr().getInstdPty().getInstdDrctPty();
        String instgPtyId = reconvertReq.getGrpHdr().getInstgPty().getInstgDrctPty();

        //付款人钱包类型为“对公钱包”时，交易资金来源必填
        if (WalletTpCdEnum.WT09.getCode().equals(reconvertReq.getDbtrInf().getDbtrWltTp())
                && StringUtils.isEmpty(reconvertReq.getTrxInf().getTrxFndSrc())) {
            logger.error("付款人钱包类型为“对公钱包”时，交易资金来源必填");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.TRANSACTION_FUNDS_SOURCE_EMPTY);
        }

        //业务种类、业务类型校验
        if (!ParamConfigCheckTask.checkConfigValue(BUSINESS_TYPE,
                trxBizTp, reconvertReq.getTrxInf().getTrxCtgyPurpCd())
                || !AppConstant.BUSINESS_TYPE_RECONVERT.equals(trxBizTp)) {
            logger.error("业务种类、业务类型校验不通过");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.BUSINESS_TYPE_INVALID);
        }

        //判断接收机构是否广发银行 及校验机构状态
        if (!AppConstant.BANK_FINANCIAL_INSTITUTION_CD.equals(instdPtyId)) {
            logger.error("接收机构传输有误,{}", instdPtyId);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTD_ERROR);
        }
        boolean partyFlag = partyService.isAvailable(instdPtyId);
        if (!partyFlag) {
            logger.error("接收机构状态异常,{}", instdPtyId);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_STATUS_ERROR);
        }

        // 发起机构权限校验 根据：发起机构号+报文编号+业务类型+发送标识
        Boolean sendAuth = authInfoService.validateAuthInfo(instgPtyId, MsgTpEnum.DCEP221.getCode(), "", AuthInfoDrctEnum.sendAuth);
        if (!sendAuth) {
            logger.error("发送机构权限不足,{}", instgPtyId);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_POWER_ERROR);
        }
    }


    /**
     * 入账处理
     *
     * @param context
     */
    private void coreProcess(TradeContext<?, ?> context) {

        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTxn(context);

        // 初始化核心请求报文
        BankCore351100InnerReq bankCore351100InnerReq = sendCoreInit(payTransDtlInfoDO);

        // 核心前处理
        sendCorePre(payTransDtlInfoDO, bankCore351100InnerReq);

        //更新上核心前处理成功后的登记簿状态
        StateMachine oldStatus = new StateMachine();
        oldStatus.setPreTrxStatus(payTransDtlInfoDO.getTrxStatus());
        oldStatus.setPreCoreProcStatus(payTransDtlInfoDO.getCoreProcStatus());
        oldStatus.setPrePathProcStatus(payTransDtlInfoDO.getPathProcStatus());
        EcnyTradeContext.getTempContext(context).put("oldStatus", oldStatus);

        // 发送核心
        BankCore351100InnerRsp bankCore351100InnerRsp = bankCoreProcessService.sendToCore(bankCore351100InnerReq);

        // 核心后处理
        sendCoreDone(payTransDtlInfoDO, bankCore351100InnerRsp);

        //更新上核心后处理成功后的登记簿状态
        oldStatus.setPreTrxStatus(payTransDtlInfoDO.getTrxStatus());
        oldStatus.setPreCoreProcStatus(payTransDtlInfoDO.getCoreProcStatus());
        oldStatus.setPrePathProcStatus(payTransDtlInfoDO.getPathProcStatus());
        EcnyTradeContext.getTempContext(context).put("oldStatus", oldStatus);

        EcnyTradeContext.getTempContext(context).put("bankCore351100InnerRsp", bankCore351100InnerRsp);
    }

    /**
     * 上核心前处理
     * 初始化核心报文、插入核心流水表、更新金融登记簿核心流水及核心状态
     *
     * @param payTransDtlInfoDO
     */
    private BankCore351100InnerReq sendCoreInit(PayTransDtlInfoDO payTransDtlInfoDO) {
        logger.info("初始化核心报文");
        BankCore351100InnerReq bankCore351100InnerReq = bankCoreProcessService.initBankCore351100InnerReq(payTransDtlInfoDO);
        return bankCore351100InnerReq;
    }


    /**
     * 核心前处理，入账务流水表，更新登记簿为状态为处理中
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerReq
     */
    public void sendCorePre(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerReq bankCore351100InnerReq) {

        logger.info("核心前处理，入账务流水表，更新登记簿为状态为处理中开始");

        String coreReqDate = generateCodeService.getCoreReqDate(payTransDtlInfoDO.getBatchId());
        String coreReqSerno = generateCodeService.generateCoreReqSerno();

        bankCore351100InnerReq.setCoreReqDate(coreReqDate);
        bankCore351100InnerReq.setCoreReqSerno(coreReqSerno);

        // 设置核心日期流水
        payTransDtlInfoDO.setCoreReqDate(coreReqDate);
        payTransDtlInfoDO.setCoreReqSerno(coreReqSerno);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_CRDT);
        payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);

        //原状态297 --> 227
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_INIT);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);

        try {
            bankCoreProcessService.sendCorePre(payTransDtlInfoDO, bankCore351100InnerReq, stateMachine);
        } catch (Exception e) {
            logger.error("sendCorePre: 核心前处理异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.OTHER_TECH_ERROR);
        }

    }


    /**
     * 核心后处理，接收核心结果，更新对应状态
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     */
    public void sendCoreDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {
        logger.info("核心返回，核心状态：{}，核心返回码：{}，核心返回信息：{}",
                bankCore351100InnerRsp.getCoreStatus(), bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
        setTradeResult(payTransDtlInfoDO, bankCore351100InnerRsp);

        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_RECIPE);

        try {
            bankCoreProcessService.sendCoreDone(payTransDtlInfoDO, bankCore351100InnerRsp, stateMachine);
        } catch (Exception e) {
            logger.error("afterSendCoreDeal：接收核心结果，更新对应状态异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.OTHER_TECH_ERROR);
        }
    }

    /**
     * 设置核心返回结果
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     */
    private void setTradeResult(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {

        logger.info("设置核心返回结果开始");
        String coreStatus = bankCore351100InnerRsp.getCoreStatus();

        if (StringUtils.isBlank(bankCore351100InnerRsp.getCoreReqDate())) {
            bankCore351100InnerRsp.setCoreReqDate(payTransDtlInfoDO.getCoreReqDate());
        }
        if (StringUtils.isBlank(bankCore351100InnerRsp.getCoreReqSerno())) {
            bankCore351100InnerRsp.setCoreReqSerno(payTransDtlInfoDO.getCoreReqSerno());
        }

        payTransDtlInfoDO.setPayDate(payTransDtlInfoDO.getPayDate());
        payTransDtlInfoDO.setPaySerno(payTransDtlInfoDO.getPaySerno());
        payTransDtlInfoDO.setCoreProcStatus(coreStatus);
        payTransDtlInfoDO.setCoreAcctDate(bankCore351100InnerRsp.getCoreRspDate());
        payTransDtlInfoDO.setCoreSerno(bankCore351100InnerRsp.getCoreRspSerno());
        payTransDtlInfoDO.setCoreRetCode(bankCore351100InnerRsp.getErrorCode());
        payTransDtlInfoDO.setCoreRetMsg(bankCore351100InnerRsp.getErrorMsg());
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_CRDT);
        payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());

        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(Constant.CORE_SYS_ID, AppConstant.DCEP_SYS_ID, bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());

        switch (coreStatus) {
            case AppConstant.CORESTATUS_SUCCESS:
                logger.info("上核心入账成功");
                payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
                payTransDtlInfoDO.setTrxRetCode(EcnyTransError.SUCCESS.getErrorCode());
                payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
                payTransDtlInfoDO.setPayPathRspStatus(ProcessStsCdEnum.PR00.getCode());
                payTransDtlInfoDO.setPayPathRetCode(RejectCdEnum.SUCCESS.getCode());
                payTransDtlInfoDO.setPayPathRetMsg(RejectCdEnum.SUCCESS.getDesc());

                break;
            case AppConstant.CORESTATUS_FAILED:
                logger.info("上核心入账失败");
                payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_FAIL);
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
                payTransDtlInfoDO.setTrxRetCode(bankCore351100InnerRsp.getErrorCode());
                payTransDtlInfoDO.setTrxRetMsg(bankCore351100InnerRsp.getErrorMsg());
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
                payTransDtlInfoDO.setPayPathRspStatus(ProcessStsCdEnum.PR01.getCode());
                payTransDtlInfoDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
                payTransDtlInfoDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
                break;
            default:
                logger.error("核心异常");
                // 核心异常
                payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);
                payTransDtlInfoDO.setTrxRetCode(bankCore351100InnerRsp.getErrorCode());
                payTransDtlInfoDO.setTrxRetMsg(bankCore351100InnerRsp.getErrorMsg());
                payTransDtlInfoDO.setPayPathRspStatus(ProcessStsCdEnum.PR02.getCode());
                payTransDtlInfoDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
                payTransDtlInfoDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
                break;
        }

        logger.info("设置核心返回结果结束，核心日期：{}，核心请求流水：{}",
                bankCore351100InnerRsp.getCoreReqDate(), bankCore351100InnerRsp.getCoreReqSerno());
    }


    /**
     * 兑回响应报文封装
     *
     * @param context
     */
    private void packRspMsg(TradeContext<?, ?> context) {
        DCEPRspDTO<ReconvertRspDTO> dcepRspDTO = EcnyTradeContext.getRspMsg(context);
        RspsnInf rspsnInf = dcepRspDTO.getBody().getReconvertRsp().getRspsnInf();

        BankCore351100InnerRsp bankCore351100InnerRsp = (BankCore351100InnerRsp) EcnyTradeContext.getTempContext(context).get("bankCore351100InnerRsp");
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

        logger.info("兑回222应答报文的业务回执状态:{}", rspsnInf.getRspsnSts());
    }

    /**
     * 兑回异常处理
     *
     * @param context
     * @param exception
     */
    private void reconverErrHandler(TradeContext<?, ?> context, Throwable exception) {
        logger.error("兑回异常处理");

        // 获取响应报文
        DCEPRspDTO<ReconvertRspDTO> dcepRspDTO = EcnyTradeContext.getRspMsg(context);
        RspsnInf rspsnInf = dcepRspDTO.getBody().getReconvertRsp().getRspsnInf();

        // 初始化错误码，返回结果初始化为异常
        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(exception);
        rspsnInf.setRjctCd(rspCodeMapDO.getDestRspCode());
        rspsnInf.setRjctInf(rspCodeMapDO.getRspCodeDsp());

        // 获取流水表实体
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTxn(context);

        if (null != payTransDtlInfoDO) {

            StateMachine oldStatus = (StateMachine) EcnyTradeContext.getTempContext(context).get("oldStatus");

            PayTransDtlInfoDO updateDO = new PayTransDtlInfoDO();
            updateDO.setPayDate(payTransDtlInfoDO.getPayDate());
            updateDO.setPaySerno(payTransDtlInfoDO.getPaySerno());
            // 获取交易状态，即发生异常时，交易是失败还是异常，默认是异常
            if (exception instanceof EcnyTransException) {
                updateDO.setTrxStatus(((EcnyTransException) exception).getStatus());
                updateDO.setTrxRetCode(((EcnyTransException) exception).getErrorCode());
                updateDO.setTrxRetMsg(((EcnyTransException) exception).getErrorMsg());
            } else {
                updateDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
                updateDO.setTrxRetCode(EcnyTransError.OTHER_TECH_ERROR.getErrorCode());
                updateDO.setTrxRetMsg(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
            }

            // 若交易失败,返回失败
            if (AppConstant.TRXSTATUS_FAILED.equals(updateDO.getTrxStatus())) {
                rspsnInf.setRspsnSts(ProcessStsCdEnum.PR01.getCode());
                updateDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            }

            // 更新流水表
            updateDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
            updateDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
            updateDO.setPayPathRspStatus(rspsnInf.getRspsnSts());
            updateDO.setPayPathRetDate(DateUtil.getDefaultDate());

            try {
                // 更新金融交易表
                payTransDtlInfoService.update(updateDO, oldStatus);
            } catch (Exception e) {
                logger.error("兑回异常处理时更新交易状态异常：{}-{}", e.getMessage(), e);
                throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
            }

        } else {
            // 未入库，返回失败
            rspsnInf.setRspsnSts(ProcessStsCdEnum.PR01.getCode());
        }

        logger.info("兑回222应答报文的业务回执状态:{},错误码:{},错误信息:{}", rspsnInf.getRspsnSts(), rspsnInf.getRjctCd(), rspsnInf.getRjctInf());
    }
}
