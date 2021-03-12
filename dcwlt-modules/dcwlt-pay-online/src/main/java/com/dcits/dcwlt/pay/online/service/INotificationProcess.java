package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.pay.api.domain.dcep.fault.Fault;
import com.dcits.dcwlt.pay.api.domain.dcep.party.trblntfctn.TrblNtfctnDTO;

/**
 * 机构状态变更处理接口
 *
 * @author majun
 * @date 2020/12/31
 */
public interface INotificationProcess {

    default Fault getFault(TrblNtfctnDTO trblNtfctnDTO, TradeContext context) {
        return new Fault();
    }

    default void process(TrblNtfctnDTO trblNtfctnDTO, TradeContext context) {
        doProcess(trblNtfctnDTO, context);
    }

    void doProcess(TrblNtfctnDTO trblNtfctn, TradeContext context);
}
