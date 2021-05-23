package com.dcits.dcwlt.pay.api.domain.dcep.cashbox;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.Amt;

public class AdjInf {
    private String oprTp;
    private Amt amt;
    private String coopBankInstnId;
    private String cshBoxInstnId;

    @JSONField(name = "OprTp")
    public String getOprTp() {
        return oprTp;
    }

    public void setOprTp(String oprTp) {
        this.oprTp = oprTp;
    }

    @JSONField(name = "Amt")
    public Amt getAmt() {
        return amt;
    }

    public void setAmt(Amt amt) {
        this.amt = amt;
    }

    @JSONField(name = "CoopBankInstnId")
    public String getCoopBankInstnId() {
        return coopBankInstnId;
    }

    public void setCoopBankInstnId(String coopBankInstnId) {
        this.coopBankInstnId = coopBankInstnId;
    }

    @JSONField(name = "CshBoxInstnId")
    public String getCshBoxInstnId() {
        return cshBoxInstnId;
    }

    public void setCshBoxInstnId(String cshBoxInstnId) {
        this.cshBoxInstnId = cshBoxInstnId;
    }

    @Override
    public String toString() {
        return "AdjInf [" +
                "oprTp=" + oprTp + ", " +
                "amt=" + amt + ", " +
                "coopBankInstnId=" + coopBankInstnId + ", " +
                "cshBoxInstnId=" + cshBoxInstnId + " " +
                "]";
    }
}
