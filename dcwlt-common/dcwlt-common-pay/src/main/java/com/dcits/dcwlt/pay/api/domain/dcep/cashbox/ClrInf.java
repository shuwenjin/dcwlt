package com.dcits.dcwlt.pay.api.domain.dcep.cashbox;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.Amt;

public class ClrInf {
    private String clrReptFlg;
    private String cdtDbtInd;
    private Amt clrAmt;

    @JSONField(name = "ClrReptFlg")
    public String getClrReptFlg() {
        return clrReptFlg;
    }

    public void setClrReptFlg(String clrReptFlg) {
        this.clrReptFlg = clrReptFlg;
    }

    @JSONField(name = "CdtDbtInd")
    public String getCdtDbtInd() {
        return cdtDbtInd;
    }

    public void setCdtDbtInd(String cdtDbtInd) {
        this.cdtDbtInd = cdtDbtInd;
    }

    @JSONField(name = "ClrAmt")
    public Amt getClrAmt() {
        return clrAmt;
    }

    public void setClrAmt(Amt clrAmt) {
        this.clrAmt = clrAmt;
    }

    @Override
    public String toString() {
        return "ClrInf [" +
                "clrReptFlg=" + clrReptFlg + ", " +
                "cdtDbtInd=" + cdtDbtInd + ", " +
                "clrAmt=" + clrAmt + " " +
                "]";
    }
}
