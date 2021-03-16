package com.dcits.dcwlt.pay.api.domain.dcep.clrsummrychk;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.validator.annotation.ISODate;
import com.dcits.dcwlt.pay.api.domain.dcep.common.TrxAmt;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 *
 * @desc 汇总核对信息
 */
public class SummryChkInf {
    /*
     * 清算日期
     * */
    @NotBlank(message = "清算日期")
    @ISODate
    private String ClrDt;
    /*
     * 调整总笔数
     * */
    @NotBlank(message = "调整总笔数")
    @Length(max = 10)
    private String ClrCntNb;
    /*
     * 调整借方金额
     * */
//    @Sensitive(type = FilterType.AMT)
//    @Valid
    private TrxAmt DbtCntAmt;
    /*
     * 调整贷方金额
     * */
//    @Sensitive(type = FilterType.AMT)
//    @Valid
    private TrxAmt CdtCntAmt;
    /*
     *调整场次列表
     * */
//    @Valid
    private List<ClrList> clrList;

    @JSONField(name = "ClrDt")
    public String getClrDt() {
        return ClrDt;
    }

    public void setClrDt(String clrDt) {
        ClrDt = clrDt;
    }

    @JSONField(name = "ClrCntNb")
    public String getClrCntNb() {
        return ClrCntNb;
    }

    public void setClrCntNb(String clrCntNb) {
        ClrCntNb = clrCntNb;
    }

    @JSONField(name = "DbtCntAmt")
    public TrxAmt getDbtCntAmt() {
        return DbtCntAmt;
    }

    public void setDbtCntAmt(TrxAmt dbtCntAmt) {
        DbtCntAmt = dbtCntAmt;
    }

    @JSONField(name = "CdtCntAmt")
    public TrxAmt getCdtCntAmt() {
        return CdtCntAmt;
    }

    public void setCdtCntAmt(TrxAmt cdtCntAmt) {
        CdtCntAmt = cdtCntAmt;
    }

    @JSONField(name = "ClrList")
    public List<ClrList> getClrList() {
        return clrList;
    }

    public void setClrList(List<ClrList> clrList) {
        this.clrList = clrList;
    }


    @Override
    public String toString() {
        return "SummryChkInf{" +
                "ClrDt='" + ClrDt + '\'' +
                ", ClrCntNb='" + ClrCntNb + '\'' +
                ", DbtCntAmt=" + DbtCntAmt +
                ", CdtCntAmt=" + CdtCntAmt +
                ", clrList=" + clrList +
                '}';
    }
}
