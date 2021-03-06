package com.dcits.dcwlt.pay.online.flow.send;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.enums.CashboxTypeEnum;
import com.dcits.dcwlt.common.pay.enums.LogMonitorLevelCdEnum;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.common.pay.enums.RejectCdEnum;
import com.dcits.dcwlt.common.pay.enums.SummaryCdEnum;
import com.dcits.dcwlt.common.pay.enums.TrxStatusEnum;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.type.OutOrgTypeEnum;
import com.dcits.dcwlt.common.pay.util.AmountUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.cashbox.Cashbox121ReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.Amt;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspHead;
import com.dcits.dcwlt.pay.api.domain.ecny.cashbox.AdjInf;
import com.dcits.dcwlt.pay.api.domain.ecny.cashbox.CshBoxAdjAppl;
import com.dcits.dcwlt.pay.api.domain.ecny.cashbox.EcnyCashboxRspDTO;
import com.dcits.dcwlt.pay.api.model.PayCashboxProcessDO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.baffle.dcep.impl.BankCoreImplDubboService;
import com.dcits.dcwlt.pay.online.event.callback.ConvertBankRevCallBack;
import com.dcits.dcwlt.pay.online.event.service.BankRevService;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.mapper.PayCashboxProcessMapper;
import com.dcits.dcwlt.pay.online.mapper.PayTransDtlInfoMapper;
import com.dcits.dcwlt.pay.online.mapper.PayTransDtlNonfMapper;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.impl.CoreEventServiceImpl;
import com.dcits.dcwlt.pay.online.service.impl.DcepSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author
 * @version 1.0.0
 * description ????????????????????????
 * @date 2021/1/5
 */
