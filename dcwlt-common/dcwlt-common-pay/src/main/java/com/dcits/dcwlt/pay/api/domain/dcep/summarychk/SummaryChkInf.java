package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;


import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import java.util.List;

/**
 *
 * @Time 2021/1/3 14:39
 * @Version 1.0
 * Description: 对账汇总核对信息
 */
public class SummaryChkInf {

    //对账汇总消息头
    @Valid
    private SummaryHdr summaryHdr;
    //汇总消息体
    @Valid
    private SummaryBody summaryBody;
    //对账明细文件信息
    @Valid
    private DtlFileInf dtlFileInf;
    //区块链对账数据索引
    @Length(max = 70)
    private String reconIndex;

    @JSONField(name = "SummaryHdr")
    public SummaryHdr getSummaryHdr() {
        return summaryHdr;
    }

    public void setSummaryHdr(SummaryHdr summaryHdr) {
        this.summaryHdr = summaryHdr;
    }

    @JSONField(name = "SummaryBody")
    public SummaryBody getSummaryBody() {
        return summaryBody;
    }

    public void setSummaryBody(SummaryBody summaryBody) {
        this.summaryBody = summaryBody;
    }

    @JSONField(name = "DtlFileInf")
    public DtlFileInf getDtlFileInf() {
        return dtlFileInf;
    }

    public void setDtlFileInf(DtlFileInf dtlFileInf) {
        this.dtlFileInf = dtlFileInf;
    }

    @JSONField(name = "ReconIndex")
    public String getReconIndex() {
        return reconIndex;
    }

    public void setReconIndex(String reconIndex) {
        this.reconIndex = reconIndex;
    }

    @Override
    public String toString() {
        return "SummaryChkInf{" +
                "summaryHdr=" + summaryHdr +
                ", summaryBody=" + summaryBody +
                ", dtlFileInf=" + dtlFileInf +
                ", reconIndex=" + reconIndex +
                '}';
    }
}
