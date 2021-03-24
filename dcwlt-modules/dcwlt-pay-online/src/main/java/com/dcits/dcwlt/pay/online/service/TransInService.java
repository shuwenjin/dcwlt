package com.dcits.dcwlt.pay.online.service;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.util.APPUtil;
import com.dcits.dcwlt.common.pay.util.IOCheckUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPHeader;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.model.IdempotentDO;
import com.dcits.dcwlt.pay.online.config.EcnyTradeConfig;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @Time 2021/1/11 14:32
 * @Version 1.0
 * Description:交易入口公共服务
 */
@Service
public class TransInService {

    private static final Logger logger = LoggerFactory.getLogger(TransInService.class);

    @Autowired(required = false)
    private EcnyTradeConfig ecnyTradeConfig;


    /**
     * 记录请求信息，堵重使用
     *
     * @param dcepHead
     */
    public void saveMsg(DCEPHeader dcepHead, String direct) {
        try {
            IdempotentDO idempotentDO = new IdempotentDO();
            idempotentDO.setDirect(direct);
            idempotentDO.setHostname(APPUtil.getLocalHostName());
            idempotentDO.setMsgId(dcepHead.getMsgSn().substring(0, 32));
            idempotentDO.setMsgTp(dcepHead.getMsgTp());
            idempotentDO.setReceiver(dcepHead.getReceiver());
            idempotentDO.setSender(dcepHead.getSender());
            idempotentDO.setSndDtTm(dcepHead.getSndDtTm());
//            idempotentRepository.insert(idempotentDO);
        } catch (DuplicateKeyException e) {
            //不进行堵重时，返回false，即不进行堵重，调出方法
            boolean idempotent = allowIdempotent(dcepHead.getMsgTp());
            if (!idempotent) {
                logger.error("【{}】交易重复，通讯级标识号为：{}，该交易配置不堵重", dcepHead.getMsgTp(), dcepHead.getMsgSn());
                return;
            }
            logger.error("【{}】交易重复，通讯级标识号为：{}", dcepHead.getMsgTp(), dcepHead.getMsgSn());
            throw new EcnyTransException(EcnyTransError.DUPLICATE_KEY_ERROR);
        }
    }


    /**
     * json对象转实体
     *
     * @param reqMsg 请求报文
     * @return
     */
    public DCEPReqDTO<?> jsonToDCEPReqDTO(JSONObject reqMsg) {
        System.out.println("reqMsg: " + reqMsg.toJSONString());
        DCEPHeader dcepHeader = JSONObject.toJavaObject(reqMsg.getJSONObject(AppConstant.DCEP_HEAD), DCEPHeader.class);    //互联互通报文头json对象-->DCEPHeader实体
        Class<?> clazz = getClassName(dcepHeader.getMsgTp());
        DCEPReqBody body = (DCEPReqBody) JSONObject.toJavaObject(reqMsg.getJSONObject(AppConstant.DCEP_BODY), clazz);          //互联互通报文体json对象-->DCEPReqBody实体
        return DCEPReqDTO.newInstance(dcepHeader, body);
    }

    /**
     * 检查入参
     */
    public void checkMessage(Object... msg) {
        //校验报文信息
        try {
            IOCheckUtil.verify(msg);
        } catch (Exception e) {
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, AppConstant.ECNY_SYS_ID, EcnyTransError.INPARAMS_INVALID.getErrorCode(), e.getMessage());
        }
    }

    /**
     * 是否允许堵重
     *
     * @param msgTp
     * @return
     */
    private boolean allowIdempotent(String msgTp) {
        try {
            String idempotentFlag = ecnyTradeConfig.getTradeMappings().get(msgTp).get(EcnyTradeConfig.TRADE_IDEMPOTENT_CODE);
            if (idempotentFlag.equals("N")) {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
        return true;
    }

    /**
     * 获取类名
     *
     * @param msgTp
     * @return
     */
    private Class<?> getClassName(String msgTp) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(ecnyTradeConfig.getTradeMappings().get(msgTp).get(EcnyTradeConfig.TRADE_CLASS_NAME));
        } catch (Exception e) {
            logger.error("未配置交易请求报文类名，请检查");
            throw new EcnyTransException(EcnyTransError.PARAM_NOT_INIT_ERROR);
        }
        return clazz;
    }

    /**
     * 获取交易处理器
     *
     * @param msgTp
     * @return
     */
    public String getTradeProcessor(String msgTp) {
        try {
            return ecnyTradeConfig.getTradeMappings().get(msgTp).get(EcnyTradeConfig.TRADE_PROCESSOR_NAME);
        } catch (Exception e) {
            logger.error("未配置交易处理器，请检查");
            throw new EcnyTransException(EcnyTransError.PARAM_NOT_INIT_ERROR);
        }
    }
}
