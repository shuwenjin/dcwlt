package com.dcits.dcwlt.pay.api.domain.dcep.txstsqry;

/**
 * 原应答的业务信息
 */
public class BusinessRpt {
    /**
     * 原业务状态
     */
    private String trnRs;
    /**
     * 平台处理码：由平台处理，运营机构无需填写
     */
    private String prcCd;

    /**
     * 业务拒绝码：当原业务状态为“PR01”时必填
     */
    private String rjctCd;

    /**
     * 业务拒绝信息
     */
    private String rjctInf;

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

    public String getTrnRs() {
        return trnRs;
    }

    public void setTrnRs(String trnRs) {
        this.trnRs = trnRs;
    }

    public String getPrcCd() {
        return prcCd;
    }

    public void setPrcCd(String prcCd) {
        this.prcCd = prcCd;
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

    public String getOrgnlMsgId() {
        return orgnlMsgId;
    }

    public void setOrgnlMsgId(String orgnlMsgId) {
        this.orgnlMsgId = orgnlMsgId;
    }

    public String getOrgnlInstgPty() {
        return orgnlInstgPty;
    }

    public void setOrgnlInstgPty(String orgnlInstgPty) {
        this.orgnlInstgPty = orgnlInstgPty;
    }

    public String getOrgnlMT() {
        return orgnlMT;
    }

    public void setOrgnlMT(String orgnlMT) {
        this.orgnlMT = orgnlMT;
    }

    public String getOrgnlBizTp() {
        return orgnlBizTp;
    }

    public void setOrgnlBizTp(String orgnlBizTp) {
        this.orgnlBizTp = orgnlBizTp;
    }

    public String getOrgnlBizKind() {
        return orgnlBizKind;
    }

    public void setOrgnlBizKind(String orgnlBizKind) {
        this.orgnlBizKind = orgnlBizKind;
    }

    public String getOrgnlBatchId() {
        return orgnlBatchId;
    }

    public void setOrgnlBatchId(String orgnlBatchId) {
        this.orgnlBatchId = orgnlBatchId;
    }

    public String getDbtrBankId() {
        return dbtrBankId;
    }

    public void setDbtrBankId(String dbtrBankId) {
        this.dbtrBankId = dbtrBankId;
    }

    public String getCdtrBankId() {
        return cdtrBankId;
    }

    public void setCdtrBankId(String cdtrBankId) {
        this.cdtrBankId = cdtrBankId;
    }

    public String getdC() {
        return dC;
    }

    public void setdC(String dC) {
        this.dC = dC;
    }

    @Override
    public String toString() {
        return "BizRpt{" +
                "trnRs='" + trnRs + '\'' +
                ", prcCd='" + prcCd + '\'' +
                ", rjctCd='" + rjctCd + '\'' +
                ", rjctInf='" + rjctInf + '\'' +
                ", orgnlMsgId='" + orgnlMsgId + '\'' +
                ", orgnlInstgPty='" + orgnlInstgPty + '\'' +
                ", orgnlMT='" + orgnlMT + '\'' +
                ", orgnlBizTp='" + orgnlBizTp + '\'' +
                ", orgnlBizKind='" + orgnlBizKind + '\'' +
                ", orgnlBatchId='" + orgnlBatchId + '\'' +
                ", dbtrBankId='" + dbtrBankId + '\'' +
                ", cdtrBankId='" + cdtrBankId + '\'' +
                ", dC='" + dC + '\'' +
                '}';
    }
}
