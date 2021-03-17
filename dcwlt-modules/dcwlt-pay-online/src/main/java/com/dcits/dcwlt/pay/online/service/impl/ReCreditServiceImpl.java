package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.common.pay.channel.event.msg.EventDealRspMsg;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.constant.EventConst;
import com.dcits.dcwlt.common.pay.exception.PlatformError;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IEventService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import com.dcits.dcwlt.pay.online.service.IReCreditCallBackService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 补入账事件处理
 * 状态为001,201,291可进行补入账
 */
@Service
public class ReCreditServiceImpl implements IEventService {

    private static final Logger logger = LoggerFactory.getLogger(ReCreditServiceImpl.class);

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoRepository;

    @Autowired
    private BankCoreAccTxnServiceImpl bankCoreAccTxnServiceImpl;

    @Autowired
    private ICoreProcessService bankCoreProcessService;

    @Autowired
    private CoreEventServiceImpl coreEventServiceImpl;

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;


    //@Override
    //@ParamLog
    public EventDealRspMsg runFlow(EventDealReqMsg eventDealReqMsg) {
        //1、初始化返回信息
        EventDealRspMsg eventDealRspMsg = initEventRspMsg(eventDealReqMsg);
        try {
            //2、获取异常事件配置
            String callBackClassName = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("callBackCanonicalName");
            String payDate = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("payDate");
            String paySerno = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("paySerno");

            BankCore351100InnerRsp bankCore351100InnerRsp = new BankCore351100InnerRsp();

            logger.info("补入账开始,平台日期 ：{},平台流水：{}", payDate, paySerno);
            PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoRepository.query(payDate, paySerno);
            String coreProcStatus = payTransDtlInfoDO.getCoreProcStatus();

            //3、判断核心状态支持补入账
            if (!AppConstant.CORESTATUS_INIT.equals(coreProcStatus)
                    && !AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)) {
                logger.info("该核心状态为 {}, 不允许补入账", coreProcStatus);
                eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
                eventDealRspMsg.setRespCode(PlatformError.SUCCESS.getErrorCode());
                eventDealRspMsg.setRespMsg("该核心状态不为0或9，不允许补入账");
                return eventDealRspMsg;
            }

            //补入账开始
            //上核心前异常 297，终态通知成功，291
            boolean status291 = (AppConstant.TRXSTATUS_ABEND.equals(payTransDtlInfoDO.getTrxStatus())
                    && AppConstant.CORESTATUS_INIT.equals(payTransDtlInfoDO.getCoreProcStatus())
                    && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

            //上核心前失败 090，终态通知成功，091
            boolean status091 = (AppConstant.TRXSTATUS_FAILED.equals(payTransDtlInfoDO.getTrxStatus())
                    && AppConstant.CORESTATUS_INIT.equals(payTransDtlInfoDO.getCoreProcStatus())
                    && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

            //201上核心失败后异常，终态推定成功
            boolean status201 = (AppConstant.TRXSTATUS_ABEND.equals(payTransDtlInfoDO.getTrxStatus())
                    && AppConstant.CORESTATUS_FAILED.equals(payTransDtlInfoDO.getCoreProcStatus())
                    && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

            //001，原交易上核心失败，终态推定成功
            boolean status001 = (AppConstant.TRXSTATUS_FAILED.equals(payTransDtlInfoDO.getTrxStatus())
                    && AppConstant.CORESTATUS_FAILED.equals(payTransDtlInfoDO.getCoreProcStatus())
                    && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

            if (status291 || status091 || status201 || status001) {

                logger.info("生成新核心请求流水调351100上核心,平台日期 ：{},平台流水：{}", payDate, paySerno);

                //状态 291、091--> 221，状态 201、001 --> 221
                StateMachine stateMachine = new StateMachine();
                stateMachine.setPreTrxStatus(payTransDtlInfoDO.getTrxStatus());
                stateMachine.setPreCoreProcStatus(payTransDtlInfoDO.getCoreProcStatus());
                stateMachine.setPrePathProcStatus(payTransDtlInfoDO.getPathProcStatus());

                bankCore351100InnerRsp = reCreditNew(payTransDtlInfoDO, stateMachine);
            }

            //5.执行回调程序
            eventDealRspMsg = runCallBack(eventDealRspMsg, bankCore351100InnerRsp, callBackClassName, JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()));

            //拼包响应
            return packEventRspMsg(eventDealRspMsg, bankCore351100InnerRsp);

        } catch (Exception e) {

            errHandler(eventDealRspMsg, e);

        }

        return eventDealRspMsg;
    }


    /**
     * 生成新核心请求流水调351100上核心
     *
     * @param payTransDtlInfoDO
     */
    public BankCore351100InnerRsp reCreditNew(PayTransDtlInfoDO payTransDtlInfoDO, StateMachine stateMachine) {

        //上核心前处理
        BankCore351100InnerReq bankCore351100InnerReq = bankCoreProcessService.initBankCore351100InnerReq(payTransDtlInfoDO);
        sendCorePre(payTransDtlInfoDO, bankCore351100InnerReq, stateMachine);

        //上核心入账
        BankCore351100InnerRsp bankCore351100InnerRsp = bankCoreProcessService.sendToCore(bankCore351100InnerReq);

        //上核心后处理
        sendCoreDoneReCredit(payTransDtlInfoDO, bankCore351100InnerRsp);

        return bankCore351100InnerRsp;
    }


    /**
     * 核心前处理，入账务流水表，更新登记簿为状态为处理中,插入操作流水信息
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerReq
     */
    public void sendCorePre(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerReq bankCore351100InnerReq,StateMachine stateMachine) {
        logger.info("sendCorePre: 入账务流水表，更新登记簿为状态为处理中，平台日期：{}，平台流水：{} ",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
        String coreReqDate = generateCodeService.getCoreReqDate(payTransDtlInfoDO.getBatchId());
        String coreReqSerno = generateCodeService.generateCoreReqSerno();

        bankCore351100InnerReq.setCoreReqDate(coreReqDate);
        bankCore351100InnerReq.setCoreReqSerno(coreReqSerno);
        // 设置核心日期流水
        payTransDtlInfoDO.setCoreReqDate(coreReqDate);
        payTransDtlInfoDO.setCoreReqSerno(coreReqSerno);
        payTransDtlInfoDO.setCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        payTransDtlInfoDO.setTrxStatus(AppConstant.TRXSTATUS_ABEND);
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_CRDT);
        payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_EXPT);

        try {
            bankCoreProcessService.sendCorePre(payTransDtlInfoDO, bankCore351100InnerReq, stateMachine);
        } catch (Exception e) {
            logger.error("sendCorePre: 核心前处理异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
        }
    }



    /**
     * 补入账核心后处理
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     */
    public void sendCoreDoneReCredit(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {
        logger.info("补入账核心后处理，核心返回，核心状态：{}，核心返回码：{}，核心返回信息：{}",
                bankCore351100InnerRsp.getCoreStatus(), bankCore351100InnerRsp.getErrorCode(), bankCore351100InnerRsp.getErrorMsg());

        setTradeResult(payTransDtlInfoDO, bankCore351100InnerRsp);

        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(AppConstant.TRXSTATUS_ABEND);
        stateMachine.setPreCoreProcStatus(AppConstant.CORESTATUS_ABEND);
        stateMachine.setPrePathProcStatus(AppConstant.PAYPATHSTATUS_SUCCESS);

        try {
            if (AppConstant.CORESTATUS_SUCCESS.equals(bankCore351100InnerRsp.getCoreStatus())
                    || AppConstant.CORESTATUS_FAILED.equals(bankCore351100InnerRsp.getCoreStatus())) {
                bankCoreProcessService.sendCoreDone(payTransDtlInfoDO,bankCore351100InnerRsp,stateMachine);
            }
        } catch (Exception e) {
            logger.error("sendCoreDone：接收核心结果，更新对应状态异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
        }
    }


    /**
     * 补入账核心返回后设置核心对应参数
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerRsp
     */
    private void setTradeResult(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp) {
        logger.info("补入账设置核心返回结果开始");
        String coreStatus = bankCore351100InnerRsp.getCoreStatus();

        if (StringUtils.isBlank(bankCore351100InnerRsp.getCoreReqDate())) {
            bankCore351100InnerRsp.setCoreReqDate(payTransDtlInfoDO.getCoreReqDate());
        }
        if (StringUtils.isBlank(bankCore351100InnerRsp.getCoreReqSerno())) {
            bankCore351100InnerRsp.setCoreReqSerno(payTransDtlInfoDO.getCoreReqSerno());
        }

        //补入账状态只更新核心状态
        payTransDtlInfoDO.setCoreProcStatus(coreStatus);
        payTransDtlInfoDO.setCoreAcctDate(bankCore351100InnerRsp.getCoreRspDate());
        payTransDtlInfoDO.setCoreSerno(bankCore351100InnerRsp.getCoreRspSerno());
        payTransDtlInfoDO.setCoreRetCode(bankCore351100InnerRsp.getErrorCode());
        payTransDtlInfoDO.setCoreRetMsg(bankCore351100InnerRsp.getErrorMsg());
        payTransDtlInfoDO.setOperStep(AppConstant.OPERSTEP_CRDT);

        switch (coreStatus) {
            case AppConstant.CORESTATUS_SUCCESS:
                logger.info("上核心入账成功");
                payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_SUCC);
                break;
            case AppConstant.CORESTATUS_FAILED:
                logger.info("上核心入账失败");
                payTransDtlInfoDO.setOperStatus(AppConstant.OPERSTATUS_FAIL);
                break;
            default:
                logger.info("上核心入账异常，回查核心");
                coreEventServiceImpl.registerCoreQry(bankCore351100InnerRsp.getCoreReqDate(), bankCore351100InnerRsp.getCoreReqSerno(), payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), ReCreditCoreQryCallBackServiceImpl.class);
                break;
        }

        logger.info("设置核心返回结果结束，核心日期：{}，核心请求流水：{}",
                bankCore351100InnerRsp.getCoreReqDate(), bankCore351100InnerRsp.getCoreReqSerno());

    }

    /**
     * 补登回调事件
     *
     * @param eventDealRspMsg
     * @param bankCore351100InnerRsp
     * @param callBackClassName
     * @param eventParam
     * @return
     */
    private EventDealRspMsg runCallBack(EventDealRspMsg eventDealRspMsg, BankCore351100InnerRsp bankCore351100InnerRsp, String callBackClassName, JSONObject eventParam) {
        try {
            //应用不存在回调处理
            if (StringUtils.isBlank(callBackClassName)) {
                return eventDealRspMsg;
            }
            //存在回调,调用对应回调方法
            String coreProcStatus = bankCore351100InnerRsp.getCoreStatus();
            IReCreditCallBackService callBack = (IReCreditCallBackService) Class.forName(callBackClassName).newInstance();

            if (Constant.CORESTATUS_SUCCESS.equals(coreProcStatus)) {
                logger.info("补登成功");
                eventDealRspMsg.setRespCode(Constant.CORESTATUS_SUCCESS);
                return callBack.reCreditSucc(eventDealRspMsg, bankCore351100InnerRsp, eventParam);

            } else if (Constant.CORESTATUS_FAILED.equals(coreProcStatus)) {
                logger.info("补登失败");
                eventDealRspMsg.setRespCode(Constant.CORESTATUS_FAILED);
                return callBack.reCreditFail(eventDealRspMsg, bankCore351100InnerRsp, eventParam);

            } else if (Constant.CORESTATUS_ABEND.equals(coreProcStatus)) {
                logger.info("补登异常");
                eventDealRspMsg.setRespCode(Constant.CORESTATUS_ABEND);
                return callBack.reCreditException(eventDealRspMsg, bankCore351100InnerRsp, eventParam);

            } else {
                eventDealRspMsg.setRespCode(PlatformError.OTHER_BUSI_ERROR.getErrorCode());
            }

            eventDealRspMsg.setRespMsg(bankCore351100InnerRsp.getErrorMsg());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (e instanceof Exception) {
                Exception error = (Exception) e;
              //  eventDealRspMsg.setRespCode(error.getLocalizedMessage());
                eventDealRspMsg.setRespMsg(error.getMessage());
            } else {
                eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
                eventDealRspMsg.setRespMsg(e.getMessage());
            }
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
        }
        return eventDealRspMsg;
    }

    /**
     * 异常事件拼包响应
     *
     * @param eventDealRspMsg
     * @param bankCore351100InnerRsp
     * @return
     */
    private EventDealRspMsg packEventRspMsg(EventDealRspMsg eventDealRspMsg, BankCore351100InnerRsp bankCore351100InnerRsp) {
        String coreProcStatus = bankCore351100InnerRsp.getCoreStatus();

        if (Constant.CORESTATUS_SUCCESS.equals(coreProcStatus)) {
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
            eventDealRspMsg.setRespCode(PlatformError.SUCCESS.getErrorCode());
            eventDealRspMsg.setRespMsg(PlatformError.SUCCESS.getErrorMsg());
        } else if (Constant.CORESTATUS_FAILED.equals(coreProcStatus)) {
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_Y);
            eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
            eventDealRspMsg.setRespMsg("上核心补登失败");
        } else {
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
            eventDealRspMsg.setRespCode(bankCore351100InnerRsp.getErrorCode());
            eventDealRspMsg.setRespMsg(bankCore351100InnerRsp.getErrorMsg());
        }
        return eventDealRspMsg;
    }

    /**
     * 初始化异常事件响应信息
     *
     * @param eventDealReqMsg
     * @return
     */
    private EventDealRspMsg initEventRspMsg(EventDealReqMsg eventDealReqMsg) {
        EventDealRspMsg eventDealRspMsg = new EventDealRspMsg();
        eventDealRspMsg.setExceptEventCode(eventDealReqMsg.getExceptEventCode());
        eventDealRspMsg.setExceptEventSeqNo(eventDealReqMsg.getExceptEventSeqNo());
        return eventDealRspMsg;
    }

    /**
     * 异常处理
     *
     * @param eventDealRspMsg
     * @param e
     */
    private void errHandler(EventDealRspMsg eventDealRspMsg, Exception e) {

        logger.error(e.getMessage(), e);
        eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);

        if (e instanceof Exception) {
            Exception error =  e;
         //   eventDealRspMsg.setRespCode(error.));
            eventDealRspMsg.setRespMsg(error.getMessage());
        } else if (e instanceof EcnyTransException) {
            EcnyTransException error = (EcnyTransException) e;
            eventDealRspMsg.setRespCode(error.getErrorCode());
            eventDealRspMsg.setRespMsg(error.getErrorMsg());
        } else {
            eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
            eventDealRspMsg.setRespMsg(e.getMessage());
        }

    }
}
