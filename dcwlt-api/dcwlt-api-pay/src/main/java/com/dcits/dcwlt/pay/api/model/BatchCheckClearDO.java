package com.dcits.dcwlt.pay.api.model;

import com.dcits.dcwlt.common.core.annotation.Excel;

/**
 *
 * @desc 资金调整汇总核对表实体
 */
public class BatchCheckClearDO {
    /*
     * 报文标识号
     * */
    @Excel(name = "报文标识号")
    private String msgId;
    /*
     * 报文发送时间
     * */
    @Excel(name = "报文发送时间")
    private String senderDateTime;
    /*
     * 发起机构
     * */
    @Excel(name = "发起机构")
    private String instgDrctPty;
    /*
     * 接收机构
     * */
    @Excel(name = "接收机构")
    private String instdDrctPty;
    /*
     * 备注
     * */
    @Excel(name = "备注")
    private String remark;
    /*
     * 清算日期
     * */
    @Excel(name = "清算日期")
    private String clearDate;
    /*
     * 清算总笔数
     * */
    @Excel(name = "清算总笔数")
    private String clearCountNum;
    /*
     * 清算借方总金额
     * */
    @Excel(name = "清算借方总金额")
    private String clearDbtTotAmt;
    /*
     * 清算贷方总金额
     * */
    @Excel(name = "清算贷方总金额")
    private String clearCbtTotAmt;
    /*
     * 清算场次编号
     * */
    @Excel(name = "清算场次编号")
    private String clearNetNum;
    /*
     * 清算报文标识号
     * */
    @Excel(name = "清算报文标识号")
    private String clearMsgId;
    /*
     * 清算借贷标识
     * */
    @Excel(name = "清算借贷标识")
    private String clearDrct;
    /*
     * 清算金额
     * */
    @Excel(name = "清算金额")
    private String clearAmt;
    /*
     * 批次号
     * */
    @Excel(name = "批次号")
    private String batchId;
    /*
     * 批次借贷标识
     * */
    @Excel(name = "批次借贷标识")
    private String batchDrct;

    /*
     * 批次扎差净额
     * */
    @Excel(name = "批次扎差净额")
    private String batchNetAmt;
    /*
     * 最后更新日期
     * */
    private String lastUpDate;
    /*
     * 最后更新时间
     * */
    private String lastUpTime;

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

    public String getClearDate() {
        return clearDate;
    }

    public void setClearDate(String clearDate) {
        this.clearDate = clearDate;
    }

    public String getClearCountNum() {
        return clearCountNum;
    }

    public void setClearCountNum(String clearCountNum) {
        this.clearCountNum = clearCountNum;
    }

    public String getClearDbtTotAmt() {
        return clearDbtTotAmt;
    }

    public void setClearDbtTotAmt(String clearDbtTotAmt) {
        this.clearDbtTotAmt = clearDbtTotAmt;
    }

    public String getClearCbtTotAmt() {
        return clearCbtTotAmt;
    }

    public void setClearCbtTotAmt(String clearCbtTotAmt) {
        this.clearCbtTotAmt = clearCbtTotAmt;
    }

    public String getClearNetNum() {
        return clearNetNum;
    }

    public void setClearNetNum(String clearNetNum) {
        this.clearNetNum = clearNetNum;
    }

    public String getClearMsgId() {
        return clearMsgId;
    }

    public void setClearMsgId(String clearMsgId) {
        this.clearMsgId = clearMsgId;
    }

    public String getClearDrct() {
        return clearDrct;
    }

    public void setClearDrct(String clearDrct) {
        this.clearDrct = clearDrct;
    }

    public String getClearAmt() {
        return clearAmt;
    }

    public void setClearAmt(String clearAmt) {
        this.clearAmt = clearAmt;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchDrct() {
        return batchDrct;
    }

    public void setBatchDrct(String batchDrct) {
        this.batchDrct = batchDrct;
    }


    public String getBatchNetAmt() {
        return batchNetAmt;
    }

    public void setBatchNetAmt(String batchNetAmt) {
        this.batchNetAmt = batchNetAmt;
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
        return "BatchCheckClearDO{" +
                "msgId='" + msgId + '\'' +
                ", senderDateTime='" + senderDateTime + '\'' +
                ", instgDrctPty='" + instgDrctPty + '\'' +
                ", instdDrctPty='" + instdDrctPty + '\'' +
                ", remark='" + remark + '\'' +
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
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                '}';
    }
}
