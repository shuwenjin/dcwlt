package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;

import com.dcits.dcwlt.pay.api.domain.dcep.common.TrxAmt;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @Time 2021/1/3 14:42
 * @Version 1.0
 * Description: 对账汇总消息头
 */
public class SummaryHdr {
    /*
    * 交易批次号
    * */
    @Length(max = 13)
    @NotNull(message = "交易批次号不能为空")
    private String batchId;
    /*
    * 汇总记录笔数总数
    * */
    @Length(max = 15)
    @NotNull(message = "汇总记录笔数总数不能为空")
    private String cntNb;

    /*
    * 汇总记录金额总数
    * */
    @Valid
    private TrxAmt cntAmt;
    /*
    * 汇总记录付款笔数总数
    * */
    @Length(max = 15)
    private String dbtCntNb;
    /*
    * 汇总记录付款金额总数
    * */
    @Valid
    private TrxAmt dbtCntAmt;
    /*
    * 汇总记录收款笔数总数
    * */
    @Length(max = 15)
    private String cdtCntNb;
    /*
     * 汇总记录收款金额总数
     * */
    @Valid
    private TrxAmt cdtCntAmt;

    @JSONField(name = "BatchId")
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
    @JSONField(name = "CntNb")
    public String getCntNb() {
        return cntNb;
    }

    public void setCntNb(String cntNb) {
        this.cntNb = cntNb;
    }

    @JSONField(name = "CntAmt")
    public TrxAmt getCntAmt() {
        return cntAmt;
    }

    public void setCntAmt(TrxAmt cntAmt) {
        this.cntAmt = cntAmt;
    }

    @JSONField(name = "DbtCntNb")
    public String getDbtCntNb() {
        return dbtCntNb;
    }

    public void setDbtCntNb(String dbtCntNb) {
        this.dbtCntNb = dbtCntNb;
    }


    @JSONField(name = "DbtCntAmt")
    public TrxAmt getDbtCntAmt() {
        return dbtCntAmt;
    }

    public void setDbtCntAmt(TrxAmt dbtCntAmt) {
        this.dbtCntAmt = dbtCntAmt;
    }

    @JSONField(name = "CdtCntNb")
    public String getCdtCntNb() {
        return cdtCntNb;
    }

    public void setCdtCntNb(String cdtCntNb) {
        this.cdtCntNb = cdtCntNb;
    }

    @JSONField(name = "CdtCntAmt")
    public TrxAmt getCdtCntAmt() {
        return cdtCntAmt;
    }

    public void setCdtCntAmt(TrxAmt cdtCntAmt) {
        this.cdtCntAmt = cdtCntAmt;
    }

    @Override
    public String toString() {
        return "SummaryHdr{" +
                "batchId='" + batchId + '\'' +
                ", cntNb=" + cntNb +
                ", cntAmt=" + cntAmt +
                ", dbtCntNb=" + dbtCntNb +
                ", dbtCntAmt=" + dbtCntAmt +
                ", cdtCntNb=" + cdtCntNb +
                ", cdtCntAmt=" + cdtCntAmt +
                '}';
    }
}
