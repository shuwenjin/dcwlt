package com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.enums.AccTpCdEnum;
import com.dcits.dcwlt.common.pay.enums.SignTpCdEnum;
import com.dcits.dcwlt.common.pay.util.HiddenUtil;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

/**
 *
 * @date  2020/12/29
 * @version 1.0.0
 * <p>签约人信息</p>
 */
public class SgnInf {

    /**
     * 签约人银行账户所属机构
     */
    @NotBlank(message = "签约人银行账户所属机构不能为空")
    @Length(max = 14)
    private String sgnAcctPtyId;

    /**
     * 签约人银行账户类型
     */
    @NotBlank(message = "签约人银行账户类型不能为空")
    @EnumValue(value = AccTpCdEnum.class)
    private String sgnAcctTp;

    /**
     * 签约人银行账户账号
     */
    @NotBlank(message = "签约人银行账户账号不能为空")
    @Length(max = 68)
    private String sgnAcctId;

    /**
     * 签约人银行账户户名
     */
    @NotBlank(message = "签约人银行账户户名不能为空")
    @Length(max = 240)
    private String sgnAcctNm;


    /**
     * 签约人证件类型
     * 取消枚举校验，手动在应用校验，映射人行 R087：非个人客户
     */
    @NotBlank(message = "签约人证件类型不能为空")
    private String idTp;

    /**
     * 签约人证件号码
     */
    @NotBlank(message = "签约人证件号码不能为空")
    @Length(max = 64)
    private String idNo;

    /**
     * 银行预留手机号码
     */
    @NotBlank(message = "银行预留手机号码不能为空")
    @Length(max = 70)
    private String tel;

    /**
     * 签约类型
     */
    @NotBlank(message = "签约类型不能为空")
    @EnumValue(value = SignTpCdEnum.class)
    private String sgnTp;


    @JSONField(name = "SgnAcctTp")
    public String getSgnAcctTp() {
        return sgnAcctTp;
    }

    public void setSgnAcctTp(String sgnAcctTp) {
        this.sgnAcctTp = sgnAcctTp;
    }

    @JSONField(name = "SgnAcctId")
    public String getSgnAcctId() {
        return sgnAcctId;
    }

    public void setSgnAcctId(String sgnAcctId) {
        this.sgnAcctId = sgnAcctId;
    }




    @JSONField(name = "IDTp")
    public String getIdTp() {
        return idTp;
    }

    public void setIdTp(String idTp) {
        this.idTp = idTp;
    }

    @JSONField(name = "IDNo")
    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    @JSONField(name = "Tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @JSONField(name = "SgnTp")
    public String getSgnTp() {
        return sgnTp;
    }

    public void setSgnTp(String sgnTp) {
        this.sgnTp = sgnTp;
    }

    @JSONField(name = "SgnAcctPtyId")
    public String getSgnAcctPtyId() {
        return sgnAcctPtyId;
    }

    public void setSgnAcctPtyId(String sgnAcctPtyId) {
        this.sgnAcctPtyId = sgnAcctPtyId;
    }

    @JSONField(name = "SgnAcctNm")
    public String getSgnAcctNm() {
        return sgnAcctNm;
    }

    public void setSgnAcctNm(String sgnAcctNm) {
        this.sgnAcctNm = sgnAcctNm;
    }

    @Override
    public String toString() {
        return "SgnInf{" +
                "sgnAccPtyId='" + sgnAcctPtyId + '\'' +
                ", sgnAcctTp='" + sgnAcctTp + '\'' +
                ", sgnAcctId='" + HiddenUtil.acHidden(sgnAcctId) + '\'' +
                ", sgnAcctNm='" + HiddenUtil.acNameHidden(sgnAcctNm) + '\'' +
                ", idTp='" + idTp + '\'' +
                ", idNo='" + HiddenUtil.certIdHidden(idNo) + '\'' +
                ", tel='" + HiddenUtil.telNoHidden(tel) + '\'' +
                ", sgnTp='" + sgnTp + '\'' +
                '}';
    }
}
