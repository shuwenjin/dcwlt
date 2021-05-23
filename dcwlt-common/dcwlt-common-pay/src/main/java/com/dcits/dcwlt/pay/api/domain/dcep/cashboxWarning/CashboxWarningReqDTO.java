package com.dcits.dcwlt.pay.api.domain.dcep.cashboxWarning;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;

import javax.validation.Valid;

public class CashboxWarningReqDTO extends DCEPReqBody {

    @Valid
    private BalWrngSetng balWrngSetng;

    @JSONField(name = "BalWrngSetng")
    public BalWrngSetng getBalWrngSetng() {
        return balWrngSetng;
    }

    public void setBalWrngSetng(BalWrngSetng balWrngSetng) {
        this.balWrngSetng = balWrngSetng;
    }

    @Override
    public String toString() {
        return "CashboxWarningReqDTO{" +
                "balWrngSetng=" + balWrngSetng +
                '}';
    }
}
