package com.dcits.dcwlt.pay.api.model;

import com.dcits.dcwlt.common.pay.enums.CheckStatusEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;

/**
 * 通道对账汇总表
 */
public class CheckPathDO {
    private String payDate;          	//平台日期          VARCHAR          8
    private String paySerno;          //平台流水          VARCHAR          32
    private String payTime;          	//平台时间          VARCHAR          10
    private String msgId;        		//报文标识号          VARCHAR          35
    private String senderDateTime;    //报文发送时间          VARCHAR          14
    private String instgDrctPty;      //发起机构          VARCHAR          14
    private String instdDrctPty;      //接收机构          VARCHAR          14
    private String remark;           //备注          VARCHAR          256
    private String digitalEnvelope;  //数字信封	VARCHAR	512
    private String batchDate;        //  批次日期          VARCHAR          8
    private String batchId;          //交易批次号          VARCHAR          13
    private String countNum;        // 总笔数          VARCHAR          15
    private String countAmt;         // 总金额          VARCHAR          18
    private String ccy;             //货币代码	VARCHAR	3
    private String dBITCountNum;     //     付款笔数          VARCHAR          15
    private String dBITCountAmt;     //     付款金额          VARCHAR          18
    private String cRDTCountNum;     //     收款笔数          VARCHAR          15
    private String cRDTCountAmt;     //     收款金额          VARCHAR          18
    private String msgType;          //报文编号          VARCHAR          15
    private ProcessStsCdEnum msgBizStatus;      //    业务状态          VARCHAR          4
    private String msgCountNum;       //   总笔数          VARCHAR          15
    private String msgCountAmt;       //   总金额          VARCHAR          18
    private String msgDBITCountNum;   //       付款笔数          VARCHAR          15
    private String msgDBITCountAmt;   //       付款金额          VARCHAR          18
    private String msgCRDTCountNum;   //       收款笔数          VARCHAR          15
    private String msgCRDTCountAmt;   //       收款金额          VARCHAR          18
    private CheckStatusEnum checkStatus;       //   对账标识          VARCHAR          1
    private String lastUpDate;        //  最后更新日期          VARCHAR          8
    private String lastUpTime;        //  最后更新时间          VARCHAR          6
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getPaySerno() {
		return paySerno;
	}
	public void setPaySerno(String paySerno) {
		this.paySerno = paySerno;
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
	public String getDigitalEnvelope() {
		return digitalEnvelope;
	}
	public void setDigitalEnvelope(String digitalEnvelope) {
		this.digitalEnvelope = digitalEnvelope;
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
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
	public String getDBITCountNum() {
		return dBITCountNum;
	}
	public void setDBITCountNum(String dBITCountNum) {
		this.dBITCountNum = dBITCountNum;
	}
	public String getDBITCountAmt() {
		return dBITCountAmt;
	}
	public void setDBITCountAmt(String dBITCountAmt) {
		this.dBITCountAmt = dBITCountAmt;
	}
	public String getCRDTCountNum() {
		return cRDTCountNum;
	}
	public void setCRDTCountNum(String cRDTCountNum) {
		this.cRDTCountNum = cRDTCountNum;
	}
	public String getCRDTCountAmt() {
		return cRDTCountAmt;
	}
	public void setCRDTCountAmt(String cRDTCountAmt) {
		this.cRDTCountAmt = cRDTCountAmt;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public ProcessStsCdEnum getMsgBizStatus() {
		return msgBizStatus;
	}
	public void setMsgBizStatus(ProcessStsCdEnum msgBizStatus) {
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
	public String getMsgDBITCountNum() {
		return msgDBITCountNum;
	}
	public void setMsgDBITCountNum(String msgDBITCountNum) {
		this.msgDBITCountNum = msgDBITCountNum;
	}
	public String getMsgDBITCountAmt() {
		return msgDBITCountAmt;
	}
	public void setMsgDBITCountAmt(String msgDBITCountAmt) {
		this.msgDBITCountAmt = msgDBITCountAmt;
	}
	public String getMsgCRDTCountNum() {
		return msgCRDTCountNum;
	}
	public void setMsgCRDTCountNum(String msgCRDTCountNum) {
		this.msgCRDTCountNum = msgCRDTCountNum;
	}
	public String getMsgCRDTCountAmt() {
		return msgCRDTCountAmt;
	}
	public void setMsgCRDTCountAmt(String msgCRDTCountAmt) {
		this.msgCRDTCountAmt = msgCRDTCountAmt;
	}
	public CheckStatusEnum getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(CheckStatusEnum checkStatus) {
		this.checkStatus = checkStatus;
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
		return "CheckPathDO [payDate=" + payDate + ", paySerno=" + paySerno + ", payTime=" + payTime + ", msgId="
				+ msgId + ", senderDateTime=" + senderDateTime + ", instgDrctPty=" + instgDrctPty + ", instdDrctPty="
				+ instdDrctPty + ", remark=" + remark + ", digitalEnvelope=" + digitalEnvelope + ", batchDate="
				+ batchDate + ", batchId=" + batchId + ", countNum=" + countNum + ", countAmt=" + countAmt + ", ccy="
				+ ccy + ", dBITCountNum=" + dBITCountNum + ", dBITCountAmt=" + dBITCountAmt + ", cRDTCountNum="
				+ cRDTCountNum + ", cRDTCountAmt=" + cRDTCountAmt + ", msgType=" + msgType + ", msgBizStatus="
				+ msgBizStatus + ", msgCountNum=" + msgCountNum + ", msgCountAmt=" + msgCountAmt + ", msgDBITCountNum="
				+ msgDBITCountNum + ", msgDBITCountAmt=" + msgDBITCountAmt + ", msgCRDTCountNum=" + msgCRDTCountNum
				+ ", msgCRDTCountAmt=" + msgCRDTCountAmt + ", checkStatus=" + checkStatus + ", lastUpDate=" + lastUpDate
				+ ", lastUpTime=" + lastUpTime + "]";
	}
	
	
}

