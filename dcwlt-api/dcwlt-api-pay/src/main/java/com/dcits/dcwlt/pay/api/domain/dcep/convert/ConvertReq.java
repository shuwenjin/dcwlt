package com.dcits.dcwlt.pay.api.domain.dcep.convert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;


import javax.validation.Valid;

public class ConvertReq {

    /**
     * 业务头组件
     */
    @Valid
    private GrpHdr grpHdr;

    /**
     * 交易信息
     */
    @Valid
    private TrxInfConvert trxInf;

    /**
     * 付款人信息
     */
    @Valid
    private ConvertDbtrInf dbtrInf;

    /**
     * 兑出钱包信息
     */
    @Valid
    private ConvertCdtrInf cdtrInf;

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "TrxInf")
    public TrxInfConvert getTrxInf() {
        return trxInf;
    }

    public void setTrxInf(TrxInfConvert trxInf) {
        this.trxInf = trxInf;
    }

    @JSONField(name = "DbtrInf")
    public ConvertDbtrInf getDbtrInf() {
        return dbtrInf;
    }

    public void setDbtrInf(ConvertDbtrInf dbtrInf) {
        this.dbtrInf = dbtrInf;
    }

    @JSONField(name = "CdtrInf")
    public ConvertCdtrInf getCdtrInf() {
        return cdtrInf;
    }

    public void setCdtrInf(ConvertCdtrInf cdtrInf) {
        this.cdtrInf = cdtrInf;
    }

    @Override
    public String toString() {
        return "ConvertReq [grpHdr=" + grpHdr + ", trxInf=" + trxInf + ", dbtrInf=" + dbtrInf + ", cdtrInf=" + cdtrInf
                + "]";
    }
}
