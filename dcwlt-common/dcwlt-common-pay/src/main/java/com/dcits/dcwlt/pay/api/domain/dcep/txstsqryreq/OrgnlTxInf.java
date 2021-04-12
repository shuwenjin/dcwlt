package com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 原业务信息
 */
public class OrgnlTxInf {

    /**
     * 原报文标识号
     */
    private String orgnlMsgId;

    /**
     * 原发起机构
     */
    private String orgnlInstgPty;

    /**
     * 原报文编号
     */
    private String orgnlMT;

    /**
     * 原业务类型
     */
    private String orgnlBizTp;

    /**
     * 原业务种类
     */
    private String orgnlBizKind;

    /**
     * 原交易批次号
     */
    private String orgnlBatchId;

    /**
     * 付款机构流水号
     */
    private String dbtrBankId;

    /**
     * 收款机构流水号
     */
    private String cdtrBankId;

    /**
     * 数字货币
     */
    private String dC;

    @JSONField(name = "OrgnlMsgId")
    public String getOrgnlMsgId() {
        return orgnlMsgId;
    }

    public void setOrgnlMsgId(String orgnlMsgId) {
        this.orgnlMsgId = orgnlMsgId;
    }

    @JSONField(name = "OrgnlInstgPty")
    public String getOrgnlInstgPty() {
        return orgnlInstgPty;
    }

    public void setOrgnlInstgPty(String orgnlInstgPty) {
        this.orgnlInstgPty = orgnlInstgPty;
    }

    @JSONField(name = "OrgnlMT")
    public String getOrgnlMT() {
        return orgnlMT;
    }

    public void setOrgnlMT(String orgnlMT) {
        this.orgnlMT = orgnlMT;
    }

    @JSONField(name = "OrgnlBizTp")
    public String getOrgnlBizTp() {
        return orgnlBizTp;
    }

    public void setOrgnlBizTp(String orgnlBizTp) {
        this.orgnlBizTp = orgnlBizTp;
    }

    @JSONField(name = "OrgnlBizKind")
    public String getOrgnlBizKind() {
        return orgnlBizKind;
    }

    public void setOrgnlBizKind(String orgnlBizKind) {
        this.orgnlBizKind = orgnlBizKind;
    }

    @JSONField(name = "OrgnlBatchId")
    public String getOrgnlBatchId() {
        return orgnlBatchId;
    }

    public void setOrgnlBatchId(String orgnlBatchId) {
        this.orgnlBatchId = orgnlBatchId;
    }

    @JSONField(name = "DbtrBankId")
    public String getDbtrBankId() {
        return dbtrBankId;
    }

    public void setDbtrBankId(String dbtrBankId) {
        this.dbtrBankId = dbtrBankId;
    }

    @JSONField(name = "CdtrBankId")
    public String getCdtrBankId() {
        return cdtrBankId;
    }

    public void setCdtrBankId(String cdtrBankId) {
        this.cdtrBankId = cdtrBankId;
    }

    @JSONField(name = "DC")
    public String getdC() {
        return dC;
    }

    public void setdC(String dC) {
        this.dC = dC;
    }

    @Override
    public String toString() {
        return "OrgnlTxInf [ " +
                "orgnlMsgId='" + orgnlMsgId + '\'' +
                ", orgnlInstgPty='" + orgnlInstgPty + '\'' +
                ", orgnlMT='" + orgnlMT + '\'' +
                ", orgnlBizTp='" + orgnlBizTp + '\'' +
                ", orgnlBizKind='" + orgnlBizKind + '\'' +
                ", orgnlBatchId='" + orgnlBatchId + '\'' +
                ", dbtrBankId='" + dbtrBankId + '\'' +
                ", cdtrBankId='" + cdtrBankId + '\'' +
                ", dC='" + dC + '\'' +
                " ] ";
    }
}
