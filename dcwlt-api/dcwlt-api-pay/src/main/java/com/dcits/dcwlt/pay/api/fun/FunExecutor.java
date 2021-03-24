package com.dcits.dcwlt.pay.api.fun;

import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;

/**
 * FunInvoker 方法执行类
 * @author zhangyaodong
 */
public class FunExecutor {

    public static Object execute(FunInvoker invoker, EventDealReqMsg eventInfo) {
        if (null == invoker) {
            return null;
        }
        try {
            Object[] classs = new Object[1];
            classs[0] = (Object) eventInfo;
            return invoker.getMethod().invoke(invoker.getBean(), classs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
