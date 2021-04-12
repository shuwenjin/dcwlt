package com.dcits.dcwlt.pay.api.model;

/**
 * 通道对账明细表
 */
public class CheckPathDetialDO {
    private String workdate;  //业务日期	VARCHAR	8
    private String msgId;  //	报文标识号	VARCHAR	35
    private String batchId;  //	交易批次号	VARCHAR	13
    private String splitNum;  //	分片号	VARCHAR	2
    private String fileName;  //	文件名	VARCHAR	64
    private String dtlBizTime;  //	业务处理时间	VARCHAR	14
    private String msgType;  //	报文编号	VARCHAR	35
    private String dtlMsgId;  //	明细的报文标识号	VARCHAR	35
    private String instgDrctPty;  //	发起机构	VARCHAR	14
    private String dBITParty;  //	付款机构编码	VARCHAR	14
    private String cRDTParty;  //	收款机构编码	VARCHAR	14
    private String ccy;  //	货币代码	VARCHAR	3
    private String amount;  //	金额	VARCHAR	19
    private String dtlBizStatus;  //	业务状态	VARCHAR	4
    private String dtlDesc;  //	交易描述信息	VARCHAR	384
    private String payeeName;  //	收款人名称	VARCHAR	180
    private String payeeAccount;  //	收款人账号	VARCHAR	32
    private String payeeWalletId;  //	收款人钱包ID	VARCHAR	34
    private String payerAccount;  //	付款人账号	VARCHAR	32
    private String ognlMsgType;  //	原报文编号	VARCHAR	35
    private String ognlMsgId;  //	原报文标识号	VARCHAR	35
    private String checkStatus;  //	对账标识	VARCHAR	1
    private String lastUpDate;  //	最后更新日期	VARCHAR	8
    private String lastUpTime;  //	最后更新时间	VARCHAR	6

    public String getWorkdate() {
        return workdate;
    }

    public void setWorkdate(String workdate) {
        this.workdate = workdate;
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

    public String getSplitNum() {
        return splitNum;
    }

    public void setSplitNum(String splitNum) {
        this.splitNum = splitNum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDtlBizTime() {
        return dtlBizTime;
    }

    public void setDtlBizTime(String dtlBizTime) {
        this.dtlBizTime = dtlBizTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getDtlMsgId() {
        return dtlMsgId;
    }

    public void setDtlMsgId(String dtlMsgId) {
        this.dtlMsgId = dtlMsgId;
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

    public String getCRDTParty() {
        return cRDTParty;
    }

    public void setCRDTParty(String cRDTParty) {
        this.cRDTParty = cRDTParty;
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

    public String getDtlBizStatus() {
        return dtlBizStatus;
    }

    public void setDtlBizStatus(String dtlBizStatus) {
        this.dtlBizStatus = dtlBizStatus;
    }

    public String getDtlDesc() {
        return dtlDesc;
    }

    public void setDtlDesc(String dtlDesc) {
        this.dtlDesc = dtlDesc;
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

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
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

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
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
        return "CheckPathDetialDo{" +
                "workdate='" + workdate + '\'' +
                ", msgId='" + msgId + '\'' +
                ", batchId='" + batchId + '\'' +
                ", splitNum='" + splitNum + '\'' +
                ", fileName='" + fileName + '\'' +
                ", dtlBizTime='" + dtlBizTime + '\'' +
                ", msgType='" + msgType + '\'' +
                ", dtlMsgId='" + dtlMsgId + '\'' +
                ", instgDrctPty='" + instgDrctPty + '\'' +
                ", dBITParty='" + dBITParty + '\'' +
                ", cRDTParty='" + cRDTParty + '\'' +
                ", ccy='" + ccy + '\'' +
                ", amount='" + amount + '\'' +
                ", dtlBizStatus='" + dtlBizStatus + '\'' +
                ", dtlDesc='" + dtlDesc + '\'' +
                ", payeeName='" + payeeName + '\'' +
                ", payeeAccount='" + payeeAccount + '\'' +
                ", payeeWalletId='" + payeeWalletId + '\'' +
                ", payerAccount='" + payerAccount + '\'' +
                ", ognlMsgType='" + ognlMsgType + '\'' +
                ", ognlMsgId='" + ognlMsgId + '\'' +
                ", checkStatus='" + checkStatus + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                '}';
    }
}
