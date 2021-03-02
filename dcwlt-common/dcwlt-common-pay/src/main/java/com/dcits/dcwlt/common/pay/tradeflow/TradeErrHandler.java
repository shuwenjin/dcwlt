package com.dcits.dcwlt.common.pay.tradeflow;

public interface TradeErrHandler {

	public void handle(TradeContext<?, ?> context, Throwable e);
}
