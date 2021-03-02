package com.dcits.dcwlt.pay.api.domain.dcep.authinfo;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;


import javax.validation.Valid;

public class AuthInfoRspDTO  extends DCEPRspBody {
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
        return "AuthInfoRspDTO{" +
                "authrtyChngNtfctn=" + authrtyChngNtfctn +
                '}';
    }
}
