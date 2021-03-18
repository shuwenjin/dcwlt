package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.pay.api.domain.dcep.txstsqry.TxStsQrySRspDTO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;

/**
 * 银行回查平台-交易状态查询
 */
public interface ITxStsQryNetPartyService {
    /**
     * 银行回查平台-交易状态查询
     *
     * @param msgId 报文标识号
     * @return
     */
    TxStsQrySRspDTO txStsQryNetParty(String msgId);

    void registerTrxStsQry(PayTransDtlInfoDO payTransDtlInfoDO);
}
