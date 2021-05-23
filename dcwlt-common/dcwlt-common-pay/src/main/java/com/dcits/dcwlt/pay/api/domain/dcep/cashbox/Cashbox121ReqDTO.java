package com.dcits.dcwlt.pay.api.domain.dcep.cashbox;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;
import com.dcits.dcwlt.pay.api.domain.ecny.cashbox.CshBoxAdjAppl;

import javax.validation.Valid;

public class Cashbox121ReqDTO extends DCEPReqBody {

    @Valid
    private CshBoxAdjAppl cshBoxAdjAppl;

    @JSONField(name = "CshBoxAdjAppl")
    public CshBoxAdjAppl getCshBoxAdjAppl() {
        return cshBoxAdjAppl;
    }

    public void setCshBoxAdjAppl(CshBoxAdjAppl cshBoxAdjAppl) {
        this.cshBoxAdjAppl = cshBoxAdjAppl;
    }

    @Override
    public String toString() {
        return "CashboxReqDTO{" +
                "cshBoxAdjAppl=" + cshBoxAdjAppl +
                '}';
    }
}
