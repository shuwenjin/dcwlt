package com.dcits.dcwlt.pay.api.domain.dcep.resendapply;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @date  2021/1/5
 * @version 1.0.0
 * <p>重发申请请求报文</p>
 */
public class ApplMsg {

    /**
     * 业务头组件
     */
    @NotNull(message = "业务头组件不能为空")
    @Valid
    private GrpHdr grpHdr;

    /**
     * 申请信息
     */
    @NotNull(message = "申请信息不能为空")
    @Valid
    private ApplInf applInf;

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "ApplInf")
    public ApplInf getApplInf() {
        return applInf;
    }

    public void setApplInf(ApplInf applInf) {
        this.applInf = applInf;
    }

    @Override
    public String toString() {
        return "ApplMsg{" +
                "grpHdr=" + grpHdr +
                ", applInf=" + applInf +
                '}';
    }
}
