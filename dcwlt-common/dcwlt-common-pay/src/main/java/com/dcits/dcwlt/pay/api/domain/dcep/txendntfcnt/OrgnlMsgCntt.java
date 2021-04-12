package com.dcits.dcwlt.pay.api.domain.dcep.txendntfcnt;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 终态通知信息
 *
 */
public class OrgnlMsgCntt {
    /**
     * 原业务报文的原文
     */
    @NotBlank(message = "原业务报文不能为空")
    private String cntt;
    /**
     * 业务状态
     */
    @NotBlank(message = "业务状态不能为空")
    @EnumValue(value = ProcessStsCdEnum.class, message = "业务状态有误")
    private String prcSts;
    /**
     * 业务状态码
     */
    @NotBlank(message = "业务状态码不能为空")
    @Length(max = 10)
    private String prcCd;
    /**
     * 机构业务拒绝码
     */
    private String rjctCd;
    /**
     * 业务拒绝信息
     */
    private String rjctInf;

    @JSONField(name = "cntt")
    public String getCntt() {
        return cntt;
    }

    public void setCntt(String cntt) {
        this.cntt = cntt;
    }

    @JSONField(name = "PrcSts")
    public String getPrcSts() {
        return prcSts;
    }

    public void setPrcSts(String prcSts) {
        this.prcSts = prcSts;
    }

    @JSONField(name = "PrcCd")
    public String getPrcCd() {
        return prcCd;
    }

    public void setPrcCd(String prcCd) {
        this.prcCd = prcCd;
    }

    @JSONField(name = "RjctCd")
    public String getRjctCd() {
        return rjctCd;
    }

    public void setRjctCd(String rjctCd) {
        this.rjctCd = rjctCd;
    }

    @JSONField(name = "RjctInf")
    public String getRjctInf() {
        return rjctInf;
    }

    public void setRjctInf(String rjctInf) {
        this.rjctInf = rjctInf;
    }

    @Override
    public String toString() {
        return "OrgnlMsgCntt{" +
                ", prcSts='" + prcSts + '\'' +
                ", prcCd='" + prcCd + '\'' +
                ", rjctCd='" + rjctCd + '\'' +
                ", rjctInf='" + rjctInf + '\'' +
                '}';
    }
}
