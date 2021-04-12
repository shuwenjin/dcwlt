package com.dcits.dcwlt.pay.api.domain.dcep.clrsummrychk;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.enums.CreditDebitCodeEnum;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import com.dcits.dcwlt.pay.api.domain.dcep.common.TrxAmt;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @desc 批次列表
 *
 *
 *
 */
public class BatchList {
    /*
     * 批次号
     * */
    @NotBlank(message = "批次号不能为空")
    @Length(max = 13)
    private String batchId;
    /*
     * 批次借贷标识
     *
     * */
    @EnumValue(value = CreditDebitCodeEnum.class,message = "批次借贷标识格式错误")
    private String cdtDbtInd;
    /*
     * 轧差净额
     * */
//    @Sensitive(type = FilterType.AMT)
    @Valid
    private TrxAmt netgAmt;

    @JSONField(name = "BatchId")
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @JSONField(name = "CdtDbtInd")
    public String getCdtDbtInd() {
        return cdtDbtInd;
    }

    public void setCdtDbtInd(String cdtDbtInd) {
        this.cdtDbtInd = cdtDbtInd;
    }

    @JSONField
    public TrxAmt getNetgAmt() {
        return netgAmt;
    }

    public void setNetgAmt(TrxAmt netgAmt) {
        this.netgAmt = netgAmt;
    }

    @Override
    public String toString() {
        return "BatchList{" +
                "batchId='" + batchId + '\'' +
                ", cdtDbtInd=" + cdtDbtInd +
                ", netgAmt=" + netgAmt +
                '}';
    }
}
