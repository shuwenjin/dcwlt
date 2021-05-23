package com.dcits.dcwlt.pay.api.domain.ecny.cashbox;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;

import javax.validation.Valid;

/**
 * 钱柜入库出库申请报文
 *
 *
 *
 */
public class CshBoxAdjAppl {
    /**
     * 业务头组件
     */
    @Valid
    private GrpHdr grpHdr;

    /**
     * 入库出库信息
     */
    @Valid
    private AdjInf adjInf;

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "AdjInf")
    public AdjInf getAdjInf() {
        return adjInf;
    }

    public void setAdjInf(AdjInf adjInf) {
        this.adjInf = adjInf;
    }


    @Override
    public String toString() {
        return "CshBoxAdjAppl [ " +
                "grpHdr=" + grpHdr +
                ", adjInf=" + adjInf +
                "]";
    }
}
