package com.dcits.dcwlt.pay.online.flow.receive;

import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore358040.BankCore358040Req;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore358040.BankCore358040Rsp;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.*;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement.*;
import com.dcits.dcwlt.pay.api.domain.dcep.common.*;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.api.model.SignInfoDO;
import com.dcits.dcwlt.pay.api.model.SignJrnDO;
import com.dcits.dcwlt.pay.online.baffle.core.impl.ECIFService;
import com.dcits.dcwlt.pay.online.baffle.core.impl.SmsService;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.flow.builder.TradeFlowRuner;
import com.dcits.dcwlt.pay.online.mapper.SignInfoMapper;
import com.dcits.dcwlt.pay.online.mapper.SignJrnMapper;
import com.dcits.dcwlt.pay.online.service.impl.AuthInfoServiceimpl;
import com.dcits.dcwlt.pay.online.service.impl.BankAccountVerifyService;
import com.dcits.dcwlt.pay.online.service.impl.ECNYSerNoService;
import com.dcits.dcwlt.pay.online.service.impl.PartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwang
 * @version 1.0.0
 * <p>银行账户挂接管理流程</p>
 * @date 2021/1/2
 */
@Configuration("bankAttAcctManage433RTradeFlowConf")
public class BankAttAcctManage433RTradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(BankAttAcctManage433RTradeFlow.class);

    /************************************************** TradeFlowName **************************************/

    private static final String TRADE_FLOW_NAME = "BankAttAcctManage433RTradeFlow";

    private static final String ACC_AUTH_FLOW = "accountAuthFlow";

    private static final String ACC_CONFIRM_FLOW = "accountConfirmFlow";

    private static final String ACC_RESCIND_FLOW = "accountRescindFlow";

    /************************************************** 错误码、错误信息 **************************************/

    private static final String ERRORCODE = "errorCode";

    private static final String ERRORMSG = "errorMsg";

    /************************************************** 协议状态 ********************************************/

    private static final String CLOSE_SIGN_STATUS = "C";

    private static final String VALID_SIGN_STATUS = "N";

    /**************************************************** 短信常量 ******************************************/

    private static final String SIGN_AUTH_TEMPCODE = "230J200001";    //身份认证短信模板代码

    private static final String SIGN_CONFIRM_TEMPCODE = "230J200002"; //身份确认短信模板代码

    private static final String SMSID = "J2";                         //身份认证短信业务代码

    private static final String CHANNELCODE = "230";                  //身份认证请求渠道号

    private static final String SENDBRANCH = "173001";                //todo 身份认证发送机构,待确认

    private static final String SIGN_SUCCESS = "成功";                 //签约成功

    private static final String SIGN_FAILED = "失败";                 //签约失败


    /**************************************************** context中间变量 **********************************/

    private static final String SIGN_INSERT_DO = "signInsertDO";

    private static final String SIGN_UPDATE_DO = "signUpdateDO";

    private static final String BANK_ACCT_INFO = "bankAcctInfo";


    /***************************************************** 反洗钱响应码 *************************************/

    private static final String LSFK43_SUCCESS_CODE = "0000";

    /*************************************************** 卡类型 ********************************************/

    private static final String DEBIT_CARD_TYPE = "借记卡";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SmsService smsService;

    @Autowired
    private BankAccountVerifyService bankAccountVerifyService;

    @Autowired
    private SignInfoMapper signInfoMapper;

    @Autowired
    private SignJrnMapper signJrnMapper;

    @Autowired
    private ECNYSerNoService ecnySerNoService;

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

    @Autowired
    private PartyService partyService;

    @Autowired
    private AuthInfoServiceimpl authInfoService;

    @Autowired
    private ECIFService ecifService;


    @Bean(name = TRADE_FLOW_NAME)
    public TradeFlow accountAttachManagement() {
        return EcnyTradeFlowBuilder.get()
                .initTxn(this::initRsp)                               //初始化响应报文
                .initTxn(this::initJrnDO)                             //初始化交易流水
                .saveTxn(this::insertJrnDO)                           //插入签约交易流水
                .process(this::authCheck)                             //权限控制
                .process(this::baseCheck)                             //基础检查（检查钱包类型和管理类型）
                .process(this::executeStrategy)                       //根据管理类型执行具体的策略
                .response(this::response)                             //拼装响应报文
                .updateTxn(this::updateJrn)                           //更新交易流水
                .errHandler(this::errHandler)                         //异常处理
                .build();
    }


    @Bean(name = ACC_AUTH_FLOW)
    public TradeFlow accountAuthFlow() {
        return EcnyTradeFlowBuilder.get()
                .process(this::checkAccount)                          //验证账户状态(银行核心358040、反洗钱检查、ECIF9要素检查)
                .process(this::sendAuthCode)                          //发送短信验证码
                .build();
    }


    @Bean(name = ACC_CONFIRM_FLOW)
    public TradeFlow accountConfirmFlow() {
        return EcnyTradeFlowBuilder.get()
                .process(this::checkOriginTrade)                      //检查原身份认证交易
                .process(this::genOrUpdSign)                          //生成协议或更新协议
                .process(this::sendSignSuccessMsg)                    //发送签约成功信息
                .build();
    }


    @Bean(name = ACC_RESCIND_FLOW)                                    //解绑流程
    public TradeFlow accountRescindFlow() {
        return EcnyTradeFlowBuilder.get()
                .process(this::rescindProcess)
                .build();
    }


    /**
     * 机构权限校验
     *
     * @param tradeContext 交易上下文
     */
    private void authCheck(TradeContext<?, ?> tradeContext) {
        // 接收机构状态校验
        BankAttAcctReq reqBody = getReqBody(tradeContext);

        //接收机构
        String instdPty = reqBody.getGrpHdr().getInstdPty().getInstdDrctPty();

        //发送机构
        String instgPty = reqBody.getGrpHdr().getInstgPty().getInstgDrctPty();
        boolean partyFlag = partyService.isAvailable(instdPty);

        //判断接收机构是否广发银行
        if (!AppConstant.CGB_FINANCIAL_INSTITUTION_CD.equals(instdPty)) {
            logger.info("接收机构有误,{}", instgPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.INSTD_PTY_ERROR);
        }

        //判断发起机构状态（不校验发起机构状态  2021-01-28)
//        boolean sendPartyFlag = partyService.isAvailable(instgPty);
//        if (!sendPartyFlag) {
//            logger.info("发起机构状态异常,{}", instgPty);
//            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTG_STATUS_UNSUPPORT);
//        }

        //判断接收机构状态
        boolean recvPartyFlag = partyService.isAvailable(instdPty);
        if (!recvPartyFlag) {
            logger.info("接收机构状态异常,{}", instdPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTD_STATUS_UNSUPPORT);
        }

        // 接收机构权限校验 根据：接收机构号+报文编号+业务类型+发送标识
        Boolean sendAuth = authInfoService.validateAuthInfo(instdPty, MsgTpEnum.DCEP433.getCode(), "", AuthInfoDrctEnum.recvAuth);
        if (!sendAuth) {
            logger.error("接收机构权限不足,{}", instdPty);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.PARTY_INSTD_POWER_UNSUPPORT);
        }
    }


    /**
     * 发送签约成功信息
     *
     * @param tradeContext 交易上下文
     */
    private void sendSignSuccessMsg(TradeContext<?, ?> tradeContext) {
        sendSignMsg(tradeContext, SIGN_SUCCESS);
    }

    /**
     * 发送签约失败信息
     *
     * @param tradeContext 交易上下文
     */
    private void sendSignFailedMsg(TradeContext<?, ?> tradeContext) {
        sendSignMsg(tradeContext, SIGN_FAILED);
    }


    /**
     * 发送签约信息
     *
     * @param tradeContext 交易上下文
     */
    private void sendSignMsg(TradeContext<?, ?> tradeContext, String signResult) {
        BankAttAcctReq reqBody = getReqBody(tradeContext);
        String cardNo = reqBody.getSgnInf().getSgnAcctId();
        Map<String, String> map = new HashMap<>();
        map.put("ACCTNO", cardNo.substring(cardNo.length() - 4));
        map.put("CARDTY", DEBIT_CARD_TYPE);
        map.put("MON", DateUtil.getCurMonth());
        map.put("DAY", DateUtil.getCurDay());
        map.put("TRADETIME", DateUtil.getCurTime());
        map.put("RSPDESC", signResult);
        try {
            boolean sendMsgResult = smsService.sendMsg(reqBody.getSgnInf().getTel(), SIGN_CONFIRM_TEMPCODE, SMSID, CHANNELCODE, SENDBRANCH, map, true);
            if (sendMsgResult) {
                logger.info("短信发送成功，本次请求报文标识号为：{}", reqBody.getGrpHdr().getMsgId());
            } else {
                logger.info("短信发送失败，本次请求报文标识号为：{}", reqBody.getGrpHdr().getMsgId());
            }
        } catch (Exception e) {
            logger.error("短信发送异常{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.SEND_SMS_ERROR);
        }
    }


    /**
     * 初始化响应报文
     *
     * @param tradeContext 交易上下文
     */
    private void initRsp(TradeContext<?, ?> tradeContext) {
        DCEPReqDTO<BankAttAcctReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        PmtPtcMgmtRspDTO rspBody = new PmtPtcMgmtRspDTO();
        rspBody.setPmtPtcMgmtRsp(buildRspMsg(reqMsg.getBody().getBankAttAcctReq()));
        DCEPRspDTO<PmtPtcMgmtRspDTO> rspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP434.getCode(), rspBody);
        EcnyTradeContext.setRspMsg(tradeContext, rspDTO);
    }

    /**
     * 初始化交易流水
     *
     * @param tradeContext 交易上下文
     */
    private void initJrnDO(TradeContext<?, ?> tradeContext) {
        DCEPReqDTO<BankAttAcctReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        Map<String, Object> contextMap = tradeContext.getTempCtx();
        SignJrnDO signInsertJrnDO = buildSignInsertJrnDo(reqMsg.getBody().getBankAttAcctReq());
        contextMap.put(SIGN_INSERT_DO, signInsertJrnDO);
        SignJrnDO signUpdateJrnDO = buildSignUpdateJrnDo(tradeContext);
        contextMap.put(SIGN_UPDATE_DO, signUpdateJrnDO);
    }


    /**
     * 检查钱包类型和管理类型
     *
     * @param tradeContext 交易上下文
     */
    private void baseCheck(TradeContext<?, ?> tradeContext) {
        BankAttAcctReq reqBody = getReqBody(tradeContext);
        String manageTp = reqBody.getMgmtTp();
        String walletTp = reqBody.getWltInf().getWltTp();
        String idTp = reqBody.getSgnInf().getIdTp();
        //检查管理类型
        if (!(ManagementTpCdEnum.MT01.getCode().equals(manageTp) || ManagementTpCdEnum.MT02.getCode().equals(manageTp) || ManagementTpCdEnum.MT03.getCode().equals(manageTp))) {
            logger.error("不支持{}管理类型,本次请求报文标识号为：{}", manageTp, reqBody.getGrpHdr().getMsgId());
            throw new EcnyTransException(EcnyTransError.UN_SUPPORTED_MANAGEMENT_TP);
        }
        //检查钱包类型 2021/1/13确定不用校验钱包类型
//        if (!(WalletTpCdEnum.WT01.getCode().equals(walletTp) || WalletTpCdEnum.WT02.getCode().equals(walletTp))) {
//            logger.error("不支持{}钱包类型,本次请求报文标识号为：{}", walletTp, reqBody.getGrpHdr().getMsgId());
//            throw new EcnyTransException(EcnyTransError.UN_SUPPORTED_WALLET_TP);
//        }
        //暂不支持只做身份认证的交易
        if (ManagementTpCdEnum.MT01.getCode().equals(manageTp) && SignTpCdEnum.SG00.getCode().equals(reqBody.getSgnInf().getSgnTp())) {
            logger.error("不支持只做身份认证的交易,本次请求报文标识号为：{}", reqBody.getGrpHdr().getMsgId());
            throw new EcnyTransException(EcnyTransError.UN_SUPPORTED_BIZ_TP);
        }
        //校验证件类型枚举值
        if(!isValidEnum(idTp)){
            logger.error("暂不支持当前证件类型：{}"+idTp);
            throw new EcnyTransException(EcnyTransError.IDTP_ERROR);
        }
    }

    private boolean isValidEnum(String code){
        for(IDTpEnum idTpEnum : IDTpEnum.values()){
            if(idTpEnum.getIDKey().equals(code)){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据管理类型执行不同策略
     *
     * @param tradeContext 交易上下文
     */
    private void executeStrategy(TradeContext<?, ?> tradeContext) {
        BankAttAcctReq reqBody = getReqBody(tradeContext);
        String manageTp = reqBody.getMgmtTp();
        TradeFlow tradeFlow = getStrategy(manageTp);
        TradeFlowRuner.execute(tradeFlow, tradeContext);
    }

    /**
     * 根据管理类型获取TradeFlow
     *
     * @param manageType 管理类型
     * @return TradeFlow
     */
    private TradeFlow getStrategy(String manageType) {
//        final RtpUtil rtpUtil = RtpUtil.getInstance();
        if (ManagementTpCdEnum.MT01.getCode().equals(manageType)) {
            return (TradeFlow) applicationContext.getBean(ACC_AUTH_FLOW);
        } else if (ManagementTpCdEnum.MT02.getCode().equals(manageType)) {
            return (TradeFlow) applicationContext.getBean(ACC_CONFIRM_FLOW);
        } else if (ManagementTpCdEnum.MT03.getCode().equals(manageType)) {
            return (TradeFlow) applicationContext.getBean(ACC_RESCIND_FLOW);
        } else {
            logger.error("不支持{}管理类型", manageType);
            throw new EcnyTransException(EcnyTransError.UN_SUPPORTED_MANAGEMENT_TP);
        }
    }


    /**
     * 从交易上下文中获取请求体
     *
     * @param tradeContext 交易上下文
     * @return BankAttAcctReq
     */
    private BankAttAcctReq getReqBody(TradeContext<?, ?> tradeContext) {
        DCEPReqDTO<BankAttAcctReqDTO> reqDTO = EcnyTradeContext.getReqMsg(tradeContext);
        return reqDTO.getBody().getBankAttAcctReq();
    }

    /**
     * 从交易上下文中获取响应体
     *
     * @param tradeContext 交易上下文
     * @return PmtPtcMgmtRsp
     */
    private PmtPtcMgmtRsp getRspBody(TradeContext<?, ?> tradeContext) {
        DCEPRspDTO<PmtPtcMgmtRspDTO> rspDTO = EcnyTradeContext.getRspMsg(tradeContext);
        return rspDTO.getBody().getPmtPtcMgmtRsp();
    }


    /**
     * 身份认证检查：当管理类型为MT01(身份认证时)
     *
     * @param tradeContext 交易上下文
     */
    private void checkAccount(TradeContext<?, ?> tradeContext) {
        BankAttAcctReq reqBody = getReqBody(tradeContext);
        //账户校验
        BankCore358040Rsp bankCore358040Rsp = BankCore358040Verify(tradeContext);

        //保存账户信息
        Map<String, Object> contextMap = EcnyTradeContext.getTempContext(tradeContext);
        contextMap.put(BANK_ACCT_INFO, bankCore358040Rsp);

        //反洗钱检查
        String acctIdNo = reqBody.getSgnInf().getIdNo();
        String acctType = bankCore358040Rsp.getType();
        String acctName = reqBody.getSgnInf().getSgnAcctNm();
        //      Map<String, String> errMap = bankAccountVerifyService.geteWayLSFK43Imp(acctIdNo, acctType, acctName, DateTimeUtil.getCurrentDateStr(), DateTimeUtil.getCurrentTimeStr(), ecnySerNoService.getNBitsRandNum(6));
        Map<String, String> errMap = bankAccountVerifyService.invokeLSFK43(acctIdNo, acctType, acctName, DateUtil.getCurDay(), DateUtil.getCurTime(), ecnySerNoService.getNBitsRandNum(6));
        String errorCode = errMap.get(ERRORCODE);
        String errorMsg = errMap.get(ERRORMSG);
        if (!LSFK43_SUCCESS_CODE.equals(errorCode)) {
            logger.error("调用反洗钱失败，错误码：{}，错误信息：{}", errorCode, errorMsg);

            throw new EcnyTransException(EcnyTransError.INVALID_ACCT_STATUS);
        }
        //ECIF检查
        //bankAccountVerifyService.ecifCheckAcctBaseInfo(bankCore358040Rsp);

        ecifService.checkAcctInfoByEcif(bankCore358040Rsp);
    }


    /**
     * 银行核心358040校验
     *
     * @param tradeContext 交易上下文
     */
    private BankCore358040Rsp  BankCore358040Verify(TradeContext<?, ?> tradeContext) {
        BankAttAcctReq reqBody = getReqBody(tradeContext);
        return bankAccountVerifyService.verifyAccount(TrxTpCdEnum.SIGN.getCode(), buildBankCore358040Req(reqBody));
    }

    /**
     * 检查原交易
     * 身份确认检查：当管理类型为MT02(身份认证时)
     *
     * @param tradeContext 交易上下文
     */
    private void checkOriginTrade(TradeContext<?, ?> tradeContext) {
        BankAttAcctReq reqBody = getReqBody(tradeContext);
        String walletId = reqBody.getWltInf().getWltId();
        PtcInf ptcInf = reqBody.getPtcInf();
        if (ptcInf == null || StringUtils.isEmpty(ptcInf.getMsgVrfy())) {
            logger.error("身份确认时短信验证码不能为空：errCode：{}，errMsg：{}");
            throw new EcnyTransException(EcnyTransError.AUTH_MSG_EMPTY);
        }
        //身份认证时动态关联码不能为空
        if (null == reqBody.getPtcInf() || null == reqBody.getPtcInf().getMsgSndCd()) {
            logger.error("身份认证时，动态关联码不能为空,errCode：{}，errMsg：{}", EcnyTransError.MSG_SND_CD_EMPTY.getErrorCode(), EcnyTransError.MSG_SND_CD_EMPTY.getErrorMsg());
            throw new EcnyTransException(EcnyTransError.MSG_SND_CD_EMPTY);
        }
        String msgSndCd = reqBody.getPtcInf().getMsgSndCd();
        SignJrnDO oriSignJrnDO = signJrnMapper.selectByWalletIdAndMsgSndCd(walletId, msgSndCd);
        //原身份认证交易不存在
        if (null == oriSignJrnDO) {
            logger.error("身份确认检查失败,原交易不存在,errCode：{}，errMsg：{}", EcnyTransError.TRADE_NOT_FOUND.getErrorCode(), EcnyTransError.TRADE_NOT_FOUND.getErrorMsg());
            throw new EcnyTransException(EcnyTransError.TRADE_NOT_FOUND);
        }
        //原身份认证交易非成功状态
        if (!AppConstant.TRXSTATUS_SUCCESS.equals(oriSignJrnDO.getTrxStatus())) {
            logger.error("身份确认检查失败,原交易非成功状态,errCode：{}，errMsg：{}", EcnyTransError.ORI_TRADE_INVALID.getErrorCode(), EcnyTransError.ORI_TRADE_INVALID.getErrorMsg());
            throw new EcnyTransException(EcnyTransError.ORI_TRADE_INVALID);
        }
        //校验身份认证和身份确认的签约人信息是否一致
        SgnInf sgnInf = reqBody.getSgnInf();
        if (!sgnInf.getSgnAcctPtyId().equals(oriSignJrnDO.getAcctPtyId()) ||
                !sgnInf.getSgnAcctTp().equals(oriSignJrnDO.getAcctType()) ||
                !sgnInf.getSgnAcctId().equals(oriSignJrnDO.getAcctId()) ||
                !sgnInf.getSgnAcctNm().equals(oriSignJrnDO.getAcctName()) ||
                !sgnInf.getIdTp().equals(oriSignJrnDO.getIdType()) ||
                !sgnInf.getIdNo().equals(oriSignJrnDO.getIdNo()) ||
                !sgnInf.getTel().equals(oriSignJrnDO.getTelephone())
        ) {
            logger.error("身份确认失败，身份认证和身份确认传入的信息不一致：{}，{}", EcnyTransError.UN_MATCHED_SIGN_INFO.getErrorCode(), EcnyTransError.UN_MATCHED_SIGN_INFO.getErrorMsg());
            throw new EcnyTransException(EcnyTransError.UN_MATCHED_SIGN_INFO);
        }
        //校验短信验证码
        String sendMsgTime = oriSignJrnDO.getLastUpDate() + oriSignJrnDO.getLastUpTime();
        String oriEncAuthMsg = oriSignJrnDO.getMsgVerifyCode();
        smsService.verifyAuthMsg(reqBody.getPtcInf().getMsgVrfy(), oriEncAuthMsg, sendMsgTime);
    }

    /**
     * 账户解约检查：当管理类型为MT03(解约)
     *
     * @param tradeContext 交易上下文
     */
    private SignInfoDO checkOriginSignInfo(TradeContext<?, ?> tradeContext) {
        BankAttAcctReq reqBody = getReqBody(tradeContext);
        //解约时协议号为空
        if (null == reqBody.getPtcInf() || null == reqBody.getPtcInf().getPtcId()) {
            logger.error("[--checkOriginSignInfo--] 账户解约检查失败,本次请求的报文标识号为：{}", reqBody.getGrpHdr().getMsgId());
            throw new EcnyTransException(EcnyTransError.PTC_ID_EMPTY);
        }
        //不存在原签约信息
        SignInfoDO signInfoDO = signInfoMapper.selectBySignNo(reqBody.getPtcInf().getPtcId());
        if (null == signInfoDO) {
            logger.error("[--checkOriginSignInfo--] 账户解约检查失败,不存在原签约信息,本次请求的报文标识号为：{}", reqBody.getGrpHdr().getMsgId());
            throw new EcnyTransException(EcnyTransError.ORIGIN_SIGN_INFO_NOT_FOUND);
        }

        //解约时校验客户信息
        SgnInf sgnInf = reqBody.getSgnInf();
        if (!sgnInf.getSgnAcctPtyId().equals(signInfoDO.getAcctPtyId()) ||
                !sgnInf.getSgnAcctTp().equals(signInfoDO.getAcctType()) ||
                !sgnInf.getSgnAcctId().equals(signInfoDO.getAcctId()) ||
                !sgnInf.getSgnAcctNm().equals(signInfoDO.getAcctName())

        ) {
            logger.error("解约失败：{}，{}", EcnyTransError.UN_MATCHED_SIGN_INFO.getErrorCode(), EcnyTransError.UN_MATCHED_SIGN_INFO.getErrorMsg());
            throw new EcnyTransException(EcnyTransError.UN_MATCHED_SIGN_INFO);
        }

        return signInfoDO;
    }


    /**
     * 保存交易流水
     *
     * @param tradeContext 交易上下文
     */
    private void insertJrnDO(TradeContext<?, ?> tradeContext) {
        SignJrnDO signJrnDO = getSignInsertJrnDOFromContext(tradeContext);
        signJrnMapper.insert(signJrnDO);
    }


    /**
     * 身份认证处理
     */
    private void sendAuthCode(TradeContext<?, ?> tradeContext) {
        DCEPReqDTO<BankAttAcctReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        BankAttAcctReq reqBody = reqMsg.getBody().getBankAttAcctReq();
        PmtPtcMgmtRsp rspBody = getRspBody(tradeContext);
        //获取待更新流水
        SignJrnDO signUpdateJrnDO = getSignUpdateJrnDOFromContext(tradeContext);
        //todo
//        SmsFactory factory = SmsFactory.getInstance(Constant.SM3);
//        String smsCode = factory.getSmsCode();
        //获取短信验证码的sm3散列值，用于存数据库
        //todo
//        String encryptSmsCode = factory.encryptSmsCode(smsCode);
        //设置动态关联码 = 平台日期 + 平台流水
        String dynAssCode = DateUtil.getCurDay() + generateCodeService.generatePlatformFlowNo();
        rspBody.getRspsnInf().setMsgSndCd(dynAssCode);
//        signUpdateJrnDO.setMsgVerifyCode(encryptSmsCode);
        signUpdateJrnDO.setMsgSendCode(dynAssCode);
        String cardNo = reqBody.getSgnInf().getSgnAcctId();
//        logger.debug("生产短信验证码的散列值为smsCode：{}->{}", smsCode, encryptSmsCode);
        Map<String, String> map = new HashMap<>();
//        map.put("MSGCODE", smsCode);
        map.put("ACCTNO", cardNo.substring(cardNo.length() - 4));
        map.put("CARDTY", DEBIT_CARD_TYPE);
        try {
            boolean sendMsgResult = smsService.sendMsg(reqBody.getSgnInf().getTel(), SIGN_AUTH_TEMPCODE, SMSID, CHANNELCODE, SENDBRANCH, map, true);
            if (sendMsgResult) {
                logger.info("短信发送成功，本次请求报文标识号为：{}", reqBody.getGrpHdr().getMsgId());
                signUpdateJrnDO.setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
            } else {
                logger.info("短信发送失败，本次请求报文标识号为：{}", reqBody.getGrpHdr().getMsgId());
                signUpdateJrnDO.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
            }
        } catch (Exception e) {
            logger.error("短信发送异常{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.SEND_SMS_ERROR);
        }

    }


    /**
     * 生成协议或者更新协议
     *
     * @param tradeContext 交易上下文
     */
    private void genOrUpdSign(TradeContext<?, ?> tradeContext) {
        BankAttAcctReq reqBody = getReqBody(tradeContext);
        SignInfoDO oriSignInfoDO = null;

        //先根据协议号查询原协议
        if (null != reqBody.getPtcInf() && !StringUtils.isEmpty(reqBody.getPtcInf().getPtcId())) {
            oriSignInfoDO = signInfoMapper.selectBySignNo(reqBody.getPtcInf().getPtcId());
            if (null == oriSignInfoDO) {
                logger.error("不存在协议号为：{}的协议", reqBody.getPtcInf().getPtcId());
                throw new EcnyTransException(EcnyTransError.ORIGIN_SIGN_INFO_NOT_FOUND);
            }
        }

        //根据协议号查不到原协议则根据钱包ID和账户ID查询原协议
        if (null == oriSignInfoDO) {
            SignInfoDO queryParam = new SignInfoDO();
            queryParam.setWalletId(reqBody.getWltInf().getWltId());
            queryParam.setAcctId(reqBody.getSgnInf().getSgnAcctId());
            oriSignInfoDO = signInfoMapper.selectByWltIdAndAcctId(queryParam);
        }

        SignInfoDO signInfoDO = null;
        if (null == oriSignInfoDO) {
            //原协议信息为空重新生成协议
            signInfoDO = generateSign(reqBody);
            signInfoMapper.insert(signInfoDO);
        } else if (CLOSE_SIGN_STATUS.equals(oriSignInfoDO.getSignStatus())) {
            //原协议为失效状态
            signInfoDO = generateSign(reqBody);
            signInfoMapper.updateByWltIdAndAcctId(signInfoDO);
        } else {
            signInfoDO = updateSignInfo(reqBody);
            signInfoDO.setSignNo(oriSignInfoDO.getSignNo());
            signInfoMapper.updateBySignNo(signInfoDO);
        }
        PmtPtcMgmtRsp rspBody = getRspBody(tradeContext);
        rspBody.getRspsnInf().setPtcId(signInfoDO.getSignNo());

    }

    private SignInfoDO updateSignInfo(BankAttAcctReq reqBody) {
        SignInfoDO signInfoDO = new SignInfoDO();
        signInfoDO.setPayDate(DateUtil.getCurDay());                        //平台日期
        signInfoDO.setPaySerNo(generateCodeService.generatePlatformFlowNo());           //平台流水
        signInfoDO.setPayTime(DateUtil.getCurTime());                        //平台时间
        signInfoDO.setSignStatus(VALID_SIGN_STATUS);                                    //协议状态
        signInfoDO.setAcctPtyId(AppConstant.CGB_FINANCIAL_INSTITUTION_CD);              //签约人银行账户所属机构
        signInfoDO.setAcctType(reqBody.getSgnInf().getSgnAcctTp());                     //签约人银行账户类型
        signInfoDO.setAcctId(reqBody.getSgnInf().getSgnAcctId());                       //签约人银行账户账号
        signInfoDO.setAcctName(reqBody.getSgnInf().getSgnAcctNm());                     //签约人银行账户户名
        signInfoDO.setIdType(reqBody.getSgnInf().getIdTp());                            //签约人证件类型
        signInfoDO.setIdNo(reqBody.getSgnInf().getIdNo());                              //签约人证件号码
        signInfoDO.setTelephone(reqBody.getSgnInf().getTel());                          //银行预留手机号码
        signInfoDO.setWalletPtyId(reqBody.getWltInf().getWltPtyId());                   //钱包开立所属机构编码
        signInfoDO.setWalletId(reqBody.getWltInf().getWltId());                         //钱包ID
        signInfoDO.setWalletLevel(reqBody.getWltInf().getWltLvl());                     //钱包等级
        signInfoDO.setWalletType(reqBody.getWltInf().getWltTp());                       //钱包类型
        signInfoDO.setLastUpDate(DateUtil.getCurDay());                     //最后更新日期
        signInfoDO.setLastUpTime(DateUtil.getDefaultTime());                     //最后更新时间
        return signInfoDO;
    }

    /**
     * 生成协议并插入
     *
     * @param reqBody 请求体
     */
    private SignInfoDO generateSign(BankAttAcctReq reqBody) {
        SignInfoDO signInfoDO = updateSignInfo(reqBody);
        signInfoDO.setSignNo(generateCodeService.generateAgreementNo(AppConstant.ORGCODE));
        return signInfoDO;
    }


    /**
     * 更新交易流水
     * RSPCODE	应答业务处理码
     * RSPMSG	应答业务处理信息
     *
     * @param tradeContext 交易上下文
     */
    private void updateJrn(TradeContext<?, ?> tradeContext) {
        SignJrnDO signUpdateJrnDO = getSignUpdateJrnDOFromContext(tradeContext);
        signUpdateJrnDO.setLastUpDate(DateUtil.getCurDay());
        signUpdateJrnDO.setLastUpTime(DateUtil.getDefaultTime());
        signJrnMapper.update(signUpdateJrnDO);
    }

    /**
     * 响应报文封装
     * 设置正常状态即可-如果有异常场景可以在errHandler中处理
     *
     * @param tradeContext 交易上下文
     */
    private void response(TradeContext<?, ?> tradeContext) {
        DCEPRspDTO<PmtPtcMgmtRspDTO> rspDTO = EcnyTradeContext.getRspMsg(tradeContext);
        PmtPtcMgmtRsp rspBody = rspDTO.getBody().getPmtPtcMgmtRsp();
        //设置回执状态
        BankAttRspsnInf rspsnInf = rspBody.getRspsnInf();
        rspsnInf.setRspsnSts(ProcessStsCdEnum.PR00.getCode());
        //设置平台处理状态
        SignJrnDO signUpJrnDO = getSignUpdateJrnDOFromContext(tradeContext);
        signUpJrnDO.setTrxStatus(TrxStatusEnum.SUCCESS.getCode());
        signUpJrnDO.setRspStatus(ProcessStsCdEnum.PR00.getCode());
        signUpJrnDO.setTrxRetCode(AppConstant.TRX_RET_CODE_SUCCESS);
        signUpJrnDO.setTrxRetMsg(AppConstant.TRX_RET_MSG_SUCCESS);
        signUpJrnDO.setRspCode(ProcessStsCdEnum.PR00.getCode());
        signUpJrnDO.setRspMsg(ProcessStsCdEnum.PR00.getDesc());
    }

    /**
     * 错误处理
     *
     * @param tradeContext 交易上下文
     * @param throwable    Throwable
     */
    private void errHandler(TradeContext<?, ?> tradeContext, Throwable throwable) {
        DCEPRspDTO<PmtPtcMgmtRspDTO> rspDTO = EcnyTradeContext.getRspMsg(tradeContext);
        PmtPtcMgmtRsp rspBody = rspDTO.getBody().getPmtPtcMgmtRsp();
        BankAttRspsnInf rspsnInf = rspBody.getRspsnInf();
        //设置业务回执状态
        rspsnInf.setRspsnSts(ProcessStsCdEnum.PR01.getCode());
        //更新错误码和错误信息到待插入流水信息
        SignJrnDO signUpdateJrnDO = getSignUpdateJrnDOFromContext(tradeContext);
        signUpdateJrnDO.setLastUpDate(DateUtil.getCurDay());
        signUpdateJrnDO.setLastUpTime(DateUtil.getDefaultTime());
        signUpdateJrnDO.setTrxStatus(TrxStatusEnum.FAIL.getCode());
        signUpdateJrnDO.setTrxRetCode(AppConstant.TRX_RET_CODE_FAILED);
        signUpdateJrnDO.setRspStatus(ProcessStsCdEnum.PR01.getCode());
        signUpdateJrnDO.setRspCode(ProcessStsCdEnum.PR01.getCode());

        //设置错误码错误信息
        if (throwable instanceof EcnyTransException) {

            rspsnInf.setRjctInf(((EcnyTransException) throwable).getMessage());
            rspsnInf.setRjctCd(((EcnyTransException) throwable).getErrorCode());
            signUpdateJrnDO.setTrxRetCode(((EcnyTransException) throwable).getErrorCode());
            signUpdateJrnDO.setTrxRetMsg(((EcnyTransException) throwable).getErrorMsg());
            signUpdateJrnDO.setRspMsg(((EcnyTransException) throwable).getErrorMsg());
        } else {
            rspsnInf.setRjctInf(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
            signUpdateJrnDO.setTrxRetMsg(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
            signUpdateJrnDO.setRspMsg(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
        }
        //错误码映射
        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(throwable, TrxTpCdEnum.SIGN.getCode(), AppConstant.DCEP_SYS_ID);
        if (null != rspCodeMapDO) {
            rspsnInf.setRjctCd(rspCodeMapDO.getDestRspCode());
            rspsnInf.setRjctInf(rspCodeMapDO.getRspCodeDsp());
            signUpdateJrnDO.setTrxRetCode(rspCodeMapDO.getDestRspCode());
            signUpdateJrnDO.setRspMsg(rspCodeMapDO.getRspCodeDsp());
        }

        signJrnMapper.update(signUpdateJrnDO);

        //发送签约失败信息
        BankAttAcctReq reqBody = getReqBody(tradeContext);
        String manageTp = reqBody.getMgmtTp();
        if (ManagementTpCdEnum.MT02.getCode().equals(manageTp) && SignTpCdEnum.SG01.getCode().equals(reqBody.getSgnInf().getSgnTp())) {
            sendSignFailedMsg(tradeContext);
        }

    }


    /**
     * 构建签约流水实体
     *
     * @param req BankAttAcctReq
     * @return SignJrnDO
     */
    private SignJrnDO buildSignInsertJrnDo(BankAttAcctReq req) {
        PtcInf ptcInf = req.getPtcInf();                                                         //协议信息
        SgnInf sgnInf = req.getSgnInf();                                                         //签约人信息
        WltInf wltInf = req.getWltInf();                                                         //钱包信息
//        SmsFactory factory = SmsFactory.getInstance(Constant.SM3);
        SignJrnDO signJrnDO = new SignJrnDO();
        signJrnDO.setPayDate(DateUtil.getCurDay());                                  //设置平台日期
        signJrnDO.setPayTime(DateUtil.getDefaultTime());                                  //设置平台时间
        signJrnDO.setPaySerNo(generateCodeService.generatePlatformFlowNo());                     //设置平台流水号
        signJrnDO.setMsgId(req.getGrpHdr().getMsgId());                                          //设置报文标识号
        signJrnDO.setInstGpTy(req.getGrpHdr().getInstgPty().getInstgDrctPty());                  //设置发起机构
        signJrnDO.setInstDpTy(req.getGrpHdr().getInstdPty().getInstdDrctPty());                  //设置接收结构
        signJrnDO.setDirect(DirectEnum.RECV.getCode());                                          //设置往来方向
        signJrnDO.setManageType(req.getMgmtTp());                                                //签约管理类型
        signJrnDO.setSignType(sgnInf.getSgnTp());                                                //签约类型
        if (ptcInf != null) {
            if (ptcInf.getMsgVrfy() != null) {
                //todo
//                signJrnDO.setMsgVerifyCode(factory.encryptSmsCode(ptcInf.getMsgVrfy()));         //动态验证码(sm3加密存储)
            }
            signJrnDO.setSignNo(ptcInf.getPtcId());                                              //挂接协议号
        }
        signJrnDO.setTrxStatus(TrxStatusEnum.PROCESSING.getCode());                              //初始化时设置状态为处理中
        signJrnDO.setRspMsgId(req.getGrpHdr().getMsgId());                                       //设置应答报文标识号
        signJrnDO.setRspStatus(ProcessStsCdEnum.PR02.getCode());                                 //设置业务回执状态为处理中
        signJrnDO.setAcctPtyId(sgnInf.getSgnAcctPtyId());                                        //签约人银行账户所属机构
        signJrnDO.setAcctType(sgnInf.getSgnAcctTp());                                            //签约人银行账户类型
        signJrnDO.setAcctId(sgnInf.getSgnAcctId());                                              //签约人银行账户账号
        signJrnDO.setAcctName(sgnInf.getSgnAcctNm());                                            //签约人银行账户户名
        signJrnDO.setIdType(sgnInf.getIdTp());                                                   //签约人证件类型
        signJrnDO.setIdNo(sgnInf.getIdNo());                                                     //签约人证件号码
        signJrnDO.setTelephone(sgnInf.getTel());                                                 //签约人银行预留手机号
        signJrnDO.setWalletPtyId(wltInf.getWltPtyId());                                          //钱包开立所属机构编码
        signJrnDO.setWalletId(wltInf.getWltId());                                                //钱包Id
        signJrnDO.setWalletType(wltInf.getWltTp());                                              //钱包类型
        signJrnDO.setWalletLevel(wltInf.getWltLvl());                                            //钱包等级
        signJrnDO.setLastUpDate(DateUtil.getCurDay());                               //设置当前日期
        signJrnDO.setLastUpTime(DateUtil.getDefaultTime());                               //设置当前时间
        signJrnDO.setRemark(req.getGrpHdr().getRmk());                                           //设置备注
        signJrnDO.setTrxRetCode(AppConstant.TRX_RET_CODE_PROCESSING);                            //平台业务处理码
        signJrnDO.setTrxRetMsg(AppConstant.TRX_RET_MSG_PROCESSING);                              //平台业务处理信息
        signJrnDO.setRspCode(ProcessStsCdEnum.PR02.getCode());                                   //RSPCODE
        signJrnDO.setRspMsg(ProcessStsCdEnum.PR02.getDesc());                                    //RSPMSG
        return signJrnDO;
    }


    /**
     * 初始化账户挂接管理响应报文
     *
     * @param req BankAttAcctReq
     * @return PmtPtcMgmtRsp
     */
    private PmtPtcMgmtRsp buildRspMsg(BankAttAcctReq req) {
        logger.info("[--buildRspMsg--] 开始初始化响应报文,本次请求报文标识号为：{}", req.getGrpHdr().getMsgId());
        PmtPtcMgmtRsp pmtPtcMgmtRsp = new PmtPtcMgmtRsp();
        GrpHdr grpHdr = new GrpHdr();                                                           //业务组件头
        OrgnlGrpHdr orgnlGrpHdr = new OrgnlGrpHdr();                                            //原报文主键组件
        BankAttRspsnInf rspsnInf = new BankAttRspsnInf();                                                     //响应信息
        SgnInf sgnInf = new SgnInf();                                                           //签约人信息
        WltInf wltInf = new WltInf();                                                           //钱包信息
        //初始化业务组件头
        grpHdr.setMsgId(req.getGrpHdr().getMsgId());                                            //响应报文的报文标识号可以和请求报文的报文标识号一致
        grpHdr.setCreDtTm(DateUtil.getISODateTime());                                           //设置业务处理时间
        grpHdr.setInstdPty(new InstdPty(req.getGrpHdr().getInstgPty().getInstgDrctPty()));      //设置接收机构
        grpHdr.setInstgPty(new InstgPty(req.getGrpHdr().getInstdPty().getInstdDrctPty()));      //设置发起机构
        grpHdr.setRmk(req.getGrpHdr().getRmk());                                                //设置备注信息

        //初始化原报文主键组件
        orgnlGrpHdr.setOrgnlMsgId(req.getGrpHdr().getMsgId());                                  //设置原报文标识号
        orgnlGrpHdr.setOrgnlMT(MsgTpEnum.DCEP433.getCode());                                    //设置原报文编号
        orgnlGrpHdr.setOrgnlInstgPty(req.getGrpHdr().getInstgPty().getInstgDrctPty());          //设置发起机构
        //初始化响应信息
        rspsnInf.setRspsnSts(ProcessStsCdEnum.PR01.getCode());                                  //初始化时设置业务回执状态为失败状态
        rspsnInf.setMgmtTp(req.getMgmtTp());                                                    //设置管理类型
        //初始化签约人信息
        sgnInf.setSgnAcctTp(req.getSgnInf().getSgnAcctTp());                                    //设置签约人银行账户类型
        sgnInf.setSgnAcctId(req.getSgnInf().getSgnAcctId());                                    //设置签约人银行账户账号
        sgnInf.setSgnAcctNm(req.getSgnInf().getSgnAcctNm());                                    //设置签约人账户户名
        sgnInf.setSgnTp(req.getSgnInf().getSgnTp());                                            //设置签约类型
        //初始化钱包信息
        wltInf.setWltId(req.getWltInf().getWltId());                                            //设置钱包ID
        wltInf.setWltTp(req.getWltInf().getWltTp());                                            //设置钱包类型
        wltInf.setWltLvl(req.getWltInf().getWltLvl());                                          //设置钱包等级
        //为响应报文赋值
        pmtPtcMgmtRsp.setGrpHdr(grpHdr);                                                        //设置业务组件头
        pmtPtcMgmtRsp.setOrgnlGrpHdr(orgnlGrpHdr);                                              //设置原报文主键组件
        pmtPtcMgmtRsp.setRspsnInf(rspsnInf);                                                    //设置响应信息
        pmtPtcMgmtRsp.setSgnInf(sgnInf);                                                        //设置签约人信息
        pmtPtcMgmtRsp.setWltInf(wltInf);                                                        //设置钱包信息
        logger.info("[--buildRspMsg--] 初始化响应报文结束,本次请求报文标识号为：{}", req.getGrpHdr().getMsgId());
        return pmtPtcMgmtRsp;
    }


    /**
     * 构建核心请求报文体
     *
     * @param msg 签约认证接口实体
     * @return BankCore358040Req
     */
    private BankCore358040Req buildBankCore358040Req(BankAttAcctReq msg) {
        //构建账户查询接口请求报文体
        BankCore358040Req bankCore358040Req = new BankCore358040Req();
        final SgnInf sgnInf = msg.getSgnInf();
        //设置账号
        bankCore358040Req.setAc(sgnInf.getSgnAcctId());
        //设置户名
        bankCore358040Req.setName(sgnInf.getSgnAcctNm());
        //设置证件号码
        bankCore358040Req.setIdType(IDTpEnum.getBankCoreIDTP(sgnInf.getIdTp()));
        bankCore358040Req.setIdNo(sgnInf.getIdNo());
        //设置手机号
        bankCore358040Req.setTelNo(sgnInf.getTel());
        return bankCore358040Req;
    }

    /**
     * 从交易上下文中获取待插入流水
     *
     * @param tradeContext 交易上下文
     * @return SignJrnDO
     */
    private SignJrnDO getSignInsertJrnDOFromContext(TradeContext<?, ?> tradeContext) {
        BankAttAcctReq reqBody = getReqBody(tradeContext);
        Map<String, Object> contextMap = tradeContext.getTempCtx();
        SignJrnDO signInsertJrnDO = (SignJrnDO) contextMap.get(SIGN_INSERT_DO);
        if (null == signInsertJrnDO) {
            signInsertJrnDO = buildSignInsertJrnDo(reqBody);
        }
        contextMap.put(SIGN_INSERT_DO, signInsertJrnDO);
        return signInsertJrnDO;
    }

    /**
     * 从交易上下文中获取待更新流水
     *
     * @param tradeContext 交易上下文
     * @return SignJrnDO
     */
    private SignJrnDO getSignUpdateJrnDOFromContext(TradeContext<?, ?> tradeContext) {
        Map<String, Object> contextMap = tradeContext.getTempCtx();
        SignJrnDO signUpdateJrnDO = (SignJrnDO) contextMap.get(SIGN_UPDATE_DO);
        if (null == signUpdateJrnDO) {
            SignJrnDO signInsertJrnDO = getSignInsertJrnDOFromContext(tradeContext);
            signUpdateJrnDO = new SignJrnDO();
            BeanUtils.copyProperties(signInsertJrnDO, signUpdateJrnDO);
            contextMap.put(SIGN_UPDATE_DO, signUpdateJrnDO);
        }
        return signUpdateJrnDO;
    }

    /**
     * 创建待更新流水实体
     *
     * @param tradeContext 交易上下文
     * @return SignJrnDO
     */
    private SignJrnDO buildSignUpdateJrnDo(TradeContext<?, ?> tradeContext) {
        SignJrnDO signInsertJrnDO = getSignInsertJrnDOFromContext(tradeContext);
        SignJrnDO signUpdateJrnDO = new SignJrnDO();
        BeanUtils.copyProperties(signInsertJrnDO, signUpdateJrnDO);
        return signUpdateJrnDO;
    }


    /**
     * 解绑流程
     * 1.校验原协议信息、
     * 2.更新原协议为失败状态
     *
     * @param tradeContext 交易上下文
     */
    private void rescindProcess(TradeContext<?, ?> tradeContext) {
        //校验原协议信息
        SignInfoDO signInfoDO = checkOriginSignInfo(tradeContext);
        //更新原协议信息
        signInfoDO.setSignStatus(CLOSE_SIGN_STATUS);
        signInfoDO.setLastUpDate(DateUtil.getCurDay());
        signInfoDO.setLastUpTime(DateUtil.getDefaultTime());
        signInfoMapper.updateBySignNo(signInfoDO);
    }


}