@Configuration
public class CashBox121STradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(CashBox121STradeFlow.class);

    public static final String CASHBOX_TRADE_FLOW = "CashBox121STradeFlow";

    private static final String DCEP_REQ_DTO = "dcepReqDTO";

    private static final String DCEP_RSP_DTO = "dcepRspDTO";

    private static final String RES_DESC_SUCCESS = "??????";

    private static final String RES_DESC_FAILED = "??????";

    private static final String SEND_DIRECT = "S";

    private static final String RES_STS_SUCCESS = "success";

    private static final String RES_STS_FAILED = "failed";

    private static final String INSERT_JRN_DO = "insertJrnDO";

    private static final String UPDATE_JRN_DO = "updateJrnDO";

    private static final String PROCESS_DO = "processDO";

    private static final String PAYER_AMOUNT = "_payerAmount";

    @Autowired
    private PayTransDtlNonfMapper payTransDtlNonfmapper;

    @Autowired
    private DcepSendService dcepSendService;


    @Autowired
    private ICoreProcessService bankCoreProcessService;

    @Autowired
    private CoreEventServiceImpl coreEventService;

    @Autowired
    private BankCoreImplDubboService bankCoreImplDubboService;

    @Autowired
    private PayTransDtlInfoMapper payTransDtlInfoMapper;

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

    @Autowired
    private PayCashboxProcessMapper payCashboxProcessMapper;

    @Bean(name = CASHBOX_TRADE_FLOW)
    public TradeFlow cashboxTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .process(this::initRsp)                                                            //?????????????????????
                .process(this::initReq)                                                            //?????????????????????
                .process(this::initJrn)                                                            //???????????????
                .saveTxn(this::insertJrnDO)                                                        //????????????
                .initTxn(this::initTxn)                                                            //?????????????????????
                .process(this::insertCshboxPrs)                                                    //??????????????????????????????
                //.process(this::coreProcess)                                                        //?????????
                //.process(this::sendDCEP)                                                           //???????????????????????????
                //.response(this::packRspMsg)                                                        //??????????????????
                .updateTxn(this::updateJrnDO)                                                      //??????????????????
                .errHandler(this::errHandle)
                .build();
    }

    /**
     * ?????????????????????
     *
     * @param tradeContext ???????????????
     */
    private void initRsp(TradeContext<?, ?> tradeContext) {
        // ??????????????????
        ECNYRspHead head = new ECNYRspHead();
        head.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
        ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(null, head, null, EcnyTransError.OTHER_TECH_ERROR.getErrorCode(), EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
        EcnyCashboxRspDTO rspBody = new EcnyCashboxRspDTO();
        rspBody.setPrcDesc(RES_DESC_FAILED);
        rspBody.setPrcSts(RES_STS_FAILED);
        ecnyRspDTO.setBody(rspBody);
        EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
    }

    /**
     * ?????????????????????
     *
     * @param tradeContext ???????????????
     */
    private void initReq(TradeContext<?, ?> tradeContext) {
        Map params = EcnyTradeContext.getReqMsg(tradeContext);

        //?????????????????????
        CshBoxAdjAppl cshBoxAdjAppl = new CshBoxAdjAppl();
        AdjInf adjInf = new AdjInf();

        //???params??????
        String oprTp = (String) params.get("OprTp");//??????????????????
        //??????????????????????????????
        String cdtDbtInd = CashboxTypeEnum.OT00.getCode().equals(oprTp) ? CashboxTypeEnum.DBIT.getCode() : CashboxTypeEnum.CRDT.getCode();
        // todo ????????????156
        //String amtCcy = (String) params.get("AmtCcy");//??????/????????????
        String amtCcy = "156";
        String amtValue = (String) params.get("AmtValue");//??????/????????????
        String cshBoxInstnId = (String) params.get("CshBoxInstnId");//????????????????????????
        String MsgId = (String) params.get("MsgId");//??????ID
        //?????????????????????????????? todo
        //String coopBankInstnId = PayCommParamTask.getPayCommParamVal("data", "OWN_PARTY_ID");
        String coopBankInstnId = "";
        //????????????????????????ID
        //String coopBankWltId = PayCommParamTask.getPayCommParamVal("data", "OWN_CASHBOX_ID");
        String coopBankWltId = "";

        adjInf.setOprTp(oprTp);
        adjInf.setCdtDbtInd(cdtDbtInd);
        Amt amt = new Amt();
        amt.setCcy(amtCcy);
        amt.setValue(amtValue);
        adjInf.setAmt(amt);
        adjInf.setCshBoxInstnId(cshBoxInstnId);
        adjInf.setCoopBankInstnId(coopBankInstnId);
        adjInf.setCoopBankWltId(coopBankWltId);
        cshBoxAdjAppl.setAdjInf(adjInf);

        //????????????
        GrpHdr grpHdr = GrpHdr.getInstance(generateCodeService.generateMsgId(OutOrgTypeEnum.OutOrg, MsgTpEnum.DCEP121.getCode().substring(5, 8), ""));
        cshBoxAdjAppl.setGrpHdr(grpHdr);
        if(MsgId != null && !"".equals(MsgId)){
            //?????? ????????????msgid
            cshBoxAdjAppl.getGrpHdr().setMsgId(MsgId);
        }
        //?????????ReqDTO
        Cashbox121ReqDTO cashbox121ReqDTO = new Cashbox121ReqDTO();
        cashbox121ReqDTO.setCshBoxAdjAppl(cshBoxAdjAppl);
        //????????????????????????Context???
        DCEPReqDTO<Cashbox121ReqDTO> dcepReqDTO = DCEPReqDTO.newInstance(MsgTpEnum.DCEP121.getCode(), generateCodeService.generateMsgSN(grpHdr.getMsgId()), cashbox121ReqDTO);
//        dcepReqDTO.getHead().setSrvcCode(ApiConstant.DCEP_OUT_SERVICE_NAME);
        EcnyTradeContext.getTempContext(tradeContext).put(DCEP_REQ_DTO, dcepReqDTO);
    }

    /**
     * ?????????????????????
     *
     * @param tradeContext
     */
    public void initTxn(TradeContext<?, ?> tradeContext) {
        //    JSONObject.parseObject(tradeContext.,JSONObject.class);
        Map<String, Object> contextMap = EcnyTradeContext.getTempContext(tradeContext);
        DCEPReqDTO<Cashbox121ReqDTO> dcepReqDTO = (DCEPReqDTO<Cashbox121ReqDTO>) contextMap.get(DCEP_REQ_DTO);
        GrpHdr grpHdr = dcepReqDTO.getBody().getCshBoxAdjAppl().getGrpHdr();
        AdjInf adjInf = dcepReqDTO.getBody().getCshBoxAdjAppl().getAdjInf();
        // ??????????????????
        //      ESBReqSysHeader esbReqSysHeader = reqMsg.getEsbReqSysHeader();
        //      PayConvertChnlReqDTO reqBody = reqMsg.getBody();
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
        payTransDtlInfoDO.setBusiSysDate(DateUtil.getDefaultDate());       //??????????????????
        payTransDtlInfoDO.setBusiSysTime(DateUtil.getDefaultTime());          //  ??????????????????
        payTransDtlInfoDO.setBusiSysSerno(grpHdr.getMsgId());     //     ??????????????????

        payTransDtlInfoDO.setMsgType(MsgTpEnum.DCEP121.getCode());
        payTransDtlInfoDO.setBusiType(adjInf.getOprTp());
        payTransDtlInfoDO.setBusiKind(adjInf.getCdtDbtInd());
        payTransDtlInfoDO.setInstgPty(grpHdr.getInstgPty().getInstgDrctPty());
        payTransDtlInfoDO.setInstdPty(grpHdr.getInstdPty().getInstdDrctPty());
        payTransDtlInfoDO.setCcy(AppConstant.CURRENCY_SYMBOL);
        payTransDtlInfoDO.setAmount(AmountUtil.yuanToFen(adjInf.getAmt().getValue()));
        payTransDtlInfoDO.setPayerPtyId(adjInf.getCshBoxInstnId());
        payTransDtlInfoDO.setPayeePtyId(adjInf.getCoopBankInstnId());
        payTransDtlInfoDO.setPayeeWalletId(adjInf.getCoopBankWltId());
        payTransDtlInfoDO.setPayeeWalletName(adjInf.getCoopBankInstnId());
//        payTransDtlInfoDO.setPayeeWalletLv(reqBody.getWalletLevel());
//        payTransDtlInfoDO.setPayeeWalletType(reqBody.getWalletType());
//        payTransDtlInfoDO.setTellerNo(esbReqSysHeader.get);
//        payTransDtlInfoDO.setZoneNo(ecnyHead.getZoneno());
//        payTransDtlInfoDO.setBrno(ecnyHead.getBrno());
//        payTransDtlInfoDO.setAcctBrno(ecnyHead.getBrno());
//        payTransDtlInfoDO.setOrigChnl(esbReqSysHeader.getSvcCd());
//        payTransDtlInfoDO.setOrigChnl2(esbReqSysHeader.getScnCd());
//        payTransDtlInfoDO.setOrigChnlDtl(ecnyHead.getOrigChnlDtl());
        payTransDtlInfoDO.setSummary(SummaryCdEnum.XSG.getCode());
        payTransDtlInfoDO.setLastUpJrnno(paySerno);
        payTransDtlInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setLastUpTime(DateUtil.getDefaultTime());
//        payTransDtlInfoDO.setRemark(reqBody.getRemark());
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
     * ?????????
     *
     * @param tradeContext
     */
    public void coreProcess(TradeContext<?, ?> tradeContext) {
        Map<String, Object> contextMap = EcnyTradeContext.getTempContext(tradeContext);
        DCEPReqDTO<Cashbox121ReqDTO> dcepReqDTO = (DCEPReqDTO<Cashbox121ReqDTO>) contextMap.get(DCEP_REQ_DTO);
        PayCashboxProcessDO payCashboxProcessDO = (PayCashboxProcessDO) contextMap.get(PROCESS_DO);
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) contextMap.get("payTransDtlInfoDO");
        // ???????????????????????????
        BankCore351100InnerReq bankCore30410002Req = sendCoreInit(dcepReqDTO);
        // ???????????????
        sendCorePre(payTransDtlInfoDO, bankCore30410002Req);
        // ????????????
        BankCore351100InnerRsp bankCore30410002Rsp = sendToCore(bankCore30410002Req);
        // ???????????????
        sendCoreDone(payTransDtlInfoDO, bankCore30410002Rsp, payCashboxProcessDO);
    }

    /**
     * ???????????????????????????
     *
     * @param
     * @return
     */
    public BankCore351100InnerReq sendCoreInit(DCEPReqDTO<Cashbox121ReqDTO> dcepReqDTO) {

        BankCore351100InnerReq bankCore351100InnerReq = new BankCore351100InnerReq();
        AdjInf adjInf = dcepReqDTO.getBody().getCshBoxAdjAppl().getAdjInf();
        bankCore351100InnerReq.setBookType(Constant.BANKCORE_DEBIT);//????????????
        bankCore351100InnerReq.setAmount(AmountUtil.fenToYuan(adjInf.getAmt().getValue()));
        bankCore351100InnerReq.setCurrency(Constant.CURRENCY_CODE_156);
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
     * @param
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
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, Constant.CORE_SYS_ID, bankCore30410002Rsp.getErrorCode(), bankCore30410002Rsp.getErrorMsg());
        } else if (AppConstant.CORESTATUS_ABEND.equals(payTransDtlInfoDO.getCoreProcStatus())) {  //bankCore30410002Rsp.getCoreStatus()
            logger.error("????????????");
            payCashboxProcessDO.setCoreSts(AppConstant.CORESTATUS_FAILED);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, Constant.CORE_SYS_ID, bankCore30410002Rsp.getErrorCode(), bankCore30410002Rsp.getErrorMsg());
        }
    }

    /**
     * ??????????????????
     *
     * @param payTransDtlInfoDO
     * @param
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
            RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(com.dcits.dcwlt.pay.online.base.Constant.CORE_SYS_ID, com.dcits.dcwlt.pay.online.base.Constant.DCEP_SYS_ID, bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
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
            RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(com.dcits.dcwlt.pay.online.base.Constant.CORE_SYS_ID, com.dcits.dcwlt.pay.online.base.Constant.DCEP_SYS_ID, bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());
            payTransDtlInfoDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
            payTransDtlInfoDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
        }
    }

    /**
     * ??????????????????
     *
     * @param tradeContext ???????????????
     */
    public void sendDCEP(TradeContext<?, ?> tradeContext) {
        Map<String, Object> contextMap = EcnyTradeContext.getTempContext(tradeContext);
        DCEPReqDTO<Cashbox121ReqDTO> dcepReqDTO = (DCEPReqDTO<Cashbox121ReqDTO>) contextMap.get(DCEP_REQ_DTO);
        JSONObject rspObj = null;
        try {
            logger.info("????????????:{}", dcepReqDTO);
            rspObj = dcepSendService.sendDcep(dcepReqDTO);
            //rspObj = dcepSendService.receive920(dcepReqDTO);
        } catch (Exception e) {
            logger.error("????????????????????????????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.OTHER_TECH_ERROR);
        }
        EcnyTradeContext.getTempContext(tradeContext).put(DCEP_RSP_DTO, rspObj);
    }

    /**
     * ???????????????
     *
     * @param tradeContext ???????????????
     */
    private void packRspMsg(TradeContext<?, ?> tradeContext) {
        JSONObject jsonObject = (JSONObject) EcnyTradeContext.getTempContext(tradeContext).get(DCEP_RSP_DTO);
        String prcSts = "";
        try {
            prcSts = jsonObject.getJSONObject("BODY").getJSONObject("CmonConf").getJSONObject("CmonConfInf").getString("PrcSts");
        } catch (Exception e) {
            logger.error("?????????????????????????????????{}???{}", EcnyTransError.GET_RSP_INFO_ERROR.getErrorCode(), EcnyTransError.GET_RSP_INFO_ERROR.getErrorMsg());
            throw new EcnyTransException(EcnyTransError.GET_RSP_INFO_ERROR);
        }
        ECNYRspDTO rspMsg = EcnyTradeContext.getRspMsg(tradeContext);
        EcnyCashboxRspDTO rspBody = (EcnyCashboxRspDTO) rspMsg.getBody();

        PayTransDtlNonfDO msgUpdDO = (PayTransDtlNonfDO) tradeContext.getTempCtx().get(UPDATE_JRN_DO);

        PayCashboxProcessDO payCashboxProcessDO = (PayCashboxProcessDO) tradeContext.getTempCtx().get(PROCESS_DO);
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) tradeContext.getTempCtx().get("payTransDtlInfoDO");

        if (ProcessStsCdEnum.PR00.getCode().equals(prcSts)) {
            rspBody.setPrcDesc(RES_DESC_SUCCESS);
            rspBody.setPrcSts(RES_STS_SUCCESS);

            msgUpdDO.setProcStatus(ProcessStsCdEnum.PR00.getCode());
            msgUpdDO.setTradeStatus(TrxStatusEnum.SUCCESS.getCode());
        }else{
            rspBody.setPrcDesc(RES_DESC_FAILED);
            rspBody.setPrcSts(RES_STS_FAILED);

            msgUpdDO.setProcStatus(ProcessStsCdEnum.PR01.getCode());
            msgUpdDO.setTradeStatus(TrxStatusEnum.FAIL.getCode());

            payCashboxProcessDO.setCoreSts(AppConstant.CORESTATUS_FAILED);

            //????????????
            bankRev(payTransDtlInfoDO);
        }

        //????????????????????????????????????
        payCashboxProcessMapper.update(payCashboxProcessDO);
    }

    /**
     * ????????????
     *
     * @param tradeContext ???????????????
     * @param throwable    ???????????????
     */
    private void errHandle(TradeContext<?, ?> tradeContext, Throwable throwable) {
        PayTransDtlNonfDO updatePayTransDtlNonfDO = (PayTransDtlNonfDO) tradeContext.getTempCtx().get(UPDATE_JRN_DO);
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) tradeContext.getTempCtx().get("payTransDtlInfoDO");
        updatePayTransDtlNonfDO.setProcStatus(ProcessStsCdEnum.PR01.getCode());
        ECNYRspDTO rspDTO = EcnyTradeContext.getRspMsg(tradeContext);
        //???????????????
        EcnyCashboxRspDTO rspBody = (EcnyCashboxRspDTO) rspDTO.getBody();
        rspBody.setPrcSts(RES_STS_FAILED);
        rspBody.setPrcDesc(RES_DESC_FAILED);

        //????????????
        bankRev(payTransDtlInfoDO);

        //???????????????????????????
        if (throwable instanceof EcnyTransException) {
            updatePayTransDtlNonfDO.setRejectCode(((EcnyTransException) throwable).getErrorCode());
            updatePayTransDtlNonfDO.setRejectInfo(((EcnyTransException) throwable).getErrorMsg());

        } else {
            updatePayTransDtlNonfDO.setRejectCode(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
            updatePayTransDtlNonfDO.setRejectInfo(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());

        }
        //?????????????????????????????????
        updatePayTransDtlNonfDO.setTradeStatus(TrxStatusEnum.FAIL.getCode());
        try {
            payTransDtlNonfmapper.updatePayTransDtlNonf(updatePayTransDtlNonfDO);
        } catch (Exception e) {
            logger.error("????????????????????????????????????{}", e);
        } finally {
            rspBody.setPrcSts(RES_STS_FAILED);
            rspBody.setPrcDesc(RES_DESC_FAILED);
        }

        //????????????????????????????????????
        PayCashboxProcessDO payCashboxProcessDO = (PayCashboxProcessDO) tradeContext.getTempCtx().get(PROCESS_DO);
        payCashboxProcessDO.setPrcSts(ProcessStsCdEnum.PR01.getCode());

        payCashboxProcessMapper.update(payCashboxProcessDO);
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param tradeContext ???????????????
     */
    private void initJrn(TradeContext<?, ?> tradeContext) {
        Map<String, Object> contextMap = tradeContext.getTempCtx();
        DCEPReqDTO<Cashbox121ReqDTO> dcepReqDTO = (DCEPReqDTO<Cashbox121ReqDTO>) contextMap.get(DCEP_REQ_DTO);
        //????????????????????????
        PayTransDtlNonfDO insertPayTransDtlNonfDO = buildPayTransDtlNonfDO(dcepReqDTO);
        //????????????????????????????????????  todo
        insertPayTransDtlNonfDO.setTlrNo("000000");
        PayTransDtlNonfDO updatePayTransDtlNonfDO = new PayTransDtlNonfDO();
        BeanUtils.copyProperties(insertPayTransDtlNonfDO, updatePayTransDtlNonfDO);
        //?????????????????????????????????????????????context???
        contextMap.put(INSERT_JRN_DO, insertPayTransDtlNonfDO);
        contextMap.put(UPDATE_JRN_DO, updatePayTransDtlNonfDO);
    }

    private PayTransDtlNonfDO buildPayTransDtlNonfDO(DCEPReqDTO<Cashbox121ReqDTO> dcepReqDTO) {
        Cashbox121ReqDTO cashbox121ReqDTO = dcepReqDTO.getBody();
        PayTransDtlNonfDO payTransDtlNonfDO = new PayTransDtlNonfDO();
        String curDate = DateUtil.getDefaultDate();
        payTransDtlNonfDO.setPayDate(curDate);
        payTransDtlNonfDO.setPaySerNo(generateCodeService.generatePlatformFlowNo());
        payTransDtlNonfDO.setPayTime(DateUtil.getDefaultTime());
        payTransDtlNonfDO.setPkgNo(MsgTpEnum.DCEP121.getCode());
        payTransDtlNonfDO.setMsgId(cashbox121ReqDTO.getCshBoxAdjAppl().getGrpHdr().getMsgId());
        payTransDtlNonfDO.setSenderDateTime(DateUtil.getSysTime());
        payTransDtlNonfDO.setTradeStatus(TrxStatusEnum.PROCESSING.getCode());
        payTransDtlNonfDO.setDrct(SEND_DIRECT);
        payTransDtlNonfDO.setInstgDrctPty(AppConstant.BANK_FINANCIAL_INSTITUTION_CD);
        payTransDtlNonfDO.setInstdDrctPty(AppConstant.DCEP_FINANCIAL_INSTITUTION_CD);
        //??????????????????????????????????????????????????????????????????????????????
        payTransDtlNonfDO.setProcStatus(ProcessStsCdEnum.PR02.getCode());
        payTransDtlNonfDO.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlNonfDO.setLastUpTime(DateUtil.getDefaultTime());
        return payTransDtlNonfDO;
    }

    /**
     * ???????????????????????????
     *
     * @param tradeContext ???????????????
     */
    private void insertJrnDO(TradeContext<?, ?> tradeContext) {
        PayTransDtlNonfDO insertPayTransDtlNonfDO = (PayTransDtlNonfDO) tradeContext.getTempCtx().get(INSERT_JRN_DO);
        try {
            payTransDtlNonfmapper.insertPayTransDtlNonf(insertPayTransDtlNonfDO);
        } catch (Exception e) {
            logger.error("??????????????????????????????{}", e);
            throw e;
        }
    }

    /**
     * ???????????????????????????
     *
     * @param tradeContext ???????????????
     */
    private void updateJrnDO(TradeContext<?, ?> tradeContext) {
        PayTransDtlNonfDO updatePayTransDtlNonfDO = (PayTransDtlNonfDO) tradeContext.getTempCtx().get(UPDATE_JRN_DO);
        try {
            payTransDtlNonfmapper.updatePayTransDtlNonf(updatePayTransDtlNonfDO);
        } catch (Exception e) {
            logger.error("??????????????????????????????{}", e);
            throw e;
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @param tradeContext
     */
    private void insertCshboxPrs(TradeContext<?, ?> tradeContext){
        Map<String, Object> contextMap = EcnyTradeContext.getTempContext(tradeContext);
        DCEPReqDTO<Cashbox121ReqDTO> dcepReqDTO = (DCEPReqDTO<Cashbox121ReqDTO>) contextMap.get(DCEP_REQ_DTO);
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) contextMap.get("payTransDtlInfoDO");

        GrpHdr grpHdr = dcepReqDTO.getBody().getCshBoxAdjAppl().getGrpHdr();
        AdjInf adjInf = dcepReqDTO.getBody().getCshBoxAdjAppl().getAdjInf();

        PayCashboxProcessDO payCashboxProcessDO = new PayCashboxProcessDO();
        payCashboxProcessDO.setMsgId(grpHdr.getMsgId());
        payCashboxProcessDO.setOprTp(adjInf.getOprTp());
        payCashboxProcessDO.setCdtDbtInd(adjInf.getCdtDbtInd());
        payCashboxProcessDO.setAmtCcy(adjInf.getAmt().getCcy());
        payCashboxProcessDO.setAmtValue(adjInf.getAmt().getValue());
        payCashboxProcessDO.setCoopBankInstnId(adjInf.getCoopBankInstnId());
        payCashboxProcessDO.setCoopBankWltId(adjInf.getCoopBankWltId());
        payCashboxProcessDO.setCshBoxInstnId(adjInf.getCshBoxInstnId());
        payCashboxProcessDO.setCert(adjInf.getCert());
        payCashboxProcessDO.setCreDtTmS(grpHdr.getCreDtTm());
        payCashboxProcessDO.setPrcSts(ProcessStsCdEnum.PR02.getCode());
        payCashboxProcessDO.setCoreBatchId(payTransDtlInfoDO.getBatchId());
        payCashboxProcessDO.setCorepaySerno(payTransDtlInfoDO.getPaySerno());
        payCashboxProcessDO.setCorePaydate(payTransDtlInfoDO.getPayDate());

        payCashboxProcessMapper.insert(payCashboxProcessDO);

        contextMap.put(PROCESS_DO, payCashboxProcessDO);
    }

}
