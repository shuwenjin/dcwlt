package com.dcits.dcwlt.pay.api.domain.dcep.txstsqry;


import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspBody;

/**
 * @author
 * @Time 2021/1/3 16:00
 * @Version 1.0
 * Description:交易查询接口响应报文
 */
public class TxStsQrySRspDTO extends ECNYRspBody {

    /**
     * 原查询报文标识号
     */
    private String qryRef;
    /**
     * 原查询发起运营机构
     */
    private String qryNm;
    /**
     * 查询处理状态
     */
    private String qryRs;
    /**
     * 平台处理码
     */
    private String prcCd;
    /**
     * 业务拒绝码
     */
    private String rjctCd;
    /**
     * 业务拒绝信息
     */
    private String RjctInf;
    /**
     * 原应答的业务信息
     */
    private BusinessRpt bizRpt;

    public String getQryRef() {
        return qryRef;
    }

    public void setQryRef(String qryRef) {
        this.qryRef = qryRef;
    }

    public String getQryNm() {
        return qryNm;
    }

    public void setQryNm(String qryNm) {
        this.qryNm = qryNm;
    }

    public String getQryRs() {
        return qryRs;
    }

    public void setQryRs(String qryRs) {
        this.qryRs = qryRs;
    }

    public String getPrcCd() {
        return prcCd;
    }

    public void setPrcCd(String prcCd) {
        this.prcCd = prcCd;
    }

    public String getRjctCd() {
        return rjctCd;
    }

    public void setRjctCd(String rjctCd) {
        this.rjctCd = rjctCd;
    }

    public String getRjctInf() {
        return RjctInf;
    }

    public void setRjctInf(String rjctInf) {
        RjctInf = rjctInf;
    }

    public BusinessRpt getBizRpt() {
        return bizRpt;
    }

    public void setBizRpt(BusinessRpt bizRpt) {
        this.bizRpt = bizRpt;
    }

    @Override
    public String toString() {
        return "TxStsQryRspSDTO{" +
                "qryRef='" + qryRef + '\'' +
                ", qryNm='" + qryNm + '\'' +
                ", qryRs='" + qryRs + '\'' +
                ", prcCd='" + prcCd + '\'' +
                ", rjctCd='" + rjctCd + '\'' +
                ", RjctInf='" + RjctInf + '\'' +
                ", bizRpt=" + bizRpt +
                '}';
    }
}
