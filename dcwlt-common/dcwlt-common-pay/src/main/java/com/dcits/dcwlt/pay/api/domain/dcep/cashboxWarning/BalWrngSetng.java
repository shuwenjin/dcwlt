package com.dcits.dcwlt.pay.api.domain.dcep.cashboxWarning;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;

import javax.validation.Valid;

public class BalWrngSetng {
    /**
     * 业务头组件
     */
    @Valid
    private GrpHdr grpHdr;

    /**
     * 钱柜预警报文
     */
    @Valid
    private BalWrngSetngInf balWrngSetngInf;

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "BalWrngSetngInf")
    public BalWrngSetngInf getBalWrngSetngInf() {
        return balWrngSetngInf;
    }

    public void setBalWrngSetngInf(BalWrngSetngInf balWrngSetngInf) {
        this.balWrngSetngInf = balWrngSetngInf;
    }


    @Override
    public String toString() {
        return "BalWrngSetng [ " +
                "grpHdr=" + grpHdr +
                ", balWrngSetngInf=" + balWrngSetngInf +
                "]";
    }
}
