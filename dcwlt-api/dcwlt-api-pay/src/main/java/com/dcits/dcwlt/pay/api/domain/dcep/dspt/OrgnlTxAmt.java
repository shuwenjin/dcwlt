package com.dcits.dcwlt.pay.api.domain.dcep.dspt;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.validator.annotation.Amount;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class OrgnlTxAmt {

    @NotBlank(message = "币种不能为空")
    private String ccy;// 币种

    @NotBlank(message = "交易金额不能为空")
    @Length(max = 21)
    @Amount
    private String value;// 金额数值

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
    public String toString() {
        return "TrxAmt{" +
                "ccy='" + ccy + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
