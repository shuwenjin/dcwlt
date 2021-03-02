package com.dcits.dcwlt.common.pay.tradeflow;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TradeFlowRuner {
    @Autowired
    private ApplicationContext applicationContext;
    private TradeFlowRuner() {
    }

    public static <T, R> R execute(String flowName, TradeContext<T, R> context) {

        //交易执行
        TradeFlow tradeFlow = (TradeFlow) new ClassPathXmlApplicationContext("applicationContext.xml").getBean(flowName);
        tradeFlow.execute(context);

        return context.getRspMsg();
    }


    public static <T, R> R execute(TradeFlow tradeFlow, TradeContext<T, R> context){
        tradeFlow.execute(context);
        return context.getRspMsg();
    }
}
