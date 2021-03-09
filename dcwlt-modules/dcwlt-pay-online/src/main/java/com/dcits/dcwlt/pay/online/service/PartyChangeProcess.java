package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.pay.api.domain.dcep.party.Party;

import java.text.ParseException;

/**
 * 生效类型处理
 * 针对不同的生效类型， 使用不同的实现
 *
 * @author majun
 * @date 2020/12/30
 */
public interface PartyChangeProcess {

    void doChange(Party party, TradeContext context);

    /**
     *  机构变更处理方法， 对交易报文处理提供
     * @param party 变更机构
     * @param context 业务处理上下文
     * @throws ParseException
     */
    default void change(Party party, TradeContext context) {
        doChange(party, context);
    }
}
