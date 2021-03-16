package com.dcits.dcwlt.pay.online.baffle.core.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.online.baffle.core.CoreService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.alibaba.druid.util.FnvHash.Constants.EVENT;
@Service
public class CoreServiceImpl implements CoreService {

    /**
     * 注册核心回查异常
     * @param coreReqSerno
     * @param payDate
     * @param paySerno
     * @param callBackClass
     * @param <T>
     */
    //Todo
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
}
