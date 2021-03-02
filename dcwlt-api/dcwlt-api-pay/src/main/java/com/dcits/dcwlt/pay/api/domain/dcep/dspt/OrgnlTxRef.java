package com.dcits.dcwlt.pay.api.domain.dcep.dspt;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.Valid;

/**
 * 原交易信息
 */
public class OrgnlTxRef {
    /**
     * 原交易金额
     */
    @Valid
    private OrgnlTxAmt orgnlTxAmt;

    @JSONField(name = "OrgnlTxAmt")
    public OrgnlTxAmt getOrgnlTxAmt() {
        return orgnlTxAmt;
    }

    public void setOrgnlTxAmt(OrgnlTxAmt orgnlTxAmt) {
        this.orgnlTxAmt = orgnlTxAmt;
    }

    @Override
    public String toString() {
        return "OrgnlTxRef{" +
                "orgnlTxAmt='" + orgnlTxAmt + '\'' +
                '}';
    }
}
