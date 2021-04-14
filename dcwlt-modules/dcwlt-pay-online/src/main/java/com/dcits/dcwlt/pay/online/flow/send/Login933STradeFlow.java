package com.dcits.dcwlt.pay.online.flow.send;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.enums.*;
import com.dcits.dcwlt.common.pay.sequence.service.IGenerateCodeService;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.type.OutOrgTypeEnum;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPHeader;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.cmonconf.CmonConf;
import com.dcits.dcwlt.pay.api.domain.dcep.cmonconf.CmonConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.login.*;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspHead;
import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.online.baffle.dcep.DcepService;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.service.IPartyInfoservice;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlNonfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 登录登出处理配置
 *
 * @author
 */
@Configuration
public class Login933STradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(Login933STradeFlow.class);
    public static final String LOGIN_TRADE_FLOW = "Login933STradeFlow";
    //DCEP请求报文上下文key
    private static final String KEY_DCEP_LOGIN_REQ = "loginDcepReq";
    //DCEP响应报文上下文key
    private static final String KEY_DCEP_LOGIN_RSP = "loginDcepRsp";
    //DCEP响应报文体上下文key
    private static final String KEY_LOGIN_RSPDTO = "loginRspDTO";
    //DCEP报文登记簿上下文key
    private static final String KEY_PAY_TRANS_DTL_NONF = "payTransDtlNonfDO";
    //DCEP流水号上下文key
    private static final String KEY_PAY_SEQ_NO = "paySeqNo";
    //DCEP登录机构实体key
    private static final String KEY_LOGIN_PARTY_DO = "loginPartyDO";
    //柜员号长度
    private static final int TLR_NO_MAX_LENGTH = 16;

