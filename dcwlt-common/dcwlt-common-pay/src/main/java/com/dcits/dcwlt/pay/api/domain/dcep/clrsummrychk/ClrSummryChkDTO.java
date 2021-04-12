package com.dcits.dcwlt.pay.api.domain.dcep.clrsummrychk;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;


import javax.validation.Valid;

public class ClrSummryChkDTO extends DCEPReqBody {
    @Valid
    private ClrSummryChk clrSummryChk;

    @JSONField(name = "ClrSummryChk")
    public ClrSummryChk getClrSummryChk() {
        return clrSummryChk;
    }

    public void setClrSummryChk(ClrSummryChk clrSummryChk) {
        this.clrSummryChk = clrSummryChk;
    }

    @Override
    public String toString() {
        return "ClrSummryChkDTO{" +
                "clrSummryChk=" + clrSummryChk +
                '}';
    }
}
