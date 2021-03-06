package com.dcits.dcwlt.pay.online.flow.receive;

import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.CashboxTypeEnum;
import com.dcits.dcwlt.common.pay.enums.LogMonitorLevelCdEnum;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.common.pay.enums.RejectCdEnum;
import com.dcits.dcwlt.common.pay.enums.SummaryCdEnum;
import com.dcits.dcwlt.common.pay.sequence.service.IGenerateCodeService;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.util.AmountUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.cashbox.AdjInf;
import com.dcits.dcwlt.pay.api.domain.dcep.cashbox.Cashbox121ReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.cashbox.Cashbox123ReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.cashbox.CashboxRspsnInf;
import com.dcits.dcwlt.pay.api.domain.dcep.cashbox.ClrInf;
import com.dcits.dcwlt.pay.api.domain.dcep.cashbox.CshBoxAdjNtfctn;
import com.dcits.dcwlt.pay.api.domain.dcep.comconf.ComConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.RspsnInf;
import com.dcits.dcwlt.pay.api.domain.ecny.cashbox.CshBoxAdjAppl;
import com.dcits.dcwlt.pay.api.model.PayCashboxProcessDO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.baffle.dcep.impl.BankCoreImplDubboService;
import com.dcits.dcwlt.pay.online.base.Constant;
import com.dcits.dcwlt.pay.online.event.callback.ConvertBankRevCallBack;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.mapper.PayCashboxProcessMapper;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import com.dcits.dcwlt.pay.online.service.impl.CoreEventServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * ??????????????????????????????
 */
