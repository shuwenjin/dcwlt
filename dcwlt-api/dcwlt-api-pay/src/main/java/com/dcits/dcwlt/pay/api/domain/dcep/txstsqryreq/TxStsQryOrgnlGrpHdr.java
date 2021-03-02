package com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq;

import com.alibaba.fastjson.annotation.JSONField;

import com.dcits.dcwlt.common.pay.util.HiddenUtil;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 原报文主键组件
 *
 */
public class TxStsQryOrgnlGrpHdr {

    /**
     * 原报文标识号
     */
    @NotBlank(message = "原报文标识号不能为空")
    @Length(max = 35)
    private String orgnlMsgId;

    /**
     * 原发起机构
     */
    @NotBlank(message = "原发起机构不能为空")
    @Length(max = 14)
    private String orgnlInstgPty;

    /**
     * 原报文编号
     */
    @NotBlank(message = "原报文编号不能为空")
    @Length(max = 15)
    private String orgnlMT;

    /**
     * 原业务类型
     */
    @NotBlank
    @Length(max = 4)
    private String orgnlBizTp;

    /**
     * 原业务种类
     */
    @NotBlank
    @Length(max = 5)
    private String orgnlBizKind;

    /**
     * 原付款人钱包Id
     */
    @Length(max = 34)
    private String orgnlDbtrWltId;

    /**
     * 原收款人钱包Id
     */
    @Length(max = 34)
    private String  orgnlCdtrWltId;

    public TxStsQryOrgnlGrpHdr() {
        super();
    }

    public TxStsQryOrgnlGrpHdr(String orgnlMsgId, String orgnlInstgPty, String orgnlMT) {
        super();
        this.orgnlMsgId = orgnlMsgId;
        this.orgnlInstgPty = orgnlInstgPty;
        this.orgnlMT = orgnlMT;
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

    @JSONField(name = "OrgnlBizTp")
    public String getOrgnlBizTp() {
        return orgnlBizTp;
    }

    public void setOrgnlBizTp(String orgnlBizTp) {
        this.orgnlBizTp = orgnlBizTp;
    }

    @JSONField(name = "OrgnlBizKind")
    public String getOrgnlBizKind() {
        return orgnlBizKind;
    }

    public void setOrgnlBizKind(String orgnlBizKind) {
        this.orgnlBizKind = orgnlBizKind;
    }

    @JSONField(name = "OrgnlDbtrWltId")
    public String getOrgnlDbtrWltId() {
        return orgnlDbtrWltId;
    }

    public void setOrgnlDbtrWltId(String orgnlDbtrWltId) {
        this.orgnlDbtrWltId = orgnlDbtrWltId;
    }

    @JSONField(name = "OrgnlCdtrWltId")
    public String getOrgnlCdtrWltId() {
        return orgnlCdtrWltId;
    }

    public void setOrgnlCdtrWltId(String orgnlCdtrWltId) {
        this.orgnlCdtrWltId = orgnlCdtrWltId;
    }

    @Override
    public String toString() {
        return "OrgnlGrpHdr [" +
                "orgnlMsgId='" + orgnlMsgId + '\'' +
                ", orgnlInstgPty='" + orgnlInstgPty + '\'' +
                ", orgnlMT='" + orgnlMT + '\'' +
                ", orgnlBizTp='" + orgnlBizTp + '\'' +
                ", orgnlBizKind='" + orgnlBizKind + '\'' +
                ", orgnlDbtrWltId='" + HiddenUtil.acHidden(orgnlDbtrWltId)+ '\'' +
                ", orgnlCdtrWltId='" + HiddenUtil.acHidden(orgnlCdtrWltId) + '\'' +
                ']';
    }
}
