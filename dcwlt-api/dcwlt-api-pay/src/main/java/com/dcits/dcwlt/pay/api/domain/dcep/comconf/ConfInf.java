package com.dcits.dcwlt.pay.api.domain.dcep.comconf;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.constraints.NotBlank;

/**
 *
 * @Time 2021/1/1 12:48
 * @Version 1.0
 * Description:confirmationInformation,确认消息内容
 */
public class ConfInf {
    @NotBlank(message = "原报文的发起时间日期不能为空")
    private String origSndDtTm;                      //原报文的发起时间日期

    @NotBlank(message = "原报文标志号不能为空")
    private String orgnlMsgId;                       //原报文标志号

    @NotBlank(message = "原报文发起机构不能为空")
    private String orgnlInstgPty;                    //原报文发起机构

    @NotBlank(message = "原报文编号不能为空")
    private String orgnlMT;                          //原报文编号

    @NotBlank(message = "处理状态不能为空")
    private String prcSts;                          //固定填写PR00-报文接收成功

    private String remark;                          //备注

    public ConfInf() {
    }

    @JSONField(name = "OrigSndDtTm")
    public String getOrigSndDtTm() {
        return origSndDtTm;
    }

    public void setOrigSndDtTm(String origSndDtTm) {
        this.origSndDtTm = origSndDtTm;
    }

    @JSONField(name = "OrgnlMsgId")
    public String getOrgnlMsgId() {
        return orgnlMsgId;
    }

    public void setOrgnlMsgId(String orgnlMsgId) {
        this.orgnlMsgId = orgnlMsgId;
    }

    @JSONField(name = "OrgnlInstgPty")
    public String getOrgnlInstgPty() {
        return orgnlInstgPty;
    }

    public void setOrgnlInstgPty(String orgnlInstgPty) {
        this.orgnlInstgPty = orgnlInstgPty;
    }

    @JSONField(name = "OrgnlMT")
    public String getOrgnlMT() {
        return orgnlMT;
    }

    public void setOrgnlMT(String orgnlMT) {
        this.orgnlMT = orgnlMT;
    }

    @JSONField(name = "PrcSts")
    public String getPrcSts() {
        return prcSts;
    }

    public void setPrcSts(String prcSts) {
        this.prcSts = prcSts;
    }

    @JSONField(name = "Remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Override
    public String toString() {
        return "ConfInf{" +
                "origSndDtTm='" + origSndDtTm + '\'' +
                ", orgnlMsgId='" + orgnlMsgId + '\'' +
                ", orgnlInstgPty='" + orgnlInstgPty + '\'' +
                ", orgnlMT='" + orgnlMT + '\'' +
                ", prcSts='" + prcSts + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
