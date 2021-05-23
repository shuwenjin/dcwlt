package com.dcits.dcwlt.pay.api.domain.dcep.cert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;

import javax.validation.Valid;

/**
 * 兑回业务请求报文
 *
 */
public class CertNtfctn {
    /**
     * 业务头组件
     */
    @Valid
    private GrpHdr grpHdr;
    /**
     * 交易信息
     */
    @Valid
    private CertNtfctnInf certNtfctnInf;

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "CertNtfctnInf")
    public CertNtfctnInf getCertNtfctnInf() {
        return certNtfctnInf;
    }

    public void setCertNtfctnInf(CertNtfctnInf certNtfctnInf) {
        this.certNtfctnInf = certNtfctnInf;
    }

    @Override
    public String toString() {
        return "ReconvertReq [ " +
                "grpHdr=" + grpHdr +
                ", certNtfctnInf=" + certNtfctnInf +
                "]";
    }
}
