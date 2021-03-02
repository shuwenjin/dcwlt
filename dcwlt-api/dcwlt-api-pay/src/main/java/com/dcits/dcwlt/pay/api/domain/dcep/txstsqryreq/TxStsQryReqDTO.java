package com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq;


import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;


import javax.validation.Valid;

/**
 * 交易状态查询请求报文结构
 *
 */
public class TxStsQryReqDTO extends DCEPReqBody {

    @Valid
    private TxStsQryReq txStsQryReq;

    @JSONField(name = "TxStsQryReq")
    public TxStsQryReq getTxStsQryReq() {
        return txStsQryReq;
    }

    public void setTxStsQryReq(TxStsQryReq txStsQryReq) {
        this.txStsQryReq = txStsQryReq;
    }

    @Override
    public String toString() {
        return "TxStsQryReqDTO{" +
                "txStsQryReq=" + txStsQryReq +
                '}';
    }
}
