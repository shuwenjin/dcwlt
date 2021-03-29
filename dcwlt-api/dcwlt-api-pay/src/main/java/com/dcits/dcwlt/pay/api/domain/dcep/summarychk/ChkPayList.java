package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.Valid;
import java.util.List;

public class ChkPayList {

    @Valid
    private List<ChkPayInf> chkPayInf;

    @JSONField(name = "ChkPayInf")
    public List<ChkPayInf> getChkPayInf() {
        return chkPayInf;
    }

    public void setChkPayInf(List<ChkPayInf> chkPayInf) {
        this.chkPayInf = chkPayInf;
    }

    @Override
    public String toString() {
        return "ChkPayList{" +
                "chkPayInf=" + chkPayInf +
                '}';
    }
}
