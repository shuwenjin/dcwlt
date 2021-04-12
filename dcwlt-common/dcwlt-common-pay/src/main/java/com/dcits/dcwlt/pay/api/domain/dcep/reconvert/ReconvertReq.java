package com.dcits.dcwlt.pay.api.domain.dcep.reconvert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;


import javax.validation.Valid;

/**
 * 兑回业务请求报文
 *
 */
public class ReconvertReq {
    /**
     * 业务头组件
     */
    @Valid
    private GrpHdr grpHdr;
    /**
     * 交易信息
     */
    @Valid
    private TrxInfReconvert trxInf;
    /**
     * 兑回钱包信息
     */
    @Valid
    private ReconvertDbtrInf dbtrInf;
    /**
     * 收款人信息
     */
    @Valid
    private ReconvertCdtrInf cdtrInf;

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "TrxInf")
    public TrxInfReconvert getTrxInf() {
        return trxInf;
    }

    public void setTrxInf(TrxInfReconvert trxInf) {
        this.trxInf = trxInf;
    }

    @JSONField(name = "DbtrInf")
    public ReconvertDbtrInf getDbtrInf() {
        return dbtrInf;
    }

    public void setDbtrInf(ReconvertDbtrInf dbtrInf) {
        this.dbtrInf = dbtrInf;
    }

    @JSONField(name = "CdtrInf")
    public ReconvertCdtrInf getCdtrInf() {
        return cdtrInf;
    }

    public void setCdtrInf(ReconvertCdtrInf cdtrInf) {
        this.cdtrInf = cdtrInf;
    }

    @Override
    public String toString() {
        return "ReconvertReq [ " +
                "grpHdr=" + grpHdr +
                ", trxInf=" + trxInf +
                ", dbtrInf=" + dbtrInf +
                ", cdtrInf=" + cdtrInf +
                "]";
    }
}
