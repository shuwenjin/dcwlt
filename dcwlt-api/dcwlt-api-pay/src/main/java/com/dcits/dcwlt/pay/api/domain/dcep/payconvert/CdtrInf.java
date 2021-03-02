package com.dcits.dcwlt.pay.api.domain.dcep.payconvert;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.enums.WalletLevelCdEnum;
import com.dcits.dcwlt.common.pay.enums.WalletTpCdEnum;
import com.dcits.dcwlt.common.pay.util.HiddenUtil;
import com.dcits.dcwlt.common.pay.util.StringUtil;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 兑出钱包信息
 *
 *
 */
public class CdtrInf {

    /**
     * 收款人钱包开立所属机构
     */
    @NotBlank(message = "收款人钱包所属机构不能为空")
    @Length(max = 14)
    private String cdtrPtyId;

    /**
     * 收款人名称
     */
    @Length(max = 60)
    private String cdtrNm;

    /**
     * 收款人钱包ID
     */
    @NotBlank(message = "收款人钱包ID不能为空")
    @Length(max = 68)
    private String cdtrWltId;

    /**
     * 收款人钱包等级
     */
    @EnumValue(value = WalletLevelCdEnum.class, message = "收款人钱包等级有误", required = false)
    private String cdtrWltLvl;

    /**
     * 收款人钱包类型
     */
    @EnumValue(value = WalletTpCdEnum.class, message = "收款人钱包类型有误", required = false)
    private String cdtrWltTp;

    /**
     * 收款人钱包名称
     */
    @NotBlank(message = "收款人钱包名称不能为空")
    @Length(max = 60)
    private String cdtrWltNm;

    public static CdtrInf getInstance(PayTransDtlInfoDO payTransDtlInfoDO) {
        CdtrInf cdtrInf = new CdtrInf();
        cdtrInf.setCdtrPtyId(payTransDtlInfoDO.getPayeePtyId());
        if (StringUtils.isNotBlank(payTransDtlInfoDO.getPayeeName())) {
            cdtrInf.setCdtrNm(payTransDtlInfoDO.getPayeeName());
        }
        cdtrInf.setCdtrWltNm(payTransDtlInfoDO.getPayeeWalletName());
        cdtrInf.setCdtrWltId(payTransDtlInfoDO.getPayeeWalletId());
        //如果钱包等级长度为0，则不set
        if (StringUtils.isNotBlank(payTransDtlInfoDO.getPayeeWalletLv())) {
            cdtrInf.setCdtrWltLvl(payTransDtlInfoDO.getPayeeWalletLv());
        }
        if (StringUtils.isNotBlank(payTransDtlInfoDO.getPayeeWalletType())) {
            cdtrInf.setCdtrWltTp(payTransDtlInfoDO.getPayeeWalletType());
        }
        return cdtrInf;
    }

    @JSONField(name = "CdtrPtyId")
    public String getCdtrPtyId() {
        return cdtrPtyId;
    }

    public void setCdtrPtyId(String cdtrPtyId) {
        this.cdtrPtyId = cdtrPtyId;
    }

    @JSONField(name = "CdtrNm")
    public String getCdtrNm() {
        return cdtrNm;
    }

    public void setCdtrNm(String cdtrNm) {
        this.cdtrNm = cdtrNm;
    }

    @JSONField(name = "CdtrWltId")
    public String getCdtrWltId() {
        return cdtrWltId;
    }

    public void setCdtrWltId(String cdtrWltId) {
        this.cdtrWltId = cdtrWltId;
    }

    @JSONField(name = "CdtrWltLvl")
    public String getCdtrWltLvl() {
        return cdtrWltLvl;
    }

    public void setCdtrWltLvl(String cdtrWltLvl) {
        this.cdtrWltLvl = cdtrWltLvl;
    }

    @JSONField(name = "CdtrWltTp")
    public String getCdtrWltTp() {
        return cdtrWltTp;
    }

    public void setCdtrWltTp(String cdtrWltTp) {
        this.cdtrWltTp = cdtrWltTp;
    }

    @JSONField(name = "CdtrWltNm")
    public String getCdtrWltNm() {
        return cdtrWltNm;
    }

    public void setCdtrWltNm(String cdtrWltNm) {
        this.cdtrWltNm = cdtrWltNm;
    }

    @Override
    public String toString() {
        return "CdtrInf{" +
                "cdtrPtyId='" + cdtrPtyId + '\'' +
                ", cdtrNm='" + HiddenUtil.acNameHidden(cdtrNm) + '\'' +
                ", cdtrWltId='" + HiddenUtil.acHidden(cdtrWltId) + '\'' +
                ", cdtrWltLvl='" + cdtrWltLvl + '\'' +
                ", cdtrWltTp='" + cdtrWltTp + '\'' +
                ", cdtrWltNm='" + HiddenUtil.acNameHidden(cdtrWltNm) + '\'' +
                '}';
    }
}
