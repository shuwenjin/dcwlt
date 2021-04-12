package com.dcits.dcwlt.pay.api.domain.dcep.transdetailquery;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq.BizQryRef;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq.OprlErr;


/**
 * 交易明细查询响应报文体
 */
public class TxDtlQryRsp {
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
     * 处理状态为 PR00 时填写
     */
    private TxDtlQryBizRpt bizRpt;
    /**
     * 应答拒绝信息
     * 处理状态为 PR01 时填写
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
    public TxDtlQryBizRpt getBizRpt() {
        return bizRpt;
    }

    public void setBizRpt(TxDtlQryBizRpt bizRpt) {
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
        return "TxStsQryRspDTO [ " +
                "grpHdr=" + grpHdr +
                ", bizQryRef=" + bizQryRef +
                ", bizRpt=" + bizRpt +
                ", oprlErr=" + oprlErr +
                " ] ";
    }
}
