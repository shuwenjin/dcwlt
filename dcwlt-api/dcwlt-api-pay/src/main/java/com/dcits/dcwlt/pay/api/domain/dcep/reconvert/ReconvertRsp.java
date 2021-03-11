package com.dcits.dcwlt.pay.api.domain.dcep.reconvert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement.BankAttRspsnInf;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.RspsnInf;


/**
 * 兑回业务响应报文
 *
 */
public class ReconvertRsp {

    private GrpHdr grpHdr;				//业务头组件

    private OrgnlGrpHdr orgnlGrpHdr;	//原报文主键组件

    private RspsnInf rspsnInf;			//响应信息

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

    @JSONField(name = "RspsnInf")
    public RspsnInf getRspsnInf() {
        return rspsnInf;
    }

    public void setRspsnInf(RspsnInf rspsnInf) {
        this.rspsnInf = rspsnInf;
    }

    @Override
    public String toString() {
        return "ReconvertRsp{" +
                "grpHdr=" + grpHdr +
                ", orgnlGrpHdr=" + orgnlGrpHdr +
                ", rspsnInf=" + rspsnInf +
                '}';
    }
}
