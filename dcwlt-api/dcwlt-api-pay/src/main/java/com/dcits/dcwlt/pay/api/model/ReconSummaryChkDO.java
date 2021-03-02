package com.dcits.dcwlt.pay.api.model;



/**
 *
 * @version 1.0.0
 * Description:机构对账汇总数据库实体
 * @Date 2021/1/4 22:19
 */
public class ReconSummaryChkDO {
    //平台日期
    private String payDate;
    //平台流水
    private String paySerNo;
    //平台时间
    private String payTime;
    //报文标识号
    private String msgId;
    //报文发送时间
    private String senderDateTime;
    //发起机构
    private String instgDrctPty;
    //接受机构
    private String instdDrctPty;
    //批次日期
    private String batchDate;
    //交易批次号
    private String batchId;
    //汇总记录总笔数
    private String countNum;
    // 汇总记录总金额
    private String countAmt;
    //货币代码
    private String ccY;
    //付款笔数
    private String dbitCountNum;
    //付款金额
    private String dbitCountAmt;
    //收款笔数
    private String crdtCountNum;
    //收款金额
    private String crdtCountAmt;
    //最后更新日期
    private String lastUpDate;
    //最后更新时间
    private String lastUpTime;
    //区块链对账
    private String reconIndex;
    //备注
    private String remark;
    //数字信封
    private String digitalEnvelope;


    public String getLastUpDate() {
        return lastUpDate;
    }

    public void setLastUpDate(String lastUpDate) {
        this.lastUpDate = lastUpDate;
    }

    public String getLastUpTime() {
        return lastUpTime;
    }

    public void setLastUpTime(String lastUpTime) {
        this.lastUpTime = lastUpTime;
    }

    public String getReconIndex() {
        return reconIndex;
    }

    public void setReconIndex(String reconIndex) {
        this.reconIndex = reconIndex;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPaySerNo() {
        return paySerNo;
    }

    public void setPaySerNo(String paySerNo) {
        this.paySerNo = paySerNo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSenderDateTime() {
        return senderDateTime;
    }

    public void setSenderDateTime(String senderDateTime) {
        this.senderDateTime = senderDateTime;
    }

    public String getInstgDrctPty() {
        return instgDrctPty;
    }

    public void setInstgDrctPty(String instgDrctPty) {
        this.instgDrctPty = instgDrctPty;
    }

    public String getInstdDrctPty() {
        return instdDrctPty;
    }

    public void setInstdDrctPty(String instdDrctPty) {
        this.instdDrctPty = instdDrctPty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBatchDate() {
        return batchDate;
    }


    public void setBatchDate(String batchDate) {
        this.batchDate = batchDate;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getCountNum() {
        return countNum;
    }

    public void setCountNum(String countNum) {
        this.countNum = countNum;
    }

    public String getCountAmt() {
        return countAmt;
    }

    public void setCountAmt(String countAmt) {
        this.countAmt = countAmt;
    }

    public String getCcY() {
        return ccY;
    }

    public void setCcY(String ccY) {
        this.ccY = ccY;
    }

    public String getDbitCountNum() {
        return dbitCountNum;
    }

    public void setDbitCountNum(String dbitCountNum) {
        this.dbitCountNum = dbitCountNum;
    }

    public String getDbitCountAmt() {
        return dbitCountAmt;
    }

    public void setDbitCountAmt(String dbitCountAmt) {
        this.dbitCountAmt = dbitCountAmt;
    }

    public String getCrdtCountNum() {
        return crdtCountNum;
    }

    public void setCrdtCountNum(String crdtCountNum) {
        this.crdtCountNum = crdtCountNum;
    }

    public String getCrdtCountAmt() {
        return crdtCountAmt;
    }

    public void setCrdtCountAmt(String crdtCountAmt) {
        this.crdtCountAmt = crdtCountAmt;
    }

    public String getDigitalEnvelope() {
        return digitalEnvelope;
    }

    public void setDigitalEnvelope(String digitalEnvelope) {
        this.digitalEnvelope = digitalEnvelope;
    }

    @Override
    public String toString() {
        return "ReconSummaryChkDO{" +
                "payDate='" + payDate + '\'' +
                ", paySerNo='" + paySerNo + '\'' +
                ", payTime='" + payTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", senderDateTime='" + senderDateTime + '\'' +
                ", instgDrctPty='" + instgDrctPty + '\'' +
                ", instdDrctPty='" + instdDrctPty + '\'' +
                ", remark='" + remark + '\'' +
                ", batchDate='" + batchDate + '\'' +
                ", batchId='" + batchId + '\'' +
                ", countNum='" + countNum + '\'' +
                ", countAmt='" + countAmt + '\'' +
                ", ccY='" + ccY + '\'' +
                ", dbitCountNum='" + dbitCountNum + '\'' +
                ", dbitCountAmt='" + dbitCountAmt + '\'' +
                ", crdtCountNum='" + crdtCountNum + '\'' +
                ", crdtCountAmt='" + crdtCountAmt + '\'' +
                ", reconIndex='" + reconIndex + '\'' +
                ", digitalEnvelope='" + digitalEnvelope + '\'' +
                '}';
    }


}
