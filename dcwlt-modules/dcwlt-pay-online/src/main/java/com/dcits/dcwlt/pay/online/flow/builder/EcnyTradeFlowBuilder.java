package com.dcits.dcwlt.pay.online.flow.builder;

import com.dcits.dcwlt.common.pay.tradeflow.CheckMsg;
import com.dcits.dcwlt.common.pay.tradeflow.InitTxn;
import com.dcits.dcwlt.common.pay.tradeflow.Response;
import com.dcits.dcwlt.common.pay.tradeflow.SaveTxn;
import com.dcits.dcwlt.common.pay.tradeflow.TradeErrHandler;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.common.pay.tradeflow.TradeProcess;
import com.dcits.dcwlt.common.pay.tradeflow.UpdateTxn;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易流程构造器
 * 
 * 针对不同的交易场景可以制定不同改的交易流程
 * 
 * @author liuyuanhui
 *
 */
public class EcnyTradeFlowBuilder {
	
    private List<TradeProcess> processList = new ArrayList<>(16);

    private TradeErrHandler errHandler;

    public static EcnyTradeFlowBuilder get() {
        return new EcnyTradeFlowBuilder();
    }

    public EcnyTradeFlowBuilder checkMsg(CheckMsg checkMsg) {
    	processList.add(checkMsg);
        return this;
    }
    
    public EcnyTradeFlowBuilder initTxn(InitTxn initTxn) {
    	processList.add(initTxn);
        return this;
    }

    public EcnyTradeFlowBuilder saveTxn(SaveTxn saveTxn) {
    	processList.add(saveTxn);
        return this;
    }

    public EcnyTradeFlowBuilder initRspMsg(Response response) {
        processList.add(response);
        return this;
    }

    /**
     * 采用默认的数据库保存服务
     * @return
     */
    public EcnyTradeFlowBuilder saveTxn() {
//    	processList.add(RtpUtil.getInstance().getBean("ecnySaveTxn", SaveTxn.class));
    	return this;
    }


    public EcnyTradeFlowBuilder process(TradeProcess process) {
        processList.add(process);
        return this;
    }

    public EcnyTradeFlowBuilder updateTxn(UpdateTxn updateTxn) {
    	processList.add(updateTxn);
        return this;
    }
    
    /**
     * 采用默认的数据库更新
     * @return
     */
    public EcnyTradeFlowBuilder updateTxn() {
//    	processList.add(RtpUtil.getInstance().getBean("ecnyUpdateTxn", UpdateTxn.class));
        return this;
    }

    public EcnyTradeFlowBuilder response(Response response) {
    	processList.add(response);
        return this;
    }
    
    public EcnyTradeFlowBuilder errHandler(TradeErrHandler handler) {
    	this.errHandler = handler;
    	return this;
    }


    public TradeFlow build() {
    	        
        TradeFlow tradeFlow = new TradeFlow(processList);
        
        //添加异常处理器
        if(errHandler != null) {
        	tradeFlow.setTradeErrHandler(errHandler);
        }

        return tradeFlow;
    }
}
