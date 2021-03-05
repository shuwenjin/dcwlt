package com.dcits.dcwlt.pay.online.flow;

import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspHead;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.TradeFlowRuner;
import com.dcits.dcwlt.pay.online.service.TransInService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhanguohai
 * @Time 2021/1/5 16:51
 * @Version 1.0
 * Description:对外行内渠道，公共处理
 */
@Service
public class EcnyTransInTradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(DcepTransInTradeFlow.class);

    @Autowired
    private TransInService transInService;

    @Autowired
    private TradeFlowRuner tradeFlowRuner;

    /**
     * 交易公共入口
     *
     * @param reqDTO
     * @param processor
     * @return
     */
    public ECNYRspDTO execute(ECNYReqDTO reqDTO, String processor) {
        logger.info("请求报文：{}", reqDTO);

        try {
            //报文合法性检查
            transInService.checkMessage(reqDTO);

            //交易处理
            ECNYRspDTO<?> rspMsg = tradeFlowRuner.execute(processor, EcnyTradeContext.getInstance(reqDTO));

            //响应处理
            logger.info("响应报文：{}", rspMsg);
            return rspMsg;
        } catch (Exception e) {
            return dealException(reqDTO, e);
        }
    }

    /**
     * 交易异常处理
     *
     * @param reqDTO
     * @param e
     * @return
     */
    private ECNYRspDTO dealException(ECNYReqDTO reqDTO, Exception e) {
        Throwable cause = e.getCause();
        if (null == cause) {
            cause = e;
        }
        ECNYRspHead ecnyRspHead = new ECNYRspHead();
        String trxStatus = AppConstant.TRXSTATUS_ABEND;
        String retCode = EcnyTransError.OTHER_TECH_ERROR.getErrorCode();
        String retInfo = EcnyTransError.OTHER_TECH_ERROR.getErrorMsg();
        if (cause instanceof EcnyTransException) {
            if (StringUtils.isNotBlank(((EcnyTransException) cause).getStatus())) {
                trxStatus = ((EcnyTransException) cause).getStatus();
            }
            retCode = ((EcnyTransException) cause).getErrorCode();
            retInfo = ((EcnyTransException) cause).getErrorMsg();
        }
        ecnyRspHead.setTrxStatus(trxStatus);
        return ECNYRspDTO.newInstance(reqDTO, ecnyRspHead, null, retCode, retInfo);
    }

}