package com.dcits.dcwlt.common.pay.tradeflow;

import java.io.Serializable;

@FunctionalInterface
public interface TradeProcess extends Serializable {

    void process(TradeContext<?, ?> context);

}
