package com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;


public class TxStsQryRsp {

    /**
     * 业务头组件
     */
    private GrpHdr grpHdr;
    /**
     * 原查询结果
     */
    private BizQryRef bizQryRef;
    /**
     * 应答的原业务信息
     */
    private BizRpt bizRpt;
    /**
     * 应答拒绝信息
     */
    private OprlErr oprlErr;

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "BizQryRef")
    public BizQryRef getBizQryRef() {
        return bizQryRef;
    }

    public void setBizQryRef(BizQryRef bizQryRef) {
        this.bizQryRef = bizQryRef;
    }

    @JSONField(name = "BizRpt")
    public BizRpt getBizRpt() {
        return bizRpt;
    }

    public void setBizRpt(BizRpt bizRpt) {
        this.bizRpt = bizRpt;
    }

    @JSONField(name = "OprlErr")
    public OprlErr getOprlErr() {
        return oprlErr;
    }

    public void setOprlErr(OprlErr oprlErr) {
        this.oprlErr = oprlErr;
    }

    @Override
    public String toString() {
        return "TxStsQryRsp{" +
                "grpHdr=" + grpHdr +
                ", bizQryRef=" + bizQryRef +
                ", bizRpt=" + bizRpt +
                ", oprlErr=" + oprlErr +
                '}';
    }
}
