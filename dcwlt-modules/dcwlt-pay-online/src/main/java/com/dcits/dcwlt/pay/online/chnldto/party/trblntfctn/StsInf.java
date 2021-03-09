package com.dcits.dcwlt.pay.online.chnldto.party.trblntfctn;


import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.enums.PartyTpCdEnum;
import com.dcits.dcwlt.common.pay.enums.StatusTpCdEnum;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 状态变更信息
 *
 * @author majun
 * @date 2020/12/31
 */
public class StsInf {

    @NotNull
    @EnumValue(StatusTpCdEnum.class)
    @JSONField(name = "Tp")
    private String tp;      //状态变更类型

    @NotNull
    @EnumValue(PartyTpCdEnum.class)
    @JSONField(name = "PtyTp")
    private String ptyTp;    //机构类型

    @NotBlank(message = "机构id不能为空")
    @Size(max = 14, message = "机构编码最大14位")
    @JSONField(name = "PtyId")
    private String ptyId;           //机构编码

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getPtyTp() {
        return ptyTp;
    }

    public void setPtyTp(String ptyTp) {
        this.ptyTp = ptyTp;
    }

    public String getPtyId() {
        return ptyId;
    }

    public void setPtyId(String ptyId) {
        this.ptyId = ptyId;
    }

    @Override
    public String toString() {
        return "StsInf{" +
                "tp=" + tp +
                ", ptyTp=" + ptyTp +
                ", ptyId='" + ptyId + '\'' +
                '}';
    }
}
