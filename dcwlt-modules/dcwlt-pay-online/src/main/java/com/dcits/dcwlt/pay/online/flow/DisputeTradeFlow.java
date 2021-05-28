package com.dcits.dcwlt.pay.online.flow;

import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore998889.BankCore998889Rsp;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.CheckStatusEnum;
import com.dcits.dcwlt.common.pay.enums.DsptChnlRspEnum;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspHead;
import com.dcits.dcwlt.pay.api.domain.ecny.dspt.DsptChnlReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.dspt.DsptChnlRspDTO;
import com.dcits.dcwlt.pay.api.model.CheckResultDO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.baffle.core.impl.BankCoreImplDubboServiceImpl;
import com.dcits.dcwlt.pay.online.event.service.BankRevService;
import com.dcits.dcwlt.pay.online.event.service.ReCreditService;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.flow.builder.TradeFlowRuner;
import com.dcits.dcwlt.pay.online.flow.send.Dispute801STradeFlow;
import com.dcits.dcwlt.pay.online.mapper.SettleCheckResultMapper;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 调账处理
 */
@Configuration
public class DisputeTradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(DisputeTradeFlow.class);

    public static final String DISPUTE_BATCH_TRADE_FLOW = "DisputeBatchTradeFlow";

    public static final String CORE_QRY_FLOW = "CoreQryFlow";

    public static final String CORE_REVERSED_FLOW = "CoreReversedFlow";

    public static final String CORE_RECREDIT_FLOW = "CoreReCreditFlow";

    @Autowired
    private TradeFlowRuner tradeFlowRuner;

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoService;

    @Autowired
    private BankCoreImplDubboServiceImpl bankCoreImplDubboService;

    @Autowired
    private ICoreProcessService bankCoreProcessService;

    @Autowired
    private BankRevService bankRevService;

    @Autowired
    private ReCreditService reCreditService;

    @Autowired
    private SettleCheckResultMapper settleCheckResultMapper;


    /**
     * 单笔核心回查
     *
     * @return
     */
    @Bean(name = CORE_QRY_FLOW)
    public TradeFlow coreQryFlow() {
        return EcnyTradeFlowBuilder.get()
                .process(this::initRspMsg)                           //初始化响应报文
                .process(this::checkMsg)                           //请求参数校验
                .process(this::updatePathStatus)                     //更新通道状态
                .process(this::bankCoreQry)                          //核心回查
                .process(this::bankCoreQryDone)                      //核心回查后处理，是否冲正、补入账
                .process(this::updateResultStatusCore)                   //更新结果状态
                .process(this::response)                             //核心回查响应
                .errHandler(this::errHandler)                          // 异常响应
                .build();
    }

    /**
     * 单笔核心冲正
     *
     * @return
     */
    @Bean(name = CORE_REVERSED_FLOW)
    public TradeFlow coreReversedFlow() {
        return EcnyTradeFlowBuilder.get()
                .process(this::initRspMsg)                           //初始化响应报文
                .process(this::checkMsg)                           //请求参数校验
                .process(this::updatePathStatus)                     //更新通道状态
                .process(this::bankCoreReversed)                     //核心冲正
                .process(this::updateResultStatusRever)                   //更新结果状态
                .process(this::response)                             //核心冲正响应
                .errHandler(this::errHandler)                          // 异常响应
                .build();
    }

    /**
     * 单笔核心补入账
     *
     * @return
     */
    @Bean(name = CORE_RECREDIT_FLOW)
    public TradeFlow coreReCreditFlow() {
        return EcnyTradeFlowBuilder.get()
                .process(this::initRspMsg)                           //初始化响应报文
                .process(this::checkMsg)                           //请求参数校验
                .process(this::updatePathStatus)                     //更新通道状态
                .process(this::coreReCredit)                         //核心补入账
                .process(this::updateResultStatusEdit)                   //更新结果状态
                .process(this::response)                             //核心补入账响应
                .errHandler(this::errHandler)                          // 异常响应
                .build();
    }

    /**
     * 单笔差错贷记调整
     *
     * @return
     */
    @Bean(name = DISPUTE_BATCH_TRADE_FLOW)
    public TradeFlow disputeBatchTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .process(this::initRspMsg)                           //初始化响应报文
                .process(this::checkMsg)                           //请求参数校验
                .process(this::updatePathStatus)                     //更新通道状态
                .process(this::disputeHandle)                        //具体差错处理
                .process(this::response)                             //响应
                .errHandler(this::errHandler)                          // 异常响应
                .build();
    }

    /**
     * 初始化响应报文,默认失败
     *
     * @param context
     */
    private void initRspMsg(TradeContext<?, ?> context) {
        DsptChnlRspDTO rspDTO = new DsptChnlRspDTO();
        // 响应头
        ECNYRspHead head = new ECNYRspHead(AppConstant.TRXSTATUS_FAILED);
        rspDTO.setProcResult(DsptChnlRspEnum.DSPT02.getDesc());

        ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(null, head, rspDTO, EcnyTransError.OTHER_TECH_ERROR.getErrorCode(), EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
        EcnyTradeContext.setRspMsg(context, ecnyRspDTO);
    }


    private void updateResultStatusCore(TradeContext<?, ?> context) {
        updateResultStatus("已回查", context);

    }

    private void updateResultStatusEdit(TradeContext<?, ?> context) {
        updateResultStatus("已补入帐", context);

    }

    private void updateResultStatusRever(TradeContext<?, ?> context) {
        updateResultStatus("已冲正", context);

    }

    private void updateResultStatusDisp(TradeContext<?, ?> context) {
        updateResultStatus("已差错调账", context);

    }

    private void updateResultStatus(String result, TradeContext<?, ?> context) {
        // 响应头
        DsptChnlReqDTO dsptChnlReqDTO = EcnyTradeContext.getReqMsg(context);
        CheckResultDO checkResultDO = new CheckResultDO();
        checkResultDO.setMsgId(dsptChnlReqDTO.getMsgId());
        checkResultDO.setLastUpDate(DateUtil.getDefaultDate());
        checkResultDO.setLastUpTime(DateUtil.getDefaultTime());
        checkResultDO.setProcStatus(result);
        try {
            settleCheckResultMapper.updateCheckResult(checkResultDO);
        } catch (Exception e) {
            logger.error("更新状态异常： {}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }


    /**
     * 请求参数校验
     */
    public void checkMsg(TradeContext<?, ?> tradeContext) {
        DsptChnlReqDTO dsptChnlReqDTO = EcnyTradeContext.getReqMsg(tradeContext);
        boolean dcep221 = MsgTpEnum.DCEP221.getCode().equals(dsptChnlReqDTO.getMsgTp());
        boolean dcepRecv801 = (StringUtils.equals(MsgTpEnum.DCEP801.getCode(), dsptChnlReqDTO.getMsgTp())
                && !StringUtils.equals(AppConstant.BANK_FINANCIAL_INSTITUTION_CD, dsptChnlReqDTO.getInstgPty()));
        boolean dcep225 = MsgTpEnum.DCEP225.getCode().equals(dsptChnlReqDTO.getMsgTp());
        boolean dcep227 = MsgTpEnum.DCEP227.getCode().equals(dsptChnlReqDTO.getMsgTp());
        if (!(dcep221 || dcepRecv801 || dcep225 || dcep227)) {
            logger.error("{}报文不可做调账", dsptChnlReqDTO.getMsgTp());
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.MSG_TYPE_ERROR);
        }
        if (!StringUtils.endsWithAny(dsptChnlReqDTO.getCheckStatus(), CheckStatusEnum.SAME.getCode(),
                dsptChnlReqDTO.getCheckStatus(), CheckStatusEnum.SANF.getCode(),
                dsptChnlReqDTO.getCheckStatus(), CheckStatusEnum.FANS.getCode(),
                dsptChnlReqDTO.getCheckStatus(), CheckStatusEnum.EANS.getCode(),
                dsptChnlReqDTO.getCheckStatus(), CheckStatusEnum.EANF.getCode())) {
            logger.error("{}报文状态不可做调账", dsptChnlReqDTO.getMsgTp());
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.NO_DSPT_ALLOW);
        }
    }

    /**
     * 根据对账结果状态更新原交易通道状态
     *
     * @param context
     */
    private void updatePathStatus(TradeContext<?, ?> context) {
        DsptChnlReqDTO reqMsg = EcnyTradeContext.getReqMsg(context);
        String checkStatus = reqMsg.getCheckStatus();
        String msgId = reqMsg.getMsgId();

        logger.info("根据对账结果状态更新原交易通道状态开始，报文标识号：{}，对账标识：{}",
                reqMsg.getMsgId(), reqMsg.getCheckStatus());

        //查询原交易
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(msgId);
        if (null == payTransDtlInfoDO) {
            logger.error("交易不存在，报文标识号：{}", msgId);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.OLD_PAY_INFO_UNFOUND);
        }

        String payDate = payTransDtlInfoDO.getPayDate();
        String paySerno = payTransDtlInfoDO.getPaySerno();
        String endPathStatus = StringUtils.EMPTY;

        //人行失败，更新通道状态为失败
        if (CheckStatusEnum.SANF.getCode().equals(checkStatus)
                || CheckStatusEnum.EANF.getCode().equals(checkStatus)) {
            logger.info("人行失败，更新通道状态为失败,平台日期：{}，平台流水：{}", payDate, paySerno);
            endPathStatus = "0";
        }

        //人行成功，更改通道状态为成功
        if (CheckStatusEnum.FANS.getCode().equals(checkStatus)
                || CheckStatusEnum.EANS.getCode().equals(checkStatus)) {
            logger.info("人行成功，更新通道状态为成功,平台日期：{}，平台流水：{}", payDate, paySerno);
            endPathStatus = "1";
        }

        if (StringUtils.isBlank(endPathStatus)) {
            logger.error("交易无法确定人行终态，不能进行对账处理,平台日期：{}，平台流水：{}", payDate, paySerno);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.NOT_SURE_END_STATUS);
        }

        payTransDtlInfoService.updatePathStatus(payTransDtlInfoDO, endPathStatus);

        payTransDtlInfoDO = payTransDtlInfoService.query(payDate, paySerno);
        EcnyTradeContext.setTxn(context, payTransDtlInfoDO);
    }

    /**
     * 发核心回查
     *
     * @param
     */
    private void bankCoreQry(TradeContext<?, ?> context) {
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTxn(context);
        String payDate = payTransDtlInfoDO.getPayDate();
        String paySerno = payTransDtlInfoDO.getPaySerno();
        String trxStatus = payTransDtlInfoDO.getTrxStatus();
        String coreProcStatus = payTransDtlInfoDO.getCoreProcStatus();
        String pathProcStatus = payTransDtlInfoDO.getPathProcStatus();

        StateMachine stateMachine = new StateMachine();
        stateMachine.setPreTrxStatus(trxStatus);
        stateMachine.setPreCoreProcStatus(coreProcStatus);
        stateMachine.setPrePathProcStatus(pathProcStatus);

        //核心状态非异常，返回
        if (!AppConstant.CORESTATUS_ABEND.equals(coreProcStatus)) {
            logger.info("核心状态非异常，无需回查核心,平台日期：{}，平台流水：{}", payDate, paySerno);
            return;
        }

        //核心状态为异常，发核心回查
        logger.info("核心状态为异常，发核心回查,平台日期：{}，平台流水：{}", payDate, paySerno);

        BankCore996666Rsp bankCore30430001Rsp = bankCoreImplDubboService.queryCoreStatus(paySerno, paySerno);

        if (StringUtils.isEmpty(bankCore30430001Rsp.getTxnSts())) {
            logger.error("回查核心失败，该交易不存在,平台日期：{}，平台流水：{}", payDate, paySerno);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.TRADE_NOT_FOUND);
        }

        try {
            payTransDtlInfoDO.setCoreProcStatus(bankCore30430001Rsp.getTxnSts());
            // 根据核心回查状态更新金融登记簿
            payTransDtlInfoService.update(payTransDtlInfoDO, stateMachine);
        } catch (Exception e) {
            logger.error("金融流水表入库失败:{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.DATABASE_ERROR);
        }
    }

    /**
     * 核心异常回查后处理，是否冲正、补入账
     *
     * @param context
     */
    private void bankCoreQryDone(TradeContext<?, ?> context) {
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTxn(context);
        logger.info("核心异常回查后处理，交易日期：{}，交易流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
        disputeByMsgType(payTransDtlInfoDO);
    }

    /**
     * 通过原报文编号区分原报文类型：兑回、兑出、汇款兑出还是贷记调整
     *
     * @param payTransDtlInfoDO
     */
    private void disputeByMsgType(PayTransDtlInfoDO payTransDtlInfoDO) {

        String msgType = payTransDtlInfoDO.getMsgType();
        String payDate = payTransDtlInfoDO.getPayDate();
        String paySerno = payTransDtlInfoDO.getPaySerno();
        logger.info("通过原报文编号区分原报文类型：兑回、兑出、汇款兑出还是贷记调整,平台日期：{}，平台流水：{}", payDate, paySerno);

        if (MsgTpEnum.DCEP221.getCode().equals(msgType)) {

            logger.info("差错处理：兑回 ,平台日期：{}，平台流水：{}", payDate, paySerno);
            this.reConvertHandle(payTransDtlInfoDO);

        } else if (MsgTpEnum.DCEP225.getCode().equals(msgType)) {

            logger.info("差错处理：兑出 ,平台日期：{}，平台流水：{}", payDate, paySerno);
            this.convertHandle(payTransDtlInfoDO);

        } else if (MsgTpEnum.DCEP227.getCode().equals(msgType)) {

            this.payConvertHandle(payTransDtlInfoDO);
            logger.info("差错处理：汇款兑出 ,平台日期：{}，平台流水：{}", payDate, paySerno);

        } else if (MsgTpEnum.DCEP801.getCode().equals(msgType)) {

            this.dsptChnlHandle(payTransDtlInfoDO);
            logger.info("差错处理：贷记调整 ,平台日期：{}，平台流水：{}", payDate, paySerno);

        } else {
            logger.info("该报文类型不需要进行差错处理 ,平台日期：{}，平台流水：{}", payDate, paySerno);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.MSG_TYPE_ERROR);
        }
    }

    /**
     * 差错处理-兑回
     *
     * @param payTransDtlInfoDO
     */
    private void reConvertHandle(PayTransDtlInfoDO payTransDtlInfoDO) {
//        // 判断是否可以冲正
//        if (checkCoreReversed(payTransDtlInfoDO)) {
//            logger.info("上核心冲正,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
//            BankCore3041000204Rsp bankCore3041000204Rsp = bankRevService.bankRevOnTime(payTransDtlInfoDO);
//            logger.info("核心冲正完成：{}", bankCore3041000204Rsp.toString());
//            return;
//        }

        //判断是否可补入账
        boolean reCreditFlag = checkCoreReCredit(payTransDtlInfoDO);
        if (reCreditFlag) {
            //上核心补入账
            BankCore351100InnerRsp bankCore30410002Rsp = bankCoreReCredit(payTransDtlInfoDO);
            logger.info("核心补入帐完成：[{}]", bankCore30410002Rsp.toString());
        }
    }

    /**
     * 差错处理：兑出
     *
     * @param payTransDtlInfoDO
     */
    private void convertHandle(PayTransDtlInfoDO payTransDtlInfoDO) {
        //兑出X10冲正
        if (checkCoreReversed(payTransDtlInfoDO)) {
            logger.info("上核心冲正,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
            BankCore998889Rsp bankCore3041000204Rsp = bankRevService.bankRevOnTime(payTransDtlInfoDO);
            logger.info("核心冲正完成：{}", bankCore3041000204Rsp.toString());
        }

        // 补扣帐
        //coreDebit(payTransDtlInfoDO);
    }

    /**
     * 差错处理：汇款兑出
     *
     * @param payTransDtlInfoDO
     */
    private void payConvertHandle(PayTransDtlInfoDO payTransDtlInfoDO) {
        //汇款X10冲正
        if (checkCoreReversed(payTransDtlInfoDO)) {
            logger.info("上核心冲正,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
            BankCore998889Rsp bankCore3041000204Rsp = bankRevService.bankRevOnTime(payTransDtlInfoDO);
            logger.info("核心冲正完成：{}", bankCore3041000204Rsp.toString());
        }

        //汇款兑出、兑出291、X01、X31发起补扣账
        //coreDebit(payTransDtlInfoDO);
    }

    /**
     * 差错处理：贷记调整
     *
     * @param payTransDtlInfoDO
     */
    private void dsptChnlHandle(PayTransDtlInfoDO payTransDtlInfoDO) {

        if (StringUtils.equals(AppConstant.BANK_FINANCIAL_INSTITUTION_CD, payTransDtlInfoDO.getInstgPty())) {
            //贷记调账往账处理
            logger.info("贷记调账往账处理,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
            //X10冲正
//            if (StringUtils.equals(payTransDtlInfoDO.getCoreProcStatus(), AppConstant.CORESTATUS_SUCCESS) &&
//                    StringUtils.equals(payTransDtlInfoDO.getCoreProcStatus(), AppConstant.PAYPATHSTATUS_FAILED)) {
//                logger.info("上核心冲正,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
//                BankCore3041000204Rsp bankCore3041000204Rsp = bankRevService.bankRevOnTime(payTransDtlInfoDO);
//                logger.info("核心冲正完成：{}", bankCore3041000204Rsp.toString());
//            }

            //发起补扣账
            //coreDebit(payTransDtlInfoDO);
        } else {
            //贷记调账来账处理
            logger.info("贷记调账来账处理,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

//            if (StringUtils.equals(payTransDtlInfoDO.getCoreProcStatus(), AppConstant.CORESTATUS_SUCCESS) &&
//                    StringUtils.equals(payTransDtlInfoDO.getCoreProcStatus(), AppConstant.PAYPATHSTATUS_FAILED)) {
//                logger.info("上核心冲正,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
//                BankCore3041000204Rsp bankCore3041000204Rsp = bankRevService.bankRevOnTime(payTransDtlInfoDO);
//                logger.info("核心冲正完成：{}", bankCore3041000204Rsp.toString());
//                return;
//            }
            //补入账
            dsptChnlRecv(payTransDtlInfoDO);
        }
    }

    /**
     * 差错处理
     *
     * @param context
     */
    private void disputeHandle(TradeContext<?, ?> context) {

        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTxn(context);
        String payDate = payTransDtlInfoDO.getPayDate();
        String paySerno = payTransDtlInfoDO.getPaySerno();

        //判断是否可差错调账
        boolean reCreditFlag = checkCoreDisputeSend(payTransDtlInfoDO);

        DsptChnlReqDTO dsptChnlReqDTO = EcnyTradeContext.getReqMsg(context);

        // 调801进行差错调账
        if (reCreditFlag) {
            ECNYRspDTO<DsptChnlRspDTO> result = tradeFlowRuner.execute(Dispute801STradeFlow.DSPT_TRADE_FLOW,
                    EcnyTradeContext.getInstance(dsptChnlReqDTO));
            // 根据处理结果判断差错调账
            if (StringUtils.equals(AppConstant.TRXSTATUS_SUCCESS, result.getEcnyRspHead().getTrxStatus())) {
                logger.info("差错调账成功,平台日期：{}，平台流水：{}", payDate, paySerno);
                updateResultStatusDisp(context);
            } else {
                logger.error("差错调账失败,平台日期：{}，平台流水：{}", payDate, paySerno);
                throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.UPDATE_INFO_ERROR);
            }
        }

        //查询最新交易簿信息
        payTransDtlInfoDO = payTransDtlInfoService.query(payDate, paySerno);
        EcnyTradeContext.setTxn(context, payTransDtlInfoDO);
    }

    /**
     * 上核心冲正
     *
     * @param context
     */
    private void bankCoreReversed(TradeContext<?, ?> context) {

        logger.info("上核心冲正");
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTxn(context);
        String payDate = payTransDtlInfoDO.getPayDate();
        String paySerno = payTransDtlInfoDO.getPaySerno();

        if (!checkCoreReversed(payTransDtlInfoDO)) {
            logger.error("该交易不允许冲正,平台日期：{}，平台流水：{}", payDate, paySerno);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.NOT_SUPPORTED_REVERSED);
        }

        logger.info("上核心冲正,平台日期：{}，平台流水：{}", payDate, paySerno);
        BankCore998889Rsp bankCore3041000204Rsp = bankRevService.bankRevOnTime(payTransDtlInfoDO);

        logger.info("核心冲正完成：{}", bankCore3041000204Rsp.toString());

        //查询最新交易簿信息
        payTransDtlInfoDO = payTransDtlInfoService.query(payDate, paySerno);
        EcnyTradeContext.setTxn(context, payTransDtlInfoDO);
    }

    /**
     * 核心补入账前检查,兑回、差错贷记来账，291、X01(除了A01),可补入账
     *
     * @param context
     */
    private void coreReCredit(TradeContext<?, ?> context) {

        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTxn(context);

        //判断是否可补入账
        boolean reCreditFlag = checkCoreReCredit(payTransDtlInfoDO);
        if (!reCreditFlag) {
            logger.error("该交易不允许补入账,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.NOT_SUPPORTED_RECREDIT);
        }

        logger.info("该交易允许补入账,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
        bankCoreReCredit(payTransDtlInfoDO);
    }


    /**
     * 核心补入账前检查,兑回、差错贷记来账，291、X01(除了A01),可补入账
     *
     * @param payTransDtlInfoDO
     */
    private boolean checkCoreReCredit(PayTransDtlInfoDO payTransDtlInfoDO) {

        String payDate = payTransDtlInfoDO.getPayDate();
        String paySerno = payTransDtlInfoDO.getPaySerno();

        if (!txn221OrRecv801(payTransDtlInfoDO)) {
            logger.info("仅兑回和贷记调账来账可补入账,平台日期：{}，平台流水：{}", payDate, paySerno);
            return false;
        }

        boolean statusX91 = (AppConstant.CORESTATUS_INIT.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

        boolean statusX01 = (AppConstant.CORESTATUS_FAILED.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

        boolean statusA01 = (AppConstant.TRXSTATUS_PRECREDITSUCCESS.equals(payTransDtlInfoDO.getTrxStatus())
                && AppConstant.CORESTATUS_FAILED.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

        //交易状态X91、X01(除了A01),可补入账
        if (statusX91 || (statusX01 && !statusA01)) {
            logger.info("该交易可进行补入账,平台日期：{}，平台流水：{}", payDate, paySerno);
            return true;
        } else {
            logger.info("该交易状态不允许补入账,平台日期：{}，平台流水：{}", payDate, paySerno);
            return false;
        }
    }

    /**
     * 差错调账前检查,兑回、差错贷记来账，291、X01(除了A01),可差错调账
     *
     * @param payTransDtlInfoDO
     */
    private boolean checkCoreDisputeSend(PayTransDtlInfoDO payTransDtlInfoDO) {

        String payDate = payTransDtlInfoDO.getPayDate();
        String paySerno = payTransDtlInfoDO.getPaySerno();

        if (!txn221OrRecv801(payTransDtlInfoDO)) {
            logger.info("仅兑回和贷记调账来账可差错调账,平台日期：{}，平台流水：{}", payDate, paySerno);
            return false;
        }

        boolean statusX91 = (AppConstant.CORESTATUS_INIT.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

        boolean statusX01 = (AppConstant.CORESTATUS_FAILED.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

        boolean statusA01 = (AppConstant.TRXSTATUS_PRECREDITSUCCESS.equals(payTransDtlInfoDO.getTrxStatus())
                && AppConstant.CORESTATUS_FAILED.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

        //交易状态X91、X01(除了A01),可补入账
        if (statusX91 || (statusX01 && !statusA01)) {
            logger.info("该交易可进行差错调账,平台日期：{}，平台流水：{}", payDate, paySerno);
            return true;
        } else {
            logger.info("该交易状态不允许差错调账,平台日期：{}，平台流水：{}", payDate, paySerno);
            return false;
        }
    }

    /**
     * 上核心补入账
     *
     * @param payTransDtlInfoDO
     */
    private BankCore351100InnerRsp bankCoreReCredit(PayTransDtlInfoDO payTransDtlInfoDO) {

        StateMachine stateMachine = new StateMachine();

        //状态 X91、X01(除A01) --> 221
        stateMachine.setPreTrxStatus(payTransDtlInfoDO.getTrxStatus());
        stateMachine.setPreCoreProcStatus(payTransDtlInfoDO.getCoreProcStatus());
        stateMachine.setPrePathProcStatus(payTransDtlInfoDO.getPathProcStatus());

        //生成新核心请求流水调351100上核心
        logger.info("上核心补入账,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
        return reCreditService.reCreditNew(payTransDtlInfoDO, stateMachine);
    }


    /**
     * 核心冲正前检查,仅兑出、汇款兑出交易且状态为210可冲正
     *
     * @param payTransDtlInfoDO
     */
    private boolean checkCoreReversed(PayTransDtlInfoDO payTransDtlInfoDO) {

        logger.info("核心冲正前检查");

        String payDate = payTransDtlInfoDO.getPayDate();
        String paySerno = payTransDtlInfoDO.getPaySerno();

        if (!StringUtils.equalsAny(payTransDtlInfoDO.getMsgType(), MsgTpEnum.DCEP225.getCode(), MsgTpEnum.DCEP227.getCode())) {
            logger.info("非兑出、汇款兑出交易不允许冲正,平台日期：{}，平台流水：{}", payDate, paySerno);
            return false;
        }

        boolean statusX10 = (StringUtils.equals(payTransDtlInfoDO.getCoreProcStatus(), AppConstant.CORESTATUS_SUCCESS)
                && StringUtils.equals(payTransDtlInfoDO.getPathProcStatus(), AppConstant.PAYPATHSTATUS_FAILED));

        if (!statusX10) {
            logger.info("交易状态非X10，不允许冲正,平台日期：{}，平台流水：{}", payDate, paySerno);
            return false;
        }

        return true;
    }

    /**
     * 汇款兑出、兑出291、X01、X31发起补扣账
     *
     * @param payTransDtlInfoDO
     * @return
     */
    private boolean checkCoreDebit(PayTransDtlInfoDO payTransDtlInfoDO) {

        logger.info("检查交易是否可发起补扣款,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        if (!StringUtils.equalsAny(payTransDtlInfoDO.getMsgType(), MsgTpEnum.DCEP225.getCode(), MsgTpEnum.DCEP227.getCode())) {
            logger.info("非兑出、汇款兑出交易不允许补扣账,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
            return false;
        }

        //上核心失败或冲正成功，人行成功
        boolean statusX01 = (AppConstant.CORESTATUS_FAILED.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

        boolean statusX31 = (AppConstant.CORESTATUS_REVERSED.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

        boolean status291 = (AppConstant.TRXSTATUS_ABEND.equals(payTransDtlInfoDO.getTrxStatus())
                && AppConstant.CORESTATUS_INIT.equals(payTransDtlInfoDO.getCoreProcStatus())
                && AppConstant.PAYPATHSTATUS_SUCCESS.equals(payTransDtlInfoDO.getPathProcStatus()));

        if (statusX01 || statusX31 || status291) {
            logger.info("汇款兑出、兑出291、X01、X31发起补扣账,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
            return true;
        }

        logger.info("交易状态不允许补扣账,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
        return false;
    }


    /**
     * 贷记调账来账处理
     *
     * @param payTransDtlInfoDO
     */
    private void dsptChnlRecv(PayTransDtlInfoDO payTransDtlInfoDO) {

        //判断是否可补入账
        boolean reCreditFlag = checkCoreReCredit(payTransDtlInfoDO);
        if (reCreditFlag) {
            logger.info("上核心补入账,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
            // 更新收款人信息
            bankCoreReCredit(payTransDtlInfoDO);
        }
    }


    /**
     * 判断交易类型：兑回、差错来账
     *
     * @param payTransDtlInfoDO
     */
    private boolean txn221OrRecv801(PayTransDtlInfoDO payTransDtlInfoDO) {
        //判断交易是否兑回
        boolean dcep221 = MsgTpEnum.DCEP221.getCode().equals(payTransDtlInfoDO.getMsgType());

        //交易是否贷记调账来账
        boolean dcepRecv801 = (StringUtils.equals(MsgTpEnum.DCEP801.getCode(), payTransDtlInfoDO.getMsgType())
                && !StringUtils.equals(AppConstant.BANK_FINANCIAL_INSTITUTION_CD, payTransDtlInfoDO.getInstgPty()));

        //仅兑回和贷记调账来账可补入账
        if (dcep221 || dcepRecv801) {
            logger.info("交易类型为兑回或差错来账，交易日期：{}，交易流水：{}",
                    payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
            return true;
        }

        logger.info("交易类型不为兑回或差错来账，交易日期：{}，交易流水：{}",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
        return false;
    }

    /**
     * 差错响应
     *
     * @param context
     */
    private void response(TradeContext<?, ?> context) {

        ECNYRspDTO rspMsg = EcnyTradeContext.getRspMsg(context);
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO) EcnyTradeContext.getTxn(context);

        //判断交易是否终态
        boolean endStatus = checkEndStatus(payTransDtlInfoDO);

        //交易状态为终态响应成功，终态 330、111、000、191（差错贷记调账往账）、090（兑回、兑出、汇款兑出、差错贷记调账往账），其余状态响应失败
        if (endStatus) {
            logger.info("差错处理-交易状态为终态响应成功,平台日期：{}，平台流水：{}", payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
            rspMsg.getEcnyRspHead().setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
            DsptChnlRspDTO dsptChnlRspDTO = (DsptChnlRspDTO) rspMsg.getBody();
            dsptChnlRspDTO.setProcResult(DsptChnlRspEnum.DSPT01.getDesc());
        }
    }

    /**
     * 判断交易是否终态
     *
     * @param payTransDtlInfoDO
     * @return
     */
    private boolean checkEndStatus(PayTransDtlInfoDO payTransDtlInfoDO) {

        String payDate = payTransDtlInfoDO.getPayDate();
        String paySerno = payTransDtlInfoDO.getPaySerno();
        String msgType = payTransDtlInfoDO.getMsgType();
        String trxStatus = payTransDtlInfoDO.getTrxStatus();
        String coreProcStatus = payTransDtlInfoDO.getCoreProcStatus();
        String pathProcStatus = payTransDtlInfoDO.getPathProcStatus();

        logger.info("判断交易是否终态，平台日期：{}，平台流水：{}，交易类型：{}，业务状态：{}，核心状态：{}，通道状态：{}",
                payDate, paySerno, msgType, trxStatus, coreProcStatus, pathProcStatus);

        //差错贷记调账往账
        boolean dcepSent801 = (StringUtils.equals(MsgTpEnum.DCEP801.getCode(), msgType)
                && StringUtils.equals(AppConstant.BANK_FINANCIAL_INSTITUTION_CD, payTransDtlInfoDO.getInstgPty()));

        //终态 111
        boolean status111 = (StringUtils.equals(trxStatus, AppConstant.TRXSTATUS_SUCCESS)
                && StringUtils.equals(coreProcStatus, AppConstant.CORESTATUS_SUCCESS)
                && StringUtils.equals(pathProcStatus, AppConstant.PAYPATHSTATUS_SUCCESS));

        //终态 000
        boolean status000 = (StringUtils.equals(trxStatus, AppConstant.TRXSTATUS_FAILED)
                && StringUtils.equals(coreProcStatus, AppConstant.CORESTATUS_FAILED)
                && StringUtils.equals(pathProcStatus, AppConstant.PAYPATHSTATUS_FAILED));

        //终态 330
        boolean status330 = (StringUtils.equals(trxStatus, AppConstant.TRXSTATUS_REVERSED)
                && StringUtils.equals(coreProcStatus, AppConstant.CORESTATUS_REVERSED)
                && StringUtils.equals(pathProcStatus, AppConstant.PAYPATHSTATUS_FAILED));

        //终态 191
        boolean endStatus191 = (StringUtils.equals(trxStatus, AppConstant.TRXSTATUS_SUCCESS)
                && StringUtils.equals(coreProcStatus, AppConstant.CORESTATUS_INIT)
                && StringUtils.equals(pathProcStatus, AppConstant.PAYPATHSTATUS_SUCCESS)
                && dcepSent801);

        //终态 090（兑回、兑出、汇款兑出、差错往账）
        boolean endStatus090 = (StringUtils.equals(trxStatus, AppConstant.TRXSTATUS_FAILED)
                && StringUtils.equals(coreProcStatus, AppConstant.CORESTATUS_INIT)
                && StringUtils.equals(pathProcStatus, AppConstant.PAYPATHSTATUS_FAILED)
                && (payTransDtlInfoService.checkMsgType(payTransDtlInfoDO)));

        //交易终态
        boolean endStatus = status111 || status000 || status330 || endStatus191 || endStatus090;
        logger.info("平台日期：{}，平台流水：{}，交易是否到达终态：{}", payDate, paySerno, endStatus);
        return endStatus;
    }

    /**
     * 整体异常处理，异常均响应失败
     *
     * @param context
     * @param throwable
     */
    private void errHandler(TradeContext<?, ?> context, Throwable throwable) {

        DsptChnlRspDTO rspDTO = new DsptChnlRspDTO();
        // 响应头
        ECNYRspHead head = new ECNYRspHead(AppConstant.TRXSTATUS_FAILED);
        rspDTO.setProcResult(DsptChnlRspEnum.DSPT02.getDesc());

        String retCode = EcnyTransError.OTHER_TECH_ERROR.getErrorCode();
        String retInfo = EcnyTransError.OTHER_TECH_ERROR.getErrorMsg();

        //设置错误码错误信息
        if (throwable instanceof EcnyTransException) {
            retCode = ((EcnyTransException) throwable).getErrorCode();
            retInfo = ((EcnyTransException) throwable).getErrorMsg();
        }

        rspDTO.setProcResult(retInfo);

        ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(null, head, rspDTO, retCode, retInfo);
        EcnyTradeContext.setRspMsg(context, ecnyRspDTO);
    }
}
