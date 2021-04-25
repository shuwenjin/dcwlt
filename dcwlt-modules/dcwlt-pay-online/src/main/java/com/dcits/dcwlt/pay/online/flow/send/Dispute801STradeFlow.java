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
 * 差错贷记调账往账
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
                .initTxn(this::initTxn)                     // 交易流水初始化
                .checkMsg(this::checkMsg)                   // 请求参数校验
                .process(this::sendDcep)                    // 发送801到互联互通
                .response(this::response)                   // 响应报文
                .errHandler(this::dsptTradeErrHandler)      // 异常处理
                .build();
    }

    /**
     * 初始化流水入库
     */
    public void initTxn(TradeContext<?, ?> tradeContext) {
        logger.info("initTxn：初始化金融登记簿开始 ");
        ECNYReqDTO<DsptChnlReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        ECNYReqHead ecnyHead = reqMsg.getEcnyHead();
        // 新交易流水信息
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

        // 初始化状态为299
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
        // 差错原因码 + 差错原因说明
        payTransDtlInfoDO.setNarraTive(reqMsg.getBody().getDisputeReasonCode() + "_" +
                reqMsg.getBody().getDisputeReason());

        // 通道流水
        payTransDtlInfoDO.setPayPathSerno(generateCodeService.generateMsgId(OutOrgTypeEnum.OutOrg,
                MsgTpEnum.DCEP801.getCode().substring(5, 8), ""));
        try {
            payTransDtlInfoService.insert(payTransDtlInfoDO);
        } catch (Exception e) {
            logger.error("金融流水表入库失败:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }
        //保存到context中
        EcnyTradeContext.getTempContext(tradeContext).put("PAY_TRANS_DTL", payTransDtlInfoDO);
        logger.info("initTxn：初始化金融登记簿成功 ");
    }

    /**
     * 请求参数校验
     */
    public void checkMsg(TradeContext<?, ?> tradeContext) {
        logger.info("业务检查");
        ECNYReqDTO<DsptChnlReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        PayTransDtlInfoDO payTransDtlInfo_new = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("PAY_TRANS_DTL");

        // 检查交易流水是否存在
        // 平台日期
        String payDate = reqMsg.getBody().getPayDate();
        // 平台流水号
        String paySerno = reqMsg.getBody().getPaySerno();
        // 获取原交易信息
        PayTransDtlInfoDO payTransDtlInfo_old = payTransDtlInfoService.query(payDate, paySerno);

        if (null == payTransDtlInfo_old) {
            logger.error("原交易流水不存在");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.OLD_PAY_INFO_UNFOUND);
        }

        if (!StringUtils.equals(MsgTpEnum.DCEP221.getCode(),payTransDtlInfo_old.getMsgType())) {
            logger.error("此交易不能做差错处理:{}",payTransDtlInfo_old.getMsgType());
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.MSG_TYPE_ERROR);
        }

        // 判断原交易是否差错成功
        if (AppConstant.TRXSTATUS_PRECREDITSUCCESS.equals(payTransDtlInfo_old.getTrxStatus())
                && AppConstant.CORESTATUS_FAILED.equals(payTransDtlInfo_old.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfo_old.getPathProcStatus())) {
            logger.info("{},原交易已经做过差错并调账成功", payTransDtlInfo_old.getPayPathSerno());
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.SUCCESS);
        }


        if (AppConstant.TRXSTATUS_ABEND.equals(payTransDtlInfo_old.getTrxStatus())
                && AppConstant.CORESTATUS_FAILED.equals(payTransDtlInfo_old.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfo_old.getPathProcStatus())) {
            // 状态201才能调账
            // 调账金额
            payTransDtlInfo_new.setAmount(payTransDtlInfo_old.getAmount());
            // 币种
            payTransDtlInfo_new.setCcy(payTransDtlInfo_old.getCcy());

            // 原交易信息
            payTransDtlInfo_new.setOrigPayPathSerno(payTransDtlInfo_old.getPayPathSerno());
            payTransDtlInfo_new.setOrigMsgType(payTransDtlInfo_old.getMsgType());
            payTransDtlInfo_new.setOrigPayPathDate(payTransDtlInfo_old.getOrigPayPathDate());
            payTransDtlInfo_new.setInstgPty(payTransDtlInfo_old.getInstdPty());
            payTransDtlInfo_new.setInstdPty(payTransDtlInfo_old.getInstgPty());

            EcnyTradeContext.getTempContext(tradeContext).put("PAY_TRANS_DTL", payTransDtlInfo_new);

            EcnyTradeContext.getTempContext(tradeContext).put("PAY_TRANS_DTL_OLD", payTransDtlInfo_old);

            // 更新登记簿
            update(payTransDtlInfo_new);
        } else {
            logger.error("当前状态不允许此操作");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.NO_DSPT_ALLOW);
        }

        // 机构校验
        checkInstgPty(payTransDtlInfo_new);
    }

    private void update(PayTransDtlInfoDO payTransDtlInfoDO) {
        try {
            payTransDtlInfoService.update(payTransDtlInfoDO);
        } catch (Exception e) {
            logger.error("金融流水表入库失败:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 机构校验
     */
    private void checkInstgPty(PayTransDtlInfoDO payTransDtlInfoDO) {
        logger.info("机构代码校验开始");
        // 发起机构
        String instgPty = payTransDtlInfoDO.getInstgPty();
        // 接收机构
        String instdPty = payTransDtlInfoDO.getInstdPty();
        // 报文编号
        String msgType = MsgTpEnum.DCEP801.getCode();
        // 发起机构权限校验
        Boolean sendAuth = authInfoService.validateAuthInfo(instgPty, msgType, DSPT_BIZ_TP, AuthInfoDrctEnum.sendAuth);
        //判断发起机构是否广发银行
        if (!AppConstant.CGB_FINANCIAL_INSTITUTION_CD.equals(instgPty)) {
            logger.error("发起机构传输有误,{}", instgPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTG_ERROR);
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
        if (!sendAuth) {
            logger.error("发送机构权限不足,{}", instgPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.ORGAN_POWER_ERROR);
        }
        // 接收机构权限校验
        Boolean recvAuth = authInfoService.validateAuthInfo(instdPty, msgType, DSPT_BIZ_TP, AuthInfoDrctEnum.recvAuth);
        if (!recvAuth) {
            logger.error("接收机构权限不足,{}", instdPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.ORGAN_POWER_ERROR);
        }
        logger.info("机构代码校验完成");
    }

    /**
     * 发送801到互联互通平台
     */
    public void sendDcep(TradeContext<?, ?> tradeContext) {
        Map<String, Object> tempContext = EcnyTradeContext.getTempContext(tradeContext);
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) tempContext.get("PAY_TRANS_DTL");

        // 构建请求互联互通801报文
        DCEPReqDTO<DsptReqDTO> dcepReqDTO = buildDcepReqDTO(payTransDtlInfoDO, tradeContext);

        // 请求金融开放平台
        logger.info("请求互联互通开始:{}", dcepReqDTO.toString());
        JSONObject dcep = sendToDcep(dcepReqDTO, payTransDtlInfoDO);
        logger.info("请求互联互通结束:{}", dcep);

        // 结果处理
        sendDcepDone(dcep, payTransDtlInfoDO, tradeContext);
    }

    /**
     * 构建请求互联互通801报文
     */
    private DCEPReqDTO<DsptReqDTO> buildDcepReqDTO(PayTransDtlInfoDO payTransDtlInfoDO, TradeContext<?, ?> tradeContext) {
        ECNYReqDTO<DsptChnlReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        // 组装请求报文
        DsptReqDTO dsptReqDTO = new DsptReqDTO();
        DsptReq req = new DsptReq();

        GrpHdr grpHdr = new GrpHdr();
        grpHdr.setMsgId(payTransDtlInfoDO.getPayPathSerno());
        grpHdr.setCreDtTm(DateUtil.getISODateTime());
        grpHdr.setInstdPty(new InstdPty(payTransDtlInfoDO.getInstdPty()));
        grpHdr.setInstgPty(new InstgPty(payTransDtlInfoDO.getInstgPty()));
        grpHdr.setRmk(OperTypeEnum.OT04.getDesc());
        req.setGrpHdr(grpHdr);

        // 组装原报文组件
        OrgnlGrpHdr orgnlGrpHdr = new OrgnlGrpHdr();
        orgnlGrpHdr.setOrgnlMsgId(payTransDtlInfoDO.getOrigPayPathSerno());
        orgnlGrpHdr.setOrgnlInstgPty(payTransDtlInfoDO.getInstdPty());
        orgnlGrpHdr.setOrgnlMT(payTransDtlInfoDO.getOrigMsgType());
        req.setOrgnlGrpHdr(orgnlGrpHdr);

        // 组装差错请求报文体
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
            //TODO 模拟互联互通接收
            return dcepSendService.sendDcep(dsptReqDTODCEPReqDTO);
            //return dcepService.receive802From801(dsptReqDTODCEPReqDTO);
        } catch (Exception e) {
            logger.error("发送801到互联互通请求失败：{}-{}", e.getMessage(), e);
            // 发送互联互通交易状态查询
            txStsQryNetPartyService.registerTrxStsQry(payTransDtlInfoDO);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.PAY_TIME_ERROR);
        }
    }

    private void sendDcepDone(JSONObject rspObj, PayTransDtlInfoDO payTransDtlInfoDO, TradeContext<?, ?> tradeContext) {
        if (null == rspObj.getJSONObject(AppConstant.DCEP_HEAD)) {
            logger.error("互联互通应答后出异常");
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.ECNY_DSPT_RESPOSE_ERROR);
        }
        // 互联互通返回报文
        String msgType = rspObj.getJSONObject(AppConstant.DCEP_HEAD).getString(AppConstant.MSG_TYPE);

        // 获取当前状态对象
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
            // 封装802报文
            DCEPRspDTO<DsptRspDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(rspObj, DsptRspDTO.class);
            RspsnInf rspsnInf = dcepRspDTO.getBody().getDsptRsp().getRspsnInf();
            String processStatus = dcepRspDTO.getBody().getDsptRsp().getRspsnInf().getRspsnSts();

            payTransDtlInfoDO.setPayPathRetSerno(dcepRspDTO.getBody().getDsptRsp().getGrpHdr().getMsgId());
            payTransDtlInfoDO.setPayPathRspStatus(rspsnInf.getPrcSts());
            payTransDtlInfoDO.setPayPathRetCode(rspsnInf.getRjctCd());
            payTransDtlInfoDO.setPayPathRetMsg(rspsnInf.getRjctInf());
            payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());
            // 应答成功
            if (StringUtils.equalsAny(processStatus, ProcessStsCdEnum.PR00.getCode(), ProcessStsCdEnum.PR03.getCode())) {
                StateMachine stateMachine_orgi = new StateMachine();
                stateMachine_orgi.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
                stateMachine_orgi.setPreCoreProcStatus(AppConstant.CORESTATUS_FAILED);
                stateMachine_orgi.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
                // 更新原交易状态为A01
                PayTransDtlInfoDO payTransDtlInfoDOOLD = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("PAY_TRANS_DTL_OLD");
                payTransDtlInfoDOOLD.setTrxStatus(AppConstant.TRXSTATUS_PRECREDITSUCCESS);
                payTransDtlInfoDOOLD.setCoreProcStatus(AppConstant.CORESTATUS_FAILED);
                payTransDtlInfoDOOLD.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);

                // 更新差错状态为191
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
                payTransDtlInfoDO.setTrxRetCode(Constant.SERVER_SUCC_RSPCODE);
                payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
                payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
                payTransDtlInfoDO.setPayPathRetCode(Constant.SERVER_SUCC_RSPCODE);
                payTransDtlInfoDO.setPayPathRetMsg(EcnyTransError.SUCCESS.getErrorMsg());

                // 更新原金融交易表
                update(payTransDtlInfoDOOLD, stateMachine_orgi);
                // 更新新金融登记簿
                update(payTransDtlInfoDO, stateMachine_dis);
                return;
            } else {
                // 应答失败
                // 更新差错状态为090
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
                payTransDtlInfoDO.setTrxRetCode(rspsnInf.getRjctCd());
                payTransDtlInfoDO.setTrxRetMsg(rspsnInf.getRjctInf());
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            }
        } else if (MsgTpEnum.DCEP911.getCode().equals(msgType)) {
            // 更新差错状态为292
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
            // 更新新金融登记簿
            int retCount = payTransDtlInfoService.update(payTransDtlInfoDO, stateMachine);

            if (retCount != 1) {
                throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
            }
        } catch (Exception e) {
            logger.error("更新登记簿异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 响应报文
     */
    public void response(TradeContext<?, ?> tradeContext) {
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("PAY_TRANS_DTL");
        ECNYReqDTO<DsptChnlReqDTO> ecnyReqDTO = EcnyTradeContext.getReqMsg(tradeContext);
        // 响应头tradeContext = {EcnyTradeContext@18374} "TradeContext{reqMsg=ECNYReqDTO{ecnyHead=ECNYReqHead{busiChnl='ECNY', busiChnl2='null', zoneno='null', brno='173001', tellerno='11', origChnl='ECN', origChnl2='null', origChnlDtl='null'}, body=DsptChnlReqDTO{payDate='20210413', paySerno='15422340001', checkStatus='null', operType='DR04', disputeReasonCode='OT04', disputeReason='123123123', msgId='20210308000171181982980514700000', instgPty='G4001011000013', msgTp='dcep.711.001.01'}}, rspMsg=null, tempCtx={PAY_TRANS_DTL_OLD=PayTransDtlInfoDO{payDate='20210413', paySerno='15422340001', payTime='154223', direct='R', payFlag='PAYEE', operStep='CRDT', operStatus='EXPT', trxStatus='2', trxRetCode='null', trxRetMsg='null', coreProcStatus='0', coreReqDate='20210113', coreReqSerno='ECNY2021041415423000003050010001', coreAcctDate='null', coreSerno='null', coreRetCode='null', coreRetMsg='null', payPathSerno='20210113000122184595346246598765', payPathDateTime='2021-01-13T16:49:07', pathProcStatus='1', payPathRspStatus='null', payPathRetCode='null',"… View
        ECNYRspHead head = new ECNYRspHead(payTransDtlInfoDO.getTrxStatus());
        // 响应体
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
     * 异常处理
     */
    public void dsptTradeErrHandler(TradeContext<?, ?> tradeContext, Throwable exception) {
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTempContext(tradeContext).get("PAY_TRANS_DTL");
        ECNYReqDTO<DsptChnlReqDTO> ecnyReqDTO = EcnyTradeContext.getReqMsg(tradeContext);
        // 获取交易状态，即发生异常时，交易是失败还是异常，默认是异常
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
            // 请求互联互通前的异常处理
            if (StringUtils.equals(status, AppConstant.TRXSTATUS_FAILED)) {
                payTransDtlInfoDO.setPayPathRetCode(payTransDtlInfoDO.getTrxRetCode());
                payTransDtlInfoDO.setPayPathRetMsg(payTransDtlInfoDO.getTrxRetMsg());

                // 更新业务状态
                payTransDtlInfoDO.setTrxStatus(status);
                payTransDtlInfoDO.setPathProcStatus(status);
            }

            payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());

            // 响应报文
            ECNYRspHead ecnyRspHead = new ECNYRspHead(payTransDtlInfoDO.getTrxStatus());
            DsptChnlRspDTO rspDTO = new DsptChnlRspDTO();
            rspDTO.setProcResult(payTransDtlInfoDO.getTrxRetMsg());

            payTransDtlInfoService.update(payTransDtlInfoDO);
            ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(ecnyReqDTO, ecnyRspHead, rspDTO,
                    payTransDtlInfoDO.getTrxRetCode(), payTransDtlInfoDO.getTrxRetMsg());
            EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
        } else {
            // 响应报文
            ECNYRspHead ecnyRspHead = new ECNYRspHead(status);
            ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(ecnyReqDTO, ecnyRspHead, null, code, msg);
            EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
        }
    }
}
