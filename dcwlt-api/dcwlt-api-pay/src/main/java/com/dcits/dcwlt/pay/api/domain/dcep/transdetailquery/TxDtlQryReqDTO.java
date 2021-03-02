package com.dcits.dcwlt.pay.api.domain.dcep.transdetailquery;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;


/**
 * 交易明细查询请求报文结构
 *
 *
 */
public class TxDtlQryReqDTO extends DCEPReqBody {
    TxDtlQryReq txDtlsQryReq;

    @JSONField(name = "TxDtlsQryReq")
    public TxDtlQryReq getTxDtlsQryReq() {
        return txDtlsQryReq;
    }

    public void setTxDtlsQryReq(TxDtlQryReq txDtlsQryReq) {
        this.txDtlsQryReq = txDtlsQryReq;
    }

    @Override
    public String toString() {
        return "TxDtlQryReqDTO{" +
                "txDtlsQryReq=" + txDtlsQryReq +
                '}';
    }
}
