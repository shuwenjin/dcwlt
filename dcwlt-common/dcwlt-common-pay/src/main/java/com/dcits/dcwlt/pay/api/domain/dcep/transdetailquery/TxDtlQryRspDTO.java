package com.dcits.dcwlt.pay.api.domain.dcep.transdetailquery;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;


/**
 * 交易状态查询响应报文结构
 *
 *
 */
public class TxDtlQryRspDTO extends DCEPRspBody {
    TxDtlQryRsp txDtlsQryRsp;

    @JSONField(name = "TxDtlsQryRsp")
    public TxDtlQryRsp getTxDtlsQryRsp() {
        return txDtlsQryRsp;
    }

    public void setTxDtlsQryRsp(TxDtlQryRsp txDtlsQryRsp) {
        this.txDtlsQryRsp = txDtlsQryRsp;
    }

    @Override
    public String toString() {
        return "TxDtlQryRspDTO{" +
                "txDtlsQryRsp=" + txDtlsQryRsp +
                '}';
    }
}
