package com.dcits.dcwlt.pay.online.service.impl;

import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 核心事件服务
 */
@Service
public class CoreEventService {

    private static final Logger logger = LoggerFactory.getLogger(CoreEventService.class);

//    @Autowired
//    private IEventRegisterAppService eventRegisterAppService;

    /**
     * 注册核心回查异常
     * @param coreReqSerno
     * @param payDate
     * @param paySerno
     * @param callBackClass
     * @param <T>
     */
//    public <T extends ICoreQryCallBack> void registerCoreQry(String coreReqDate, String coreReqSerno, String payDate, String paySerno, Class<T> callBackClass) {
//        Map<String, String> param = new HashMap<>();
//        if (null != callBackClass) {
//            param.put("callBackCanonicalName", callBackClass.getCanonicalName());
//        }
//        param.put("coreReqDate", coreReqDate);
//        param.put("coreReqSerno", coreReqSerno);
//        param.put("payDate", payDate);
//        param.put("paySerno", paySerno);
//        param.put("canResn", "核心异常回查");
//
//        String paramStr = JSON.toJSONString(param,
//                SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteMapNullValue);
//
//        EventDealReqMsg eventDealReqMsg = new EventDealReqMsg();
//        eventDealReqMsg.setExceptEventCode(ENVET_CORE_QRY_CODE);
//        eventDealReqMsg.setExceptEventSeqNo(coreReqSerno);
//        eventDealReqMsg.setExceptEventContext(paramStr);
//
//        eventRegisterAppService.registerEvent(eventDealReqMsg, EVENT);
//    }

    /**
     * 注册核心冲正异常事件
     * @param payDate
     * @param paySerno
     * @param callBackClass
     * @param <T>
     */
//    public <T extends IBankRevCallBack> void registerBankRev(String payDate, String paySerno,  Class<T> callBackClass) {
//        Map<String, String> param = new HashMap<>();
//        if (null != callBackClass) {
//            param.put("callBackCanonicalName", callBackClass.getCanonicalName());
//        }
//        param.put("payDate", payDate);
//        param.put("paySerno", paySerno);
//        param.put("canResn", "核心冲正");
//
//        String paramStr = JSON.toJSONString(param,
//                SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteMapNullValue);
//
//        EventDealReqMsg eventDealReqMsg = new EventDealReqMsg();
//        eventDealReqMsg.setExceptEventCode(EVENT_CORE_REVERSAL_CODE);
//        eventDealReqMsg.setExceptEventSeqNo(payDate + splitStr + paySerno);
//        eventDealReqMsg.setExceptEventContext(paramStr);
//
//        eventRegisterAppService.registerEvent(eventDealReqMsg,EVENT);
//
//    }

    /**
     * 注册补入账异常事件
     * @param payDate
     * @param paySerno
     * @param callBackClass
     * @param <T>
     */
//    public <T extends IReCreditCallBack> void registerReCredit(String payDate, String paySerno, Class<T> callBackClass) {
//        Map<String, String> param = new HashMap<>();
//        if (null != callBackClass) {
//            param.put("callBackCanonicalName", callBackClass.getCanonicalName());
//        }
//        param.put("payDate", payDate);
//        param.put("paySerno", paySerno);
//        param.put("canResn", "补入账");
//
//        String paramStr = JSON.toJSONString(param,
//                SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteMapNullValue);
//
//        EventDealReqMsg eventDealReqMsg = new EventDealReqMsg();
//        eventDealReqMsg.setExceptEventCode(EVENT_CORE_RECREDIT_CODE);
//        eventDealReqMsg.setExceptEventSeqNo(payDate+splitStr+paySerno);
//        eventDealReqMsg.setExceptEventContext(paramStr);
//
//        eventRegisterAppService.registerEvent(eventDealReqMsg,EVENT);
//
//    }

}