@Configuration
public class Cashbox123RTradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(Cashbox123RTradeFlow.class);

    private static final String CASHBOX_TRADE_FLOW = "Cashbox123RTradeFlow";

    private static final String ORIGINAL_MESSAGE_TYPE = "msgTp";

    private static final String PAYER_AMOUNT = "_payerAmount";

    @Autowired
    private PayCashboxProcessMapper payCashboxProcessMapper;

    @Autowired
    private IGenerateCodeService generateCodeService;

    @Autowired
    private IPayTransDtlInfoService iPayTransDtlInfo1Service;


    @Autowired
    private BankCoreImplDubboService bankCoreImplDubboService;

    @Autowired
    private CoreEventServiceImpl coreEventService;

    @Autowired
    private ICoreProcessService bankCoreProcessService;

    @Bean(name = CASHBOX_TRADE_FLOW)
    public TradeFlow cashboxTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .initRspMsg(this::initRspMsg)      //?????????????????????
                .initTxn(this::initTxnOld)         //????????????????????????
                .initTxn(this::initTxn)            //?????????????????????
                .process(this::updateCshboxPrs)    //??????????????????????????????
                .errHandler(this::errHandler)
                .build();
    }

    /**
     * ?????????????????????
     *
     * @param context
     */
    public void initRspMsg(TradeContext<?, ?> context) {

        DCEPReqDTO<Cashbox121ReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);

        //?????????????????????
        Cashbox121ReqDTO reqBody = reqMsg.getBody();
        CshBoxAdjAppl reqDTO = reqBody.getCshBoxAdjAppl();

        //???????????????????????????????????????????????????????????????
        String msgId = reqMsg.getBody().getCshBoxAdjAppl().getGrpHdr().getMsgId();
        context.getTempCtx().put(ORIGINAL_MESSAGE_TYPE, msgId);

        //???????????????
        GrpHdr grpHdr = GrpHdr.getInstance(reqMsg.getBody().getCshBoxAdjAppl().getGrpHdr());

        //????????????
        ComConfDTO comConfDTO = ComConfDTO.newInstance(grpHdr, reqMsg.getDcepHead(), "");

        //??????????????????
        DCEPRspDTO<ComConfDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP902.getCode(), comConfDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
    }

    /**
     * ??????????????????????????????
     *
     * @param context
     */
    private void updateCshboxPrs(TradeContext<?, ?> context) {

        //?????????????????????????????????
        DCEPReqDTO<Cashbox123ReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        logger.info("?????????????????????????????????????????????????????????{}", context.getTempCtx().get(ORIGINAL_MESSAGE_TYPE));
        try {
            Cashbox123ReqDTO cashbox123ReqDTO = reqMsg.getBody();
            CshBoxAdjNtfctn cshBoxAdjNtfctn = cashbox123ReqDTO.getCshBoxAdjNtfctn();

            GrpHdr grpHdr = cshBoxAdjNtfctn.getGrpHdr();
            OrgnlGrpHdr orgnlGrpHdr = cshBoxAdjNtfctn.getOrgnlGrpHdr();
            CashboxRspsnInf rspsnInf = cshBoxAdjNtfctn.getRspsnInf();
            AdjInf adjInf = cshBoxAdjNtfctn.getAdjInf();
            ClrInf clrInf = cshBoxAdjNtfctn.getClrInf();

            PayCashboxProcessDO payCashboxProcessDO = new PayCashboxProcessDO();
            payCashboxProcessDO.setMsgId(orgnlGrpHdr.getOrgnlMsgId());
            payCashboxProcessDO.setCreDtTmR(grpHdr.getCreDtTm());
            payCashboxProcessDO.setPrcSts(rspsnInf.getPrcSts());
            payCashboxProcessDO.setPrcCd(rspsnInf.getPrcCd());
            payCashboxProcessDO.setRspsnSts(rspsnInf.getRspsnSts());
            payCashboxProcessDO.setRjctCd(rspsnInf.getRjctCd());
            payCashboxProcessDO.setRjctInf(rspsnInf.getRjctInf());
            payCashboxProcessDO.setOprTp(adjInf.getOprTp());
            payCashboxProcessDO.setAmtCcy(adjInf.getAmt() != null ? adjInf.getAmt().getCcy() : null);
            payCashboxProcessDO.setAmtValue(adjInf.getAmt() != null ? adjInf.getAmt().getValue() : null);
            payCashboxProcessDO.setCoopBankInstnId(adjInf.getCoopBankInstnId());
            payCashboxProcessDO.setCshBoxInstnId(adjInf.getCshBoxInstnId());
            payCashboxProcessDO.setClrReptFlg(clrInf.getClrReptFlg());
            payCashboxProcessDO.setCdtDbtInd(clrInf.getCdtDbtInd());
            payCashboxProcessDO.setClearAmountCcy(clrInf.getClrAmt() != null ? clrInf.getClrAmt().getCcy() : null);
            payCashboxProcessDO.setClearAmountValue(clrInf.getClrAmt() != null ? clrInf.getClrAmt().getValue() : null);
            payCashboxProcessMapper.update(payCashboxProcessDO);

            if(ProcessStsCdEnum.PR00.getCode().equals(rspsnInf.getPrcSts()) || ProcessStsCdEnum.PR03.getCode().equals(rspsnInf.getPrcSts())){
                //??????
                PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(context).get("payTransDtlInfoDO");
                // ???????????????????????????
                BankCore351100InnerReq bankCore30410002Req = sendCoreInit(reqMsg);
                // ???????????????
                sendCorePre(payTransDtlInfoDO, bankCore30410002Req);
                // ????????????
                BankCore351100InnerRsp bankCore30410002Rsp = sendToCore(bankCore30410002Req);
                // ???????????????
                sendCoreDone(payTransDtlInfoDO, bankCore30410002Rsp, payCashboxProcessDO);
            }else {
                //?????????
                PayTransDtlInfoDO payTransDtlInfoDOOld = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(context).get("payTransDtlInfoDOOld");
                //??????
                bankRev(payTransDtlInfoDOOld);
            }
        } catch (Throwable e) {
            logger.warn("??????????????????{}????????????????????????????????????,????????????:{}",
                    context.getTempCtx().get(ORIGINAL_MESSAGE_TYPE),
                    e.getMessage(), e);
            throw e;
        }
    }

    /**
     * ???????????????????????????
     *
     * @param dcepReqDTO
     * @return
     */
    /**
     * ???????????????????????????
     *
     * @param
     * @return
     */
    public BankCore351100InnerReq sendCoreInit(DCEPReqDTO<Cashbox123ReqDTO> dcepReqDTO) {

        BankCore351100InnerReq bankCore351100InnerReq = new BankCore351100InnerReq();
        AdjInf adjInf = dcepReqDTO.getBody().getCshBoxAdjNtfctn().getAdjInf();
        bankCore351100InnerReq.setBookType(com.dcits.dcwlt.common.pay.constant.Constant.BANKCORE_DEBIT);//????????????
        bankCore351100InnerReq.setAmount(AmountUtil.fenToYuan(adjInf.getAmt().getValue()));
        bankCore351100InnerReq.setCurrency(com.dcits.dcwlt.common.pay.constant.Constant.CURRENCY_CODE_156);
        if(adjInf.getOprTp().equals(CashboxTypeEnum.OT00.getCode())) {
            // todo ????????????????????????????????????
            bankCore351100InnerReq.setPayerAcct("1");     //???????????????
            bankCore351100InnerReq.setPayerName("2");     //???????????????
            bankCore351100InnerReq.setPayeeAcct("2");   // ????????????
            bankCore351100InnerReq.setPayeeName("3");   //????????????
        }else if(adjInf.getOprTp().equals(CashboxTypeEnum.OT01.getCode())){
            bankCore351100InnerReq.setPayerAcct("1");     //???????????????
            bankCore351100InnerReq.setPayerName("2");     //???????????????
            bankCore351100InnerReq.setPayeeAcct("2");   // ????????????
            bankCore351100InnerReq.setPayeeName("3");   //????????????
        }
        bankCore351100InnerReq.setChkNameFlg1("Y");
        return bankCore351100InnerReq;
    }

    /**
     * ???????????????
     *
     * @param payTransDtlInfoDO
     * @param bankCore30410002Req
     */
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
            logger.error("?????????????????????{}-{}-{}", LogMonitorLevelCdEnum.ECNY_LOG_MONITOR_NORMAL.getCode(), e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.GATEWAY_REQUEST_ERROR);
        }
        return bankCore351100InnerRsp;
    }

    /**
     * ??????????????????bankCore351100InnerReq.setPayPath
     *
     * @param payTransDtlInfoDO
     */
    public void bankRev(PayTransDtlInfoDO payTransDtlInfoDO) {
        coreEventService.registerBankRev(payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), ConvertBankRevCallBack.class);
    }

    /**
     * ???????????????
     *
     * @param payTransDtlInfoDO
     * @param bankCore30410002Rsp
     */
    public void sendCoreDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore30410002Rsp, PayCashboxProcessDO payCashboxProcessDO) {
        logger.info("???????????????");
        // ????????????????????????
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_INIT);
        // ??????????????????
        setTradeResult(payTransDtlInfoDO, bankCore30410002Rsp);
        try {
            bankCoreProcessService.sendCoreDone(payTransDtlInfoDO, bankCore30410002Rsp,stateMachine);
            payCashboxProcessDO.setCoreSts(AppConstant.CORESTATUS_SUCCESS);
        } catch (Exception e) {
            // ????????????????????????
            bankRev(payTransDtlInfoDO);
            payCashboxProcessDO.setCoreSts(AppConstant.CORESTATUS_FAILED);
            logger.error("????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
        }
        //?????????????????????????????????????????????????????????
        if (AppConstant.CORESTATUS_FAILED.equals(payTransDtlInfoDO.getCoreProcStatus())) {       //bankCore30410002Rsp.getCoreStatus()
            logger.error("????????????");
            payCashboxProcessDO.setCoreSts(AppConstant.CORESTATUS_FAILED);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, com.dcits.dcwlt.common.pay.constant.Constant.CORE_SYS_ID, bankCore30410002Rsp.getErrorCode(), bankCore30410002Rsp.getErrorMsg());
        } else if (AppConstant.CORESTATUS_ABEND.equals(payTransDtlInfoDO.getCoreProcStatus())) {  //bankCore30410002Rsp.getCoreStatus()
            logger.error("????????????");
            payCashboxProcessDO.setCoreSts(AppConstant.CORESTATUS_FAILED);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, com.dcits.dcwlt.common.pay.constant.Constant.CORE_SYS_ID, bankCore30410002Rsp.getErrorCode(), bankCore30410002Rsp.getErrorMsg());
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
     * ?????????????????????
     *
     * @param tradeContext
     */
    public void initTxnOld(TradeContext<?, ?> tradeContext) {

        DCEPReqDTO<Cashbox123ReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        // ??????????????????
        GrpHdr grpHdr = reqMsg.getBody().getCshBoxAdjNtfctn().getGrpHdr();
        ClrInf clrInf = reqMsg.getBody().getCshBoxAdjNtfctn().getClrInf();
        AdjInf adjInf = reqMsg.getBody().getCshBoxAdjNtfctn().getAdjInf();
        RspsnInf rspsnInf = reqMsg.getBody().getCshBoxAdjNtfctn().getRspsnInf();
        OrgnlGrpHdr orgnlGrpHdr = reqMsg.getBody().getCshBoxAdjNtfctn().getOrgnlGrpHdr();

        PayCashboxProcessDO params = new PayCashboxProcessDO();
        params.setMsgId(orgnlGrpHdr.getOrgnlMsgId());
        List<PayCashboxProcessDO> list = payCashboxProcessMapper.query(params);
        if(list == null || list.size() == 0){
            return;
        }
        PayCashboxProcessDO payCashboxProcessDO = list.get(0);

        PayTransDtlInfoDO payTransDtlInfoDO = new PayTransDtlInfoDO();
        payTransDtlInfoDO.setPayDate(payCashboxProcessDO.getCorePaydate());
        payTransDtlInfoDO.setPaySerno(payCashboxProcessDO.getCorepaySerno());
        payTransDtlInfoDO.setBatchId(payCashboxProcessDO.getCoreBatchId());
        payTransDtlInfoDO.setMsgType(MsgTpEnum.DCEP121.getCode());

        //?????????context???
        EcnyTradeContext.getTempContext(tradeContext).put("payTransDtlInfoDOOld", payTransDtlInfoDO);

    }

    /**
     * ?????????????????????
     *
     * @param tradeContext
     */
    public void initTxn(TradeContext<?, ?> tradeContext) {

        DCEPReqDTO<Cashbox123ReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        // ??????????????????
        GrpHdr grpHdr = reqMsg.getBody().getCshBoxAdjNtfctn().getGrpHdr();
        ClrInf clrInf = reqMsg.getBody().getCshBoxAdjNtfctn().getClrInf();
        AdjInf adjInf = reqMsg.getBody().getCshBoxAdjNtfctn().getAdjInf();
        RspsnInf rspsnInf = reqMsg.getBody().getCshBoxAdjNtfctn().getRspsnInf();
        OrgnlGrpHdr orgnlGrpHdr = reqMsg.getBody().getCshBoxAdjNtfctn().getOrgnlGrpHdr();

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
        payTransDtlInfoDO.setBatchId(generateCodeService.getBachNo());
        payTransDtlInfoDO.setMsgType(MsgTpEnum.DCEP123.getCode());
//        payTransDtlInfoDO.setBusiType(trxInf.getTrxBizTp());
//        payTransDtlInfoDO.setBusiKind(trxInf.getTrxCtgyPurpCd());
        payTransDtlInfoDO.setInstgPty(grpHdr.getInstgPty().getInstgDrctPty());
        payTransDtlInfoDO.setInstdPty(grpHdr.getInstdPty().getInstdDrctPty());
        payTransDtlInfoDO.setCcy(adjInf.getAmt().getCcy());
        payTransDtlInfoDO.setAmount(AmountUtil.yuanToFen(adjInf.getAmt().getValue()));
//        payTransDtlInfoDO.setTradePurpose(trxInf.getTrxPrps());
//        payTransDtlInfoDO.setPayerPtyId(dbtrInf.getDbtrPtyId());
//        payTransDtlInfoDO.setPayerName(dbtrInf.getDbtrNm());
//        payTransDtlInfoDO.setPayerAcctType(dbtrInf.getDbtAcctTp());
//        payTransDtlInfoDO.setPayerAcct(dbtrInf.getDbtrAcct());
//        payTransDtlInfoDO.setPayeePtyId(cdtrInf.getCdtrPtyId());
//        payTransDtlInfoDO.setPayeeName(cdtrInf.getCdtrNm());
//        payTransDtlInfoDO.setPayeeWalletId(cdtrInf.getCdtrWltId());
//        payTransDtlInfoDO.setPayeeWalletName(cdtrInf.getCdtrWltNm());
//        payTransDtlInfoDO.setPayeeWalletLv(cdtrInf.getCdtrWltLvl());
//        payTransDtlInfoDO.setPayeeWalletType(cdtrInf.getCdtrWltTp());
//        payTransDtlInfoDO.setProtocolNum(dbtrInf.getSgnNo());
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
     * @param context
     * @param e
     */
    private void errHandler(TradeContext<?, ?> context, Throwable e) {
        DCEPReqDTO<Cashbox123ReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        Cashbox123ReqDTO cashbox123ReqDTO = reqMsg.getBody();
        CshBoxAdjNtfctn cshBoxAdjNtfctn = cashbox123ReqDTO.getCshBoxAdjNtfctn();

        GrpHdr grpHdr = cshBoxAdjNtfctn.getGrpHdr();
        OrgnlGrpHdr orgnlGrpHdr = cshBoxAdjNtfctn.getOrgnlGrpHdr();

        PayCashboxProcessDO payCashboxProcessDO = new PayCashboxProcessDO();
        payCashboxProcessDO.setMsgId(orgnlGrpHdr.getOrgnlMsgId());
        payCashboxProcessDO.setCreDtTmR(grpHdr.getCreDtTm());
        payCashboxProcessDO.setPrcSts(ProcessStsCdEnum.PR02.getCode());

        payCashboxProcessMapper.update(payCashboxProcessDO);
    }
}
