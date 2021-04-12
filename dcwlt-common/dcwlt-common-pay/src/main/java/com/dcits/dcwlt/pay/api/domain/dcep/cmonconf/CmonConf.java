package com.dcits.dcwlt.pay.api.domain.dcep.cmonconf;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;


import javax.validation.Valid;

/**
 *
 * @desc 通用处理确认实体
 */
public class CmonConf {
    /*
     * 业务头组件
     * */
    @Valid
    private GrpHdr grpHdr;
    /*
     * 原报文主键组件
     * */
    @Valid
    private OrgnlGrpHdr orgnlGrpHdr;
    /*
     * 通用处理确认信息
     * */
    @Valid
    private CmonConfInf cmonConfInf;

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

    @JSONField(name = "CmonConfInf")
    public CmonConfInf getCmonConfInf() {
        return cmonConfInf;
    }

    public void setCmonConfInf(CmonConfInf cmonConfInf) {
        this.cmonConfInf = cmonConfInf;
    }


    @Override
    public String toString() {
        return "CmonConf{" +
                "grpHdr=" + grpHdr +
                ", orgnlGrpHdr=" + orgnlGrpHdr +
                ", cmonConfInf=" + cmonConfInf +
                '}';
    }
}
