package com.dcits.dcwlt.pay.api.domain.dcep.txendntfcnt;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;


import javax.validation.Valid;

/**
 * 终态通知请求报文
 */
public class TxEndNtfctn {

    /**
     * 业务头组件
     */
    @Valid
    private GrpHdr grpHdr;
    /**
     * 原报文主键组件
     */
    @Valid
    private OrgnlGrpHdr orgnlGrpHdr;
    /**
     * 终态信息
     */
    @Valid
    private OrgnlMsgCntt orgnlMsgCntt;

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "OrgnlGrpHd")
    public OrgnlGrpHdr getOrgnlGrpHdr() {
        return orgnlGrpHdr;
    }

    public void setOrgnlGrpHdr(OrgnlGrpHdr orgnlGrpHdr) {
        this.orgnlGrpHdr = orgnlGrpHdr;
    }

    @JSONField(name = "OrgnlMsgCntt")
    public OrgnlMsgCntt getOrgnlMsgCntt() {
        return orgnlMsgCntt;
    }

    public void setOrgnlMsgCntt(OrgnlMsgCntt orgnlMsgCntt) {
        this.orgnlMsgCntt = orgnlMsgCntt;
    }

    @Override
    public String toString() {
        return "TxEndNtfctn{" +
                "grpHdr=" + grpHdr +
                ", orgnlGrpHdr=" + orgnlGrpHdr +
                ", orgnlMsgCntt=" + orgnlMsgCntt +
                '}';
    }
}
