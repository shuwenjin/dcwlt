package com.dcits.dcwlt.pay.api.fun;

import java.lang.reflect.Method;

/**
 * FunInvoker 方法调用类
 * @author zhangyaodong
 */
public class FunInvoker {
    /**
     * bean
     */
    private Object bean;
    /**
     * 方法
     */
    private Method method;
    /**
     * 参数名称数组
     */
    private String [] paramNames;

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String[] getParamNames() {
        return paramNames;
    }

    public void setParamNames(String[] paramNames) {
        this.paramNames = paramNames;
    }
}
