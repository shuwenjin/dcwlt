package com.dcits.dcwlt.pay.api.model;

import com.dcits.dcwlt.common.core.annotation.Excel;

/**
 * 钱柜余额对账通知对象
 */
public class PayCashboxBanlanceDO {
    @Excel(name = "id")
    private String id;
    @Excel(name = "对账日期")
    private String chckngDt;
    @Excel(name = "合作银行机构编码")
    private String coopBankInstnId;
    @Excel(name = "合作银行钱柜ID")
    private String coopBankWltId;
    @Excel(name = "钱柜所属运营机构")
    private String cshBoxInstnId;
    @Excel(name = "期初余额货币符号")
    private String initlAmtCcy;
    @Excel(name = "期初余额")
    private String initlAmtValue;
    @Excel(name = "借贷标识")
    private String cdtDbtInd;
    @Excel(name = "借方金额货币符号")
    private String dbtCntAmtCcy;
    @Excel(name = "借方金额")
    private String dbtCntAmtValue;
    @Excel(name = "贷方金额货币符号")
    private String cdtCntAmtCcy;
    @Excel(name = "贷方金额")
    private String cdtCntAmtValue;
    @Excel(name = "期末余额货币符号")
    private String fnlAmtCcy;
    @Excel(name = "期末余额")
    private String fnlAmtValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChckngDt() {
        return chckngDt;
    }

    public void setChckngDt(String chckngDt) {
        this.chckngDt = chckngDt;
    }

    public String getCoopBankInstnId() {
        return coopBankInstnId;
    }

    public void setCoopBankInstnId(String coopBankInstnId) {
        this.coopBankInstnId = coopBankInstnId;
    }

    public String getCoopBankWltId() {
        return coopBankWltId;
    }

    public void setCoopBankWltId(String coopBankWltId) {
        this.coopBankWltId = coopBankWltId;
    }

    public String getCshBoxInstnId() {
        return cshBoxInstnId;
    }

    public void setCshBoxInstnId(String cshBoxInstnId) {
        this.cshBoxInstnId = cshBoxInstnId;
    }

    public String getInitlAmtCcy() {
        return initlAmtCcy;
    }

    public void setInitlAmtCcy(String initlAmtCcy) {
        this.initlAmtCcy = initlAmtCcy;
    }

    public String getInitlAmtValue() {
        return initlAmtValue;
    }

    public void setInitlAmtValue(String initlAmtValue) {
        this.initlAmtValue = initlAmtValue;
    }

    public String getCdtDbtInd() {
        return cdtDbtInd;
    }

    public void setCdtDbtInd(String cdtDbtInd) {
        this.cdtDbtInd = cdtDbtInd;
    }

    public String getDbtCntAmtCcy() {
        return dbtCntAmtCcy;
    }

    public void setDbtCntAmtCcy(String dbtCntAmtCcy) {
        this.dbtCntAmtCcy = dbtCntAmtCcy;
    }

    public String getDbtCntAmtValue() {
        return dbtCntAmtValue;
    }

    public void setDbtCntAmtValue(String dbtCntAmtValue) {
        this.dbtCntAmtValue = dbtCntAmtValue;
    }

    public String getCdtCntAmtCcy() {
        return cdtCntAmtCcy;
    }

    public void setCdtCntAmtCcy(String cdtCntAmtCcy) {
        this.cdtCntAmtCcy = cdtCntAmtCcy;
    }

    public String getCdtCntAmtValue() {
        return cdtCntAmtValue;
    }

    public void setCdtCntAmtValue(String cdtCntAmtValue) {
        this.cdtCntAmtValue = cdtCntAmtValue;
    }

    public String getFnlAmtCcy() {
        return fnlAmtCcy;
    }

    public void setFnlAmtCcy(String fnlAmtCcy) {
        this.fnlAmtCcy = fnlAmtCcy;
    }

    public String getFnlAmtValue() {
        return fnlAmtValue;
    }

    public void setFnlAmtValue(String fnlAmtValue) {
        this.fnlAmtValue = fnlAmtValue;
    }
}
