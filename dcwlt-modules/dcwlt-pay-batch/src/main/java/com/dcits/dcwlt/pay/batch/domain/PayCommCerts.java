package com.dcits.dcwlt.pay.batch.domain;

import com.dcits.dcwlt.common.core.annotation.Excel;
import com.dcits.dcwlt.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 证书管理对象 pay_comm_certs
 * 
 * @author dcwlt
 * @date 2021-04-25
 */
public class PayCommCerts extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 机构编码 */
    @Excel(name = "机构编码")
    private String partyId;

    /** 证书类型 */
    @Excel(name = "证书类型")
    private String certType;

    /** 证书状态 */
    @Excel(name = "证书状态")
    private String certStatus;

    /** 证书编号 */
    @Excel(name = "证书编号")
    private String certNo;

    /** 公钥 */
    @Excel(name = "公钥")
    private String publicKey;

    /** 私钥 */
    @Excel(name = "私钥")
    private String privateKey;

    /** 生效时间 */
    @Excel(name = "生效时间")
    private String effectTime;

    /** 失效时间 */
    @Excel(name = "失效时间")
    private String expiredTime;

    public void setPartyId(String partyId) 
    {
        this.partyId = partyId;
    }

    public String getPartyId() 
    {
        return partyId;
    }
    public void setCertType(String certType) 
    {
        this.certType = certType;
    }

    public String getCertType() 
    {
        return certType;
    }
    public void setCertStatus(String certStatus) 
    {
        this.certStatus = certStatus;
    }

    public String getCertStatus() 
    {
        return certStatus;
    }
    public void setCertNo(String certNo) 
    {
        this.certNo = certNo;
    }

    public String getCertNo() 
    {
        return certNo;
    }
    public void setPublicKey(String publicKey) 
    {
        this.publicKey = publicKey;
    }

    public String getPublicKey() 
    {
        return publicKey;
    }
    public void setPrivateKey(String privateKey) 
    {
        this.privateKey = privateKey;
    }

    public String getPrivateKey() 
    {
        return privateKey;
    }
    public void setEffectTime(String effectTime) 
    {
        this.effectTime = effectTime;
    }

    public String getEffectTime() 
    {
        return effectTime;
    }
    public void setExpiredTime(String expiredTime) 
    {
        this.expiredTime = expiredTime;
    }

    public String getExpiredTime() 
    {
        return expiredTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("partyId", getPartyId())
            .append("certType", getCertType())
            .append("certStatus", getCertStatus())
            .append("certNo", getCertNo())
            .append("publicKey", getPublicKey())
            .append("privateKey", getPrivateKey())
            .append("effectTime", getEffectTime())
            .append("expiredTime", getExpiredTime())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
