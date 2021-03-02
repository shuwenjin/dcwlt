package com.dcits.dcwlt.pay.online.flow.builder;

import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.pay.api.model.EcnyBaseDO;

import java.util.Map;

/**
 * 互联互通交易处理上下文
 * 1.可添加一些上下文必须的参数，例如txn
 * 2.可添加一些常用参数获取的方法
 *
 * @param <T>
 * @param <R>
 * @author liuyuanhui
 */
@SuppressWarnings("unchecked")
public class EcnyTradeContext<T, R> extends TradeContext<T, R> {

    private EcnyBaseDO txn;

    public static <T, R> EcnyTradeContext<T, R> getInstance(T reqMsg) {
        EcnyTradeContext<T, R> context = new EcnyTradeContext<>();
        context.setReqMsg(reqMsg);
        return context;
    }

    public static <T, R> EcnyTradeContext<T, R> getContext(TradeContext<?, ?> context) {
        return (EcnyTradeContext<T, R>) context;
    }

    public static <T> T getReqMsg(TradeContext<?, ?> context) {
        return (T) context.getReqMsg();
    }

    public static <R> R getRspMsg(TradeContext<?, ?> context) {
        return (R) context.getRspMsg();
    }

    public static <T, R> void setRspMsg(TradeContext<?, ?> context, R rspMsg) {
        ((EcnyTradeContext<T, R>) context).setRspMsg(rspMsg);
    }

    public static <T, R> EcnyBaseDO getTxn(TradeContext<?, ?> context) {
        return ((EcnyTradeContext<T, R>) context).getTxn();
    }

    public static <T, R> void setTxn(TradeContext<?, ?> context, EcnyBaseDO txn) {
        ((EcnyTradeContext<T, R>) context).setTxn(txn);
    }

    public static Map<String, Object> getTempContext(TradeContext<?, ?> context) {
        return context.getTempCtx();
    }

    public static void setTempContext(TradeContext<?, ?> context, Map<String, Object> tempContext) {
        context.setTempCtx(tempContext);
    }


    public EcnyBaseDO getTxn() {
        return this.txn;
    }

    public void setTxn(EcnyBaseDO txn) {
        this.txn = txn;
    }
}
