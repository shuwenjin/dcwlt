package com.dcits.dcwlt.pay.api.domain.dcep.cashboxBanlance;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.Amt;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class ChckngInf {
    @NotBlank(message = "对账日期")
    @Length(max = 10)
    private String chckngDt;

    @NotBlank(message = "合作银行机构编码")
    @Length(max = 14)
    private String coopBankInstnId;

    @NotBlank(message = "合作银行钱柜ID")
    @Length(max = 34)
    private String coopBankWltId;

    @NotBlank(message = "钱柜所属运营机构")
    @Length(max = 14)
    private String cshBoxInstnId;

    @Valid
    private Amt initlAmt;

    @NotBlank(message = "借贷标识")
    @Length(max = 4)
    private String cdtDbtInd;

    @Valid
    private Amt dbtCntAmt;

    @Valid
    private Amt cdtCntAmt;

    @Valid
    private Amt fnlAmt;

    @JSONField(name = "ChckngDt")
    public String getChckngDt() {
        return chckngDt;
    }

    public void setChckngDt(String chckngDt) {
        this.chckngDt = chckngDt;
    }

    @JSONField(name = "CoopBankInstnId")
    public String getCoopBankInstnId() {
        return coopBankInstnId;
    }

    public void setCoopBankInstnId(String coopBankInstnId) {
        this.coopBankInstnId = coopBankInstnId;
    }

    @JSONField(name = "CoopBankWltId")
    public String getCoopBankWltId() {
        return coopBankWltId;
    }

    public void setCoopBankWltId(String coopBankWltId) {
        this.coopBankWltId = coopBankWltId;
    }

    @JSONField(name = "CshBoxInstnId")
    public String getCshBoxInstnId() {
        return cshBoxInstnId;
    }

    public void setCshBoxInstnId(String cshBoxInstnId) {
        this.cshBoxInstnId = cshBoxInstnId;
    }

    @JSONField(name = "InitlAmt")
    public Amt getInitlAmt() {
        return initlAmt;
    }

    public void setInitlAmt(Amt initlAmt) {
        this.initlAmt = initlAmt;
    }

    @JSONField(name = "CdtDbtInd")
    public String getCdtDbtInd() {
        return cdtDbtInd;
    }

    public void setCdtDbtInd(String cdtDbtInd) {
        this.cdtDbtInd = cdtDbtInd;
    }

    @JSONField(name = "DbtCntAmt")
    public Amt getDbtCntAmt() {
        return dbtCntAmt;
    }

    public void setDbtCntAmt(Amt dbtCntAmt) {
        this.dbtCntAmt = dbtCntAmt;
    }

    @JSONField(name = "CdtCntAmt")
    public Amt getCdtCntAmt() {
        return cdtCntAmt;
    }

    public void setCdtCntAmt(Amt cdtCntAmt) {
        this.cdtCntAmt = cdtCntAmt;
    }

    @JSONField(name = "FnlAmt")
    public Amt getFnlAmt() {
        return fnlAmt;
    }

    public void setFnlAmt(Amt fnlAmt) {
        this.fnlAmt = fnlAmt;
    }

    @Override
    public String toString() {
        return "ChckngInf [ " +
                "chckngDt=" + chckngDt +
                ", coopBankInstnId=" + coopBankInstnId +
                ", coopBankWltId=" + coopBankWltId +
                ", cshBoxInstnId=" + cshBoxInstnId +
                ", initlAmt=" + initlAmt +
                ", cdtDbtInd=" + cdtDbtInd +
                ", dbtCntAmt=" + dbtCntAmt +
                ", cdtCntAmt=" + cdtCntAmt +
                ", fnlAmt=" + fnlAmt +
                "]";
    }
}
