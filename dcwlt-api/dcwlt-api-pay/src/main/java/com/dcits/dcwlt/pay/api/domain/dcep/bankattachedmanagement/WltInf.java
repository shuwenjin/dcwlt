package com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.enums.WalletLevelCdEnum;
import com.dcits.dcwlt.common.pay.enums.WalletTpCdEnum;
import com.dcits.dcwlt.common.pay.util.HiddenUtil;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

/**
 *
 * @date  2020/12/30
 * @version 1.0.0
 * <p>钱包信息</p>
 */
public class WltInf {

    /**
     * 钱包开立所属机构编码
     */
    @NotBlank(message = "钱包开立所属机构编码不能为空")
    @Length(max = 14)
    private String wltPtyId;

    /**
     * 钱包ID
     */
    @NotBlank(message = "钱包ID")
    @Length(max = 68)
    private String wltId;

    /**
     * 钱包类型
     */
    @NotBlank(message = "钱包类型不能为空")
    @EnumValue(WalletTpCdEnum.class)
    private String wltTp;

    /**
     * 钱包等级
     */
    @NotBlank(message = "钱包等级不能为空")
    @EnumValue(value = WalletLevelCdEnum.class)
    private String wltLvl;

    @JSONField(name = "WltPtyId")
    public String getWltPtyId() {
        return wltPtyId;
    }

    public void setWltPtyId(String wltPtyId) {
        this.wltPtyId = wltPtyId;
    }

    @JSONField(name = "WltId")
    public String getWltId() {
        return wltId;
    }

    public void setWltId(String wltId) {
        this.wltId = wltId;
    }

    @JSONField(name = "WltTp")
    public String getWltTp() {
        return wltTp;
    }

    public void setWltTp(String wltTp) {
        this.wltTp = wltTp;
    }

    @JSONField(name = "WltLvl")
    public String getWltLvl() {
        return wltLvl;
    }

    public void setWltLvl(String wltLvl) {
        this.wltLvl = wltLvl;
    }

    @Override
    public String toString() {
        return "WltPtyId{" +
                "wltPtyId='" + wltPtyId + '\'' +
                ", wltId='" + HiddenUtil.acHidden(wltId) + '\'' +
                ", wltTp='" + wltTp + '\'' +
                ", wltLvl='" + wltLvl + '\'' +
                '}';
    }
}
