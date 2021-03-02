package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;


import javax.validation.Valid;

/**
 *
 * @Time 2021/1/3 14:20
 * @Version 1.0
 * Description:对账功能报文实体类
 */
public class ReconSummaryChk {
    //业务头组件
    @Valid
    private GrpHdr grpHdr;
    //对账汇总核对信息
    @Valid
    private SummaryChkInf summaryChkInf;

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "SummaryChkInf")
    public SummaryChkInf getSummaryChkInf() {
        return summaryChkInf;
    }

    public void setSummaryChkInf(SummaryChkInf summaryChkInf) {
        this.summaryChkInf = summaryChkInf;
    }

    @Override
    public String toString() {
        return "ReconSummaryChkDO{" +
                "grpHdr=" + grpHdr +
                ", summaryChkInf=" + summaryChkInf +
                '}';
    }
}
