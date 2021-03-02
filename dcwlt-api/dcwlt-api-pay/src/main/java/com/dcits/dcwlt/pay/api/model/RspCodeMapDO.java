package com.dcits.dcwlt.pay.api.model;

public class RspCodeMapDO {

    private String payPath;         // 业务类型
    private String srcId;           // 源的编号
    private String destId;          // 目的的编号
    private String txnType;         // 应答码分类,C为通用类,S为签约类，F为金融类，为了解决不同业务场景返回不通错误码的问题
    private String srcRspCode;      // 源应答码大类
    private String srcRspCode2;     // 源应答码小类
    private String srcrspCodeDsp;   // 源应答码说明
    private String destRspCode;     // 目的应答码大类
    private String destRspCode2;    // 目的应答码小类
    private String rspCodeDsp;      // 应答码说明
    private String rsv1;            // 备用1

    public String getPayPath() {
        return payPath;
    }

    public void setPayPath(String payPath) {
        this.payPath = payPath;
    }

    public String getSrcrspCodeDsp() {
        return srcrspCodeDsp;
    }

    public void setSrcrspCodeDsp(String srcrspCodeDsp) {
        this.srcrspCodeDsp = srcrspCodeDsp;
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getSrcRspCode() {
        return srcRspCode;
    }

    public void setSrcRspCode(String srcRspCode) {
        this.srcRspCode = srcRspCode;
    }

    public String getSrcRspCode2() {
        return srcRspCode2;
    }

    public void setSrcRspCode2(String srcRspCode2) {
        this.srcRspCode2 = srcRspCode2;
    }

    public String getDestRspCode() {
        return destRspCode;
    }

    public void setDestRspCode(String destRspCode) {
        this.destRspCode = destRspCode;
    }

    public String getDestRspCode2() {
        return destRspCode2;
    }

    public void setDestRspCode2(String destRspCode2) {
        this.destRspCode2 = destRspCode2;
    }

    public String getRspCodeDsp() {
        return rspCodeDsp;
    }

    public void setRspCodeDsp(String rspCodeDsp) {
        this.rspCodeDsp = rspCodeDsp;
    }

    public String getRsv1() {
        return rsv1;
    }

    public void setRsv1(String rsv1) {
        this.rsv1 = rsv1;
    }

    @Override
    public String toString() {
        return "RspCodeMapDO{" +
                "payPath='" + payPath + '\'' +
                ", srcId='" + srcId + '\'' +
                ", destId='" + destId + '\'' +
                ", txnType='" + txnType + '\'' +
                ", srcRspCode='" + srcRspCode + '\'' +
                ", srcRspCode2='" + srcRspCode2 + '\'' +
                ", srcrspCodeDsp='" + srcrspCodeDsp + '\'' +
                ", destRspCode='" + destRspCode + '\'' +
                ", destRspCode2='" + destRspCode2 + '\'' +
                ", rspCodeDsp='" + rspCodeDsp + '\'' +
                ", rsv1='" + rsv1 + '\'' +
                '}';
    }
}
