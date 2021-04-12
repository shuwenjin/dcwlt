package com.dcits.dcwlt.pay.api.domain.dcep.authinfo;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;


import javax.validation.Valid;

public class AuthInfoReqDTO extends DCEPReqBody {
    @Valid
    private AuthrtyChngNtfctn authrtyChngNtfctn;
    @JSONField(name = "AuthrtyChngNtfctn")
    public AuthrtyChngNtfctn getAuthrtyChngNtfctn() {
        return authrtyChngNtfctn;
    }

    public void setAuthrtyChngNtfctn(AuthrtyChngNtfctn authrtyChngNtfctn) {
        this.authrtyChngNtfctn = authrtyChngNtfctn;
    }

    @Override
    public String toString() {
        return "AuthrtyChngNtfctnDTO{" +
                "authrtyChngNtfctn=" + authrtyChngNtfctn +
                '}';
    }
}
