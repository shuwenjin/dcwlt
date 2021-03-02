package com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;

/**
 * 交易状态查询响应报文结构
 */
public class TxStsQryRspDTO  extends DCEPRspBody {

    private TxStsQryRsp txStsQryRsp;

    @JSONField(name = "TxStsQryRsp")
    public TxStsQryRsp getTxStsQryRsp() {
        return txStsQryRsp;
    }

    public void setTxStsQryRsp(TxStsQryRsp txStsQryRsp) {
        this.txStsQryRsp = txStsQryRsp;
    }

    @Override
    public String toString() {
        return "TxStsQryRspDTO{" +
                "txStsQryRsp=" + txStsQryRsp +
                '}';
    }
}
