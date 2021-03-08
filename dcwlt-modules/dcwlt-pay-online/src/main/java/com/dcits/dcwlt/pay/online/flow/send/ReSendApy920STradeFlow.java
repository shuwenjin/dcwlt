package com.dcits.dcwlt.pay.online.flow.send;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.common.pay.enums.TrxStatusEnum;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.type.OutOrgTypeEnum;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.resendapply.*;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspHead;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.service.ECNYSerNoService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlNonfRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Map;

/**
 * @author zhangwang
 * @version 1.0.0
 * description 重发申请
 * @date 2021/1/5
 */
@Configuration
public class ReSendApy920STradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(ReSendApy920STradeFlow.class);

    public static final String RESEND_APPLY_TRADE_FLOW = "ReSendApy920STradeFlow";

    private static final String DCEP_REQ_DTO = "dcepReqDTO";

    private static final String DCEP_RSP_DTO = "dcepRspDTO";

    private static final String SEND_DIRECT = "S";

    private static final String INSERT_JRN_DO = "insertJrnDO";

    private static final String UPDATE_JRN_DO = "updateJrnDO";


    private static final String RES_DESC_SUCCESS = "成功";

    private static final String RES_DESC_FAILED = "失败";

    private static final String RES_STS_SUCCESS = "success";

    private static final String RES_STS_FAILED = "failed";


    @Autowired
    private ECNYSerNoService ecnySerNoService;

