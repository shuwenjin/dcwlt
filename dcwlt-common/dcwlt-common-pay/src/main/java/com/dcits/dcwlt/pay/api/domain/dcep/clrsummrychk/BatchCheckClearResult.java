package com.dcits.dcwlt.pay.api.domain.dcep.clrsummrychk;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @desc 资金调整汇总核对查询结果集
 */
public class BatchCheckClearResult {
    @Length(max = 1)
    private String firstPageFlag;           //首页标识

    @Length(max = 1)
    private String lastPageFlag;            //尾页标识

    @Length(max = 4)
    private String renturnCount;            //返回条数

    @Length(max = 35)
    private String msgId;                   //报文标识号

    @Length(max = 14)
    private String sendDateTime;            //报文发送时间

    @Length(max = 14)
    private String instgDrctPty;            //发起机构

    @Length(max = 14)
    private String instdDrctPty;            //接收机构

    @Length(max = 8)
    private String clearDate;               //清算日期

    @Length(max = 10)
    private String clearCountNum;           //清算总笔数

    @Length(max = 18)
    private String clearDbtTotAmt;          //清算借方总金额

    @Length(max = 18)
    private String clearCbtTotAmt;          //清算贷方总金额

    @Length(max = 16)
    private String clearNetNum;            //清算场次编号

    @Length(max = 35)
    private String clearMsgId;              //清算报文标识号

    @Length(max = 4)
    private String clearDrct;               //清算借贷标识号

    @Length(max = 18)
    private String clearAmt;                //清算金额

    @Length(max = 13)
    private String batchId;                 //批次号

    @Length(max = 4)
    private String batchDrct;               //批次借贷标识

    @Length(max = 18)
    private String batchNetAmt;             //清算扎差净额

    @JSONField(name = "FirstPageFlag")
    public String getFirstPageFlag() {
        return firstPageFlag;
    }

    public void setFirstPageFlag(String firstPageFlag) {
        this.firstPageFlag = firstPageFlag;
    }

    @JSONField(name = "LastPageFlag")
    public String getLastPageFlag() {
        return lastPageFlag;
    }

    public void setLastPageFlag(String lastPageFlag) {
        this.lastPageFlag = lastPageFlag;
    }

    @JSONField(name = "RenturnCount")
    public String getRenturnCount() {
        return renturnCount;
    }

    public void setRenturnCount(String renturnCount) {
        this.renturnCount = renturnCount;
    }

    @JSONField(name = "MsgId")
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @JSONField(name = "SendDateTime")
    public String getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(String sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    @JSONField(name = "InstgDrctPty")
    public String getInstgDrctPty() {
        return instgDrctPty;
    }

    public void setInstgDrctPty(String instgDrctPty) {
        this.instgDrctPty = instgDrctPty;
    }

    @JSONField(name = "InstdDrctPty")
    public String getInstdDrctPty() {
        return instdDrctPty;
    }

    public void setInstdDrctPty(String instdDrctPty) {
        this.instdDrctPty = instdDrctPty;
    }

    @JSONField(name = "ClearDate")
    public String getClearDate() {
        return clearDate;
    }

    public void setClearDate(String clearDate) {
        this.clearDate = clearDate;
    }

    @JSONField(name = "ClearCountNum")
    public String getClearCountNum() {
        return clearCountNum;
    }

    public void setClearCountNum(String clearCountNum) {
        this.clearCountNum = clearCountNum;
    }

    @JSONField(name = "ClearDbtTotAmt")
    public String getClearDbtTotAmt() {
        return clearDbtTotAmt;
    }

    public void setClearDbtTotAmt(String clearDbtTotAmt) {
        this.clearDbtTotAmt = clearDbtTotAmt;
    }

    @JSONField(name = "ClearCbtTotAmt")
    public String getClearCbtTotAmt() {
        return clearCbtTotAmt;
    }

    public void setClearCbtTotAmt(String clearCbtTotAmt) {
        this.clearCbtTotAmt = clearCbtTotAmt;
    }

    @JSONField(name = "ClearNetNum")
    public String getClearNetNum() {
        return clearNetNum;
    }

    public void setClearNetNum(String clearNetNum) {
        this.clearNetNum = clearNetNum;
    }

    @JSONField(name = "ClearMsgId")
    public String getClearMsgId() {
        return clearMsgId;
    }

    public void setClearMsgId(String clearMsgId) {
        this.clearMsgId = clearMsgId;
    }

    @JSONField(name = "ClearDrct")
    public String getClearDrct() {
        return clearDrct;
    }

    public void setClearDrct(String clearDrct) {
        this.clearDrct = clearDrct;
    }

    @JSONField(name = "ClearAmt")
    public String getClearAmt() {
        return clearAmt;
    }

    public void setClearAmt(String clearAmt) {
        this.clearAmt = clearAmt;
    }

    @JSONField(name = "BatchId")
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @JSONField(name = "BatchDrct")
    public String getBatchDrct() {
        return batchDrct;
    }

    public void setBatchDrct(String batchDrct) {
        this.batchDrct = batchDrct;
    }

    @JSONField(name = "BatchNetAmt")
    public String getBatchNetAmt() {
        return batchNetAmt;
    }

    public void setBatchNetAmt(String batchNetAmt) {
        this.batchNetAmt = batchNetAmt;
    }

    @Override
    public String toString() {
        return "BatchCheckClearResult{" +
                "firstPageFlag='" + firstPageFlag + '\'' +
                ", lastPageFlag='" + lastPageFlag + '\'' +
                ", renturnCount='" + renturnCount + '\'' +
                ", msgId='" + msgId + '\'' +
                ", sendDateTime='" + sendDateTime + '\'' +
                ", instgDrctPty='" + instgDrctPty + '\'' +
                ", instdDrctPty='" + instdDrctPty + '\'' +
                ", clearDate='" + clearDate + '\'' +
                ", clearCountNum='" + clearCountNum + '\'' +
                ", clearDbtTotAmt='" + clearDbtTotAmt + '\'' +
                ", clearCbtTotAmt='" + clearCbtTotAmt + '\'' +
                ", clearNetNum='" + clearNetNum + '\'' +
                ", clearMsgId='" + clearMsgId + '\'' +
                ", clearDrct='" + clearDrct + '\'' +
                ", clearAmt='" + clearAmt + '\'' +
                ", batchId='" + batchId + '\'' +
                ", batchDrct='" + batchDrct + '\'' +
                ", batchNetAmt='" + batchNetAmt + '\'' +
                '}';
    }
}
