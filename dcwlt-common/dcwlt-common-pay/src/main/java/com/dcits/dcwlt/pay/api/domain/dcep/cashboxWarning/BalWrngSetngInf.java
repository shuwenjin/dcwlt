package com.dcits.dcwlt.pay.api.domain.dcep.cashboxWarning;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.Amt;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class BalWrngSetngInf {
    @NotBlank(message = "被设置合作银行机构编码")
    @Length(max = 14)
    private String coopBankInstnId;

    @NotBlank(message = "钱柜所属运营机构")
    @Length(max = 14)
    private String cshBoxInstnId;

    @NotBlank(message = "合作银行钱柜ID")
    @Length(max = 14)
    private String coopBankWltId;

    @Valid
    private Amt wrngVal;

    @JSONField(name = "CoopBankInstnId")
    public String getCoopBankInstnId() {
        return coopBankInstnId;
    }

    public void setCoopBankInstnId(String coopBankInstnId) {
        this.coopBankInstnId = coopBankInstnId;
    }

    @JSONField(name = "CshBoxInstnId")
    public String getCshBoxInstnId() {
        return cshBoxInstnId;
    }

    public void setCshBoxInstnId(String cshBoxInstnId) {
        this.cshBoxInstnId = cshBoxInstnId;
    }

    @JSONField(name = "CoopBankWltId")
    public String getCoopBankWltId() {
        return coopBankWltId;
    }

    public void setCoopBankWltId(String coopBankWltId) {
        this.coopBankWltId = coopBankWltId;
    }

    @JSONField(name = "WrngVal")
    public Amt getWrngVal() {
        return wrngVal;
    }

    public void setWrngVal(Amt wrngVal) {
        this.wrngVal = wrngVal;
    }

    @Override
    public String toString() {
        return "BalWrngSetngInf [ " +
                "coopBankInstnId=" + coopBankInstnId +
                ", cshBoxInstnId=" + cshBoxInstnId +
                ", coopBankWltId=" + coopBankWltId +
                ", wrngVal=" + wrngVal +
                "]";
    }
}
