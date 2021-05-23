package com.dcits.dcwlt.pay.api.domain.dcep.common;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

public class Amt {
    @Length(max = 3)
    private String ccy;

    @Length(max = 18)
    private String value;

    @JSONField(name = "Ccy")
    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    @JSONField(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return "Amt [ " +
                "ccy=" + ccy +
                ", value=" + value +
                "]";
    }
}
