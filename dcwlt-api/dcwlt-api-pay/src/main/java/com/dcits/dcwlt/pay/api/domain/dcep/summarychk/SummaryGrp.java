package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;

import com.dcits.dcwlt.pay.api.domain.dcep.common.TrxAmt;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 * @Time 2021/1/3 14:52
 * @Version 1.0
 * Description:汇总消息体
 */
public class SummaryGrp {
    /*
     *2位控制位（00-99）
     * */
    @Length(max = 2)
    @NotNull(message = "分片编号不能为空")
    private String nb;
    /*
     *汇总记录笔数总数
     * */
    @Length(max = 15)
    private String cntNb;
    //汇总记录金额总数
    @Valid
    private TrxAmt cntAmt;
    /*
     * 汇总记录付款笔数总数
     * */
    @Length(max = 15)
    private String dbtCntNb;

    /*
     *汇总记录付款金额总数
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
    /*
     * 业务对账清单列表
     * */
    @Valid
    private List<ChkPayInf> chkPayList;

    @JSONField(name = "Nb")
    public String getNb() {
        return nb;
    }

    @JSONField(name = "CntAmt")
    public TrxAmt getCntAmt() {
        return cntAmt;
    }

    public void setCntAmt(TrxAmt cntAmt) {
        this.cntAmt = cntAmt;
    }

    public void setNb(String nb) {
        this.nb = nb;
    }

    @JSONField(name = "CntNb")
    public String getCntNb() {
        return cntNb;
    }

    public void setCntNb(String cntNb) {
        this.cntNb = cntNb;
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

    @JSONField(name = "ChkPayList")
    public List<ChkPayInf> getChkPayList() {
        return chkPayList;
    }

    public void setChkPayList(List<ChkPayInf> chkPayList) {
        this.chkPayList = chkPayList;
    }

    @Override
    public String toString() {
        return "SummaryGrp{" +
                "nb='" + nb + '\'' +
                ", cntNb='" + cntNb + '\'' +
                ", cntAmt=" + cntAmt +
                ", dbtCntNb='" + dbtCntNb + '\'' +
                ", dbtCntAmt=" + dbtCntAmt +
                ", cdtCntNb='" + cdtCntNb + '\'' +
                ", cdtCntAmt=" + cdtCntAmt +
                ", chkPayList=" + chkPayList +
                '}';
    }
}
