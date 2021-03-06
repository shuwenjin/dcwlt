package com.dcits.dcwlt.pay.online.flow.send;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.enums.AuthInfoDrctEnum;
import com.dcits.dcwlt.common.pay.enums.DsptChnlRspEnum;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.OperTypeEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.common.pay.enums.SummaryCdEnum;
import com.dcits.dcwlt.common.pay.sequence.service.IGenerateCodeService;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.type.OutOrgTypeEnum;
import com.dcits.dcwlt.common.pay.util.AmountUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.cmonconf.CmonConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.cmonconf.CmonConfInf;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.InstdPty;
import com.dcits.dcwlt.pay.api.domain.dcep.common.InstgPty;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.RspsnInf;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.DsptAmt;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.DsptInf;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.DsptReq;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.DsptReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.DsptRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.OrgnlTxAmt;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.OrgnlTxRef;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.Fault;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.FaultDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqHead;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspHead;
import com.dcits.dcwlt.pay.api.domain.ecny.dspt.DsptChnlReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.dspt.DsptChnlRspDTO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.baffle.dcep.DcepService;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.DcepTransInTradeFlow;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.service.IAuthInfoService;
import com.dcits.dcwlt.pay.online.service.IPartyService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import com.dcits.dcwlt.pay.online.service.ITxStsQryNetPartyService;
import com.dcits.dcwlt.pay.online.service.impl.DcepSendService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


/**
 * ????????????????????????
 */
