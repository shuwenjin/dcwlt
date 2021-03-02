package com.dcits.dcwlt.common.pay.tradeflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 交易处理类，通过循环调用process完成一只交易的处理
 *
 * @time 2020/12/20 17:14
 */
public class TradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(TradeFlow.class);

    private List<TradeProcess> tradeProcessFlow;

    private TradeErrHandler tradeErrHandler;

    public TradeFlow(List<TradeProcess> tradeProcessFlow) {
        this.tradeProcessFlow = tradeProcessFlow;
    }

    public void execute(TradeContext<?, ?> context) {
        try {
            for (TradeProcess process : tradeProcessFlow) {
                // 1、获取方法名称
                String step = getMethodName(process);
                long nodeStartTime = System.currentTimeMillis();
                logger.info("开始执行步骤：[{}]", step);

                // 2、执行步骤
                process.process(context);

                // 3、打印交易耗时
                logger.info("结束执行步骤：[{}]，耗时为：{}", step, System.currentTimeMillis() - nodeStartTime);
            }
        } catch (Exception e) {
            logger.error("交易处理失败：{}", e.getMessage(), e);
            if (null == tradeErrHandler) {
                throw e;
            }
            tradeErrHandler.handle(context, e);
        }
    }

    public TradeErrHandler getTradeErrHandler() {
        return tradeErrHandler;
    }

    public void setTradeErrHandler(TradeErrHandler tradeErrHandler) {
        this.tradeErrHandler = tradeErrHandler;
    }


    /**
     * 获取方法名
     *
     * @param process
     * @return
     */
    private String getMethodName(TradeProcess process) {
        SerializedLambda serializedLambda = getSerializedLambda(process);
        if (null == serializedLambda) {
            return "";
        }
        return serializedLambda.getImplMethodName().toString();
    }

    private SerializedLambda getSerializedLambda(TradeProcess process) {
        try {
            Method write = process.getClass().getDeclaredMethod("writeReplace");
            write.setAccessible(true);
            return (SerializedLambda) write.invoke(process);
        } catch (Exception e) {
            return null;
        }
    }
}