//    @Autowired
//    DcepSendService dcepSendService;
    @Autowired
    private IPayTransDtlNonfService payTransDtlNonfService;
    @Autowired
    private IPartyInfoservice partyInfoservice;
    @Autowired
    IGenerateCodeService generateCodeService;
    @Autowired
    private DcepService dcepService;

    @Bean(name = LOGIN_TRADE_FLOW)
    public TradeFlow payConvertTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .initTxn(this::initTxn)                //初始化交易流水和请求dcep的报文
                .checkMsg(this::checkMsg)              //参数检查
                .saveTxn(this::saveTxn)                //保存登记簿
                .process(this::sendDcep)               //发送到互联互通
                .response(this::response)              //响应报文
                .updateTxn(this::updateTxn)            //更新登记簿
                .errHandler(this::tradeErrHandler)     //异常处理
                .build();
    }

    /**
     * 初始化交易流水
     */
    public void initTxn(TradeContext<?, ?> tradeContext) {
        logger.info("登录/退出初始化.");
        //报文编号
        String msgTp = MsgTpEnum.DCEP933.getCode();
        //报文标识
        String msgId = generateCodeService.generateMsgId(OutOrgTypeEnum.OutOrg, MsgTpEnum.DCEP933.getCode().substring(5, 8), null);
        String msgSn = generateCodeService.generateMsgSN(msgId);
        // 接收方运营机构 即 互联互通
        String receiver = AppConstant.DCEP_FINANCIAL_INSTITUTION_CD;

        //流水号
        String paySeqNo = generateCodeService.generatePlatformFlowNo();
        tradeContext.getTempCtx().put(KEY_PAY_SEQ_NO, paySeqNo);

        ECNYReqDTO<LoginInnerReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        //操作类型
        LoginOperationTpCdEnum loginOprTp = null;
        if (LoginOperationTpCdEnum.OT00.getCode().equals(reqMsg.getBody().getOpterationType())) {
            loginOprTp = LoginOperationTpCdEnum.OT00;
        } else if (LoginOperationTpCdEnum.OT01.getCode().equals(reqMsg.getBody().getOpterationType())) {
            loginOprTp = LoginOperationTpCdEnum.OT01;
        }

        //生成初始响应报文
        packResponse(tradeContext, AppConstant.TRXSTATUS_INIT);

        //生成请求body
        LoginoutReqDTO loginReqDTO = new LoginoutReqDTO();
        LoginReq loginReq = new LoginReq();
        GrpHdr grpHdr = GrpHdr.getInstance(msgId, receiver);
        loginReq.setGrpHdr(grpHdr);
        LoginInf loginInf = new LoginInf();
        loginInf.setLoginOprTp(loginOprTp);
        loginReq.setLoginInf(loginInf);
        loginReqDTO.setLoginReq(loginReq);

        //生成请求实体
        DCEPReqDTO dcepReqDTO = DCEPReqDTO.newInstance(msgTp, msgSn, receiver, loginReqDTO);

        //DECP 请求报文放上下文临时容器
        EcnyTradeContext.getTempContext(tradeContext).put(KEY_DCEP_LOGIN_REQ, dcepReqDTO);
    }

    /**
     * 流水保存
     */
    private void saveTxn(TradeContext<?, ?> tradeContext) {
        logger.info("登录/退出交易入登记簿.");
        DCEPReqDTO dcepReqDTO = (DCEPReqDTO) EcnyTradeContext.getTempContext(tradeContext).get(KEY_DCEP_LOGIN_REQ);

        ECNYReqDTO<LoginInnerReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        LoginInnerReqDTO reqDTO = reqMsg.getBody();

        PayTransDtlNonfDO payTransDtlNonfDO = new PayTransDtlNonfDO();
        LoginoutReqDTO loginReqDTO = (LoginoutReqDTO) dcepReqDTO.getBody();

        //设置报文登记簿信息
        payTransDtlNonfDO.setTlrNo(reqDTO.getTlrNo());
        payTransDtlNonfDO.setDrct(AppConstant.DIRECT_SEND);
        payTransDtlNonfDO.setPayDate(DateUtil.getDefaultDate());
        payTransDtlNonfDO.setPaySerNo((String) tradeContext.getTempCtx().get(KEY_PAY_SEQ_NO));
        payTransDtlNonfDO.setPayTime(DateUtil.getDefaultTime());
        payTransDtlNonfDO.setPkgNo(MsgTpEnum.DCEP933.getCode());
        payTransDtlNonfDO.setMsgId(loginReqDTO.getLoginReq().getGrpHdr().getMsgId());
        payTransDtlNonfDO.setSenderDateTime(DateUtil.formatSeconds());
        payTransDtlNonfDO.setDrct(AppConstant.DIRECT_SEND);
        payTransDtlNonfDO.setInstdDrctPty(loginReqDTO.getLoginReq().getGrpHdr().getInstdPty().getInstdDrctPty());
        payTransDtlNonfDO.setInstgDrctPty(loginReqDTO.getLoginReq().getGrpHdr().getInstgPty().getInstgDrctPty());
        payTransDtlNonfDO.setOpterationType(loginReqDTO.getLoginReq().getLoginInf().getLoginOprTp());
        //初始交易状态
        payTransDtlNonfDO.setTradeStatus(AppConstant.TRXSTATUS_INIT);
        payTransDtlNonfDO.setProcStatus(ProcessStsCdEnum.PR02.getCode());
        payTransDtlNonfDO.setLastUpDate(DateUtil.getSysDate());
        payTransDtlNonfDO.setLastUpTime(DateUtil.getDefaultTime());
        //记录
        payTransDtlNonfService.addPayTransDtlNonf(payTransDtlNonfDO);

        //将报文登记簿放入上下文， 后续完成是取出进行更新
        tradeContext.getTempCtx().put(KEY_PAY_TRANS_DTL_NONF, payTransDtlNonfDO);
    }


    /**
     * 请求合法性检查
     */
    private void checkMsg(TradeContext<?, ?> tradeContext) {
        logger.info("登录/退出交易检查.");
        ECNYReqDTO<LoginInnerReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        LoginInnerReqDTO reqDTO = reqMsg.getBody();

        //判断请求参数和登录类型是否正常
        if (null == reqDTO || !LoginOperationTpCdEnum.hasEnum(reqDTO.getOpterationType())) {
            logger.warn("登录请求，请求参数有误, 登录退出操作类型有误");
            throw new EcnyTransException(EcnyTransError.ECNY_PARAM_ERROR);
        }

        //检查柜员号长度
        if (reqDTO.getTlrNo() != null && reqDTO.getTlrNo().length() > TLR_NO_MAX_LENGTH) {
            logger.error("登录请求，请求参数有误，柜员号参数有误，传入参数为：{}", reqDTO.getTlrNo());
            throw new EcnyTransException(EcnyTransError.ECNY_PARAM_ERROR);
        }

        PartyInfoDO partyInfoDO = partyInfoservice.queryPartyInfoByPartyId(AppConstant.CGB_FINANCIAL_INSTITUTION_CD);
        if (partyInfoDO == null) {
            logger.error("登录请求，机构不存在,请先调用机构变更接口初始化机构数据");
            throw new EcnyTransException(EcnyTransError.ECNY_NOPARTY_ERROR);
        }

        //设置机构信息到上下文
        tradeContext.getTempCtx().put(KEY_LOGIN_PARTY_DO, partyInfoDO);
    }


    /**
     * 发送933到互联互通平台
     *
     * @param tradeContext
     */
    public void sendDcep(TradeContext<?, ?> tradeContext) {
        logger.info("登录/退出交易请求发送.");
        DCEPReqDTO dcepReqDTO = (DCEPReqDTO) EcnyTradeContext.getTempContext(tradeContext).get(KEY_DCEP_LOGIN_REQ);
        //通过金融开放平台 --》互联互通
       // JSONObject jsonObject = dcepSendService.sendDcep(dcepReqDTO);
        JSONObject jsonObject = dcepService.receive902From933(dcepReqDTO);
        EcnyTradeContext.getTempContext(tradeContext).put(KEY_DCEP_LOGIN_RSP, jsonObject);
    }


    /**
     * 响应处理
     * 操作成功，修改机构状态
     */
    public void response(TradeContext<?, ?> tradeContext) {
        logger.info("登录/退出交易处理响应.");
        //从上下文取出响应消息
        JSONObject jsonObject = (JSONObject) EcnyTradeContext.getTempContext(tradeContext).get(KEY_DCEP_LOGIN_RSP);
    //    Head head = JSONObject.toJavaObject(jsonObject.getJSONObject("head"), Head.class);
//        if (head == null) {
//            throw new EcnyTransException(EcnyTransError.ECNY_CHECK_HEAD_ERROR);
//        }
//        if (!Constant.SERVER_SUCC_RSPCODE.equals(head.getRetCode())) {
//            throw new EcnyTransException(EcnyTransError.ECNY_RETCODE_RESULT_ERROR);
//        }

        //解析 DCEPhead
        DCEPHeader dcepHeader = JSONObject.toJavaObject(jsonObject.getJSONObject("ecnyHead"), DCEPHeader.class);
        if (null == dcepHeader) {
            //响应头缺失
            throw new EcnyTransException(EcnyTransError.ECNY_CHECK_DCEPHEAD_ERROR);
        }
        //判断互联互通返回报文编号
        if (MsgTpEnum.DCEP900.getCode().equals(dcepHeader.getMsgTp())) {
            //业务检查不通过
            //获取900报文
            JSONObject body = (JSONObject) jsonObject.get("body");
            if (null == body) {
                //响应没有body
                throw new EcnyTransException(EcnyTransError.ECNY_TRANS_BODY_ERROR);
            }
            //处理900响应体,如果响应DCEPO6100，提示操作类型不匹配， 将错误结果响记录到库
            CmonConfDTO cmonConfDTO = JSONObject.toJavaObject(body, CmonConfDTO.class);
            CmonConf cmonConf = cmonConfDTO.getCmonConf();
            if (cmonConf.getCmonConfInf().getPrcCd() != null && "DCEPO6100".equals(cmonConf.getCmonConfInf().getPrcCd())) {
                throw new EcnyTransException(EcnyTransError.ECNY_LOGIN_TPEY_MISMATCH_ERROR.getErrorCode(),
                        StringUtil.isBlank(cmonConf.getCmonConfInf().getRjctInf()) ?
                                EcnyTransError.ECNY_LOGIN_TPEY_MISMATCH_ERROR.getErrorMsg() : cmonConf.getCmonConfInf().getRjctInf());
            }
            throw new EcnyTransException(EcnyTransError.ECNY_RESULT_ERROR);
        }
        JSONObject body = (JSONObject) jsonObject.get("body");
        if (null == body) {
            //响应没有body
            throw new EcnyTransException(EcnyTransError.ECNY_TRANS_BODY_ERROR);
        } else {
            Object faultCode = body.get("faultcode");
            //响应码非成功
//            if (null != faultCode && !Head.RET_CODE_SUCCESS.equals(faultCode.toString())) {
//                throw new EcnyTransException(EcnyTransError.ECNY_RESULT_ERROR);
//            }
        }


        //解析body
        LoginRspDTO loginRspDTO = JSONObject.toJavaObject(jsonObject.getJSONObject("body"), LoginRspDTO.class);
        //业务处理状态
        ProcessStsCdEnum processStatus = loginRspDTO.getLoginRspn().getLoginRspnInf().getPrcSts();
        if (ProcessStsCdEnum.PR00.getCode().equals(processStatus.getCode())) {
            //处理成功，修改机构状态
            try {
                updatePtrStat(tradeContext);
            } catch (Exception e) {
                logger.warn("登录/退出请求发送成功，更新机构状态失败");
                throw new EcnyTransException(EcnyTransError.ECNY_UPDATE_RESULT_STATUS_ERROR);
            }
        }
        //body放入上下文
        EcnyTradeContext.getTempContext(tradeContext).put(KEY_LOGIN_RSPDTO, loginRspDTO);

        //构建响应报文
        packResponse(tradeContext, AppConstant.TRXSTATUS_SUCCESS);

    }

    /**
     * 更新机构状态
     *
     * @param tradeContext
     */
    private void updatePtrStat(TradeContext<?, ?> tradeContext) {
        logger.info("登录/退出交易，成功更新机构状态.");
        ECNYReqDTO<LoginInnerReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        //操作类型
        PartyInfoDO partyInfoDO = (PartyInfoDO) tradeContext.getTempCtx().get(KEY_LOGIN_PARTY_DO);
        partyInfoDO.setPartyID(AppConstant.CGB_FINANCIAL_INSTITUTION_CD);
        if (LoginOperationTpCdEnum.OT00.getCode().equals(reqMsg.getBody().getOpterationType())) {
            //登录操作,更新状态为已登录
            partyInfoDO.setPartyStatus(StatusTpCdEnum.ST02);
        } else if (LoginOperationTpCdEnum.OT01.getCode().equals(reqMsg.getBody().getOpterationType())) {
            //退出操作,更新状态为已退出
            partyInfoDO.setPartyStatus(StatusTpCdEnum.ST03);
        }
        partyInfoDO.setLastUpDate(DateUtil.getDefaultDate());

        partyInfoDO.setLastUpTime(DateUtil.getDefaultTime());
        partyInfoservice.updateParty(partyInfoDO);
    }

    /**
     * 异常处理
     *
     * @param tradeContext
     * @param exception
     */
    public void tradeErrHandler(TradeContext<?, ?> tradeContext, Throwable exception) {
        logger.warn("{}登录/退出交易异常处理.", LogMonitorLevelCdEnum.ECNY_LOG_MONITOR_WARNING, exception);

        //判断是否需要更新报文登记簿最终处理状态，如果在报文登记处理就失败，最终不需要处理报文登记簿处理状态
        if (tradeContext.getTempCtx().containsKey(KEY_PAY_TRANS_DTL_NONF)) {
            try {
                if (exception instanceof EcnyTransException) {
                    String errCode = ((EcnyTransException) exception).getErrorCode();
                    String errInfo = ((EcnyTransException) exception).getErrorMsg();
                    if (errInfo != null && errInfo.length() > 315) {
                        errInfo = errInfo.substring(0, 315);
                    }
                    updateTxn(tradeContext, AppConstant.TRXSTATUS_ABEND, ProcessStsCdEnum.PR01, errCode, errInfo);
                } else {
                    updateTxn(tradeContext, AppConstant.TRXSTATUS_ABEND,
                            ProcessStsCdEnum.PR01, EcnyTransError.OTHER_TECH_ERROR.getErrorCode(),
                            EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
                }
            } catch (Exception e) {
                logger.warn("更新非金融报文登记失败，原因：", e.getMessage(), e);
            }
        }

        packResponse(tradeContext, AppConstant.TRXSTATUS_ABEND, exception);
    }

    /**
     * 构建响应报文并放入上下文
     *
     * @param tradeContext 上下文
     * @param status       状态
     */
    private void packResponse(TradeContext<?, ?> tradeContext, String status) {
        packResponse(tradeContext, status, null);
    }

    /**
     * 构建响应报文并放入上下文
     *
     * @param tradeContext 上下文
     * @param status       状态
     */
    private void packResponse(TradeContext<?, ?> tradeContext, String status, Throwable exception) {
        logger.info("登录/退出交易组装响应报文.");
        //初始化响应报文
        LoginInnerRspDTO loginInnerRspDTO = new LoginInnerRspDTO();
        //原始请求报文
        ECNYReqDTO<LoginInnerReqDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);

        String procResult;

        switch (status) {
            case AppConstant.TRXSTATUS_SUCCESS:
                procResult = "登录/退出成功。";
                //成功
                break;
            case AppConstant.TRXSTATUS_FAILED:
                procResult = "登录/退出失败。";
                //失败
                break;
            case AppConstant.TRXSTATUS_ABEND:
                procResult = "登录/退出异常。";
                //异常
                break;

            case AppConstant.TRXSTATUS_INIT:
            default:
                //初始化,默认组默认包
                procResult = "登录/退出失败。";
        }
        if (null != exception) {
            logger.warn("登录异常，", exception);
            if (exception instanceof EcnyTransException) {
                procResult = "登录/退出异常:" + ((EcnyTransException) exception).getErrorMsg();
            } else {
                procResult = "登录/退出异常";
            }
        }
        //设置响应信息
        loginInnerRspDTO.setProcResult(procResult);

        //ECNY head 处理
        ECNYRspHead ecnyRspHead = new ECNYRspHead();
        ecnyRspHead.setTrxStatus(status);
        ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(reqMsg, ecnyRspHead, loginInnerRspDTO);

        //Head 错误码映射，默认使用ECNY000000 成功错误码，
        //判断异常类型，如果是自定义类型错误，获取异常类型中的错误码，否则取其他类型错误码ECNYC13003
        String retErr = Constant.SERVER_SUCC_RSPCODE;
       // String retInfo = ecnyRspDTO.getHead().getRetInfo();
        if (exception != null) {
            if (exception instanceof EcnyTransException) {
                retErr = ((EcnyTransException) exception).getErrorCode();
              //  retInfo = ((EcnyTransException) exception).getErrorMsg();
            } else {
                retErr = EcnyTransError.ECNY_OTHER_ERROR.getErrorCode();
             //   retInfo = EcnyTransError.ECNY_OTHER_ERROR.getErrorMsg();
            }
        }
//        ecnyRspDTO.getHead().setRetCode(retErr);
//        ecnyRspDTO.getHead().setRetInfo(retInfo);
        EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
    }

    /**
     * 更新登记簿
     */
    private void updateTxn(TradeContext<?, ?> tradeContext) {
        logger.info("登录/退出交易成功，设置业务状态.");
        //从上下文取出响应消息
        LoginRspDTO loginRspDTO = (LoginRspDTO) EcnyTradeContext.getTempContext(tradeContext).get(KEY_LOGIN_RSPDTO);
        //业务处理状态
        ProcessStsCdEnum processSts = loginRspDTO.getLoginRspn().getLoginRspnInf().getPrcSts();
        String rejectCode = loginRspDTO.getLoginRspn().getLoginRspnInf().getPrcCd();
        String rejectInfo = loginRspDTO.getLoginRspn().getLoginRspnInf().getRjctInf();
        //交易状态
        String tradeStatus = AppConstant.TRXSTATUS_FAILED;
        if (ProcessStsCdEnum.PR00.getCode().equals(processSts.getCode())) {
            tradeStatus = AppConstant.TRXSTATUS_SUCCESS;
        }
        updateTxn(tradeContext, tradeStatus, processSts, rejectCode, rejectInfo);
    }

    /**
     * 更新登记簿状态
     *
     * @param tradeStatus 交易状态
     */
    private void updateTxn(TradeContext<?, ?> tradeContext, String tradeStatus) {
        updateTxn(tradeContext, tradeStatus, null, null, null);
    }

    /**
     * 更新登记簿状态
     *
     * @param tradeStatus 交易状态
     * @param processSts  业务处理状态
     * @param rejectCode  业务处理码
     * @param rejectInfo  业务拒绝信息
     */
    private void updateTxn(TradeContext<?, ?> tradeContext, String tradeStatus, ProcessStsCdEnum processSts, String rejectCode, String rejectInfo) {
        logger.info("登录/退出交易更新登记簿.");

        PayTransDtlNonfDO payTransDtlNonfDO = (PayTransDtlNonfDO) tradeContext.getTempCtx().get(KEY_PAY_TRANS_DTL_NONF);
        //设置状态
        payTransDtlNonfDO.setTradeStatus(tradeStatus);
        if (null != processSts) {
            payTransDtlNonfDO.setProcStatus(processSts.getCode());
        } else {
            payTransDtlNonfDO.setProcStatus(ProcessStsCdEnum.PR01.getCode());
        }
        if (null != rejectCode) {
            payTransDtlNonfDO.setRejectCode(rejectCode);
        }
        if (null != rejectInfo) {
            payTransDtlNonfDO.setRejectInfo(rejectInfo);
        }
        //更新时间
        payTransDtlNonfDO.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlNonfDO.setLastUpTime(DateUtil.getDefaultTime());
        //更新入库
        payTransDtlNonfService.updatePayTransDtlNonf(payTransDtlNonfDO);
    }
}
