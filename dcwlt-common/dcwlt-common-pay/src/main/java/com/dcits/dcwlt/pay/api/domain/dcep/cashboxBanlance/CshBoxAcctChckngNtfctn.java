package com.dcits.dcwlt.pay.api.domain.dcep.cashboxBanlance;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;

import javax.validation.Valid;

public class CshBoxAcctChckngNtfctn {

    /**
     * 业务头组件
     */
    @Valid
    private GrpHdr grpHdr;

    /**
     * 钱柜余额对账报文
     */
    @Valid
    private ChckngInf chckngInf;

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "ChckngInf")
    public ChckngInf getChckngInf() {
        return chckngInf;
    }

    public void setChckngInf(ChckngInf chckngInf) {
        this.chckngInf = chckngInf;
    }

    @Override
    public String toString() {
        return "CshBoxAcctChckngNtfctn [ " +
                "grpHdr=" + grpHdr +
                ", chckngInf=" + chckngInf +
                "]";
    }
}
