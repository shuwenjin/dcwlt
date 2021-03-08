package com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.enums.ManagementTpCdEnum;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;

import javax.validation.constraints.NotBlank;

/**
 * @date  2020/12/30
 * @version 1.0.0
 * <p>账户挂接管理应答报文响应信息组件</p>
 */
public class BankAttRspsnInf {

    /**
     * 业务回执状态
     */
    @NotBlank(message = "业务回执状态不能为空")
    private String rspsnSts;

    /**
     * 业务拒绝码
     */
    private String rjctCd;

    /**
     * 业务拒绝信息
     */
    private String rjctInf;

    /**
     * 管理类型
     */
    @NotBlank(message = "管理类型不能为空")
    @EnumValue(value = ManagementTpCdEnum.class)
    private String mgmtTp;

    /**
     * 挂接协议号
     */
    private String ptcId;

    /**
     * 动态关联码
     */
    private String msgSndCd;

    @JSONField(name = "RspsnSts")
    public String getRspsnSts() {
        return rspsnSts;
    }

    public void setRspsnSts(String rspsnSts) {
        this.rspsnSts = rspsnSts;
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

    @JSONField(name = "MgmtTp")
    public String getMgmtTp() {
        return mgmtTp;
    }

    public void setMgmtTp(String mgmtTp) {
        this.mgmtTp = mgmtTp;
    }

    @JSONField(name = "PtcId")
    public String getPtcId() {
        return ptcId;
    }

    public void setPtcId(String ptcId) {
        this.ptcId = ptcId;
    }

    @JSONField(name = "MsgSndCd")
    public String getMsgSndCd() {
        return msgSndCd;
    }

    public void setMsgSndCd(String msgSndCd) {
        this.msgSndCd = msgSndCd;
    }

    @Override
    public String toString() {
        return "RspsnInf{" +
                "rspsnSts='" + rspsnSts + '\'' +
                ", rjctCd='" + rjctCd + '\'' +
                ", rjctInf='" + rjctInf + '\'' +
                ", mgmtTp='" + mgmtTp + '\'' +
                ", ptcId='" + ptcId + '\'' +
                ", msgSndCd='" + msgSndCd + '\'' +
                '}';
    }

    public String getPrcSts() {
        return "";
    }
}
