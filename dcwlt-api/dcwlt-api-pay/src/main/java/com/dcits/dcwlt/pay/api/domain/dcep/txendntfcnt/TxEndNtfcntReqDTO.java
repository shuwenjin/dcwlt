package com.dcits.dcwlt.pay.api.domain.dcep.txendntfcnt;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;


import javax.validation.Valid;

/**
 * 终态通知请求报文
 *
 */
public class TxEndNtfcntReqDTO extends DCEPReqBody {

    @Valid
    private TxEndNtfctn txEndNtfctn;

    @JSONField(name = "TxEndNtfctn")
    public TxEndNtfctn getTxEndNtfctn() {
        return txEndNtfctn;
    }

    public void setTxEndNtfctn(TxEndNtfctn txEndNtfctn) {
        this.txEndNtfctn = txEndNtfctn;
    }

    @Override
    public String toString() {
        return "TxEndNtfcntReqDTO{" +
                "txEndNtfctn=" + txEndNtfctn +
                '}';
    }
}
