package com.dcits.dcwlt.pay.api.fun;

import com.dcits.dcwlt.common.core.utils.StringUtils;
import com.dcits.dcwlt.common.core.utils.SpringUtils;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 方法调用工厂类
 * @author zhangyaodong
 */
public class FunInvokerFactory {

    private static final Logger logger = LoggerFactory.getLogger(FunInvokerFactory.class);

    public static FunInvoker getFunInvoker(String exceptEventTrxCode) {
        String [] strArr = StringUtils.split(exceptEventTrxCode, '/');
        if (strArr.length != 2) {
            logger.error("exceptEventTrxCode must be like bean/method, please check exceptEventTrxCode: {}", exceptEventTrxCode);
            return null;
        }

        try {
            FunInvoker funInvoker = new FunInvoker();
            Object bean = SpringUtils.getBean(strArr[0]);
            String [] paramNames = {"eventDealReqMsg"};
            funInvoker.setBean(bean);
            funInvoker.setMethod(bean.getClass().getDeclaredMethod(strArr[1], EventDealReqMsg.class));
            funInvoker.setParamNames(paramNames);
            return funInvoker;
        } catch (Exception e) {
            logger.error("bean or method not exist, please check exceptEventTrxCode: {}", exceptEventTrxCode);
        }

        return null;
    }
}
