package com.dcits.dcwlt.pay.api.domain.dcep.check;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author maozewu
 * @Date 2021/1/14 16:58
 * @Version 1.0
 * Description:杈撳嚭鏄庣粏瀵硅处缁撴灉鏌ヨ瀹炰綋
 */
public class CheckWrongQueryResDTO {
	private String payDate; //骞冲彴鏃ユ湡
    private String paySerno; //骞冲彴娴佹按
    private String payTime; //骞冲彴鏃堕棿
    private String msgType; //鎶ユ枃缂栧彿
    private String msgId; //鎶ユ枃鏍囪瘑鍙�
    private String batchId; //浜ゆ槗鎵规鍙�
    private String payFlag; //鏀朵粯鏍囪瘑
    private String instgDrctPty; //鍙戣捣鏈烘瀯
    private String dbitParty; //浠樻鏈烘瀯缂栫爜
    private String payerWalletId; //浠樻浜洪挶鍖匢d
    private String payerAccount; //浠樻浜鸿处鍙�
    private String crdtParty; //鏀舵鏈烘瀯缂栫爜
    private String payeeName; //鏀舵浜哄悕绉�
    private String payeeAccount; //鏀舵浜鸿处鍙�
    private String payeeWalletId; //鏀舵浜洪挶鍖匢D
    private String ccy; //璐у竵浠ｇ爜
    private String amount; //閲戦
    private String ognlMsgType; //鍘熸姤鏂囩紪鍙�
    private String ognlMsgId; //鍘熸姤鏂囨爣璇嗗彿
    private String tradeStatus; //浜ゆ槗鐘舵��
    private String coreStatus; //鏍稿績鐘舵��
    private String pathStatus; //閫氶亾鐘舵��
    private String checkStatus; //瀵硅处鏍囪瘑
    private String procStatus; //涓嶅钩璁板綍澶勭悊鐘舵��
    
//    @JSONField(name = "PAYDATE")
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	
//	@JSONField(name = "PAYSERNO")
	public String getPaySerno() {
		return paySerno;
	}
	public void setPaySerno(String paySerno) {
		this.paySerno = paySerno;
	}
	
//	@JSONField(name = "PAYTIME")
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
	@JSONField(name = "msgType")
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@JSONField(name = "msgId")
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@JSONField(name = "batchId")
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
//	@JSONField(name = "PAYFLAG")
	public String getPayFlag() {
		return payFlag;
	}
	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}
	
	@JSONField(name = "instgDrctPty")
	public String getInstgDrctPty() {
		return instgDrctPty;
	}
	public void setInstgDrctPty(String instgDrctPty) {
		this.instgDrctPty = instgDrctPty;
	}
	
//	@JSONField(name = "DBITParty")
	public String getDbitParty() {
		return dbitParty;
	}
	public void setDbitParty(String dbitParty) {
		this.dbitParty = dbitParty;
	}
	
	@JSONField(name = "payerWalletId")
	public String getPayerWalletId() {
		return payerWalletId;
	}
	public void setPayerWalletId(String payerWalletId) {
		this.payerWalletId = payerWalletId;
	}
	
	@JSONField(name = "payerAccount")
	public String getPayerAccount() {
		return payerAccount;
	}
	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}
	
//	@JSONField(name = "CRDTParty")
	public String getCrdtParty() {
		return crdtParty;
	}
	public void setCrdtParty(String crdtParty) {
		this.crdtParty = crdtParty;
	}
	
	@JSONField(name = "payeeName")
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	
	@JSONField(name = "payeeAccount")
	public String getPayeeAccount() {
		return payeeAccount;
	}
	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}
	
	@JSONField(name = "payeeWalletId")
	public String getPayeeWalletId() {
		return payeeWalletId;
	}
	public void setPayeeWalletId(String payeeWalletId) {
		this.payeeWalletId = payeeWalletId;
	}
	
	@JSONField(name = "ccy")
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
	
	@JSONField(name = "amount")
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@JSONField(name = "ognlMsgType")
	public String getOgnlMsgType() {
		return ognlMsgType;
	}
	public void setOgnlMsgType(String ognlMsgType) {
		this.ognlMsgType = ognlMsgType;
	}
	
	@JSONField(name = "ognlMsgId")
	public String getOgnlMsgId() {
		return ognlMsgId;
	}
	public void setOgnlMsgId(String ognlMsgId) {
		this.ognlMsgId = ognlMsgId;
	}
	
	@JSONField(name = "tradeStatus")
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
	@JSONField(name = "coreStatus")
	public String getCoreStatus() {
		return coreStatus;
	}
	public void setCoreStatus(String coreStatus) {
		this.coreStatus = coreStatus;
	}
	
	@JSONField(name = "pathStatus")
	public String getPathStatus() {
		return pathStatus;
	}
	public void setPathStatus(String pathStatus) {
		this.pathStatus = pathStatus;
	}
	
	@JSONField(name = "checkStatus")
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	@JSONField(name = "procStatus")
	public String getProcStatus() {
		return procStatus;
	}
	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}
	
	@Override
	public String toString() {
		return "CheckWrongQueryResDTO [payDate=" + payDate + ", paySerno=" + paySerno + ", payTime=" + payTime
				+ ", msgType=" + msgType + ", msgId=" + msgId + ", batchId=" + batchId + ", payFlag=" + payFlag
				+ ", instgDrctPty=" + instgDrctPty + ", dbitParty=" + dbitParty + ", payerWalletId=" + payerWalletId
				+ ", payerAccount=" + payerAccount + ", crdtParty=" + crdtParty + ", payeeName=" + payeeName
				+ ", payeeAccount=" + payeeAccount + ", payeeWalletId=" + payeeWalletId + ", ccy=" + ccy + ", amount="
				+ amount + ", ognlMsgType=" + ognlMsgType + ", ognlMsgId=" + ognlMsgId + ", tradeStatus=" + tradeStatus
				+ ", coreStatus=" + coreStatus + ", pathStatus=" + pathStatus + ", checkStatus=" + checkStatus
				+ ", procStatus=" + procStatus + "]";
	}
    
        
}
