package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.LogMonitorLevelCdEnum;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.common.pay.enums.RejectCdEnum;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPHeader;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.DsptInf;
import com.dcits.dcwlt.pay.api.domain.dcep.dspt.DsptReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.reconvert.ReconvertReq;
import com.dcits.dcwlt.pay.api.domain.dcep.reconvert.ReconvertReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.txendntfcnt.OrgnlMsgCntt;
import com.dcits.dcwlt.pay.api.domain.dcep.txendntfcnt.TxEndNtfctn;
import com.dcits.dcwlt.pay.api.model.AccFlowDO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.config.EcnyTradeConfig;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IEventInfoService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 终态通知后处理
 * @author chenyanchun
 */
@Service("txEndNtfcntHandleService")
public class TxEndNtfcntHandleServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(TxEndNtfcntHandleServiceImpl.class);


    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoService;

    @Autowired
    private ICoreProcessService bankCoreProcessService;

    @Autowired
    private CoreEventServiceImpl coreEventServiceImpl;

    @Autowired
    private BankCoreAccTxnServiceImpl bankCoreAccTxnServiceImpl;

    @Autowired
    private ReCreditServiceImpl reCreditServiceImpl;

    @Autowired
    private BankCoreImplDubboServiceImpl bankCoreImplDubboServiceImpl;

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

    @Autowired
    private IEventInfoService eventInfoService;

    @Autowired(required = false)
    private EcnyTradeConfig ecnyTradeConfig;

    /**
     * 终态通知处理入口
     * @param txEndNtfctn
     */
   // @RtpAsync
    public void txEndNtfcntHandle(TxEndNtfctn txEndNtfctn){
        OrgnlGrpHdr orgnlGrpHdr = txEndNtfctn.getOrgnlGrpHdr();
        String orgnlMT = orgnlGrpHdr.getOrgnlMT();

        //通过原报文编号区分原报文类型：兑回、兑出、汇款兑出还是贷记调整
        if (MsgTpEnum.DCEP221.getCode().equals(orgnlMT)) {

            logger.info("终态通知处理：兑回");
            this.deal221(txEndNtfctn);

        } else if (MsgTpEnum.DCEP225.getCode().equals(orgnlMT)) {

            logger.info("终态通知处理：兑出");
            this.deal225(txEndNtfctn);

        } else if (MsgTpEnum.DCEP227.getCode().equals(orgnlMT)) {

            this.deal227(txEndNtfctn);
            logger.info("终态通知处理：汇款兑出");

        } else if (MsgTpEnum.DCEP801.getCode().equals(orgnlMT)) {
            this.deal801(txEndNtfctn);
            logger.info("终态通知处理：贷记调整");

        }else{
            logger.info("该报文类型不需要进行终态处理");
        }

    }

    /**
     * 终态通知兑回处理
     * @param txEndNtfcntReqDTO
     */
    public void deal221(TxEndNtfctn txEndNtfcntReqDTO){
        logger.info("终态通知兑回处理开始，原报文标识号：{}",txEndNtfcntReqDTO.getOrgnlGrpHdr().getOrgnlMsgId());

        //原报文主键组件
        OrgnlGrpHdr orgnlGrpHdr = txEndNtfcntReqDTO.getOrgnlGrpHdr();
        String orgnlMsgId = orgnlGrpHdr.getOrgnlMsgId();

        OrgnlMsgCntt orgnlMsgCntt = txEndNtfcntReqDTO.getOrgnlMsgCntt();

        //根据报文标志号查询金融交易簿
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(orgnlMsgId);
        if(payTransDtlInfoDO != null){
            logger.info("deal221 原兑回交易在金融交易簿有记录：{}",payTransDtlInfoDO);
            updatePayTransDtlInfo(payTransDtlInfoDO,orgnlMsgCntt);
        }else{
            logger.info("deal221 原兑回交易在金融交易簿无记录，开始补登信息");
            payTransDtlInfoDO = savePayTransDtlInfoDO(orgnlMsgCntt);
        }

        //调用兑回推断处理
        reconvertInferenceHandle(payTransDtlInfoDO.getPayDate(),payTransDtlInfoDO.getPaySerno());
    }

    /**
     * 兑回补登信息进金融交易簿
     * @param orgnlMsgCntt
     */
    private PayTransDtlInfoDO savePayTransDtlInfoDO(OrgnlMsgCntt orgnlMsgCntt) {
        //将原业务报文原文转换成对象
        JSONObject jsonObject = JSONObject.parseObject(orgnlMsgCntt.getCntt());
        ReconvertReqDTO body = JSONObject.toJavaObject(jsonObject.getJSONObject("Body"), ReconvertReqDTO.class);//互联互通报文体json对象-->DCEPReqBody实体

        ReconvertReq reqDTO = body.getReconvertReq();
        String prcSts = orgnlMsgCntt.getPrcSts();
        //初始化金融交易簿
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.init(reqDTO);

        payTransDtlInfoDO.setPayPathRspStatus(prcSts);
        if(StringUtils.isNotBlank(orgnlMsgCntt.getRjctCd())){
            payTransDtlInfoDO.setPayPathRetCode(orgnlMsgCntt.getRjctCd());
        }else{
            payTransDtlInfoDO.setPayPathRetCode(orgnlMsgCntt.getPrcCd());
        }
        payTransDtlInfoDO.setPayPathRetMsg(orgnlMsgCntt.getRjctInf());

        //业务成功2-9-1，业务失败0-0-0
        if(ProcessStsCdEnum.PR00.getCode().equals(prcSts)
            || ProcessStsCdEnum.PR03.getCode().equals(prcSts)){

            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);

            payTransDtlInfoDO.setPayPathRetMsg( StringUtils.isNotBlank(orgnlMsgCntt.getRjctInf()) ? orgnlMsgCntt.getRjctInf() : RejectCdEnum.SUCCESS.getDesc());

        }else if(ProcessStsCdEnum.PR01.getCode().equals(prcSts)
                || ProcessStsCdEnum.PR04.getCode().equals(prcSts)){

            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
            payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_FAILED);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);

        }


        try {
            payTransDtlInfoService.insert(payTransDtlInfoDO);
        } catch (Exception e) {
            logger.error("终态通知-新增金融交易异常： {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
        return payTransDtlInfoDO;
    }

    /**
     * 以终态通知业务状态为准更新通道状态
     * @param payTransDtlInfoDO
     * @param orgnlMsgCntt
     */
    private void updatePayTransDtlInfo(PayTransDtlInfoDO payTransDtlInfoDO, OrgnlMsgCntt orgnlMsgCntt){

        StateMachine stateMachine = new StateMachine();
        stateMachine.setPrePathProcStatus(payTransDtlInfoDO.getPathProcStatus());

        PayTransDtlInfoDO newPayTransDTlInfoDO = new PayTransDtlInfoDO();

        String prcSts= orgnlMsgCntt.getPrcSts();
        String rjctInf = orgnlMsgCntt.getRjctInf();
        String pathProcStatus = StringUtils.EMPTY;

        //状态转换成行内通道状态
        if(ProcessStsCdEnum.PR00.getCode().equals(prcSts)
                || ProcessStsCdEnum.PR03.getCode().equals(prcSts)){
            pathProcStatus = AppConstant.PAYPATHSTATUS_SUCCESS;
            rjctInf = StringUtils.isNotBlank(rjctInf) ? rjctInf : RejectCdEnum.SUCCESS.getDesc();
        }
        if(ProcessStsCdEnum.PR01.getCode().equals(prcSts)
                || ProcessStsCdEnum.PR04.getCode().equals(prcSts)){
            pathProcStatus = AppConstant.PAYPATHSTATUS_FAILED;
        }

        newPayTransDTlInfoDO.setPayDate(payTransDtlInfoDO.getPayDate());
        newPayTransDTlInfoDO.setPaySerno(payTransDtlInfoDO.getPaySerno());
        newPayTransDTlInfoDO.setPathProcStatus(pathProcStatus);
        newPayTransDTlInfoDO.setPayPathRspStatus(prcSts);
        newPayTransDTlInfoDO.setPayPathRetMsg(rjctInf);
        if(StringUtils.isNotBlank(orgnlMsgCntt.getRjctCd())){
            newPayTransDTlInfoDO.setPayPathRetCode(orgnlMsgCntt.getRjctCd());
        }else{
            newPayTransDTlInfoDO.setPayPathRetCode(orgnlMsgCntt.getPrcCd());
        }

        try {
            payTransDtlInfoService.update(newPayTransDTlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("终态通知-更新金融交易簿通道状态异常： {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }


    /**
     * 兑回推断处理
     * @param payDate
     * @param paySerno
     */
    public void reconvertInferenceHandle(String payDate,String paySerno) {

        logger.info("调用兑回推断处理开始,平台日期：{},平台流水：{}",payDate,paySerno);

        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(payDate,paySerno);
        String trxStatus = payTransDtlInfoDO.getTrxStatus();
        String coreProcStatus = payTransDtlInfoDO.getCoreProcStatus();
        String pathProcStatus = payTransDtlInfoDO.getPathProcStatus();

        //上核心失败,终态推定失败
        boolean status200 = (AppConstant.TRXSTATUS_ABEND.equals(trxStatus)
                && AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_FAILED.equals(pathProcStatus));

        //上核心成功，终态推定成功
        boolean status211 = (AppConstant.TRXSTATUS_ABEND.equals(trxStatus)
                && AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(pathProcStatus));

        //上核心异常，终态推定成功
        boolean status221 = (AppConstant.TRXSTATUS_ABEND.equals(trxStatus)
                && AppConstant.CORESTATUS_ABEND.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(pathProcStatus));

        //上核心异常，终态推定失败
        boolean status220 = (AppConstant.TRXSTATUS_ABEND.equals(trxStatus)
                && AppConstant.CORESTATUS_ABEND.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_FAILED.equals(pathProcStatus));

        //上核心成功，终态推定失败
        boolean status210 = (AppConstant.TRXSTATUS_ABEND.equals(trxStatus)
                && AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_FAILED.equals(pathProcStatus));

        //上核心成功，终态推定失败
        boolean status110 = (AppConstant.TRXSTATUS_SUCCESS.equals(trxStatus)
                && AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_FAILED.equals(pathProcStatus));

        //上核心失败，终态推定成功
        boolean status201 = (AppConstant.TRXSTATUS_ABEND.equals(trxStatus)
                && AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(pathProcStatus));

        //我行失败返回000，但是互联互通没接收到，推断成功，出现001
        boolean status001 = (AppConstant.TRXSTATUS_FAILED.equals(trxStatus)
                && AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(pathProcStatus));

        //上核心前失败 090，后推定成功091
        boolean status091 = (AppConstant.TRXSTATUS_FAILED.equals(trxStatus)
                && AppConstant.CORESTATUS_INIT.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(pathProcStatus));

        //上核心前异常 297，终态通知成功，291
        boolean status291 = (AppConstant.TRXSTATUS_ABEND.equals(trxStatus)
                && AppConstant.CORESTATUS_INIT.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(pathProcStatus));

        //上核心前异常 297，终态通知失败 290
        boolean status290 = (AppConstant.TRXSTATUS_ABEND.equals(trxStatus)
                && AppConstant.CORESTATUS_INIT.equals(coreProcStatus)
                && AppConstant.PAYPATHSTATUS_FAILED.equals(pathProcStatus));

        //更新211为终态111
        if(status211){
            payTransDtlInfoService.updateFinalStatusSucc(payTransDtlInfoDO);
        }

        //更新200为终态000
        if(status200){
            payTransDtlInfoService.updateFinalStatusFail(payTransDtlInfoDO);
        }

        //更新290为090
        if(status290){
            updateStatusX90To090(payTransDtlInfoDO);
        }

        //核心异常，回查核心
        if(status221 || status220){
            logger.info("调用核心回查事件,平台日期：{},平台流水：{}",payDate,paySerno);
            coreEventServiceImpl.registerCoreQry(payTransDtlInfoDO.getCoreReqDate(), payTransDtlInfoDO.getCoreReqSerno(),payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), TxEndNTCoreQryCallBackService.class);
        }

        //上核心失败，终态推定失败，拉起告警
        if(status210 || status110){
            logger.error("告警级别：{}，告警信息：核心入账成功，平台推断失败，配置监控,平台日期：{},平台流水：{}",
                    LogMonitorLevelCdEnum.ECNY_LOG_MONITOR_ERROR.getCode(), payDate,paySerno);
        }

        //终态推定成功，未上核心或上核心失败，进行补入账
        if(status201 || status291 || status001 || status091){
            logger.info("登记事件进行补入账,平台日期：{},平台流水：{}",payDate,paySerno);
            coreEventServiceImpl.registerReCredit(payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), ReCreditCallBackServiceImpl.class);
        }

        logger.info("调用兑回推断处理结束,平台日期：{},平台流水：{}",payDate,paySerno);

    }

    /**
     * 更新交易状态290为090
     * @param payTransDtlInfoDO
     */
    private void updateStatusX90To090(PayTransDtlInfoDO payTransDtlInfoDO) {
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_INIT);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
        payTransDtlInfoService.updateFinalStatusX90To090(payTransDtlInfoDO,stateMachine);
    }

    /**
     * 终态通知兑出处理
     * @param txEndNtfcntReqDTO
     */
    public void deal225(TxEndNtfctn txEndNtfcntReqDTO){

        String processStatus = txEndNtfcntReqDTO.getOrgnlMsgCntt().getPrcSts();
        // 推定失败
        if (StringUtils.equalsAny(processStatus, ProcessStsCdEnum.PR01.getCode(), ProcessStsCdEnum.PR04.getCode())) {

            String orgnlMsgId = txEndNtfcntReqDTO.getOrgnlGrpHdr().getOrgnlMsgId();
            PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(orgnlMsgId);
            // 原交易存在
            if (null != payTransDtlInfoDO) {
                processOriTxn225(payTransDtlInfoDO, txEndNtfcntReqDTO);
            } else {
                // 原交易不存在
                logger.error("原兑出交易不存在，不处理");
            }

        } else {
            logger.error("兑出交易推定成功，不处理！");
        }
    }

    public void processOriTxn225(PayTransDtlInfoDO payTransDtlInfoDO, TxEndNtfctn txEndNtfcntReqDTO) {
        // 更新交易状态和通道信息
        if (AppConstant.CORESTATUS_FAILED.equals(payTransDtlInfoDO.getCoreProcStatus())) {
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
        }

        payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);

        if(StringUtils.isNotBlank(txEndNtfcntReqDTO.getOrgnlMsgCntt().getRjctCd())){
            payTransDtlInfoDO.setPayPathRetCode(txEndNtfcntReqDTO.getOrgnlMsgCntt().getRjctCd());
        }else{
            payTransDtlInfoDO.setPayPathRetCode(txEndNtfcntReqDTO.getOrgnlMsgCntt().getPrcCd());
        }
        payTransDtlInfoDO.setPayPathRetMsg(txEndNtfcntReqDTO.getOrgnlMsgCntt().getRjctInf());
        payTransDtlInfoService.update(payTransDtlInfoDO);
        // 原核心失败，不用处理
        if (AppConstant.CORESTATUS_FAILED.equals(payTransDtlInfoDO.getCoreProcStatus())) {
            logger.error("原兑出交易失败，不处理");
        } else {
            // 核心成功或者异常,登记异常事件冲正（异常事件冲正会先回查，所以核心异常可以直接登记冲正事件）
            coreEventServiceImpl.registerBankRev(payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), ConvertBankRevCallBackServiceImpl.class);
        }
    }

    /**
     * 终态通知兑出处理
     * @param txEndNtfcntReqDTO
     */
    public void deal227(TxEndNtfctn txEndNtfcntReqDTO){

        String processStatus = txEndNtfcntReqDTO.getOrgnlMsgCntt().getPrcSts();
        String orgnlMsgId = txEndNtfcntReqDTO.getOrgnlGrpHdr().getOrgnlMsgId();
        // 原交易一定存在
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(orgnlMsgId);
        if (null == payTransDtlInfoDO) {
            logger.info("汇款兑出终态通知原交易不存在");
            return;
        }
        payTransDtlInfoDO.setTrxRetCode(txEndNtfcntReqDTO.getOrgnlMsgCntt().getRjctCd());
        payTransDtlInfoDO.setTrxRetMsg(txEndNtfcntReqDTO.getOrgnlMsgCntt().getRjctInf());
        payTransDtlInfoDO.setPayPathRspStatus(txEndNtfcntReqDTO.getOrgnlMsgCntt().getPrcSts());
        payTransDtlInfoDO.setPayPathRetMsg(txEndNtfcntReqDTO.getOrgnlMsgCntt().getRjctInf());
        payTransDtlInfoDO.setPayPathRetDate(DateUtil.getDefaultDate());
        payTransDtlInfoDO.setPayPathRetSerno(txEndNtfcntReqDTO.getGrpHdr().getMsgId());

        if(StringUtils.isNotBlank(txEndNtfcntReqDTO.getOrgnlMsgCntt().getRjctCd())){
            payTransDtlInfoDO.setPayPathRetCode(txEndNtfcntReqDTO.getOrgnlMsgCntt().getRjctCd());
        }else{
            payTransDtlInfoDO.setPayPathRetCode(txEndNtfcntReqDTO.getOrgnlMsgCntt().getPrcCd());
        }

        // 推定失败
        if (StringUtils.equalsAny(processStatus, ProcessStsCdEnum.PR01.getCode(), ProcessStsCdEnum.PR04.getCode())) {

            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            try {
                payTransDtlInfoService.update(payTransDtlInfoDO);
            } catch (Exception e) {
                logger.error("汇款兑出，终态通知失败，更新数据库信息失败：{}-{}", e.getMessage(), e);
                throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
            }
            // 登记冲正事件
            coreEventServiceImpl.registerBankRev(payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), ConvertBankRevCallBackServiceImpl.class);
        } else {
            // 推定成功
            payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);

            // 原状态机
            StateMachine stateMachine = new StateMachine();
            stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
            try {
                payTransDtlInfoService.update(payTransDtlInfoDO, stateMachine);
            } catch (Exception e) {
                logger.error("汇款兑出，终态通知成功，更新数据库信息失败：{}-{}", e.getMessage(), e);
                throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
            }
        }

    }

    /**
     * 终态通知差错贷记调账处理
     * @param txEndNtfctn
     */
    public void deal801(TxEndNtfctn txEndNtfctn){

        String orgnlInstgPty = txEndNtfctn.getOrgnlGrpHdr().getOrgnlInstgPty();
        logger.info("终态通知差错贷记调账：原发起机构号：{}，业务状态：{}，原报文标识号：{}",orgnlInstgPty,txEndNtfctn.getOrgnlMsgCntt().getPrcSts(),txEndNtfctn.getOrgnlGrpHdr().getOrgnlMsgId());
        //终态通知差错贷记调账往账流程（原发起机构为广发）
        if(StringUtils.equals(AppConstant.CGB_FINANCIAL_INSTITUTION_CD,orgnlInstgPty)){
            processSend801(txEndNtfctn);
        }else{
            //终态通知差错贷记调账来账处理
            processRecv801(txEndNtfctn);
        }
    }

    /**
     * 终态通知差错贷记调账往账处理
     * @param txEndNtfctn
     *
     */
    private void processSend801(TxEndNtfctn txEndNtfctn) {
        String processStatus = txEndNtfctn.getOrgnlMsgCntt().getPrcSts();
        String orgnlMsgId = txEndNtfctn.getOrgnlGrpHdr().getOrgnlMsgId();
        PayTransDtlInfoDO payTransDtlInfoDOOLD = payTransDtlInfoService.query(orgnlMsgId);
        boolean isSucc = (StringUtils.equalsAny(processStatus, ProcessStsCdEnum.PR00.getCode(), ProcessStsCdEnum.PR03.getCode()));
        boolean isFail = StringUtils.equalsAny(processStatus, ProcessStsCdEnum.PR01.getCode(), ProcessStsCdEnum.PR04.getCode());
        logger.info("终态通知差错贷记调账-往账：推定状态isSucc={},存在原交易801登记簿信息{}",isSucc,null!=payTransDtlInfoDOOLD);
        //推定失败
        if(isFail){
            // 有原交易信息（更新状态为090）
            if(null != payTransDtlInfoDOOLD){
                //如果拒绝码不为空更新拒绝码到通道返回码，否则更新业务状态码到通道返回码
                updatePayPathRetCode(payTransDtlInfoDOOLD,txEndNtfctn);
                payTransDtlInfoDOOLD.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
                payTransDtlInfoDOOLD.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
                payTransDtlInfoDOOLD.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);

                updatePayTransDtlInfo(payTransDtlInfoDOOLD,new StateMachine());
            }
        }
        //推定成功
        if(isSucc){
            // 有原交易信息（更新原交易金融登记簿状态为191）
            if(null != payTransDtlInfoDOOLD){
                //如果拒绝码不为空更新拒绝码到通道返回码，否则更新业务状态码到通道返回码
                updatePayPathRetCode(payTransDtlInfoDOOLD,txEndNtfctn);
                payTransDtlInfoDOOLD.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
                payTransDtlInfoDOOLD.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
                payTransDtlInfoDOOLD.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
                updatePayTransDtlInfo(payTransDtlInfoDOOLD,new StateMachine());
                //获取原原交易更新业务状态为A
                PayTransDtlInfoDO payTransDtlInfoDOTemp = payTransDtlInfoService.query(payTransDtlInfoDOOLD.getOrigPayPathSerno());
                if(null != payTransDtlInfoDOTemp){
                    payTransDtlInfoDOTemp.setTrxStatus(AppConstant.TRXSTATUS_PRECREDITSUCCESS);
                    //评审后加状态机控制201  modify 2021-01-29
                    StateMachine stateMachine = new StateMachine();
                    stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
                    stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_FAILED);
                    stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
                    updatePayTransDtlInfo(payTransDtlInfoDOTemp,new StateMachine());
                }
            }else{
                //无原交易信息
                String cntt = txEndNtfctn.getOrgnlMsgCntt().getCntt();
                JSONObject cnttJsonObj = JSONObject.parseObject(cntt);
                //1、转换原报文实体
                //2、从原报文实体获取原原报文标识号，查询原原报文
                //3、原原报文存在，补充原交易信息补插入登记簿,原原报文更新业务状态为A
                //4、原原报文不存在，补插入原交易登记簿
                PayTransDtlInfoDO payTransDtlInfoDONEW=init801Txn(cnttJsonObj);
                updatePayPathRetCode(payTransDtlInfoDONEW,txEndNtfctn);
                //补插入原交易金融登记簿（191）
                payTransDtlInfoDONEW.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
                payTransDtlInfoDONEW.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
                payTransDtlInfoDONEW.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
                payTransDtlInfoService.insert(payTransDtlInfoDONEW);
            }
        }

    }

    private void processRecv801(TxEndNtfctn txEndNtfctn){

        String processStatus = txEndNtfctn.getOrgnlMsgCntt().getPrcSts();
        String orgnlMsgId = txEndNtfctn.getOrgnlGrpHdr().getOrgnlMsgId();
        PayTransDtlInfoDO payTransDtlInfoDONEW = new PayTransDtlInfoDO();
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(orgnlMsgId);
        logger.info("终态通知差错贷记调账-来账：原报文编号{}，推定业务状态{}",orgnlMsgId,processStatus);
        if(null == payTransDtlInfoDO){
            String cntt = txEndNtfctn.getOrgnlMsgCntt().getCntt();
            JSONObject cnttJsonObj = JSONObject.parseObject(cntt);
            payTransDtlInfoDONEW = init801Txn(cnttJsonObj);//初始化原交易登记簿
            //如果拒绝码不为空更新拒绝码到通道返回码，否则更新业务状态码到通道返回码
            updatePayPathRetCode(payTransDtlInfoDONEW,txEndNtfctn);
        }else{
            //如果拒绝码不为空更新拒绝码到通道返回码，否则更新业务状态码到通道返回码
            updatePayPathRetCode(payTransDtlInfoDO,txEndNtfctn);
        }
        if(StringUtils.equalsAny(processStatus, ProcessStsCdEnum.PR00.getCode(), ProcessStsCdEnum.PR03.getCode())){
            //推定成功
            processRecv801Succ(payTransDtlInfoDO,payTransDtlInfoDONEW,txEndNtfctn);
        }else {
            //推定失败
            processRecv801Fail(payTransDtlInfoDO,payTransDtlInfoDONEW,txEndNtfctn);
        }
    }

    private void processRecv801Succ(PayTransDtlInfoDO payTransDtlInfoDO,PayTransDtlInfoDO payTransDtlInfoDONEW ,TxEndNtfctn txEndNtfctn){
      //  Map<String, Object> tempContext = EcnyTradeContext.getTempContext(context);
        String processStatus = txEndNtfctn.getOrgnlMsgCntt().getPrcSts();
        //无记录，将贷记调账来账插入金融登记簿，状态初始为221
        if(null == payTransDtlInfoDO){
            //补插入原交易金融登记簿（221）
            logger.info("终态通知差错贷记调账-来账：原交易登记簿记录不存在，补插入状态为291");
            payTransDtlInfoDONEW.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
            payTransDtlInfoDONEW.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
            payTransDtlInfoDONEW.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);

            payTransDtlInfoDONEW.setPayPathRspStatus(processStatus);//更新业务状态——通道回执状态
            payTransDtlInfoService.insert(payTransDtlInfoDONEW);
            try{
                //如果收付款人账号信息为空，说明未查到原原交易登记簿信息或者原原交易登记簿信息未记录到收付款人账号，不上核心，只补登记原交易801登记簿信息
                if(StringUtils.isBlank(payTransDtlInfoDONEW.getPayeeAcct())&&StringUtils.isBlank(payTransDtlInfoDONEW.getPayerAcct())){
                    logger.error("补插入登记簿未查询到原原交易信息，无法补充收款人和付款人账号，无法入核心，只补登记801登记簿信息payPathSerno:"+payTransDtlInfoDONEW.getPayPathSerno());
                    return;
                }
                //入金融流水表351100上核心记账
                coreProcess(payTransDtlInfoDONEW);
            }catch (EcnyTransException e){
                logger.error("告警级别：{}，告警信息：核心入账成功，平台推断失败，配置监控,平台日期：{},平台流水：{}",
                        LogMonitorLevelCdEnum.ECNY_LOG_MONITOR_ERROR.getCode(), payTransDtlInfoDONEW.getPayDate(),payTransDtlInfoDONEW.getPaySerno());
                if(StringUtils.equals(e.getErrorCode(),EcnyTransError.OTHER_TECH_ERROR.getErrorCode())){
                    //捕获异常发送核心回调事件
                    AccFlowDO accFlowDO = bankCoreAccTxnServiceImpl.selectByPayInfo(payTransDtlInfoDONEW.getPayDate(), payTransDtlInfoDONEW.getPaySerno());
                    coreEventServiceImpl.registerCoreQry(accFlowDO.getCoreReqDate(),accFlowDO.getCoreReqSerno(),accFlowDO.getPayDate(),accFlowDO.getPaySerno(), BankCoreQryCallBackServiceImpl.class);
                }
            }
        }else{
            //来账业务业务状态成功：更新通道状态XX1，更新业务状态到通道回执状态，业务处理码到通道返回码
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
            updatePayTransDtlInfo(payTransDtlInfoDO,new StateMachine());

            String trxStatus = payTransDtlInfoDO.getTrxStatus();
            String coreProcStatus = payTransDtlInfoDO.getCoreProcStatus();
            String pathProcStatus = payTransDtlInfoDO.getPathProcStatus();
            logger.info("终态通知差错贷记调账-来账：原交易登记簿记录存在，初始更新状态为{}{}{}",trxStatus,coreProcStatus,pathProcStatus);
            boolean isStatus221 = (StringUtils.equals(trxStatus,AppConstant.TRXSTATUS_ABEND)
                    && StringUtils.equals(coreProcStatus,AppConstant.CORESTATUS_ABEND)
                    && StringUtils.equals(pathProcStatus,AppConstant.PAYPATHSTATUS_SUCCESS));

            boolean isStatus001 = (StringUtils.equals(trxStatus,AppConstant.TRXSTATUS_FAILED)
                    && StringUtils.equals(coreProcStatus,AppConstant.CORESTATUS_FAILED)
                    && StringUtils.equals(pathProcStatus,AppConstant.PAYPATHSTATUS_SUCCESS));

            boolean isStauts291 = (StringUtils.equals(trxStatus,AppConstant.TRXSTATUS_ABEND)
                    && StringUtils.equals(coreProcStatus,AppConstant.CORESTATUS_INIT)
                    && StringUtils.equals(pathProcStatus,AppConstant.PAYPATHSTATUS_SUCCESS));
            //有记录(221)发送核心回查事件  
            if(isStatus221){
                AccFlowDO accFlowDO = bankCoreAccTxnServiceImpl.selectByPayInfo(payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
                coreEventServiceImpl.registerCoreQry(accFlowDO.getCoreReqDate(),accFlowDO.getCoreReqSerno(),accFlowDO.getPayDate(),accFlowDO.getPaySerno(), BankCoreQryCallBackServiceImpl.class);
            }

            //有记录（001或291）更新金融登记簿状态为221
            if(isStatus001||isStauts291){
                StateMachine stateMachine = new StateMachine();
                stateMachine.setPreTrxStatus(trxStatus);
                stateMachine.setPreCoreProcStatus(coreProcStatus);
                stateMachine.setPrePathProcStatus(pathProcStatus);
                //设置登记簿更新状态为291
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
                payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_INIT);
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);
                int updateNum = updatePayTransDtlInfo(payTransDtlInfoDO,stateMachine);
                if(updateNum != 1){
                    logger.error("差错贷记调账往账业务状态失败更新登记簿001更新为221失败{} "+payTransDtlInfoDO.getPayPathSerno());
                    throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED,EcnyTransError.OTHER_TECH_ERROR);
                }
                try{
                    //入金融流水表351100上核心记账
                    coreProcess(payTransDtlInfoDO);
                }catch (EcnyTransException e){
                    logger.error("告警级别：{}，告警信息：核心入账成功，平台推断失败，配置监控,平台日期：{},平台流水：{}",
                            LogMonitorLevelCdEnum.ECNY_LOG_MONITOR_ERROR.getCode(), payTransDtlInfoDO.getPayDate(),payTransDtlInfoDO.getPaySerno());
                    //捕获异常发送核心回调事件
                    if(StringUtils.equals(e.getErrorCode(),EcnyTransError.OTHER_TECH_ERROR.getErrorCode())){
                        logger.info("注册核心回查事件查询核心状态");
                        AccFlowDO accFlowDO = bankCoreAccTxnServiceImpl.selectByPayInfo(payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
                        coreEventServiceImpl.registerCoreQry(accFlowDO.getCoreReqDate(),accFlowDO.getCoreReqSerno(),accFlowDO.getPayDate(),accFlowDO.getPaySerno(), BankCoreQryCallBackServiceImpl.class);
                    }
                }
            }
        }
    }

    private void processRecv801Fail(PayTransDtlInfoDO payTransDtlInfoDO,PayTransDtlInfoDO payTransDtlInfoDONEW,TxEndNtfctn txEndNtfctn){
        //无记录，将贷记调账来账插入金融登记簿，状态初始为221  
        if(null == payTransDtlInfoDO){
            logger.info("终态通知差错贷记调账-来账业务状态失败：无原交易登记簿信息，补登记状态为000");
            payTransDtlInfoDONEW.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
            payTransDtlInfoDONEW.setCoreProcStatus(AppConstant.CORESTATUS_FAILED);
            payTransDtlInfoDONEW.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);

            payTransDtlInfoService.insert(payTransDtlInfoDONEW);

        }else{
            //来账业务业务状态失败：更新通道状态XX0，更新业务状态到通道回执状态，业务处理码到通道返回码
            payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_FAILED);
            updatePayTransDtlInfo(payTransDtlInfoDO,new StateMachine());
            //有记录
            String trxStatus = payTransDtlInfoDO.getTrxStatus();
            String coreProcStatus = payTransDtlInfoDO.getCoreProcStatus();
            String pathProcStatus = payTransDtlInfoDO.getPathProcStatus();

            logger.info("终态通知差错贷记调账-来账业务状态失败：原交易登记簿状态：{}{}{}",trxStatus,coreProcStatus,pathProcStatus);
            boolean isStatus220 = (StringUtils.equals(trxStatus,AppConstant.TRXSTATUS_ABEND)
                    && StringUtils.equals(coreProcStatus,AppConstant.CORESTATUS_ABEND)
                    && StringUtils.equals(pathProcStatus,AppConstant.PAYPATHSTATUS_FAILED));

            boolean isStatus110 = (StringUtils.equals(trxStatus,AppConstant.TRXSTATUS_SUCCESS)
                    && StringUtils.equals(coreProcStatus,AppConstant.CORESTATUS_SUCCESS)
                    && StringUtils.equals(pathProcStatus,AppConstant.PAYPATHSTATUS_FAILED));

            boolean isStatus290 = (StringUtils.equals(trxStatus,AppConstant.TRXSTATUS_ABEND)
                    && StringUtils.equals(coreProcStatus,AppConstant.CORESTATUS_INIT)
                    && StringUtils.equals(pathProcStatus,AppConstant.PAYPATHSTATUS_FAILED));

            //220（注册核心回调事件）
            if(isStatus220){
                AccFlowDO accFlowDO = bankCoreAccTxnServiceImpl.selectByPayInfo(payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
                coreEventServiceImpl.registerCoreQry(accFlowDO.getCoreReqDate(),accFlowDO.getCoreReqSerno(),accFlowDO.getPayDate(),accFlowDO.getPaySerno(), BankCoreQryCallBackServiceImpl.class);
            }
            //业务状态失败存在记录290，更新为000
            if(isStatus290){
                payTransDtlInfoService.updateFinalStatus290To000(payTransDtlInfoDO);
            }
            //业务状态失败存在记录110，更新为210
            if(isStatus110){
                payTransDtlInfoService.updateFinalStatus110To210(payTransDtlInfoDO);
            }
            //业务状态失败存在记录000，直接应答902
        }

    }

    private int updatePayTransDtlInfo(PayTransDtlInfoDO payTransDtlInfoDO,StateMachine stateMachine) {
        try {
            return payTransDtlInfoService.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("差错贷记调账，终态通知失败，更新数据库信息失败：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 入账处理
     * @param payTransDtlInfoDO
     */
    private void coreProcess(PayTransDtlInfoDO payTransDtlInfoDO) {
        // 初始化核心请求报文
        BankCore351100InnerReq bankCore351100InnerReq = sendCoreInit(payTransDtlInfoDO);
        // 核心前处理
        sendCorePre(payTransDtlInfoDO, bankCore351100InnerReq);
        // 发送核心
        BankCore351100InnerRsp bankCore351100InnerRsp = sendToCore(bankCore351100InnerReq);
        // 核心后处理
        sendCoreDone(payTransDtlInfoDO, bankCore351100InnerRsp);

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
        BankCore351100InnerReq bankCore351100InnerReq = bankCoreProcessService.initBankCore351100InnerReq(payTransDtlInfoDO);
        logger.info("sendCoreInit：上核心前处理结束 ");
        return bankCore351100InnerReq;
    }

    /**
     * 调351100上核心入账
     *
     * @param bankCore351100InnerReq
     */
    public BankCore351100InnerRsp sendToCore(BankCore351100InnerReq bankCore351100InnerReq) {
        logger.info("sendToCore：上核心入账开始");
        BankCore351100InnerRsp bankCore351100InnerRsp = null;
        try{
            bankCore351100InnerRsp = bankCoreImplDubboServiceImpl.coreServer(bankCore351100InnerReq);
        } catch (Exception e) {
            logger.info("上核心入账失败:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.OTHER_TECH_ERROR);
        }
        logger.info("sendToCore：上核心入账结束");
        return bankCore351100InnerRsp;
    }

    /**
     * 核心后处理，接收核心结果，更新对应状态
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendCoreDone(PayTransDtlInfoDO payTransDtlInfoDO,BankCore351100InnerRsp bankCore351100InnerRsp) {
        logger.info("afterSendCoreDeal：接收核心结果，更新对应状态开始");
        setTradeResult(payTransDtlInfoDO, bankCore351100InnerRsp);
        //221
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);

        try {
            if (AppConstant.CORESTATUS_SUCCESS.equals(bankCore351100InnerRsp.getCoreStatus())
                    || AppConstant.CORESTATUS_FAILED.equals(bankCore351100InnerRsp.getCoreStatus())) {
                bankCoreAccTxnServiceImpl.updateCoreAccFlow(bankCore351100InnerRsp);
                payTransDtlInfoService.update(payTransDtlInfoDO, stateMachine);
            }
        } catch (Exception e) {
            logger.error("afterSendCoreDeal：接收核心结果，更新对应状态异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
        }
    }

    private void setTradeResult(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {
        String coreStatus = bankCore351100InnerRsp.getCoreStatus();

        payTransDtlInfoDO.setPayDate(payTransDtlInfoDO.getPayDate());
        payTransDtlInfoDO.setPaySerno(payTransDtlInfoDO.getPaySerno());
        payTransDtlInfoDO.setCoreProcStatus(coreStatus);
        payTransDtlInfoDO.setCoreAcctDate(bankCore351100InnerRsp.getCoreRspDate());
        payTransDtlInfoDO.setCoreSerno(bankCore351100InnerRsp.getCoreRspSerno());
        payTransDtlInfoDO.setCoreRetCode(bankCore351100InnerRsp.getErrorCode());
        payTransDtlInfoDO.setCoreRetMsg(bankCore351100InnerRsp.getErrorMsg());
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_CRDT);

        switch (coreStatus) {
            case AppConstant.CORESTATUS_SUCCESS:
                logger.info("上核心入账成功");
                //更新金融登记簿111
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
                payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_SUCCESS);
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);

                payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
                payTransDtlInfoDO.setTrxRetCode(EcnyTransError.SUCCESS.getErrorCode());
                payTransDtlInfoDO.setTrxRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
                payTransDtlInfoDO.setPayPathRetCode(EcnyTransError.SUCCESS.getErrorCode());
                payTransDtlInfoDO.setPayPathRetMsg(EcnyTransError.SUCCESS.getErrorMsg());
                break;
            case AppConstant.CORESTATUS_FAILED:
                logger.info("上核心入账失败");
                //更新金融登记簿201
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
                payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_FAILED);
                payTransDtlInfoDO.setPathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);

                payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_FAIL);
                payTransDtlInfoDO.setTrxRetCode(bankCore351100InnerRsp.getErrorCode());
                payTransDtlInfoDO.setTrxRetMsg(bankCore351100InnerRsp.getErrorMsg());
                payTransDtlInfoDO.setPayPathRspStatus(ProcessStsCdEnum.PR01.getCode());
                RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(null);
                payTransDtlInfoDO.setPayPathRetCode(rspCodeMapDO.getDestRspCode());
                payTransDtlInfoDO.setPayPathRetMsg(rspCodeMapDO.getRspCodeDsp());
                break;
            default:
                logger.info("上核心入账异常，返回异常");
                payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);
                payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);

                break;
        }
    }

    /**
     * 核心前处理，入账务流水表，更新登记簿为状态为处理中
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerReq
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendCorePre(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerReq bankCore351100InnerReq) {
        logger.info("sendCorePre: 终态通知差错贷记调账入账务流水表，更新登记簿为状态为处理中，平台日期：{}，平台流水：{} ",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
        String coreReqDate = generateCodeService.getCoreReqDate(payTransDtlInfoDO.getBatchId());
        String coreReqSerno = generateCodeService.generateCoreReqSerno();

        bankCore351100InnerReq.setCoreReqDate(coreReqDate);
        bankCore351100InnerReq.setCoreReqSerno(coreReqSerno);

        // 设置核心日期流水
        payTransDtlInfoDO.setCoreReqSerno(coreReqSerno);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_CRDT);
        payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);

        //原状态291
        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_INIT);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);


        try {
            bankCoreAccTxnServiceImpl.insertCoreFlow(bankCore351100InnerReq, coreReqSerno, coreReqDate);
            int updateNum = payTransDtlInfoService.update(payTransDtlInfoDO, stateMachine);
            if(updateNum != 1){
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
            logger.info("sendCorePre: 终态通知差错贷记调账入账务流水表，更新登记簿为状态为处理中,处理结束");
        } catch (Exception e) {
            logger.error("sendCorePre: 终态通知差错贷记调账核心前处理异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
        }
    }

    /**
     * 初始化流水入库
     */
    public PayTransDtlInfoDO init801Txn(JSONObject reqMsgJson) {
        logger.info("init801Txn：终态通知差错贷记调账初始化金融登记簿开始 ");
        // 参数接收
      //  Head head = JSONObject.toJavaObject(reqMsgJson.getJSONObject("head"), Head.class);      //服务化报文json对象-->Head实体
        DCEPHeader dcepHeader = JSONObject.toJavaObject(reqMsgJson.getJSONObject("ecnyHead"), DCEPHeader.class);    //互联互通报文头json对象-->DCEPHeader实体
        DsptReqDTO body = (DsptReqDTO) JSONObject.toJavaObject(reqMsgJson.getJSONObject("body"), DsptReqDTO.class);//互联互通报文体json对象-->DCEPReqBody实体

        GrpHdr grpHdr = body.getDsptReq().getGrpHdr();
        OrgnlGrpHdr orgnlGrpHdr = body.getDsptReq().getOrgnlGrpHdr();
        DsptInf dsptInf = body.getDsptReq().getDsptInf();
        String orgnlInstgPty = orgnlGrpHdr.getOrgnlInstgPty();
        String orgnlMsgId = orgnlGrpHdr.getOrgnlMsgId();
        // 差错调账登记簿信息
        PayTransDtlInfoDO payTransDtlInfoDONEW = new PayTransDtlInfoDO();

        // 组装业务头组件信息
        payTransDtlInfoDONEW.setPayPathSerno(grpHdr.getMsgId());
        payTransDtlInfoDONEW.setInstgPty(grpHdr.getInstgPty().getInstgDrctPty());
        payTransDtlInfoDONEW.setInstdPty(grpHdr.getInstdPty().getInstdDrctPty());
        payTransDtlInfoDONEW.setRemark(grpHdr.getRmk());
        payTransDtlInfoDONEW.setMsgType(MsgTpEnum.DCEP801.getCode());

        payTransDtlInfoDONEW.setPayDate(DateUtil.getDefaultDate());
        payTransDtlInfoDONEW.setPaySerno(generateCodeService.generatePlatformFlowNo());
        payTransDtlInfoDONEW.setPayTime(DateUtil.getDefaultTime());
        // 组装原报文信息
        // 原报文标识号
        payTransDtlInfoDONEW.setOrigPayPathSerno(orgnlGrpHdr.getOrgnlMsgId());
        // 原报文编号
        payTransDtlInfoDONEW.setOrigMsgType(orgnlGrpHdr.getOrgnlMT());

        //终态通知差错贷记调账往账流程（原发起机构为广发）
        if(StringUtils.equals(AppConstant.CGB_FINANCIAL_INSTITUTION_CD,orgnlInstgPty)){
            payTransDtlInfoDONEW.setDirect(AppConstant.DIRECT_SEND);
            payTransDtlInfoDONEW.setPayFlag(AppConstant.IDENTIFICATION_PAYEE);
            payTransDtlInfoDONEW.setOperStep(AppConstant.OPERSTEP_DRDT);
        }else{
            payTransDtlInfoDONEW.setDirect(AppConstant.DIRECT_RECV);
            payTransDtlInfoDONEW.setPayFlag(AppConstant.IDENTIFICATION_PAYER);
            payTransDtlInfoDONEW.setOperStep(AppConstant.OPERSTEP_CRDT);
        }
        // 组装差错贷记调账信息
        // 差错业务类型编码
        payTransDtlInfoDONEW.setBusiType(dsptInf.getDsptBizTp());
        // 差错业务种类编码
        payTransDtlInfoDONEW.setBusiKind(dsptInf.getDsptCtgyPurpCd());
        // 差错原因码
        payTransDtlInfoDONEW.setPayPathRetCode(dsptInf.getDsptRsnCd());
        // 差错原因说明
        payTransDtlInfoDONEW.setPayPathRetMsg(dsptInf.getDsptRsnDesc());
        // 调账金额
        payTransDtlInfoDONEW.setAmount(dsptInf.getDsptAmt().getValue());
        // 币种
        payTransDtlInfoDONEW.setCcy(dsptInf.getDsptAmt().getCcy());
        // 交易批次号
        payTransDtlInfoDONEW.setBatchId(dsptInf.getBatchId());

        payTransDtlInfoDONEW.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlInfoDONEW.setLastUpTime(DateUtil.getDefaultTime());
        // 原兑换登记簿信息
        PayTransDtlInfoDO payTransDtlInfoDOOLD = payTransDtlInfoService.query(orgnlMsgId);
        if(null != payTransDtlInfoDOOLD) {
            // 原交易与现交易收付款方向调整
            payTransDtlInfoDONEW.setPayerPtyId(payTransDtlInfoDOOLD.getPayeePtyId());
            payTransDtlInfoDONEW.setPayerName(payTransDtlInfoDOOLD.getPayeeName());
            payTransDtlInfoDONEW.setPayerAcctType(payTransDtlInfoDOOLD.getPayeeAcctType());
            payTransDtlInfoDONEW.setPayerAcct(payTransDtlInfoDOOLD.getPayeeAcct());
            payTransDtlInfoDONEW.setPayerWalletId(payTransDtlInfoDOOLD.getPayeeWalletId());
            payTransDtlInfoDONEW.setPayerWalletName(payTransDtlInfoDOOLD.getPayeeWalletName());
            payTransDtlInfoDONEW.setPayerWalletLv(payTransDtlInfoDOOLD.getPayeeWalletLv());
            payTransDtlInfoDONEW.setPayerWalletType(payTransDtlInfoDOOLD.getPayeeWalletType());
            // 组装收款人信息
            payTransDtlInfoDONEW.setPayeePtyId(payTransDtlInfoDOOLD.getPayerPtyId());
            payTransDtlInfoDONEW.setPayeeName(payTransDtlInfoDOOLD.getPayerName());
            payTransDtlInfoDONEW.setPayeeAcctType(payTransDtlInfoDOOLD.getPayerAcctType());
            payTransDtlInfoDONEW.setPayeeAcct(payTransDtlInfoDOOLD.getPayerAcct());
            payTransDtlInfoDONEW.setPayeeWalletId(payTransDtlInfoDOOLD.getPayerWalletId());
            payTransDtlInfoDONEW.setPayeeWalletName(payTransDtlInfoDOOLD.getPayerWalletName());
            payTransDtlInfoDONEW.setPayeeWalletLv(payTransDtlInfoDOOLD.getPayerWalletLv());
            payTransDtlInfoDONEW.setPayeeAcctType(payTransDtlInfoDOOLD.getPayerAcctType());

            //往账原交易不存在，补充原交易后更新兑换即原原交易业务状态为A
            if(StringUtils.equals(AppConstant.CGB_FINANCIAL_INSTITUTION_CD,orgnlInstgPty)){
                payTransDtlInfoDOOLD.setTrxStatus(AppConstant.TRXSTATUS_PRECREDITSUCCESS);
                updatePayTransDtlInfo(payTransDtlInfoDOOLD,new StateMachine());
            }

        }
        logger.info("init801Txn：终态通知差错贷记调账初始化金融登记簿成功 ");
        return payTransDtlInfoDONEW;
    }

    private void updatePayPathRetCode(PayTransDtlInfoDO payTransDtlInfoDO,TxEndNtfctn txEndNtfctn){
        payTransDtlInfoDO.setPayPathRspStatus(txEndNtfctn.getOrgnlMsgCntt().getPrcSts());//更新业务状态——通道回执状态
        //如果拒绝码不为空更新拒绝码到通道返回码，否则更新业务状态码到通道返回码
        if(StringUtils.isBlank(txEndNtfctn.getOrgnlMsgCntt().getRjctCd())){
            payTransDtlInfoDO.setPayPathRetCode(txEndNtfctn.getOrgnlMsgCntt().getPrcCd());//更新业务状态码——通道返回码
        }else{
            payTransDtlInfoDO.setPayPathRetCode(txEndNtfctn.getOrgnlMsgCntt().getRjctCd());//更新业务拒绝码——通道返回码
            payTransDtlInfoDO.setPayPathRetMsg(txEndNtfctn.getOrgnlMsgCntt().getRjctInf());//更新业务拒绝信息——通道返回信息
        }
    }
}