//    @Autowired
//    private DcepSendService dcepSendService;

    @Autowired
    private IPayTransDtlNonfRepository payTransDtlNonfRepository;

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

    @Bean(name = RESEND_APPLY_TRADE_FLOW)
    public TradeFlow reSendApyTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .process(this::initRsp)                                                            //初始化响应报文
                .process(this::initReq)                                                            //初始化请求报文
                .process(this::initJrn)                                                            //初始化流水
                .saveTxn(this::insertJrnDO)                                                        //插入流水
                .process(this::sendDCEP)                                                           //向互联互通发送请求
                .response(this::packRspMsg)                                                        //响应保存封装
                .updateTxn(this::updateJrnDO)                                                      //更新交易流水
                .errHandler(this::errHandle)
                .build();

    }


    /**
     * 初始化响应报文
     *
     * @param tradeContext 交易上下文
     */
    private void initRsp(TradeContext<?, ?> tradeContext) {
        ECNYReqDTO<ReSendApyReqDTO> ecnyReqDTO = EcnyTradeContext.getReqMsg(tradeContext);
        // 默认返回失败
        ECNYRspHead head = new ECNYRspHead();
        head.setTrxStatus(AppConstant.TRXSTATUS_FAILED);
        ECNYRspDTO ecnyRspDTO = ECNYRspDTO.newInstance(ecnyReqDTO, head, null, EcnyTransError.OTHER_TECH_ERROR.getErrorCode(), EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
        ReSendApyRspDTO rspBody = new ReSendApyRspDTO();
        rspBody.setPrcDesc(RES_DESC_FAILED);
        rspBody.setPrcSts(RES_STS_FAILED);
        ecnyRspDTO.setBody(rspBody);
        EcnyTradeContext.setRspMsg(tradeContext, ecnyRspDTO);
    }

    /**
     * 初始化请求报文并登记流水
     *
     * @param tradeContext 交易上下文
     */
    private void initReq(TradeContext<?, ?> tradeContext) {
        ECNYReqDTO<ReSendApyReqDTO> reqDTO = EcnyTradeContext.getReqMsg(tradeContext);
        ReSendApyReqDTO reqBody = reqDTO.getBody();
        ApplMsg applMsg = new ApplMsg();
        //初始化请求报文
        ApplInf applInf = new ApplInf();
        applInf.setMt(reqBody.getMsgType());
        //报文描述信息
        String msgDesc = "";
        try{
            msgDesc = getMsgDesc(reqBody);
        }catch (ParseException e){
            logger.error("日期转化异常,请检查传入的日期格式");
            throw new EcnyTransException(EcnyTransError.DATE_FORMAT_ERROR);
        }
        applInf.setMsgDesc(msgDesc);
        applMsg.setApplInf(applInf);
        //业务组件
        GrpHdr grpHdr = GrpHdr.getInstance(generateCodeService.generateMsgId(OutOrgTypeEnum.OutOrg, MsgTpEnum.DCEP920.getCode().substring(5, 8), ""));
        applMsg.setApplInf(applInf);
        applMsg.setGrpHdr(grpHdr);
        //初始化ReqDTO
        EcnyReSendApyReqDTO ecnyReSendApyReqDTO = new EcnyReSendApyReqDTO();
        ecnyReSendApyReqDTO.setApplMsg(applMsg);
        //将请求报文放入到Context中
        DCEPReqDTO<EcnyReSendApyReqDTO> dcepReqDTO = DCEPReqDTO.newInstance(MsgTpEnum.DCEP920.getCode(), generateCodeService.generateMsgSN(grpHdr.getMsgId()), ecnyReSendApyReqDTO);
//        dcepReqDTO.getHead().setSrvcCode(ApiConstant.DCEP_OUT_SERVICE_NAME);
        EcnyTradeContext.getTempContext(tradeContext).put(DCEP_REQ_DTO, dcepReqDTO);
    }
    /**
     * 获取MsgDesc
     *
     * @param reqBody 重发申请请求报文
     * @return msgDesc
     */
    public String getMsgDesc(ReSendApyReqDTO reqBody) throws ParseException {
        String msgTp = reqBody.getMsgType();
        if (MsgTpEnum.DCEP711.getCode().equals(msgTp)) {
            if (StringUtils.isEmpty(reqBody.getBatchId()) && StringUtils.isEmpty(reqBody.getCheckDate())) {
                logger.error("当报文编号为{} ：{}，对账日期和交易批次号不能同时为空", MsgTpEnum.DCEP711.getCode(), MsgTpEnum.DCEP711.getDesc());
                throw new EcnyTransException(EcnyTransError.BATCH_ID_AND_CHECK_DATE_EMPTY);
            }
            String batchId = reqBody.getBatchId();
            return StringUtil.isBlank(batchId) ? DateUtil.formatDateStr(reqBody.getCheckDate()) : batchId;
        }else if(MsgTpEnum.DCEP713.getCode().equals(msgTp)){
            if(StringUtils.isEmpty(reqBody.getClearDate())){
                logger.error("当报文编号为{} ：{}，清算日期不能为空", MsgTpEnum.DCEP713.getCode(), MsgTpEnum.DCEP713.getDesc());
                throw new EcnyTransException(EcnyTransError.CLEAR_DATE_EMPTY);
            }
            return DateUtil.formatDateStr(reqBody.getClearDate());
        }
        return "";
    }


    /**
     * 初始化待插入流水和待更新流水
     *
     * @param tradeContext 交易上下文
     */
    private void initJrn(TradeContext<?, ?> tradeContext) {
        Map<String, Object> contextMap = tradeContext.getTempCtx();
        DCEPReqDTO<EcnyReSendApyReqDTO> dcepReqDTO = (DCEPReqDTO<EcnyReSendApyReqDTO>) contextMap.get(DCEP_REQ_DTO);
        //初始化待插入流水
        PayTransDtlNonfDO insertPayTransDtlNonfDO = buildPayTransDtlNonfDO(dcepReqDTO);
        //在待插入流水中添加柜员号
        ECNYReqDTO<ReSendApyReqDTO> reqDTO = EcnyTradeContext.getReqMsg(tradeContext);
        insertPayTransDtlNonfDO.setTlrNo(reqDTO.getEcnyHead().getTellerno());
        PayTransDtlNonfDO updatePayTransDtlNonfDO = new PayTransDtlNonfDO();
        BeanUtils.copyProperties(insertPayTransDtlNonfDO, updatePayTransDtlNonfDO);
        //将待插入流水和待更新流水保存到context中
        contextMap.put(INSERT_JRN_DO, insertPayTransDtlNonfDO);
        contextMap.put(UPDATE_JRN_DO, updatePayTransDtlNonfDO);
    }


    private PayTransDtlNonfDO buildPayTransDtlNonfDO(DCEPReqDTO<EcnyReSendApyReqDTO> dcepReqDTO) {
        EcnyReSendApyReqDTO ecnyReSendApyReqDTO = dcepReqDTO.getBody();
        PayTransDtlNonfDO payTransDtlNonfDO = new PayTransDtlNonfDO();
        String curDate = DateUtil.getDefaultDate();
        payTransDtlNonfDO.setPayDate(curDate);
        payTransDtlNonfDO.setPaySerNo(generateCodeService.generatePlatformFlowNo());
        payTransDtlNonfDO.setPayTime(DateUtil.getCurTime());
        payTransDtlNonfDO.setPkgNo(ecnyReSendApyReqDTO.getApplMsg().getApplInf().getMt());
        payTransDtlNonfDO.setMsgId(ecnyReSendApyReqDTO.getApplMsg().getGrpHdr().getMsgId());
        payTransDtlNonfDO.setSenderDateTime(DateUtil.getSysTime());
        payTransDtlNonfDO.setTradeStatus(TrxStatusEnum.PROCESSING.getCode());
        payTransDtlNonfDO.setDrct(SEND_DIRECT);
        payTransDtlNonfDO.setInstgDrctPty(AppConstant.CGB_FINANCIAL_INSTITUTION_CD);
        payTransDtlNonfDO.setInstdDrctPty(AppConstant.NET_PARTY_ID);
        payTransDtlNonfDO.setPkgNo(MsgTpEnum.DCEP920.getCode());
        //初始化时设置状态为处理中，在后续的处理过程中更新状态
        payTransDtlNonfDO.setProcStatus(ProcessStsCdEnum.PR02.getCode());
        payTransDtlNonfDO.setLastUpDate(DateUtil.getDefaultDate());
        payTransDtlNonfDO.setLastUpTime(DateUtil.getDefaultTime());
        return payTransDtlNonfDO;
    }


    /**
     * 插入信息报文登记簿
     *
     * @param tradeContext 交易上下文
     */
    private void insertJrnDO(TradeContext<?, ?> tradeContext) {
        PayTransDtlNonfDO insertPayTransDtlNonfDO = (PayTransDtlNonfDO) tradeContext.getTempCtx().get(INSERT_JRN_DO);
        try {
            payTransDtlNonfRepository.addPayTransDtlNonf(insertPayTransDtlNonfDO);
        } catch (Exception e) {
            logger.error("插入信息登记簿异常：{}", e);
            throw e;
        }
    }

    /**
     * 更新信息报文登记簿
     *
     * @param tradeContext 交易上下文
     */
    private void updateJrnDO(TradeContext<?, ?> tradeContext) {
        PayTransDtlNonfDO updatePayTransDtlNonfDO = (PayTransDtlNonfDO) tradeContext.getTempCtx().get(UPDATE_JRN_DO);
        try {
            payTransDtlNonfRepository.updatePayTransDtlNonf(updatePayTransDtlNonfDO);
        } catch (Exception e) {
            logger.error("更新信息登记簿异常：{}", e);
            throw e;
        }
    }


    /**
     * 请求互联互通
     *
     * @param tradeContext 交易上下文
     */
    public void sendDCEP(TradeContext<?, ?> tradeContext) {
        Map<String, Object> contextMap = EcnyTradeContext.getTempContext(tradeContext);
        DCEPReqDTO<EcnyReSendApyReqDTO> dcepReqDTO = (DCEPReqDTO<EcnyReSendApyReqDTO>) contextMap.get(DCEP_REQ_DTO);
        JSONObject rspObj = null;
        try {
            logger.info("请求报文:{}", dcepReqDTO);
//            rspObj = dcepSendService.sendDcep(dcepReqDTO);
            rspObj = new JSONObject();
        } catch (Exception e) {
            logger.error("发送重发申请到互联互通请求失败：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.OTHER_TECH_ERROR);
        }
        EcnyTradeContext.getTempContext(tradeContext).put(DCEP_RSP_DTO, rspObj);
    }

    /**
     * 拼接响应包
     *
     * @param tradeContext 交易上下文
     */
    private void packRspMsg(TradeContext<?, ?> tradeContext) {
        JSONObject jsonObject = (JSONObject) EcnyTradeContext.getTempContext(tradeContext).get(DCEP_RSP_DTO);
        String prcSts = "";
        try {
            prcSts = jsonObject.getJSONObject("body").getJSONObject("CmonConf").getJSONObject("CmonConfInf").getString("PrcSts");
        } catch (Exception e) {
            logger.error("获取互联互通响应失败：{}，{}", EcnyTransError.GET_RSP_INFO_ERROR.getErrorCode(), EcnyTransError.GET_RSP_INFO_ERROR.getErrorMsg());
            throw new EcnyTransException(EcnyTransError.GET_RSP_INFO_ERROR);
        }
        ECNYRspDTO rspMsg = EcnyTradeContext.getRspMsg(tradeContext);
        ReSendApyRspDTO rspBody = (ReSendApyRspDTO) rspMsg.getBody();
        if (ProcessStsCdEnum.PR00.getCode().equals(prcSts)) {
            rspBody.setPrcDesc(RES_DESC_SUCCESS);
            rspBody.setPrcSts(RES_STS_SUCCESS);
        }
        PayTransDtlNonfDO msgUpdDO = (PayTransDtlNonfDO) tradeContext.getTempCtx().get(UPDATE_JRN_DO);
        msgUpdDO.setProcStatus(ProcessStsCdEnum.PR00.getCode());
        msgUpdDO.setTradeStatus(TrxStatusEnum.SUCCESS.getCode());
//        Head head = rspMsg.getHead();
//        head.setRetCode(EcnyTransError.SUCCESS.getErrorCode());
//        head.setRetInfo(EcnyTransError.SUCCESS.getErrorMsg());
    }


    /**
     * 异常处理
     *
     * @param tradeContext 交易上下文
     * @param throwable    异常处理器
     */
    private void errHandle(TradeContext<?, ?> tradeContext, Throwable throwable) {
        PayTransDtlNonfDO updatePayTransDtlNonfDO = (PayTransDtlNonfDO) tradeContext.getTempCtx().get(UPDATE_JRN_DO);
        updatePayTransDtlNonfDO.setProcStatus(ProcessStsCdEnum.PR01.getCode());
        ECNYRspDTO rspDTO = EcnyTradeContext.getRspMsg(tradeContext);
        //响应体信息
        ReSendApyRspDTO rspBody = (ReSendApyRspDTO) rspDTO.getBody();
        rspBody.setPrcSts(RES_STS_FAILED);
        rspBody.setPrcDesc(RES_DESC_FAILED);

        ECNYRspDTO rspMsg = EcnyTradeContext.getRspMsg(tradeContext);
//        Head head = rspMsg.getHead();

        //设置错误码错误信息
        if (throwable instanceof EcnyTransException) {
            updatePayTransDtlNonfDO.setRejectCode(((EcnyTransException) throwable).getErrorCode());
            updatePayTransDtlNonfDO.setRejectInfo(((EcnyTransException) throwable).getErrorMsg());
//            head.setRetCode(((EcnyTransException) throwable).getErrorMsg());
//            head.setRetInfo(((EcnyTransException) throwable).getErrorMsg());
        } else {
            updatePayTransDtlNonfDO.setRejectCode(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
            updatePayTransDtlNonfDO.setRejectInfo(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
//            head.setRetCode(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
//            head.setRetInfo(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
        }
        //设置交易状态为失败状态
        updatePayTransDtlNonfDO.setTradeStatus(TrxStatusEnum.FAIL.getCode());
        try {
            payTransDtlNonfRepository.updatePayTransDtlNonf(updatePayTransDtlNonfDO);
        } catch (Exception e) {
            logger.error("更新信息报文登记簿异常，{}", e);
        } finally {
            rspBody.setPrcSts(RES_STS_FAILED);
            rspBody.setPrcDesc(RES_DESC_FAILED);
        }
    }

}
