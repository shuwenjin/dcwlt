package com.dcits.dcwlt.pay.api.domain.dcep.transdetailquery;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.constraints.NotBlank;

/**
 * 原交易内容
 *
 *
 */
public class TxDtlQryOrgnlMsgCntt {
    /**
     * 原业务报文的原文
     * 加密处理并做base64
     */
    @NotBlank
    private String cntt;
    /**
     * 原报文标识号
     */
    @NotBlank
    private String orgnlMsgId;
    /**
     * 原发起机构
     */
    @NotBlank
    private String orgnlInstgPty;

    /**
     * 原报文编号
     */
    @NotBlank
    private String orgnlMT;

    @JSONField(name = "Cntt")
    public String getCntt() {
        return cntt;
    }

    public void setCntt(String cntt) {
        this.cntt = cntt;
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


    @Override
    public String toString() {
        return "OrgnlMsgCntt{" +
                ", orgnlMsgId='" + orgnlMsgId + '\'' +
                ", orgnlInstgPty='" + orgnlInstgPty + '\'' +
                ", orgnlMT='" + orgnlMT + '\'' +
                '}';
    }
}
