package com.dcits.dcwlt.pay.api.domain.dcep.clrsummrychk;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.enums.CreditDebitCodeEnum;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import com.dcits.dcwlt.pay.api.domain.dcep.common.TrxAmt;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 *
 * @desc 调整场次列表
 */
public class ClrList {
    /*
     * 调整场次编号
     * */
    @NotBlank(message = "调整场次编号")
    @Length(max = 16)
    private String clrNetgRnd;
    /*
     * 报文标识号
     * */
    @NotBlank(message = "报文标识号")
    @Length(max = 35)
    private String clrReptFlg;
    /*
     *调整借贷标识
     * */
    @EnumValue(value = CreditDebitCodeEnum.class,message = "调整借贷标识格式错误")
    private String cdtDbtInd;
    /*
     * 调整金额
     * */
//    @Sensitive(type = FilterType.AMT)
    @Valid
    private TrxAmt clrAmt;
    /*
     *批次列表
     * */
    @Valid
    private List<BatchList> batchList;

    @JSONField(name = "ClrNetgRnd")
    public String getClrNetgRnd() {
        return clrNetgRnd;
    }

    public void setClrNetgRnd(String clrNetgRnd) {
        this.clrNetgRnd = clrNetgRnd;
    }

    @JSONField(name = "ClrReptFlg")
    public String getClrReptFlg() {
        return clrReptFlg;
    }

    public void setClrReptFlg(String clrReptFlg) {
        this.clrReptFlg = clrReptFlg;
    }

    @JSONField(name = "CdtDbtInd")
    public String getCdtDbtInd() {
        return cdtDbtInd;
    }

    public void setCdtDbtInd(String cdtDbtInd) {
        this.cdtDbtInd = cdtDbtInd;
    }

    @JSONField(name = "ClrAmt")
    public TrxAmt getClrAmt() {
        return clrAmt;
    }

    public void setClrAmt(TrxAmt clrAmt) {
        this.clrAmt = clrAmt;
    }

    @JSONField(name = "BatchList")
    public List<BatchList> getBatchList() {
        return batchList;
    }

    public void setBatchList(List<BatchList> batchList) {
        this.batchList = batchList;
    }

    @Override
    public String toString() {
        return "ClrList{" +
                "clrNetgRnd='" + clrNetgRnd + '\'' +
                ", clrReptFlg='" + clrReptFlg + '\'' +
                ", cdtDbtInd=" + cdtDbtInd +
                ", clrAmt=" + clrAmt +
                ", batchList=" + batchList +
                '}';
    }
}
