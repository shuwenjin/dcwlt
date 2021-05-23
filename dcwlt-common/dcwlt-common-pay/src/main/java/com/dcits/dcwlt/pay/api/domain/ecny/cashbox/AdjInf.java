package com.dcits.dcwlt.pay.api.domain.ecny.cashbox;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.Amt;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 入库出库信息
 *
 *
 *
 */
public class AdjInf {

    /**
     * 入库出库类型
     */
    @NotBlank(message = "入库出库类型不能为空")
    @Length(max = 4)
    private String oprTp;

    /**
     * 入库出库借贷标识
     */
    @NotBlank(message = "入库出库借贷标识不能为空")
    @Length(max = 4)
    private String cdtDbtInd;

    /**
     * 入库/出库金额
     */
    private Amt amt;

    /**
     * 合作银行机构编码
     */
    @NotBlank(message = "合作银行机构编码不能为空")
    @Length(max = 14)
    private String coopBankInstnId;

    /**
     * 合作银行钱柜ID
     */
    @NotBlank(message = "合作银行钱柜ID不能为空")
    @Length(max = 34)
    private String coopBankWltId;

    /**
     * 钱柜所属运营机构
     */
    @NotBlank(message = "钱柜所属运营机构不能为空")
    @Length(max = 14)
    private String cshBoxInstnId;

    /**
     * 额度凭证
     */
    @NotBlank(message = "额度凭证不能为空")
    @Length(max = 2048)
    private String cert;

    @JSONField(name = "OprTp")
    public String getOprTp() {
        return oprTp;
    }

    public void setOprTp(String oprTp) {
        this.oprTp = oprTp;
    }

    @JSONField(name = "CdtDbtInd")
    public String getCdtDbtInd() {
        return cdtDbtInd;
    }

    public void setCdtDbtInd(String cdtDbtInd) {
        this.cdtDbtInd = cdtDbtInd;
    }

    @JSONField(name = "Amt")
    public Amt getAmt() {
        return amt;
    }

    public void setAmt(Amt amt) {
        this.amt = amt;
    }

    @JSONField(name = "CoopBankInstnId")
    public String getCoopBankInstnId() {
        return coopBankInstnId;
    }

    public void setCoopBankInstnId(String coopBankInstnId) {
        this.coopBankInstnId = coopBankInstnId;
    }

    @JSONField(name = "CoopBankWltId")
    public String getCoopBankWltId() {
        return coopBankWltId;
    }

    public void setCoopBankWltId(String coopBankWltId) {
        this.coopBankWltId = coopBankWltId;
    }

    @JSONField(name = "CshBoxInstnId")
    public String getCshBoxInstnId() {
        return cshBoxInstnId;
    }

    public void setCshBoxInstnId(String cshBoxInstnId) {
        this.cshBoxInstnId = cshBoxInstnId;
    }

    @JSONField(name = "Cert")
    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }
}
