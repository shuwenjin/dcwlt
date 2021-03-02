package com.dcits.dcwlt.pay.api.domain.dcep.payconvert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement.BankAttRspsnInf;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;


public class PayConvertRsp {

    private GrpHdr grpHdr;				//业务头组件

    private OrgnlGrpHdr orgnlGrpHdr;	//原报文主键组件

    private BankAttRspsnInf rspsnInf;			//响应信息

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
    public BankAttRspsnInf getRspsnInf() {
        return rspsnInf;
    }

    public void setRspsnInf(BankAttRspsnInf rspsnInf) {
        this.rspsnInf = rspsnInf;
    }

    @Override
    public String toString() {
        return "DCEPRspBody [grpHdr=" + grpHdr + ", orgnlGrpHdr=" + orgnlGrpHdr + ", rspsnInf=" + rspsnInf + "]";
    }
}
