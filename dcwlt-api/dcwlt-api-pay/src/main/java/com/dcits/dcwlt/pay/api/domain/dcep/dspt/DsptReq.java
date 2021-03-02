package com.dcits.dcwlt.pay.api.domain.dcep.dspt;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;


import javax.validation.Valid;

/**
 * 差错调账请求报文
 */
public class DsptReq {

    /**
     * 业务头组件
     */
    @Valid
    private GrpHdr grpHdr;

    /**
     * 原报文信息
     */
    @Valid
    private OrgnlGrpHdr orgnlGrpHdr;

    /**
     * 差错处理信息
     */
    @Valid
    private DsptInf dsptInf;

    public DsptReq() {
    }

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "OrgnlGrpHdr")
    public OrgnlGrpHdr getOrgnlGrpHdr() {
        return orgnlGrpHdr;
    }

    public void setOrgnlGrpHdr(OrgnlGrpHdr orgnlGrpHdr) {
        this.orgnlGrpHdr = orgnlGrpHdr;
    }

    @JSONField(name = "DsptInf")
    public DsptInf getDsptInf() {
        return dsptInf;
    }

    public void setDsptInf(DsptInf dsptInf) {
        this.dsptInf = dsptInf;
    }

    @Override
    public String toString() {
        return "DsptReqDTO{" +
                "grpHdr=" + grpHdr +
                ", orgnlGrpHdr=" + orgnlGrpHdr +
                ", dsptInf=" + dsptInf +
                '}';
    }
}
