package com.dcits.dcwlt.pay.api.model;


import com.dcits.dcwlt.common.pay.util.HiddenUtil;

/**
 * 终态通知请求登记表实体
 *
 */
public class PayNotifyDO {
    /**
     * 终态通知日期
     */
    private String txEndDate;
    /**
     * 终态通知请求流水
     */
    private String txEndSerno;
    /**
     * 终态通知报文标识号
     */
    private String txEndMsgId;
    /**
     * 终态通知报文编号
     */
    private String txEndMsgType;
    /**
     * 终态通知发起机构
     */
    private String txEndInstgpty;
    /**
     * 终态通知接收机构
     */
    private String txEndInstdpty;
    /**
     * 备注
     */
    private String remark;
    /**
     * 业务状态
     */
    private String processStatus;
    /**
     * 业务处理码
     */
    private String processCode;
    /**
     * 机构业务拒绝码
     */
    private String rejectCode;
    /**
     * 业务拒绝信息
     */
    private String rejectInf;
    /**
     * 原报文标识号
     */
    private String msgId;
    /**
     * 原报文编号
     */
    private String msgType;
    /**
     * 原发起机构
     */
    private String instgpty;
    /**
     * 原接收机构
     */
    private String instdpty;
    /**
     * 交易金额
     */
    private String amount;
    /**
     * 币种
     */
    private String ccy;
    /**
     * 最后更新日期
     */
    private String lastUpDate;
    /**
     * 最后更新时间
     */
    private String lastUpTime;
    /**
     * 最后更新时间毫秒
     */
    private String lastMicroSecond;

    public String getTxEndDate() {
        return txEndDate;
    }

    public void setTxEndDate(String txEndDate) {
        this.txEndDate = txEndDate;
    }

    public String getTxEndSerno() {
        return txEndSerno;
    }

    public void setTxEndSerno(String txEndSerno) {
        this.txEndSerno = txEndSerno;
    }

    public String getTxEndMsgId() {
        return txEndMsgId;
    }

    public void setTxEndMsgId(String txEndMsgId) {
        this.txEndMsgId = txEndMsgId;
    }

    public String getTxEndMsgType() {
        return txEndMsgType;
    }

    public void setTxEndMsgType(String txEndMsgType) {
        this.txEndMsgType = txEndMsgType;
    }

    public String getTxEndInstgpty() {
        return txEndInstgpty;
    }

    public void setTxEndInstgpty(String txEndInstgpty) {
        this.txEndInstgpty = txEndInstgpty;
    }

    public String getTxEndInstdpty() {
        return txEndInstdpty;
    }

    public void setTxEndInstdpty(String txEndInstdpty) {
        this.txEndInstdpty = txEndInstdpty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getRejectCode() {
        return rejectCode;
    }

    public void setRejectCode(String rejectCode) {
        this.rejectCode = rejectCode;
    }

    public String getRejectInf() {
        return rejectInf;
    }

    public void setRejectInf(String rejectInf) {
        this.rejectInf = rejectInf;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getInstgpty() {
        return instgpty;
    }

    public void setInstgpty(String instgpty) {
        this.instgpty = instgpty;
    }

    public String getInstdpty() {
        return instdpty;
    }

    public void setInstdpty(String instdpty) {
        this.instdpty = instdpty;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
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

    public String getLastMicroSecond() {
        return lastMicroSecond;
    }

    public void setLastMicroSecond(String lastMicroSecond) {
        this.lastMicroSecond = lastMicroSecond;
    }

    @Override
    public String toString() {
        return "PayNotifyDO{" +
                "txEndDate='" + txEndDate + '\'' +
                ", txEndSerno='" + txEndSerno + '\'' +
                ", txEndMsgId='" + txEndMsgId + '\'' +
                ", txEndMsgType='" + txEndMsgType + '\'' +
                ", txEndInstgpty='" + txEndInstgpty + '\'' +
                ", txEndInstdpty='" + txEndInstdpty + '\'' +
                ", remark='" + remark + '\'' +
                ", processStatus='" + processStatus + '\'' +
                ", processCode='" + processCode + '\'' +
                ", rejectCode='" + rejectCode + '\'' +
                ", rejectInf='" + rejectInf + '\'' +
                ", msgId='" + msgId + '\'' +
                ", msgType='" + msgType + '\'' +
                ", instgpty='" + instgpty + '\'' +
                ", instdpty='" + instdpty + '\'' +
                ", amount='" + HiddenUtil.acHidden(amount) + '\'' +
                ", ccy='" + ccy + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                ", lastMicroSecond='" + lastMicroSecond + '\'' +
                '}';
    }
}

