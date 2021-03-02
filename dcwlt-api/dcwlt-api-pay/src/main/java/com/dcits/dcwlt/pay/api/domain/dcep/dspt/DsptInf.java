package com.dcits.dcwlt.pay.api.domain.dcep.dspt;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.enums.DsptRsnCdEnum;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 差错处理信息
 */
public class DsptInf {
    /**
     * 差错业务类型编码
     */
    @NotBlank(message = "差错业务类型编码不能为空")
    @Length(max = 4)
    private String dsptBizTp;
    /**
     * 差错业务种类编码
     */
    @NotBlank(message = "差错业务种类编码不能为空")
    @Length(max = 5)
    private String dsptCtgyPurpCd;
    /**
     * 差错原因码
     */
    @NotBlank(message = "差错原因码不能为空")
    @EnumValue(value = DsptRsnCdEnum.class, message = "差错原因码类型有误")
    private String dsptRsnCd;
    /**
     * 差错原因说明
     */
    @NotBlank(message = "差错原因说明不能为空")
    @Length(max = 64)
    private String dsptRsnDesc;
    /**
     * 调账金额
     */
    @Valid
    private DsptAmt dsptAmt;
    /**
     * 交易批次号
     */
    @NotBlank(message = "交易批次号不能为空")
    @Length(max = 13)
    private String batchId;
    /**
     * 原交易信息
     */
    @Valid
    private OrgnlTxRef orgnlTxRef;

    @JSONField(name = "DsptBizTp")
    public String getDsptBizTp() {
        return dsptBizTp;
    }

    public void setDsptBizTp(String dsptBizTp) {
        this.dsptBizTp = dsptBizTp;
    }

    @JSONField(name = "DsptCtgyPurpCd")
    public String getDsptCtgyPurpCd() {
        return dsptCtgyPurpCd;
    }

    public void setDsptCtgyPurpCd(String dsptCtgyPurpCd) {
        this.dsptCtgyPurpCd = dsptCtgyPurpCd;
    }

    @JSONField(name = "DsptRsnCd")
    public String getDsptRsnCd() {
        return dsptRsnCd;
    }

    public void setDsptRsnCd(String dsptRsnCd) {
        this.dsptRsnCd = dsptRsnCd;
    }

    @JSONField(name = "DsptRsnDesc")
    public String getDsptRsnDesc() {
        return dsptRsnDesc;
    }

    public void setDsptRsnDesc(String dsptRsnDesc) {
        this.dsptRsnDesc = dsptRsnDesc;
    }

    @JSONField(name = "BatchId")
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @JSONField(name = "OrgnlTxRef")
    public OrgnlTxRef getOrgnlTxRef() {
        return orgnlTxRef;
    }

    public void setOrgnlTxRef(OrgnlTxRef orgnlTxRef) {
        this.orgnlTxRef = orgnlTxRef;
    }

    @JSONField(name = "DsptAmt")
    public DsptAmt getDsptAmt() {
        return dsptAmt;
    }

    public void setDsptAmt(DsptAmt dsptAmt) {
        this.dsptAmt = dsptAmt;
    }

    @Override
    public String toString() {
        return "DsptInf{" +
                "dsptBizTp='" + dsptBizTp + '\'' +
                ", dsptCtgyPurpCd='" + dsptCtgyPurpCd + '\'' +
                ", dsptRsnCd='" + dsptRsnCd + '\'' +
                ", dsptRsnDesc='" + dsptRsnDesc + '\'' +
                ", dsptAmt='" + dsptAmt + '\'' +
                ", batchId='" + batchId + '\'' +
                ", orgnlTxRef=" + orgnlTxRef +
                '}';
    }
}
