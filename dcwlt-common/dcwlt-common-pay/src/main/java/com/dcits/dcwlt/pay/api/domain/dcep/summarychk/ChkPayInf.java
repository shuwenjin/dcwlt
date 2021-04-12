package com.dcits.dcwlt.pay.api.domain.dcep.summarychk;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.pay.api.domain.dcep.common.TrxAmt;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @Time 2021/1/3 15:26
 * @Version 1.0
 * Description:业务对账清单列表
 */
public class ChkPayInf {
    /*
     * 报文编号
     * */
    @Length(max =15 )
    @NotNull(message = "报文编号不能为空")
    private String msgTp;
    /*
     *业务状态
     * */
    @Length(max = 4)
    @NotNull(message = "业务转态不能为空")
    private String bizSts;
    /*
     *汇总记录笔数总数
     * */
    @Length(max = 15)
    private String cntNb;
    /*
     * 总金额
     * */
    @Valid
    private TrxAmt cntAmt;
    /*
     *付款笔数
     * */
    @Length(max = 15)
    private String dbtCntNb;
    /*
     * 付款金额
     * */
    @Valid
    private TrxAmt dbtCntAmt;
    /*
     * 收款笔数
     * */
    @Length(max=15)
    private String cdtCntNb;
    /*
     * 收款金额
     * */
    @Valid
    private TrxAmt cdtCntAmt;

    @JSONField(name = "MsgTp")
    public String getMsgTp() {
        return msgTp;
    }

    public void setMsgTp(String msgTp) {
        this.msgTp = msgTp;
    }

    @JSONField(name = "BizSts")
    public String getBizSts() {
        return bizSts;
    }

    public void setBizSts(String bizSts) {
        this.bizSts = bizSts;
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
        return "ChkPayInf{" +
                "msgTp='" + msgTp + '\'' +
                ", bizSts='" + bizSts + '\'' +
                ", cntNb='" + cntNb + '\'' +
                ", cntAmt=" + cntAmt +
                ", dbtCntNb='" + dbtCntNb + '\'' +
                ", dbtCntAmt=" + dbtCntAmt +
                ", cdtCntNb='" + cdtCntNb + '\'' +
                ", cdtCntAmt=" + cdtCntAmt +
                '}';
    }
}
