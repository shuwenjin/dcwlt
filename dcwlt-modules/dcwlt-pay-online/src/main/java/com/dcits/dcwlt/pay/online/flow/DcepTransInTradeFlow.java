package com.dcits.dcwlt.pay.online.flow;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlowRuner;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPHeader;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.FaultDTO;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.service.TransInService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Time 2020/12/31 15:09
 * @Version 1.0
 * Description:对外接入互联互通报文，公共处理
 */
@Service
public class DcepTransInTradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(DcepTransInTradeFlow.class);

    @Autowired
    private TransInService transInService;

    /**
     * 交易公共入口
     *
     * @param reqMsg
     * @return
     */
    public JSONObject execute(JSONObject reqMsg) {
        DCEPReqDTO<?> dcepReqDTO = transInService.jsonToDCEPReqDTO(reqMsg);
        logger.info("请求报文：{}", dcepReqDTO);
        try {
            //1、报文合法性检查
            transInService.checkMessage(dcepReqDTO);

            //2、交易堵重
            transInService.saveMsg(dcepReqDTO.getDcepHead(), AppConstant.DIRECT_RECV);

            //2、获取交易类型
            DCEPHeader dcepHeader = dcepReqDTO.getDcepHead();
            String msgTp = dcepHeader.getMsgTp();

            // 3.交易处理
            String processor = transInService.getTradeProcessor(msgTp);
            DCEPRspDTO<?> rspMsg = TradeFlowRuner.execute(processor, EcnyTradeContext.getInstance(dcepReqDTO));

            //4、响应报文
            logger.info("响应报文：{}", rspMsg);
            return (JSONObject) JSONObject.toJSON(rspMsg);
        } catch (Exception e) {
            logger.error("交易处理异常：{}", e.getMessage(), e);
            //仅处理公共报文检查的异常及业务重复异常
            return dealException(dcepReqDTO, e);
        }
    }

    /**
     * 仅处理公共报文检查的异常及业务重复异常
     *
     * @param dcepReqDTO
     * @param e
     * @return
     */
    private JSONObject dealException(DCEPReqDTO<?> dcepReqDTO, Exception e) {
        Throwable cause = e.getCause();
        if (null == cause) {
            cause = e;
        }
        DCEPRspDTO<FaultDTO> dcepRspDTO = null;
//        if (cause instanceof EcnyTransException) {
//            if (StringUtil.equalsAny(((EcnyTransException) cause).getErrorCode(), EcnyTransError.INPARAMS_INVALID.getErrorCode(), EcnyTransError.DUPLICATE_KEY_ERROR.getErrorCode())) {
//                RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode((EcnyTransException) cause);
//                FaultDTO faultDTO = new FaultDTO();
//                Fault fault = new Fault();
//                fault.setFaultCode(rspCodeMapDO.getDestRspCode());
//                fault.setFaultActor(dcepReqDTO.getDcepHead().getSender());
//                fault.setFaultString(rspCodeMapDO.getRspCodeDsp());
//                fault.setDetail(rspCodeMapDO.getRspCodeDsp());
//                faultDTO.setFault(fault);
//                dcepRspDTO = DCEPRspDTO.newInstance(dcepReqDTO, MsgTpEnum.DCEP911.getCode(), faultDTO);
//            }
//        }
        return (JSONObject) JSONObject.toJSON(dcepRspDTO);
    }

}
