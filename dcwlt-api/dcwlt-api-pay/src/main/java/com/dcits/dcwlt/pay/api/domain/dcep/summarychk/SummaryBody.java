package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.Valid;
import java.util.List;

public class SummaryBody {
    @Valid
    private List<SummaryGrp> summaryGrp;

    @JSONField(name = "SummaryGrp")
    public List<SummaryGrp> getSummaryGrp() {
        return summaryGrp;
    }

    public void setSummaryGrp(List<SummaryGrp> summaryGrp) {
        this.summaryGrp = summaryGrp;
    }

    @Override
    public String toString() {
        return "SummaryBody{" +
                "summaryGrp=" + summaryGrp +
                '}';
    }
}