@Configuration
public class Dispute801STradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(DcepTransInTradeFlow.class);

    public static final String DSPT_TRADE_FLOW = "Dspt801STradeFlow";

    public static final String DSPT_BIZ_TP = "E100";

    public static final String DSPT_CTGYP_URP_CD = "08002";

    //@Autowired
    //private DcepSendService dcepSendService;

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoService;

    @Autowired
    private IAuthInfoService authInfoService;

    @Autowired
    private IGenerateCodeService generateCodeService;

    @Autowired
    private IPartyService partyService;

    @Autowired
    private DcepService dcepService;

    @Autowired
    private ITxStsQryNetPartyService txStsQryNetPartyService;

    @Autowired
    private DcepSendService dcepSendService;

    @Bean(name = DSPT_TRADE_FLOW)
    public TradeFlow dsptTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .initTxn(this::initTxn)                     // ?????????????????????
                .checkMsg(this::checkMsg)                   // ??????????????????
                .process(this::sendDcep)                    // ??????801???????????????
                .response(this::response)                   // ????????????
                .errHandler(this::dsptTradeErrHandler)      // ????????????
                .build();
    }

    /**
     * ?????????????????????
     */
    public void initTxn(TradeContext<?, ?> tradeContext) {
        logger.info("initTxn????????????????????????????????? ");
        ECNYReqDTO<DsptChnlReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        ECNYReqHead ecnyHead = reqMsg.getEcnyHead();
        // ?????????????????????
        PayTransDtlInfoDO payTransDtlInfoDO = new PayTransDtlInfoDO();
        String generateFlowNo = generateCodeService.generatePlatformFlowNo();
        payTransDtlInfoDO.setPayDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setPaySerno(generateFlowNo);
        payTransDtlInfoDO.setPayTime(DateUtil.getDefaultTime());
        payTransDtlInfoDO.setDirect(AppConstant.DIRECT_SEND);
        payTransDtlInfoDO.setPayFlag(AppConstant.IDENTIFICATION_PAYER);
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_DRDT);
        payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
        payTransDtlInfoDO.setSummary(SummaryCdEnum.XSJ.getCode());
        payTransDtlInfoDO.setPayPathDateTime(DateUtil.getISODateTime());
        payTransDtlInfoDO.setBatchId(generateCodeService.getBachNo());

        // ??????????????????299
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_INIT);

        payTransDtlInfoDO.setMsgType(MsgTpEnum.DCEP801.getCode());
        payTransDtlInfoDO.setTellerNo(ecnyHead.getTellerno());
        payTransDtlInfoDO.setZoneNo(ecnyHead.getZoneno());
        payTransDtlInfoDO.setBrno(ecnyHead.getBrno());
        payTransDtlInfoDO.setAcctBrno(ecnyHead.getBrno());
        payTransDtlInfoDO.setOrigChnl(ecnyHead.getOrigChnl());
        payTransDtlInfoDO.setOrigChnl2(ecnyHead.getOrigChnl2());
        payTransDtlInfoDO.setOrigChnlDtl(ecnyHead.getOrigChnlDtl());
        payTransDtlInfoDO.setAmount("");
        payTransDtlInfoDO.setCcy("");

        payTransDtlInfoDO.setBusiChnl(ecnyHead.getBusiChnl());
        payTransDtlInfoDO.setBusiChnl2(ecnyHead.getBusiChnl2());

        payTransDtlInfoDO.setBusiSysDate(reqMsg.getHead().getTranDate());
        payTransDtlInfoDO.setBusiSysTime(reqMsg.getHead().getTranTime());
        payTransDtlInfoDO.setBusiSysSerno(reqMsg.getHead().getSeqNo());

        payTransDtlInfoDO.setLastUpJrnno(generateFlowNo);
        payTransDtlInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setLastUpTime(DateUtil.getDefaultTime());
        payTransDtlInfoDO.setBatchId(generateCodeService.getBachNo());

        payTransDtlInfoDO.setRemark(OperTypeEnum.OT04.getDesc());
        payTransDtlInfoDO.setBusiType(DSPT_BIZ_TP);
        payTransDtlInfoDO.setBusiKind(DSPT_CTGYP_URP_CD);
        // ??????????????? + ??????????????????
        payTransDtlInfoDO.setNarraTive(reqMsg.getBody().getDisputeReasonCode() + "_" +
                reqMsg.getBody().getDisputeReason());

        // ????????????
        payTransDtlInfoDO.setPayPathSerno(generateCodeService.generateMsgId(OutOrgTypeEnum.OutOrg,
                MsgTpEnum.DCEP801.getCode().substring(5, 8), ""));
        try {
            payTransDtlInfoService.insert(payTransDtlInfoDO);
        } catch (Exception e) {
            logger.error("???????????????????????????:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }
        //?????????context???
        EcnyTradeContext.getTempContext(tradeContext).put("PAY_TRANS_DTL", payTransDtlInfoDO);
        logger.info("initTxn????????????????????????????????? ");
    }

    /**
     * ??????????????????
     */
    public void checkMsg(TradeContext<?, ?> tradeContext) {
        logger.info("????????????");
        ECNYReqDTO<DsptChnlReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        PayTransDtlInfoDO payTransDtlInfo_new = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("PAY_TRANS_DTL");

        // ??????????????????????????????
        // ????????????
        String payDate = reqMsg.getBody().getPayDate();
        // ???????????????
        String paySerno = reqMsg.getBody().getPaySerno();
        // ?????????????????????
        PayTransDtlInfoDO payTransDtlInfo_old = payTransDtlInfoService.query(payDate, paySerno);

        if (null == payTransDtlInfo_old) {
            logger.error("????????????????????????");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.OLD_PAY_INFO_UNFOUND);
        }

        if (!StringUtils.equals(MsgTpEnum.DCEP221.getCode(),payTransDtlInfo_old.getMsgType())) {
            logger.error("??????????????????????????????:{}",payTransDtlInfo_old.getMsgType());
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.MSG_TYPE_ERROR);
        }

        // ?????????????????????????????????
        if (AppConstant.TRXSTATUS_PRECREDITSUCCESS.equals(payTransDtlInfo_old.getTrxStatus())
                && AppConstant.CORESTATUS_FAILED.equals(payTransDtlInfo_old.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfo_old.getPathProcStatus())) {
            logger.info("{},??????????????????????????????????????????", payTransDtlInfo_old.getPayPathSerno());
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.SUCCESS);
        }


        if (AppConstant.TRXSTATUS_ABEND.equals(payTransDtlInfo_old.getTrxStatus())
                && AppConstant.CORESTATUS_FAILED.equals(payTransDtlInfo_old.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfo_old.getPathProcStatus())) {
            // ??????201????????????
            // ????????????
            payTransDtlInfo_new.setAmount(payTransDtlInfo_old.getAmount());
            // ??????
            payTransDtlInfo_new.setCcy(payTransDtlInfo_old.getCcy());

            // ???????????????
            payTransDtlInfo_new.setOrigPayPathSerno(payTransDtlInfo_old.getPayPathSerno());
            payTransDtlInfo_new.setOrigMsgType(payTransDtlInfo_old.getMsgType());
            payTransDtlInfo_new.setOrigPayPathDate(payTransDtlInfo_old.getOrigPayPathDate());
            payTransDtlInfo_new.setInstgPty(payTransDtlInfo_old.getInstdPty());
            payTransDtlInfo_new.setInstdPty(payTransDtlInfo_old.getInstgPty());

            EcnyTradeContext.getTempContext(tradeContext).put("PAY_TRANS_DTL", payTransDtlInfo_new);

            EcnyTradeContext.getTempContext(tradeContext).put("PAY_TRANS_DTL_OLD", payTransDtlInfo_old);

            // ???????????????
            update(payTransDtlInfo_new);
        } else {
            logger.error("??????????????????????????????");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.NO_DSPT_ALLOW);
        }

        // ????????????
        checkInstgPty(payTransDtlInfo_new);
    }

    private void update(PayTransDtlInfoDO payTransDtlInfoDO) {
        try {
            payTransDtlInfoService.update(payTransDtlInfoDO);
        } catch (Exception e) {
            logger.error("???????????????????????????:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ????????????
     */
    private void checkInstgPty(PayTransDtlInfoDO payTransDtlInfoDO) {
        logger.info("????????????????????????");
        // ????????????
        String instgPty = payTransDtlInfoDO.getInstgPty();
        // ????????????
        String instdPty = payTransDtlInfoDO.getInstdPty();
        // ????????????
        String msgType = MsgTpEnum.DCEP801.getCode();
        // ????????????????????????
        Boolean sendAuth = authInfoService.validateAuthInfo(instgPty, msgType, DSPT_BIZ_TP, AuthInfoDrctEnum.sendAuth);
        //????????????????????????????????????
        if (!AppConstant.BANK_FINANCIAL_INSTITUTION_CD.equals(instgPty)) {
            logger.error("????????????????????????,{}", instgPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTG_ERROR);
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
        if (!sendAuth) {
            logger.error("????????????????????????,{}", instgPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.ORGAN_POWER_ERROR);
        }
        // ????????????????????????
        Boolean recvAuth = authInfoService.validateAuthInfo(instdPty, msgType, DSPT_BIZ_TP, AuthInfoDrctEnum.recvAuth);
        if (!recvAuth) {
            logger.error("????????????????????????,{}", instdPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.ORGAN_POWER_ERROR);
        }
        logger.info("????????????????????????");
    }

    /**
     * ??????801?????????????????????
     */
    public void sendDcep(TradeContext<?, ?> tradeContext) {
        Map<String, Object> tempContext = EcnyTradeContext.getTempContext(tradeContext);
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) tempContext.get("PAY_TRANS_DTL");

        // ????????????????????????801??????
        DCEPReqDTO<DsptReqDTO> dcepReqDTO = buildDcepReqDTO(payTransDtlInfoDO, tradeContext);

        // ????????????????????????
        logger.info("????????????????????????:{}", dcepReqDTO.toString());
        JSONObject dcep = sendToDcep(dcepReqDTO, payTransDtlInfoDO);
        logger.info("????????????????????????:{}", dcep);

        // ????????????
        sendDcepDone(dcep, payTransDtlInfoDO, tradeContext);
    }

    /**
     * ????????????????????????801??????
     */
    private DCEPReqDTO<DsptReqDTO> buildDcepReqDTO(PayTransDtlInfoDO payTransDtlInfoDO, TradeContext<?, ?> tradeContext) {
        ECNYReqDTO<DsptChnlReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        // ??????????????????
        DsptReqDTO dsptReqDTO = new DsptReqDTO();
        DsptReq req = new DsptReq();

        GrpHdr grpHdr = new GrpHdr();
        grpHdr.setMsgId(payTransDtlInfoDO.getPayPathSerno());
        grpHdr.setCreDtTm(DateUtil.getISODateTime());
        grpHdr.setInstdPty(new InstdPty(payTransDtlInfoDO.getInstdPty()));
        grpHdr.setInstgPty(new InstgPty(payTransDtlInfoDO.getInstgPty()));
        grpHdr.setRmk(OperTypeEnum.OT04.getDesc());
        req.setGrpHdr(grpHdr);

        // ?????????????????????
        OrgnlGrpHdr orgnlGrpHdr = new OrgnlGrpHdr();
        orgnlGrpHdr.setOrgnlMsgId(payTransDtlInfoDO.getOrigPayPathSerno());
        orgnlGrpHdr.setOrgnlInstgPty(payTransDtlInfoDO.getInstdPty());
        orgnlGrpHdr.setOrgnlMT(payTransDtlInfoDO.getOrigMsgType());
        req.setOrgnlGrpHdr(orgnlGrpHdr);

        // ???????????????????????????
        DsptInf dsptInf = new DsptInf();
        dsptInf.setDsptBizTp(payTransDtlInfoDO.getBusiType());
        dsptInf.setBatchId(payTransDtlInfoDO.getBatchId());
        dsptInf.setDsptCtgyPurpCd(payTransDtlInfoDO.getBusiKind());
        dsptInf.setDsptRsnCd(reqMsg.getBody().getDisputeReasonCode());
        dsptInf.setDsptRsnDesc(reqMsg.getBody().getDisputeReason());
        DsptAmt dsptAmt = new DsptAmt();
        dsptAmt.setCcy(payTransDtlInfoDO.getCcy());
        dsptAmt.setValue(AmountUtil.fenToYuan(payTransDtlInfoDO.getAmount()));
        dsptInf.setDsptAmt(dsptAmt);
        dsptInf.setBatchId(payTransDtlInfoDO.getBatchId());
        OrgnlTxRef orgnlTxRef = new OrgnlTxRef();
        OrgnlTxAmt orgnlTxAmt = new OrgnlTxAmt();
        orgnlTxAmt.setCcy(payTransDtlInfoDO.getCcy());
        orgnlTxAmt.setValue(AmountUtil.fenToYuan(payTransDtlInfoDO.getAmount()));
        orgnlTxRef.setOrgnlTxAmt(orgnlTxAmt);
        dsptInf.setOrgnlTxRef(orgnlTxRef);
        req.setDsptInf(dsptInf);

        dsptReqDTO.setDsptReq(req);

        String msgSn = generateCodeService.generateMsgSN(payTransDtlInfoDO.getPayPathSerno());

        DCEPReqDTO<DsptReqDTO> dcepReqDTO = DCEPReqDTO.newInstance(MsgTpEnum.DCEP801.getCode(),
                msgSn, payTransDtlInfoDO.getInstdPty(), dsptReqDTO);

        return dcepReqDTO;
    }

    private JSONObject sendToDcep(DCEPReqDTO<DsptReqDTO> dsptReqDTODCEPReqDTO, PayTransDtlInfoDO payTransDtlInfoDO) {
        try {
            //TODO ????????????????????????
            return dcepSendService.sendDcep(dsptReqDTODCEPReqDTO);
            //return dcepService.receive802From801(dsptReqDTODCEPReqDTO);
        } catch (Exception e) {
            logger.error("??????801??????????????????????????????{}-{}", e.getMessage(), e);
            // ????????????????????????????????????
            txStsQryNetPartyService.registerTrxStsQry(payTransDtlInfoDO);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.PAY_TIME_ERROR);
        }
    }

    private void sendDcepDone(JSONObject rspObj, PayTransDtlInfoDO payTransDtlInfoDO, TradeContext<?, ?> tradeContext) {
        if (null == rspObj.getJSONObject(AppConstant.DCEP_HEAD)) {
            logger.error("??????????????????????????????");
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.ECNY_DSPT_RESPOSE_ERROR);
        }
        // ????????????????????????
        String msgType = rspObj.getJSONObject(AppConstant.DCEP_HEAD).getString(AppConstant.MSG_TYPE);

        // ????????????????????????
        StateMachine stateMachine_dis = new StateMachine();
        stateMachine_dis.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine_dis.setPreCoreProcStatus(AppConstant.CORESTATUS_INIT);
        stateMachine_dis.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_INIT);

        if (MsgTpEnum.DCEP900.getCode().equals(msgType)) {
            DCEPRspDTO<CmonConfDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, CmonConfDTO.class);
            CmonConfInf cmonConfInf = dcepRspDTO.getBody().getCmonConf().getCmonConfInf();
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            payTransDtlInfoDO.setTrxRetMsg(cmonConfInf.getRjctInf());
            payTransDtlInfoDO.setTrxRetCode(cmonConfInf.getPrcCd());
            payTransDtlInfoDO.setPayPathRetSerno(dcepRspDTO.getBody().getCmonConf().getGrpHdr().getMsgId());
            payTransDtlInfoDO.setPayPathRspStatus(cmonConfInf.getPrcSts());
            payTransDtlInfoDO.setPayPathRetCode(cmonConfInf.getPrcCd());
            payTransDtlInfoDO.setPayPathRetMsg(cmonConfInf.getRjctInf());
            payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());

        } else if (MsgTpEnum.DCEP802.getCode().equals(msgType)) {
            // ??????802??????
            DCEPRspDTO<DsptRspDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, DsptRspDTO.class);
            RspsnInf rspsnInf = dcepRspDTO.getBody().getDsptRsp().getRspsnInf();
            String processStatus = dcepRspDTO.getBody().getDsptRsp().getRspsnInf().getRspsnSts();

            payTransDtlInfoDO.setPayPathRetSerno(dcepRspDTO.getBody().getDsptRsp().getGrpHdr().getMsgId());
            payTransDtlInfoDO.setPayPathRspStatus(rspsnInf.getPrcSts());
            payTransDtlInfoDO.setPayPathRetCode(rspsnInf.getRjctCd());
            payTransDtlInfoDO.setPayPathRetMsg(rspsnInf.getRjctInf());
            payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());
            // ????????????
            if (StringUtils.equalsAny(processStatus, ProcessStsCdEnum.PR00.getCode(), ProcessStsCdEnum.PR03.getCode())) {
                StateMachine stateMachine_orgi = new StateMachine();
                stateMachine_orgi.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
                stateMachine_orgi.setPreCoreProcStatus(AppConstant.CORESTATUS_FAILED);
                stateMachine_orgi.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
                // ????????????????????????A01
                PayTransDtlInfoDO payTransDtlInfoDOOLD = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("PAY_TRANS_DTL_OLD");
                payTransDtlInfoDOOLD.setTrxStatus(AppConstant.TRXSTATUS_PRECREDITSUCCESS);
                payTransDtlInfoDOOLD.setCoreProcStatus(AppConstant.CORESTATUS_FAILED);
                payTransDtlInfoDOOLD.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);

                // ?????????????????????191
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
                payTransDtlInfoDO.setTrxRetCode(Constant.SERVER_SUCC_RSPCODE);
                payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
                payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
                payTransDtlInfoDO.setPayPathRetCode(Constant.SERVER_SUCC_RSPCODE);
                payTransDtlInfoDO.setPayPathRetMsg(EcnyTransError.SUCCESS.getErrorMsg());

                // ????????????????????????
                update(payTransDtlInfoDOOLD, stateMachine_orgi);
                // ????????????????????????
                update(payTransDtlInfoDO, stateMachine_dis);
                return;
            } else {
                // ????????????
                // ?????????????????????090
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
                payTransDtlInfoDO.setTrxRetCode(rspsnInf.getRjctCd());
                payTransDtlInfoDO.setTrxRetMsg(rspsnInf.getRjctInf());
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            }
        } else if (MsgTpEnum.DCEP911.getCode().equals(msgType)) {
            // ?????????????????????292
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            DCEPRspDTO<FaultDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, FaultDTO.class);
            Fault fault = dcepRspDTO.getBody().getFault();
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_ABEND);
            payTransDtlInfoDO.setTrxRetMsg(fault.getFaultString());
            payTransDtlInfoDO.setTrxRetCode(fault.getFaultCode());
            payTransDtlInfoDO.setPayPathRetSerno(dcepRspDTO.getDcepHead().getMsgSn());
            payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());
            payTransDtlInfoDO.setPayPathRetCode(fault.getFaultCode());
            payTransDtlInfoDO.setPayPathRetMsg(fault.getFaultString());
            txStsQryNetPartyService.registerTrxStsQry(payTransDtlInfoDO);
        }
        EcnyTradeContext.getTempContext(tradeContext).put("PAY_TRANS_DTL", payTransDtlInfoDO);

        update(payTransDtlInfoDO,stateMachine_dis);
    }

    private void update(PayTransDtlInfoDO payTransDtlInfoDO,StateMachine stateMachine){
        try {
            // ????????????????????????
            int retCount = payTransDtlInfoService.update(payTransDtlInfoDO, stateMachine);

            if (retCount != 1) {
                throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
            }
        } catch (Exception e) {
            logger.error("????????????????????????{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * ????????????
     */
    public void response(TradeContext<?, ?> tradeContext) {
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("PAY_TRANS_DTL");
        ECNYReqDTO<DsptChnlReqDTO> ecnyReqDTO = EcnyTradeContext.getReqMsg(tradeContext);
        // ?????????tradeContext = {EcnyTradeContext@18374} "TradeContext{reqMsg=ECNYReqDTO{ecnyHead=ECNYReqHead{busiChnl='ECNY', busiChnl2='null', zoneno='null', brno='173001', tellerno='11', origChnl='ECN', origChnl2='null', origChnlDtl='null'}, body=DsptChnlReqDTO{payDate='20210413', paySerno='15422340001', checkStatus='null', operType='DR04', disputeReasonCode='OT04', disputeReason='123123123', msgId='20210308000171181982980514700000', instgPty='G4001011000013', msgTp='dcep.711.001.01'}}, rspMsg=null, tempCtx={PAY_TRANS_DTL_OLD=PayTransDtlInfoDO{payDate='20210413', paySerno='15422340001', payTime='154223', direct='R', payFlag='PAYEE', operStep='CRDT', operStatus='EXPT', trxStatus='2', trxRetCode='null', trxRetMsg='null', coreProcStatus='0', coreReqDate='20210113', coreReqSerno='ECNY2021041415423000003050010001', coreAcctDate='null', coreSerno='null', coreRetCode='null', coreRetMsg='null', payPathSerno='20210113000122184595346246598765', payPathDateTime='2021-01-13T16:49:07', pathProcStatus='1', payPathRspStatus='null', payPathRetCode='null',"??? View
        ECNYRspHead head = new ECNYRspHead(payTransDtlInfoDO.getTrxStatus());
        // ?????????
        DsptChnlRspDTO rspDTO = new DsptChnlRspDTO();
        if (AppConstant.TRXSTATUS_SUCCESS.equals(payTransDtlInfoDO.getTrxStatus())) {
            rspDTO.setProcResult(DsptChnlRspEnum.DSPT01.getDesc());
            ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(ecnyReqDTO, head, rspDTO, EcnyTransError.SUCCESS.getErrorCode(), EcnyTransError.SUCCESS.getErrorMsg());
            EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
        } else {
            rspDTO.setProcResult(payTransDtlInfoDO.getTrxRetMsg());
            ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(ecnyReqDTO, head, rspDTO, payTransDtlInfoDO.getTrxRetCode(), payTransDtlInfoDO.getTrxRetMsg());
            EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
        }
    }

    /**
     * ????????????
     */
    public void dsptTradeErrHandler(TradeContext<?, ?> tradeContext, Throwable exception) {
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("PAY_TRANS_DTL");
        ECNYReqDTO<DsptChnlReqDTO> ecnyReqDTO = EcnyTradeContext.getReqMsg(tradeContext);
        // ???????????????????????????????????????????????????????????????????????????????????????
        String status = AppConstant.TRXSTATUS_ABEND;
        String code = EcnyTransError.OTHER_TECH_ERROR.getErrorCode();
        String msg = EcnyTransError.OTHER_TECH_ERROR.getErrorMsg();
        if (null != payTransDtlInfoDO) {
            if (exception instanceof EcnyTransException) {
                status = ((EcnyTransException) exception).getStatus();
                code = ((EcnyTransException) exception).getErrorCode();
                msg = ((EcnyTransException) exception).getErrorMsg();
            }
            if (StringUtils.isEmpty(payTransDtlInfoDO.getTrxRetCode())) {
                payTransDtlInfoDO.setTrxRetCode(code);
            }
            if (StringUtils.isEmpty(payTransDtlInfoDO.getTrxRetMsg())) {
                payTransDtlInfoDO.setTrxRetMsg(msg);
            }
            // ????????????????????????????????????
            if (StringUtils.equals(status, AppConstant.TRXSTATUS_FAILED)) {
                payTransDtlInfoDO.setPayPathRetCode(payTransDtlInfoDO.getTrxRetCode());
                payTransDtlInfoDO.setPayPathRetMsg(payTransDtlInfoDO.getTrxRetMsg());

                // ??????????????????
                payTransDtlInfoDO.setTrxStatus(status);
                payTransDtlInfoDO.setPathProcStatus(status);
            }

            payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());

            // ????????????
            ECNYRspHead ecnyRspHead = new ECNYRspHead(payTransDtlInfoDO.getTrxStatus());
            DsptChnlRspDTO rspDTO = new DsptChnlRspDTO();
            rspDTO.setProcResult(payTransDtlInfoDO.getTrxRetMsg());

            payTransDtlInfoService.update(payTransDtlInfoDO);
            ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(ecnyReqDTO, ecnyRspHead, rspDTO,
                    payTransDtlInfoDO.getTrxRetCode(), payTransDtlInfoDO.getTrxRetMsg());
            EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
        } else {
            // ????????????
            ECNYRspHead ecnyRspHead = new ECNYRspHead(status);
            ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(ecnyReqDTO, ecnyRspHead, null, code, msg);
            EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
        }
    }
}
