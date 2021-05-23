package com.dcits.dcwlt.pay.api.domain.dcep.cashbox;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;

import javax.validation.Valid;

public class Cashbox123ReqDTO extends DCEPReqBody {

    @Valid
    private CshBoxAdjNtfctn cshBoxAdjNtfctn;

    @JSONField(name = "CshBoxAdjNtfctn")
    public CshBoxAdjNtfctn getCshBoxAdjNtfctn() {
        return cshBoxAdjNtfctn;
    }

    public void setCshBoxAdjNtfctn(CshBoxAdjNtfctn cshBoxAdjNtfctn) {
        this.cshBoxAdjNtfctn = cshBoxAdjNtfctn;
    }
}
