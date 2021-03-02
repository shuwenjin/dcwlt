package com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @date  2020/12/29
 * @version 1.0.0
 * <p>协议信息</p>
 */
public class PtcInf {

    /**
     * 挂接协议号：当管理类型为MT03(解约申请)
     */
    @Length(max = 34)
    private String ptcId;

    /**
     * 动态关联码：机构关联身份认证和签约确认的唯一标识
     */
    @Length(max = 64)
    private String msgSndCd;

    /**
     * 动态验证码
     */
    @Length(max = 20)
    private String msgVrfy;

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

    @JSONField(name = "MsgVrfy")
    public String getMsgVrfy() {
        return msgVrfy;
    }

    public void setMsgVrfy(String msgVrfy) {
        this.msgVrfy = msgVrfy;
    }

    @Override
    public String toString() {
        return "PtcInf{" +
                "ptcId='" + ptcId + '\'' +
                ", msgSndCd='" + msgSndCd + '\'' +
                ", msgVrfy='" + msgVrfy + '\'' +
                '}';
    }
}
