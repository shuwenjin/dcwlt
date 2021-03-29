package com.dcits.dcwlt.pay.online.event.service;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.utils.SpringUtils;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealRspMsg;
import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.constant.EventConst;
import com.dcits.dcwlt.common.pay.exception.PlatformError;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.online.event.callback.DisputeCoreQryCallBack;
import com.dcits.dcwlt.pay.online.service.IEventService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import com.dcits.dcwlt.pay.online.service.impl.BankCoreAccTxnServiceImpl;
import com.dcits.dcwlt.pay.online.baffle.core.impl.BankCoreImplDubboServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;


/**
 * @author zhanguohai
 * @Time 2020/11/4 18:38
 * @Version 1.0
 * Description:
 */
@Service
public class CoreQryService implements IEventService {
    private static final Logger logger = LoggerFactory.getLogger(CoreQryService.class);

    @Autowired
    BankCoreImplDubboServiceImpl bankCoreImplDubboServiceImpl;

    @Autowired
    private BankCoreAccTxnServiceImpl bankCoreAccTxnServiceImpl;

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoService;

    //@ParamLog
    @Override
    public EventDealRspMsg runFlow(EventDealReqMsg eventDealReqMsg) {
        //1、获取异常事件配置
        String callBackClassName = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("callBackCanonicalName");
        String oriCoreReqDate = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("coreReqDate");
        String oriCoreReqSerno = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("coreReqSerno");

        String payDate = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("payDate");
        String paySerno = JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()).getString("paySerno");

        //2、初始化返回信息
        EventDealRspMsg eventDealRspMsg = initEventRspMsg(eventDealReqMsg);

        //3、判断原交易核心是否异常
        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoService.query(payDate,paySerno);
        if(!Constant.CORESTATUS_ABEND.equals(payTransDtlInfoDO.getCoreProcStatus())){
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
            eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
            eventDealRspMsg.setRespMsg("核心状态非异常,无需回查");
            return eventDealRspMsg;
        }

        //4、上核心回查
        BankCore996666Rsp bankCore996666Rsp = bankCoreImplDubboServiceImpl.queryCoreStatus(oriCoreReqDate, oriCoreReqSerno);

        //5.执行回调程序
        eventDealRspMsg = runCallBack(eventDealRspMsg, bankCore996666Rsp, callBackClassName, JSONObject.parseObject(eventDealReqMsg.getExceptEventContext()));

        //拼包响应
        return packEventRspMsg(eventDealRspMsg, bankCore996666Rsp);
    }

    /**
     * 回查回调事件
     * @param eventDealRspMsg
     * @param bankCore996666Rsp
     * @param callBackClassName
     * @param eventParam
     * @return
     */
    private EventDealRspMsg runCallBack(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp, String callBackClassName, JSONObject eventParam) {
        try {
            //应用不存在回调处理
            if (StringUtil.isBlank(callBackClassName)) {
                return eventDealRspMsg;
            }
            //存在回调处理，调用应用的冲正回调
            String coreProcStatus = bankCoreAccTxnServiceImpl.getCoreStatusMap(bankCore996666Rsp.getTxnSts());
            Object bean = SpringUtils.getBean(callBackClassName.substring(callBackClassName.lastIndexOf(".")+1));
            if (Constant.CORESTATUS_SUCCESS.equals(coreProcStatus)) {
                eventDealRspMsg.setRespCode(Constant.CORESTATUS_SUCCESS);
                Method coreSucc = bean.getClass().getDeclaredMethod("coreSucc", EventDealRspMsg.class, BankCore996666Rsp.class, JSONObject.class);
                return (EventDealRspMsg)coreSucc.invoke(bean,eventDealRspMsg, bankCore996666Rsp, eventParam);
            } else if ("TC1003".equals(bankCore996666Rsp.getCoreRetCode())) {
                eventDealRspMsg.setRespCode(Constant.CORESTATUS_REVERSED);
                Method coreSucc = bean.getClass().getDeclaredMethod("coreReversed", EventDealRspMsg.class, BankCore996666Rsp.class, JSONObject.class);
                return (EventDealRspMsg)coreSucc.invoke(bean,eventDealRspMsg, bankCore996666Rsp, eventParam);
            } else if (Constant.CORESTATUS_ABEND.equals(coreProcStatus)) {
                eventDealRspMsg.setRespCode(Constant.CORESTATUS_ABEND);
                Method coreSucc = bean.getClass().getDeclaredMethod("coreAbend", EventDealRspMsg.class, BankCore996666Rsp.class, JSONObject.class);
                return (EventDealRspMsg)coreSucc.invoke(bean,eventDealRspMsg, bankCore996666Rsp, eventParam);
            } else if (Constant.CORESTATUS_FAILED.equals(coreProcStatus)) {
                eventDealRspMsg.setRespCode(Constant.CORESTATUS_FAILED);
                Method coreSucc = bean.getClass().getDeclaredMethod("coreFail", EventDealRspMsg.class, BankCore996666Rsp.class, JSONObject.class);
                return (EventDealRspMsg)coreSucc.invoke(bean,eventDealRspMsg, bankCore996666Rsp, eventParam);
            } else {
                eventDealRspMsg.setRespCode(PlatformError.OTHER_BUSI_ERROR.getErrorCode());
            }
            eventDealRspMsg.setRespMsg(bankCore996666Rsp.getCoreRetMsg());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (e instanceof Exception) {
                Exception error =  e;
                eventDealRspMsg.setRespCode(error.getLocalizedMessage());
                eventDealRspMsg.setRespMsg(error.getMessage());
            } else {
                eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
                eventDealRspMsg.setRespMsg(e.getMessage());
            }
        }
        return eventDealRspMsg;
    }

    /**
     * 异常事件拼包响应
     *
     * @param eventDealRspMsg
     * @param bankCore996666Rsp
     * @return
     */
    private EventDealRspMsg packEventRspMsg(EventDealRspMsg eventDealRspMsg, BankCore996666Rsp bankCore996666Rsp) {
        String coreProcStatus = bankCoreAccTxnServiceImpl.getCoreStatusMap(bankCore996666Rsp.getTxnSts());
        //终态不进行回查
        if (Constant.CORESTATUS_SUCCESS.equals(coreProcStatus)) {
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
            eventDealRspMsg.setRespCode(PlatformError.SUCCESS.getErrorCode());
            eventDealRspMsg.setRespMsg(PlatformError.SUCCESS.getErrorMsg());
        } else if (Constant.CORESTATUS_FAILED.equals(coreProcStatus)) {
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
            eventDealRspMsg.setRespCode(PlatformError.SYSTEM_ERROR.getErrorCode());
            eventDealRspMsg.setRespMsg("回查结果，交易状态为失败");
        } else {
            eventDealRspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_Y);
            eventDealRspMsg.setRespCode(bankCore996666Rsp.getCoreRetCode());
            eventDealRspMsg.setRespMsg(bankCore996666Rsp.getCoreRetMsg());
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
}
