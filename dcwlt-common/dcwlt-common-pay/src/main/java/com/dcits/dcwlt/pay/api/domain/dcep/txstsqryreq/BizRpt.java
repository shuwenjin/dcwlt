package com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *
 * @date  2021/1/3
 * @version 1.0.0
 * <p>应答的原业务信息</p>
 */
public class BizRpt {

    /**
     * 原业务状态
     */
    private String trnRs;

    /**
     * 原交易原因：当原业务状态为非PR00或PR02时
     */
    private Rsn rsn;

    /**
     * 原业务信息
     */
    private OrgnlTxInf orgnlTxInf;

    @JSONField(name = "TrnRs")
    public String getTrnRs() {
        return trnRs;
    }

    public void setTrnRs(String trnRs) {
        this.trnRs = trnRs;
    }

    @JSONField(name = "Rsn")
    public Rsn getRsn() {
        return rsn;
    }

    public void setRsn(Rsn rsn) {
        this.rsn = rsn;
    }

    @JSONField(name = "OrgnlTxInf")
    public OrgnlTxInf getOrgnlTxInf() {
        return orgnlTxInf;
    }

    public void setOrgnlTxInf(OrgnlTxInf orgnlTxInf) {
        this.orgnlTxInf = orgnlTxInf;
    }

    @Override
    public String toString() {
        return "BizRpt [ " +
                "trnRs='" + trnRs + '\'' +
                ", rsn=" + rsn +
                ", orgnlTxInf=" + orgnlTxInf +
                "]";
    }
}
