package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;


/**
 * @author weimeiyuan
 * @Time 2021/2/4 15:26
 * @Version 1.0
 * Description:机构对账汇总
 */
public interface IReconSummaryChkService {
    int saveSummary(TradeContext<?, ?> tradeContext);
}
