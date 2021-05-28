package com.dcits.dcwlt.pay.online.flow.send;

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
import com.dcits.dcwlt.pay.api.domain.dcep.cashboxWarning.BalWrngSetng;
import com.dcits.dcwlt.pay.api.domain.dcep.cashboxWarning.BalWrngSetngInf;
import com.dcits.dcwlt.pay.api.domain.dcep.cashboxWarning.CashboxWarningReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.Amt;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspHead;
import com.dcits.dcwlt.pay.api.domain.ecny.cashbox.EcnyCashboxRspDTO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.mapper.PayCashboxProcessMapper;
import com.dcits.dcwlt.pay.online.mapper.PayTransDtlNonfMapper;
import com.dcits.dcwlt.pay.online.service.IPayCashboxPartyService;
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
 * description 钱柜余额预警申请
 * @date 2021/1/5
 */
@Configuration
public class CashBoxWarning451STradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(CashBoxWarning451STradeFlow.class);

    public static final String CASHBOX_TRADE_FLOW = "CashBoxWarning451STradeFlow";

    private static final String DCEP_REQ_DTO = "dcepReqDTO";

    private static final String DCEP_RSP_DTO = "dcepRspDTO";

    private static final String RES_DESC_SUCCESS = "成功";

    private static final String RES_DESC_FAILED = "失败";

    private static final String SEND_DIRECT = "S";

    private static final String RES_STS_SUCCESS = "success";

    private static final String RES_STS_FAILED = "failed";

    private static final String INSERT_JRN_DO = "insertJrnDO";

    private static final String UPDATE_JRN_DO = "updateJrnDO";

    @Autowired
    private PayTransDtlNonfMapper payTransDtlNonfmapper;

    @Autowired
    private DcepSendService dcepSendService;

    @Autowired
    private IPayCashboxPartyService payCashboxPartyService;

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

    @Autowired
    private PayCashboxProcessMapper payCashboxProcessMapper;

    @Bean(name = CASHBOX_TRADE_FLOW)
    public TradeFlow cashboxTradeFlow() {
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
        // 默认返回失败
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
     * 初始化请求报文
     *
     * @param tradeContext 交易上下文
     */
    private void initReq(TradeContext<?, ?> tradeContext) {
        Map params = EcnyTradeContext.getReqMsg(tradeContext);

        //初始化请求报文
        BalWrngSetng balWrngSetng = new BalWrngSetng();
        BalWrngSetngInf balWrngSetngInf = new BalWrngSetngInf();

        //从params获取
        String wrngValCcy = (String) params.get("WrngValCcy");//预警值
        String wrngValValue = (String) params.get("WrngValValue");//预警值
        String cshBoxInstnId = (String) params.get("CshBoxInstnId");//钱柜所属运营机构
        //获取合作银行机构编码 todo
        //String coopBankInstnId = PayCommParamTask.getPayCommParamVal("data", "OWN_PARTY_ID");
        String coopBankInstnId = "";
        //获取合作银行钱柜ID
        //String coopBankWltId = PayCommParamTask.getPayCommParamVal("data", "OWN_CASHBOX_ID");
        String coopBankWltId = "";

        Amt amt = new Amt();
        amt.setCcy(wrngValCcy);
        amt.setValue(wrngValValue);
        balWrngSetngInf.setWrngVal(amt);
        balWrngSetngInf.setCshBoxInstnId(cshBoxInstnId);
        balWrngSetngInf.setCoopBankInstnId(coopBankInstnId);
        balWrngSetngInf.setCoopBankWltId(coopBankWltId);
        balWrngSetng.setBalWrngSetngInf(balWrngSetngInf);

        //业务组件
        GrpHdr grpHdr = GrpHdr.getInstance(generateCodeService.generateMsgId(OutOrgTypeEnum.OutOrg, MsgTpEnum.DCEP451.getCode().substring(5, 8), ""));
        balWrngSetng.setGrpHdr(grpHdr);
        //初始化ReqDTO
        CashboxWarningReqDTO cashboxWarningReqDTO = new CashboxWarningReqDTO();
        cashboxWarningReqDTO.setBalWrngSetng(balWrngSetng);
        //将请求报文放入到Context中
        DCEPReqDTO<CashboxWarningReqDTO> dcepReqDTO = DCEPReqDTO.newInstance(MsgTpEnum.DCEP451.getCode(), generateCodeService.generateMsgSN(grpHdr.getMsgId()), cashboxWarningReqDTO);
//        dcepReqDTO.getHead().setSrvcCode(ApiConstant.DCEP_OUT_SERVICE_NAME);
        EcnyTradeContext.getTempContext(tradeContext).put(DCEP_REQ_DTO, dcepReqDTO);
    }

    /**
     * 请求互联互通
     *
     * @param tradeContext 交易上下文
     */
    public void sendDCEP(TradeContext<?, ?> tradeContext) {
        Map<String, Object> contextMap = EcnyTradeContext.getTempContext(tradeContext);
        DCEPReqDTO<CashboxWarningReqDTO> dcepReqDTO = (DCEPReqDTO<CashboxWarningReqDTO>) contextMap.get(DCEP_REQ_DTO);
        JSONObject rspObj = null;
        try {
            logger.info("请求报文:{}", dcepReqDTO);
            rspObj = dcepSendService.sendDcep(dcepReqDTO);
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
            prcSts = jsonObject.getJSONObject("BODY").getJSONObject("CmonConf").getJSONObject("CmonConfInf").getString("PrcSts");
        } catch (Exception e) {
            logger.error("获取互联互通响应失败：{}，{}", EcnyTransError.GET_RSP_INFO_ERROR.getErrorCode(), EcnyTransError.GET_RSP_INFO_ERROR.getErrorMsg());
            throw new EcnyTransException(EcnyTransError.GET_RSP_INFO_ERROR);
        }
        ECNYRspDTO rspMsg = EcnyTradeContext.getRspMsg(tradeContext);
        EcnyCashboxRspDTO rspBody = (EcnyCashboxRspDTO) rspMsg.getBody();
        if (ProcessStsCdEnum.PR00.getCode().equals(prcSts)) {
            rspBody.setPrcDesc(RES_DESC_SUCCESS);
            rspBody.setPrcSts(RES_STS_SUCCESS);
        }
        PayTransDtlNonfDO msgUpdDO = (PayTransDtlNonfDO) tradeContext.getTempCtx().get(UPDATE_JRN_DO);
        msgUpdDO.setProcStatus(ProcessStsCdEnum.PR00.getCode());
        msgUpdDO.setTradeStatus(TrxStatusEnum.SUCCESS.getCode());
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
        EcnyCashboxRspDTO rspBody = (EcnyCashboxRspDTO) rspDTO.getBody();
        rspBody.setPrcSts(RES_STS_FAILED);
        rspBody.setPrcDesc(RES_DESC_FAILED);

        //设置错误码错误信息
        if (throwable instanceof EcnyTransException) {
            updatePayTransDtlNonfDO.setRejectCode(((EcnyTransException) throwable).getErrorCode());
            updatePayTransDtlNonfDO.setRejectInfo(((EcnyTransException) throwable).getErrorMsg());

        } else {
            updatePayTransDtlNonfDO.setRejectCode(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
            updatePayTransDtlNonfDO.setRejectInfo(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());

        }
        //设置交易状态为失败状态
        updatePayTransDtlNonfDO.setTradeStatus(TrxStatusEnum.FAIL.getCode());
        try {
            payTransDtlNonfmapper.updatePayTransDtlNonf(updatePayTransDtlNonfDO);
        } catch (Exception e) {
            logger.error("更新信息报文登记簿异常，{}", e);
        } finally {
            rspBody.setPrcSts(RES_STS_FAILED);
            rspBody.setPrcDesc(RES_DESC_FAILED);
        }
    }

    /**
     * 初始化待插入流水和待更新流水
     *
     * @param tradeContext 交易上下文
     */
    private void initJrn(TradeContext<?, ?> tradeContext) {
        Map<String, Object> contextMap = tradeContext.getTempCtx();
        DCEPReqDTO<CashboxWarningReqDTO> dcepReqDTO = (DCEPReqDTO<CashboxWarningReqDTO>) contextMap.get(DCEP_REQ_DTO);
        //初始化待插入流水
        PayTransDtlNonfDO insertPayTransDtlNonfDO = buildPayTransDtlNonfDO(dcepReqDTO);
        //在待插入流水中添加柜员号  todo
        insertPayTransDtlNonfDO.setTlrNo("000000");
        PayTransDtlNonfDO updatePayTransDtlNonfDO = new PayTransDtlNonfDO();
        BeanUtils.copyProperties(insertPayTransDtlNonfDO, updatePayTransDtlNonfDO);
        //将待插入流水和待更新流水保存到context中
        contextMap.put(INSERT_JRN_DO, insertPayTransDtlNonfDO);
        contextMap.put(UPDATE_JRN_DO, updatePayTransDtlNonfDO);
    }

    private PayTransDtlNonfDO buildPayTransDtlNonfDO(DCEPReqDTO<CashboxWarningReqDTO> dcepReqDTO) {
        CashboxWarningReqDTO cashboxWarningReqDTO = dcepReqDTO.getBody();
        PayTransDtlNonfDO payTransDtlNonfDO = new PayTransDtlNonfDO();
        String curDate = DateUtil.getDefaultDate();
        payTransDtlNonfDO.setPayDate(curDate);
        payTransDtlNonfDO.setPaySerNo(generateCodeService.generatePlatformFlowNo());
        payTransDtlNonfDO.setPayTime(DateUtil.getDefaultTime());
        payTransDtlNonfDO.setPkgNo(MsgTpEnum.DCEP121.getCode());
        payTransDtlNonfDO.setMsgId(cashboxWarningReqDTO.getBalWrngSetng().getGrpHdr().getMsgId());
        payTransDtlNonfDO.setSenderDateTime(DateUtil.getSysTime());
        payTransDtlNonfDO.setTradeStatus(TrxStatusEnum.PROCESSING.getCode());
        payTransDtlNonfDO.setDrct(SEND_DIRECT);
        payTransDtlNonfDO.setInstgDrctPty(AppConstant.BANK_FINANCIAL_INSTITUTION_CD);
        payTransDtlNonfDO.setInstdDrctPty(AppConstant.DCEP_FINANCIAL_INSTITUTION_CD);
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
            payTransDtlNonfmapper.insertPayTransDtlNonf(insertPayTransDtlNonfDO);
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
            payTransDtlNonfmapper.updatePayTransDtlNonf(updatePayTransDtlNonfDO);
        } catch (Exception e) {
            logger.error("更新信息登记簿异常：{}", e);
            throw e;
        }
    }

}
