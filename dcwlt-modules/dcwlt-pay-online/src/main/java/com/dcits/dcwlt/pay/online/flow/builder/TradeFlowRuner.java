package com.dcits.dcwlt.pay.online.flow.builder;


import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.pay.online.flow.receive.FreeFrmt401RTradeFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TradeFlowRuner {
    @Autowired
    private  ApplicationContext applicationContext;
    private TradeFlowRuner() {
    }

    public  <T, R> R execute(String flowName, TradeContext<T, R> context) {
        //交易执行
        TradeFlow tradeFlow = (TradeFlow) applicationContext.getBean(flowName);
        tradeFlow.execute(context);

        return context.getRspMsg();
    }
}
