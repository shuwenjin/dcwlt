package com.dcits.dcwlt.pay.api.model;


/**
 *
 * @Date 2021/1/6 22:23
 * @Version 1.0
 * Description: 汇总信息分类及业务清单信息数据实体
 */
public class SummaryInfoDO {
    //报文标识号
    private String msgId;
    //批次日期
    private String batchDate;
    //交易批次号
    private String batchId;
    //分片编号
    private String splitNum;
    //分片总笔数
    private String splitCountNum;
    //分片总金额
    private String splitCountAmt;
    //分片付款笔数
    private String splitDbitCountNum;
    //分片付款金额
    private String splitDbitCountAmt;
    //分片收款笔数
    private String splitCrdtCountNum;
    //分片收款金额
    private String splitCrdtCountAmt;
    //报文编号
    private String msgType;
    //业务状态
    private String msgBizStatus;
    //总笔数
    private String msgCountNum;
    //总金额
    private String msgCountAmt;
    //付款笔数
    private String msgDbitCountNum;
    //付款金额
    private String msgDbitCountAmt;
    //收款笔数
    private String msgCrdtCountNum;
    //收款金额
    private String msgCrdtCountAmt;
    //最后更新日期
    private String lastUpDate;
    //最后更新时间
    private String lastUpTime;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
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

    public String getSplitNum() {
        return splitNum;
    }

    public void setSplitNum(String splitNum) {
        this.splitNum = splitNum;
    }

    public String getSplitCountNum() {
        return splitCountNum;
    }

    public void setSplitCountNum(String splitCountNum) {
        this.splitCountNum = splitCountNum;
    }

    public String getSplitCountAmt() {
        return splitCountAmt;
    }

    public void setSplitCountAmt(String splitCountAmt) {
        this.splitCountAmt = splitCountAmt;
    }

    public String getSplitDbitCountNum() {
        return splitDbitCountNum;
    }

    public void setSplitDbitCountNum(String splitDbitCountNum) {
        this.splitDbitCountNum = splitDbitCountNum;
    }

    public String getSplitDbitCountAmt() {
        return splitDbitCountAmt;
    }

    public void setSplitDbitCountAmt(String splitDbitCountAmt) {
        this.splitDbitCountAmt = splitDbitCountAmt;
    }

    public String getSplitCrdtCountNum() {
        return splitCrdtCountNum;
    }

    public void setSplitCrdtCountNum(String splitCrdtCountNum) {
        this.splitCrdtCountNum = splitCrdtCountNum;
    }

    public String getSplitCrdtCountAmt() {
        return splitCrdtCountAmt;
    }

    public void setSplitCrdtCountAmt(String splitCrdtCountAmt) {
        this.splitCrdtCountAmt = splitCrdtCountAmt;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgBizStatus() {
        return msgBizStatus;
    }

    public void setMsgBizStatus(String msgBizStatus) {
        this.msgBizStatus = msgBizStatus;
    }

    public String getMsgCountNum() {
        return msgCountNum;
    }

    public void setMsgCountNum(String msgCountNum) {
        this.msgCountNum = msgCountNum;
    }

    public String getMsgCountAmt() {
        return msgCountAmt;
    }

    public void setMsgCountAmt(String msgCountAmt) {
        this.msgCountAmt = msgCountAmt;
    }

    public String getMsgDbitCountNum() {
        return msgDbitCountNum;
    }

    public void setMsgDbitCountNum(String msgDbitCountNum) {
        this.msgDbitCountNum = msgDbitCountNum;
    }

    public String getMsgDbitCountAmt() {
        return msgDbitCountAmt;
    }

    public void setMsgDbitCountAmt(String msgDbitCountAmt) {
        this.msgDbitCountAmt = msgDbitCountAmt;
    }

    public String getMsgCrdtCountNum() {
        return msgCrdtCountNum;
    }

    public void setMsgCrdtCountNum(String msgCrdtCountNum) {
        this.msgCrdtCountNum = msgCrdtCountNum;
    }

    public String getMsgCrdtCountAmt() {
        return msgCrdtCountAmt;
    }

    public void setMsgCrdtCountAmt(String msgCrdtCountAmt) {
        this.msgCrdtCountAmt = msgCrdtCountAmt;
    }

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

    @Override
    public String toString() {
        return "SummaryInfoDO{" +
                "msgId='" + msgId + '\'' +
                ", batchDate='" + batchDate + '\'' +
                ", batchId='" + batchId + '\'' +
                ", splitNum='" + splitNum + '\'' +
                ", splitCountNum='" + splitCountNum + '\'' +
                ", splitCountAmt='" + splitCountAmt + '\'' +
                ", splitDbitCountNum='" + splitDbitCountNum + '\'' +
                ", splitDbitCountAmt='" + splitDbitCountAmt + '\'' +
                ", splitCrdtCountNum='" + splitCrdtCountNum + '\'' +
                ", splitCrdtCountAmt='" + splitCrdtCountAmt + '\'' +
                ", msgType='" + msgType + '\'' +
                ", msgBizStatus='" + msgBizStatus + '\'' +
                ", msgCountNum='" + msgCountNum + '\'' +
                ", msgCountAmt='" + msgCountAmt + '\'' +
                ", msgDbitCountNum='" + msgDbitCountNum + '\'' +
                ", msgDbitCountAmt='" + msgDbitCountAmt + '\'' +
                ", msgCrdtCountNum='" + msgCrdtCountNum + '\'' +
                ", msgCrdtCountAmt='" + msgCrdtCountAmt + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                '}';
    }
}
