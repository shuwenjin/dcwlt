package com.dcits.dcwlt.pay.api.domain.dcep.cert;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 证书信息
 *
 *
 *
 */
public class CertNtfctnInf {

    /**
     * 变更类型
     */
    @NotBlank(message = "变更类型不能为空")
    @Length(max = 4)
    private String chgTp;
    /**
     * 证书类型
     */
    @NotBlank(message = "证书类型不能为空")
    @Length(max = 1000)
    private String certTp;

    @JSONField(name = "ChgTp")
    public String getChgTp() {
        return chgTp;
    }

    public void setChgTp(String chgTp) {
        this.chgTp = chgTp;
    }

    @JSONField(name = "CertTp")
    public String getCertTp() {
        return certTp;
    }

    public void setCertTp(String certTp) {
        this.certTp = certTp;
    }

    @Override
    public String toString() {
        return "CertNtfctnInf{" +
                "chgTp='" + chgTp + '\'' +
                ", certTp='" + certTp + '\'' +
                '}';
    }
}
