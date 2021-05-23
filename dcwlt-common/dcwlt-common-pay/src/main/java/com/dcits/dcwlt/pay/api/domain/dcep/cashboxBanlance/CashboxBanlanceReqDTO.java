package com.dcits.dcwlt.pay.api.domain.dcep.cashboxBanlance;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;

import javax.validation.Valid;

public class CashboxBanlanceReqDTO extends DCEPReqBody {

    @Valid
    private CshBoxAcctChckngNtfctn cshBoxAcctChckngNtfctn;

    @JSONField(name = "CshBoxAcctChckngNtfctn")
    public CshBoxAcctChckngNtfctn getCshBoxAcctChckngNtfctn() {
        return cshBoxAcctChckngNtfctn;
    }

    public void setCshBoxAcctChckngNtfctn(CshBoxAcctChckngNtfctn cshBoxAcctChckngNtfctn) {
        this.cshBoxAcctChckngNtfctn = cshBoxAcctChckngNtfctn;
    }

    @Override
    public String toString() {
        return "CashboxWarningReqDTO{" +
                "cshBoxAcctChckngNtfctn=" + cshBoxAcctChckngNtfctn +
                '}';
    }
}
