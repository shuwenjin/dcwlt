package com.dcits.dcwlt.pay.api.model;

import com.dcits.dcwlt.common.core.annotation.Excel;

public class PayCashboxProcessDO {
    @Excel(name = "id")
    protected String id;
    @Excel(name = "入库出库类型")
    protected String oprTp;
    @Excel(name = "入库出库借贷标识")
    protected String cdtDbtInd;
    @Excel(name = "出入库金额货币符号")
    protected String amtCcy;
    @Excel(name = "出入库金额")
    protected String amtValue;
    @Excel(name = "合作银行机构编码")
    protected String coopBankInstnId;
    @Excel(name = "合作银行钱柜ID")
    protected String coopBankWltId;
    @Excel(name = "钱柜所属运营机构")
    protected String cshBoxInstnId;
    @Excel(name = "额度凭证")
    protected String cert;
    @Excel(name = "报文标识号")
    protected String msgId;
    @Excel(name = "业务请求时间")
    protected String creDtTmS;
    @Excel(name = "业务响应时间")
    protected String creDtTmR;
    @Excel(name = "业务状态")
    protected String prcSts;
    @Excel(name = "业务回执状态")
    protected String prcCd;
    @Excel(name = "业务回执状态")
    protected String rspsnSts;
    @Excel(name = "业务拒绝")
    protected String rjctCd;
    @Excel(name = "业务拒绝信息")
    protected String rjctInf;
    @Excel(name = "清算报文标识号")
    protected String clrReptFlg;
    @Excel(name = "清算金额货币符号")
    protected String clearAmountCcy;
    @Excel(name = "清算金额")
    protected String clearAmountValue;
    @Excel(name = "核心处理状态")
    protected String coreSts;
    protected String coreBatchId;
    @Excel(name = "核心流水号")
    protected String corepaySerno;
    @Excel(name = "核心交易时间")
    protected String corePaydate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOprTp() {
        return oprTp;
    }

    public void setOprTp(String oprTp) {
        this.oprTp = oprTp;
    }

    public String getCdtDbtInd() {
        return cdtDbtInd;
    }

    public void setCdtDbtInd(String cdtDbtInd) {
        this.cdtDbtInd = cdtDbtInd;
    }

    public String getAmtCcy() {
        return amtCcy;
    }

    public void setAmtCcy(String amtCcy) {
        this.amtCcy = amtCcy;
    }

    public String getAmtValue() {
        return amtValue;
    }

    public void setAmtValue(String amtValue) {
        this.amtValue = amtValue;
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

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getCreDtTmS() {
        return creDtTmS;
    }

    public void setCreDtTmS(String creDtTmS) {
        this.creDtTmS = creDtTmS;
    }

    public String getCreDtTmR() {
        return creDtTmR;
    }

    public void setCreDtTmR(String creDtTmR) {
        this.creDtTmR = creDtTmR;
    }

    public String getPrcSts() {
        return prcSts;
    }

    public void setPrcSts(String prcSts) {
        this.prcSts = prcSts;
    }

    public String getPrcCd() {
        return prcCd;
    }

    public void setPrcCd(String prcCd) {
        this.prcCd = prcCd;
    }

    public String getRspsnSts() {
        return rspsnSts;
    }

    public void setRspsnSts(String rspsnSts) {
        this.rspsnSts = rspsnSts;
    }

    public String getRjctCd() {
        return rjctCd;
    }

    public void setRjctCd(String rjctCd) {
        this.rjctCd = rjctCd;
    }

    public String getRjctInf() {
        return rjctInf;
    }

    public void setRjctInf(String rjctInf) {
        this.rjctInf = rjctInf;
    }

    public String getClrReptFlg() {
        return clrReptFlg;
    }

    public void setClrReptFlg(String clrReptFlg) {
        this.clrReptFlg = clrReptFlg;
    }

    public String getClearAmountCcy() {
        return clearAmountCcy;
    }

    public void setClearAmountCcy(String clearAmountCcy) {
        this.clearAmountCcy = clearAmountCcy;
    }

    public String getClearAmountValue() {
        return clearAmountValue;
    }

    public void setClearAmountValue(String clearAmountValue) {
        this.clearAmountValue = clearAmountValue;
    }

    public String getCoreSts() {
        return coreSts;
    }

    public void setCoreSts(String coreSts) {
        this.coreSts = coreSts;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getCoreBatchId() {
        return coreBatchId;
    }

    public void setCoreBatchId(String coreBatchId) {
        this.coreBatchId = coreBatchId;
    }

    public String getCorepaySerno() {
        return corepaySerno;
    }

    public void setCorepaySerno(String corepaySerno) {
        this.corepaySerno = corepaySerno;
    }

    public String getCorePaydate() {
        return corePaydate;
    }

    public void setCorePaydate(String corePaydate) {
        this.corePaydate = corePaydate;
    }

    @Override
    public String toString() {
        return "PayCashboxProcessDO{" +
                "id='" + id + "'," +
                "oprTp='" + oprTp + "'," +
                "cdtDbtInd='" + cdtDbtInd + "'," +
                "amtCcy='" + amtCcy + "'," +
                "amtValue='" + amtValue + "'," +
                "coopBankInstnId='" + coopBankInstnId + "'," +
                "coopBankWltId='" + coopBankWltId + "'," +
                "cshBoxInstnId='" + cshBoxInstnId + "'," +
                "cert='" + cert + "'," +
                "msgId='" + msgId + "'," +
                "creDtTmS='" + creDtTmS + "'," +
                "creDtTmR='" + creDtTmR + "'," +
                "prcSts='" + prcSts + "'," +
                "prcCd='" + prcCd + "'," +
                "rspsnSts='" + rspsnSts + "'," +
                "rjctCd='" + rjctCd + "'," +
                "rjctInf='" + rjctInf + "'," +
                "clrReptFlg='" + clrReptFlg + "'," +
                "clearAmountCcy='" + clearAmountCcy + "'," +
                "clearAmountValue='" + clearAmountValue + "'," +
                "coreSts='" + coreSts + "'" +
                '}';
    }
}
