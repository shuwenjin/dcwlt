package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.Valid;
import java.util.List;

public class ChkPayList {

    /*
     * 业务对账清单列表
     * */
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
