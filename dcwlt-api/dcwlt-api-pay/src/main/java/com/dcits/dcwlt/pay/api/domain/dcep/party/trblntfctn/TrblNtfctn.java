package com.dcits.dcwlt.pay.api.domain.dcep.party.trblntfctn;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 机构状态变更报文（931）
 *
 *
 * @date 2020/12/31
 */
public class TrblNtfctn extends DCEPReqBody {

    @Valid
    @NotNull
    @JSONField(name = "GrpHdr")
    private GrpHdr grpHdr;      //业务头组件

    @Valid
    @NotNull
    @JSONField(name = "StsInf")
    private StsInf stsInf;      //状态变更信息

    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    public StsInf getStsInf() {
        return stsInf;
    }

    public void setStsInf(StsInf stsInf) {
        this.stsInf = stsInf;
    }

    @Override
    public String toString() {
        return "TrblNtfctn{" +
                "grpHdr=" + grpHdr +
                ", stsInf=" + stsInf +
                '}';
    }
}
