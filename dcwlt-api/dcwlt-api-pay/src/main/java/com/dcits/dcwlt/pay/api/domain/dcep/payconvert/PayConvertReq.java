package com.dcits.dcwlt.pay.api.domain.dcep.payconvert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;


import javax.validation.Valid;

public class PayConvertReq {

    /**
     * 业务头组件
     */
    @Valid
    private GrpHdr grpHdr;

    /**
     * 交易信息
     */
    @Valid
    private TrxInf trxInf;

    /**
     * 付款人信息
     */
    @Valid
    private DbtrInf dbtrInf;

    /**
     * 兑出钱包信息
     */
    @Valid
    private CdtrInf cdtrInf;

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "TrxInf")
    public TrxInf getTrxInf() {
        return trxInf;
    }

    public void setTrxInf(TrxInf trxInf) {
        this.trxInf = trxInf;
    }

    @JSONField(name = "DbtrInf")
    public DbtrInf getDbtrInf() {
        return dbtrInf;
    }

    public void setDbtrInf(DbtrInf dbtrInf) {
        this.dbtrInf = dbtrInf;
    }

    @JSONField(name = "CdtrInf")
    public CdtrInf getCdtrInf() {
        return cdtrInf;
    }

    public void setCdtrInf(CdtrInf cdtrInf) {
        this.cdtrInf = cdtrInf;
    }

    @Override
    public String toString() {
        return "ConvertReq [grpHdr=" + grpHdr + ", trxInf=" + trxInf + ", dbtrInf=" + dbtrInf + ", cdtrInf=" + cdtrInf
                + "]";
    }
}
