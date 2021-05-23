package com.dcits.dcwlt.pay.api.domain.dcep.cashbox;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.RspsnInf;

public class CashboxRspsnInf extends RspsnInf {

    private String prcCd;

    @JSONField(name = "PrcCd")
    public String getPrcCd() {
        return prcCd;
    }

    public void setPrcCd(String prcCd) {
        this.prcCd = prcCd;
    }

    @Override
    public String toString() {
        return "CashboxRspsnInf [" +
                "prcCd=" + prcCd + " " +
                "]";
    }
}
