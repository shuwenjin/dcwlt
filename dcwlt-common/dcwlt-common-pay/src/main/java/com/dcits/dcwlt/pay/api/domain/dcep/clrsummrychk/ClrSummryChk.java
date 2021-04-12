package com.dcits.dcwlt.pay.api.domain.dcep.clrsummrychk;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;


import javax.validation.Valid;

/**
 * @desc  资金调整汇总核对
 *
 *
 *
 */
public class ClrSummryChk {
    /*
    * 业务头组件
    * */
    @Valid
    private GrpHdr grpHdr;
    /*
    * 汇总核对信息
    * */
    @Valid
    private SummryChkInf summryChkInf;

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "SummryChkInf")
    public SummryChkInf getSummryChkInf() {
        return summryChkInf;
    }

    public void setSummryChkInf(SummryChkInf summryChkInf) {
        this.summryChkInf = summryChkInf;
    }

    @Override
    public String toString() {
        return "ClrSummryChk{" +
                "grpHdr=" + grpHdr +
                ", summryChkInf=" + summryChkInf +
                '}';
    }
}
