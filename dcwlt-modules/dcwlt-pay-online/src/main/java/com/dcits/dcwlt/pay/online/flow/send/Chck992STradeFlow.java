package com.dcits.dcwlt.pay.online.flow.send;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.RspnCdEnum;
import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.type.OutOrgTypeEnum;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.chckReq.ChckReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.chckRspn.ChckRspnDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.Fault;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.FaultDTO;
import com.dcits.dcwlt.pay.api.model.ChckDO;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.mapper.ChckMapper;
import com.dcits.dcwlt.pay.online.service.impl.DcepSendService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * HTTP通信级探测报文
 */
@Configuration
public class Chck992STradeFlow {
    private static final Logger logger = LoggerFactory.getLogger(Chck992STradeFlow.class);

    public static final String CHCK_TRADE_FLOW = "Chck992STradeFlow";

    @Autowired
    private GenerateCodeServiceImpl generateCodeService;

    @Autowired
    private DcepSendService dcepSendService;

    @Autowired
    private ChckMapper chckMapper;


    //报文标识号
    private String msgId;

    @Bean(name = CHCK_TRADE_FLOW)
    public TradeFlow dsptTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .process(this::initTxn)                         //初始化请求报文
                .process(this::sendDcep)                    // 发送991到城银清
                .process(this::sendDcepDone)                 // 报文处理
                .response(this::response)                   // 响应报文
                .errHandler(this::errHandler)               // 异常处理
                .build();
    }

    /*
     * 初始化请求报文
     * */
    public void initTxn(TradeContext<?, ?> context) {
        //初始化报文标识号
        msgId = generateCodeService.generateMsgId(OutOrgTypeEnum.InnerOrg, MsgTpEnum.OMSS991.getCode().substring(5, 8), "");
        String msgSn = generateCodeService.generateMsgSN(msgId);

        String requestNodeName = EcnyTradeContext.getReqMsg(context);
        ChckReqDTO chckReqDTO = ChckReqDTO.newInstance(requestNodeName);

        // 拼装请求报文
        DCEPReqDTO<ChckReqDTO> dcepReqDTO =
                DCEPReqDTO.newInstance(MsgTpEnum.OMSS991.getCode(), msgSn, AppConstant.DCPS_FINANCIAL_INSTITUTION_CD, chckReqDTO);
        EcnyTradeContext.getTempContext(context).put("dcep_req", dcepReqDTO);
    }

    /**
     * 发送到城银清
     */
    public void sendDcep(TradeContext<?, ?> tradeContext) {
        DCEPReqDTO<ChckReqDTO> dsptReqDTODCEPReqDTO = (DCEPReqDTO<ChckReqDTO>) EcnyTradeContext.
                getTempContext(tradeContext).get("dcep_req");
        try {
            JSONObject jsonObject = dcepSendService.sendDcep(dsptReqDTODCEPReqDTO);
            EcnyTradeContext.getTempContext(tradeContext).put("dcps_resp", jsonObject);
        } catch (Exception e) {
            logger.error("发送992到城银清请求失败：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.PAY_TIME_ERROR);
        }
    }

    /**
     * 接收报文处理
     */
    public void sendDcepDone(TradeContext<?, ?> tradeContext) {
        JSONObject dcps_resp = (JSONObject)
                EcnyTradeContext.getTempContext(tradeContext).get("dcps_resp");
        JSONObject jsonObject = dcps_resp.getJSONObject("Header");
        //平台响应是否成功
        if (Objects.isNull(jsonObject)) {
            //初始化异常内容
            logger.error("922响应报文失败");
            throw new EcnyTransException(EcnyTransError.RESPOSE_ERROR);
        }
        if (StringUtils.equals(jsonObject.getString("MesgType"), MsgTpEnum.DCEP911.getCode())) {
            // 响应911失败报文
            DCEPRspDTO<FaultDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(dcps_resp, FaultDTO.class);
            FaultDTO faultDTO = dcepRspDTO.getBody();
            Fault fault = faultDTO.getFault();
            //详细错误信息
            String detail = fault.getDetail();
            //故障代码
            String faultCode = fault.getFaultCode();
            //业务故障说明
            String faultString = fault.getFaultString();
            logger.error("互联互通响应为丢弃报文,错误信息为:{}", faultCode + ":" + faultString + ":" + detail);
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
        }
        // 接收992报文
        DCEPRspDTO<ChckRspnDTO> dcepRspDTO = DCEPRspDTO.jsonToDCEPRspDTO(dcps_resp, ChckRspnDTO.class);
        try {
            ChckDO chckDO = new ChckDO();
            chckDO.setRspnCd(dcepRspDTO.getBody().getChckRspn().getChckRspnInf().getRspnCd());
            chckDO.setRspnDt(dcepRspDTO.getBody().getChckRspn().getChckRspnInf().getRspnDt());
            chckDO.setRspnNdNm(dcepRspDTO.getBody().getChckRspn().getChckRspnInf().getRspnNdNm());
            chckMapper.insert(chckDO);
            EcnyTradeContext.getTempContext(tradeContext).put("resp", dcepRspDTO);
        } catch (Exception e) {
            logger.error("插入数据库失败");
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    public void response(TradeContext<?, ?> tradeContext) {
        DCEPRspDTO<ChckRspnDTO> dcepRspDTO = (DCEPRspDTO<ChckRspnDTO>)
                EcnyTradeContext.getTempContext(tradeContext).get("resp");
        // 获取响应结果
        String rspnCd = dcepRspDTO.getBody().getChckRspn().getChckRspnInf().getRspnCd();
        // 设置响应信息
        String rspn = RspnCdEnum.getValue(rspnCd);
        EcnyTradeContext.setRspMsg(tradeContext, rspn);
    }

    /**
     * 异常处理
     */
    public void errHandler(TradeContext<?, ?> tradeContext, Throwable exception) {
        logger.error("进入异常处理模块！");
        if (exception instanceof EcnyTransException) {
            EcnyTradeContext.setRspMsg(tradeContext, ((EcnyTransException) exception).getErrorMsg());
        }
    }
}
