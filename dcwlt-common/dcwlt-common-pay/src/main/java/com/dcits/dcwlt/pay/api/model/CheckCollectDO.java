package com.dcits.dcwlt.pay.api.model;

/**
 * 对账明细采集表
 */
public class CheckCollectDO {
    private String payDate;          	//平台日期          VARCHAR          8
    private String paySerno;          //平台流水          VARCHAR          32
    private String payTime;          	//平台时间          VARCHAR          10
    private String coreAcctDate;    //核心会计日期	VARCHAR	8
    private String msgType;    //	报文编号	VARCHAR	35
    private String msgId;    //	报文标识号	VARCHAR	35
    private String batchId;    //	交易批次号	VARCHAR	13
    private String payFlag;    // 收付标识	VARCHAR	5
    private String instgDrctPty;    //	发起机构	VARCHAR	14
    private String dBITParty;    //	付款机构编码	VARCHAR	14
    private String payerWalletId;    //	付款人钱包Id	VARCHAR	68
    private String payerAccount;    //	付款人账号	VARCHAR	32
    private String cRDTParty;    //	收款机构编码	VARCHAR	14
    private String payeeName;    //	收款人名称	VARCHAR	180
    private String payeeAccount;    //	收款人账号	VARCHAR	32
    private String payeeWalletId;    //	收款人钱包ID	VARCHAR	34
    private String ccy;    //	货币代码	VARCHAR	3
    private String amount;    //	金额	VARCHAR	19
    private String ognlMsgType;    //	原报文编号	VARCHAR	35
    private String ognlMsgId;    //	原报文标识号	VARCHAR	35
    private String tradeStatus;    //	交易状态	VARCHAR	1
    private String coreStatus;    //	核心状态	VARCHAR	1
    private String pathStatus;    //	通道状态	VARCHAR	1
    private String lastUpDate;    //	最后更新日期	VARCHAR	8
    private String lastUpTime;    //	最后更新时间	VARCHAR	6

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

    public String getCoreAcctDate() {
		return coreAcctDate;
	}

	public void setCoreAcctDate(String coreAcctDate) {
		this.coreAcctDate = coreAcctDate;
	}

	public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getInstgDrctPty() {
        return instgDrctPty;
    }

    public void setInstgDrctPty(String instgDrctPty) {
        this.instgDrctPty = instgDrctPty;
    }

    public String getDBITParty() {
        return dBITParty;
    }

    public void setDBITParty(String dBITParty) {
        this.dBITParty = dBITParty;
    }

    public String getPayerWalletId() {
        return payerWalletId;
    }

    public void setPayerWalletId(String payerWalletId) {
        this.payerWalletId = payerWalletId;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getCRDTParty() {
        return cRDTParty;
    }

    public void setCRDTParty(String cRDTParty) {
        this.cRDTParty = cRDTParty;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    public String getPayeeWalletId() {
        return payeeWalletId;
    }

    public void setPayeeWalletId(String payeeWalletId) {
        this.payeeWalletId = payeeWalletId;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOgnlMsgType() {
        return ognlMsgType;
    }

    public void setOgnlMsgType(String ognlMsgType) {
        this.ognlMsgType = ognlMsgType;
    }

    public String getOgnlMsgId() {
        return ognlMsgId;
    }

    public void setOgnlMsgId(String ognlMsgId) {
        this.ognlMsgId = ognlMsgId;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getCoreStatus() {
        return coreStatus;
    }

    public void setCoreStatus(String coreStatus) {
        this.coreStatus = coreStatus;
    }

    public String getPathStatus() {
        return pathStatus;
    }

    public void setPathStatus(String pathStatus) {
        this.pathStatus = pathStatus;
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

    public String getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(String payFlag) {
        this.payFlag = payFlag;
    }

    @Override
    public String toString() {
        return "CheckCollectDo{" +
                "payDate='" + payDate + '\'' +
                ", paySerno='" + paySerno + '\'' +
                ", payTime='" + payTime + '\'' +
                ", coreAcctDate='" + coreAcctDate + '\'' +
                ", msgType='" + msgType + '\'' +
                ", msgId='" + msgId + '\'' +
                ", batchId='" + batchId + '\'' +
                ", instgDrctPty='" + instgDrctPty + '\'' +
                ", dBITParty='" + dBITParty + '\'' +
                ", payerWalletId='" + payerWalletId + '\'' +
                ", payerAccount='" + payerAccount + '\'' +
                ", cRDTParty='" + cRDTParty + '\'' +
                ", payeeName='" + payeeName + '\'' +
                ", payeeAccount='" + payeeAccount + '\'' +
                ", payeeWalletId='" + payeeWalletId + '\'' +
                ", ccy='" + ccy + '\'' +
                ", amount='" + amount + '\'' +
                ", ognlMsgType='" + ognlMsgType + '\'' +
                ", ognlMsgId='" + ognlMsgId + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                ", coreStatus='" + coreStatus + '\'' +
                ", pathStatus='" + pathStatus + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                '}';
    }


}
