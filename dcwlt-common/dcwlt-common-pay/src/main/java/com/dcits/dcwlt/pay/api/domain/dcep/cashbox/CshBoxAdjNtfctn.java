package com.dcits.dcwlt.pay.api.domain.dcep.cashbox;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;

import javax.validation.Valid;

/**
 * 钱柜入库出库通知报文
 *
 */
public class CshBoxAdjNtfctn {
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
     * ResponsionInformation
     */
    @Valid
    private CashboxRspsnInf rspsnInf;

    /**
     * 入库出库信息
     */
    @Valid
    private AdjInf adjInf;

    /**
     * 大额清算信息
     */
    @Valid
    private ClrInf clrInf;

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
    public CashboxRspsnInf getRspsnInf() {
        return rspsnInf;
    }

    public void setRspsnInf(CashboxRspsnInf rspsnInf) {
        this.rspsnInf = rspsnInf;
    }

    @JSONField(name = "AdjInf")
    public AdjInf getAdjInf() {
        return adjInf;
    }

    public void setAdjInf(AdjInf adjInf) {
        this.adjInf = adjInf;
    }

    @JSONField(name = "ClrInf")
    public ClrInf getClrInf() {
        return clrInf;
    }

    public void setClrInf(ClrInf clrInf) {
        this.clrInf = clrInf;
    }

    @Override
    public String toString() {
        return "CshBoxAdjNtfctn [grpHdr=" + grpHdr + ", orgnlGrpHdr=" + orgnlGrpHdr + ", rspsnInf=" + rspsnInf + ", adjInf=" + adjInf + ", clrInf=" + clrInf + "]";
    }
}
