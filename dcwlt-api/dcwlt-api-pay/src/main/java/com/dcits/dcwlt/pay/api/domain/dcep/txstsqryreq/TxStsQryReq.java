package com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;


import javax.validation.Valid;

/**
 * 交易状态查询请求报文
 */
public class TxStsQryReq {
    /**
     * 业务头组件
     */
    @Valid
    private GrpHdr grpHdr;
    /**
     * 原报文主键组件
     */
    @Valid
    private TxStsQryOrgnlGrpHdr orgnlGrpHdr;

    public static TxStsQryReq getInstance(GrpHdr grpHdr, TxStsQryOrgnlGrpHdr orgnlGrpHdr) {
        TxStsQryReq txStsQryReq = new TxStsQryReq();
        txStsQryReq.setGrpHdr(grpHdr);
        txStsQryReq.setOrgnlGrpHdr(orgnlGrpHdr);
        return txStsQryReq;
    }

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "OrgnlGrpHdr")
    public TxStsQryOrgnlGrpHdr getOrgnlGrpHdr() {
        return orgnlGrpHdr;
    }

    public void setOrgnlGrpHdr(TxStsQryOrgnlGrpHdr orgnlGrpHdr) {
        this.orgnlGrpHdr = orgnlGrpHdr;
    }

    @Override
    public String toString() {
        return "TxStsQryReq{" +
                "grpHdr=" + grpHdr +
                ", orgnlGrpHdr=" + orgnlGrpHdr +
                '}';
    }
}
