package com.dcits.dcwlt.common.pay.tradeflow;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易处理上下文
 *
 * @time 2020/12/20 17:14
 */
public class TradeContext<T, R> {

    private T reqMsg;
    private R rspMsg;
    private Map<String, Object> tempCtx = new HashMap<>();

    public T getReqMsg() {
        return reqMsg;
    }

    public void setReqMsg(T reqMsg) {
        this.reqMsg = reqMsg;
    }

    public R getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(R rspMsg) {
        this.rspMsg = rspMsg;
    }

    public Map<String, Object> getTempCtx() {
        return tempCtx;
    }

    public void setTempCtx(Map<String, Object> tempCtx) {
        this.tempCtx = tempCtx;
    }

    @Override
    public String toString() {
        return "TradeContext{" +
                "reqMsg=" + reqMsg +
                ", rspMsg=" + rspMsg +
                ", tempCtx=" + tempCtx +
                '}';
    }
}
