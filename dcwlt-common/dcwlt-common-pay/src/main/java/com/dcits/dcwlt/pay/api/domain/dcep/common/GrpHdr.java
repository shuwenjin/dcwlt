package com.dcits.dcwlt.pay.api.domain.dcep.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.common.pay.validator.annotation.ISODateTime;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.dcits.dcwlt.common.core.utils.DateUtils;

/**
 * 业务头组件
 *
 *
 */
public class GrpHdr {

    /**
     * 报文标识号
     */
    @NotBlank(message = "报文标识号不能为空")
    @Length(max = 35)
    private String msgId;

    /**
     * 业务处理时间
     */
    @NotBlank(message = "业务处理时间不能为空")
    @ISODateTime
    private String creDtTm;

    /**
     * 发起机构
     */
    @NotNull(message = "发起机构不能为空")
    @Valid
    private InstgPty instgPty;

    /**
     * 接收机构
     */
    @NotNull(message = "接收机构不能为空")
    @Valid
    private InstdPty instdPty;

    /**
     * 备注
     */
    private String rmk;

    /**
     * 获取业务报文头
     *
     * @param msgId
     * @param receiver
     * @return
     */
    public static GrpHdr getInstance(String msgId, String receiver) {
        GrpHdr grpHdr = new GrpHdr();
        grpHdr.setMsgId(msgId);
        grpHdr.setCreDtTm(DateUtil.getISODateTime());
        grpHdr.setInstdPty(new InstdPty(receiver));
        grpHdr.setInstgPty(new InstgPty(AppConstant.CGB_FINANCIAL_INSTITUTION_CD));
        return grpHdr;
    }

    /**
     * 获取业务报文头
     *
     * @param origGrpHdr
     * @return
     */
    public static GrpHdr getInstance(GrpHdr origGrpHdr) {
        GrpHdr grpHdr = new GrpHdr();
        grpHdr.setMsgId(origGrpHdr.getMsgId());
        grpHdr.setCreDtTm(DateUtil.formatISODateTimeToDate(origGrpHdr.getCreDtTm()));
        grpHdr.setInstdPty(new InstdPty(origGrpHdr.getInstgPty().getInstgDrctPty()));
        grpHdr.setInstgPty(new InstgPty(origGrpHdr.getInstdPty().getInstdDrctPty()));
        grpHdr.setRmk(origGrpHdr.getRmk());
        return grpHdr;
    }

    public static GrpHdr getInstance(String msgId) {
        GrpHdr grpHdr = new GrpHdr();
        grpHdr.setMsgId(msgId);
        grpHdr.setCreDtTm(grpHdr.getCreDtTm());
        grpHdr.setInstdPty(new InstdPty(AppConstant.NET_PARTY_ID));
        grpHdr.setInstgPty(new InstgPty(AppConstant.CGB_FINANCIAL_INSTITUTION_CD));
        return grpHdr;
    }


    @JSONField(name = "MsgId")
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @JSONField(name = "CreDtTm")
    public String getCreDtTm() {
        return creDtTm;
    }

    public void setCreDtTm(String creDtTm) {
        this.creDtTm = creDtTm;
    }

    @JSONField(name = "InstgPty")
    public InstgPty getInstgPty() {
        return instgPty;
    }

    public void setInstgPty(InstgPty instgPty) {
        this.instgPty = instgPty;
    }

    @JSONField(name = "InstdPty")
    public InstdPty getInstdPty() {
        return instdPty;
    }

    public void setInstdPty(InstdPty instdPty) {
        this.instdPty = instdPty;
    }

    @JSONField(name = "Rmk")
    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    @Override
    public String toString() {
        return "GrpHdr [msgId=" + msgId + ", creDtTm=" + creDtTm + ", instgPty=" + instgPty + ", instdPty=" + instdPty
                + ", rmk=" + rmk + "]";
    }

}
