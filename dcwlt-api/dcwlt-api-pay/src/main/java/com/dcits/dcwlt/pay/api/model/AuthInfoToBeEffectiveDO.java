package com.dcits.dcwlt.pay.api.model;

/*
* 业务权限更变临时表DO
* */
public class AuthInfoToBeEffectiveDO {

      private String partyId;              //机构编码
      private String msgType;              //报文编号
      private String tradeCtgyCode;        //业务类型
      private String sendAuth;             //发起权限标识
      private String recvAuth;             //接收权限标识
      private String changeType;           //变更类型
      private String effectiveType;        //生效类型
      private String effectDate;           //生效日期
      private String inEffectiveDate;      //失效日期
      private String lastUpDate;           //最后更新日期
      private String lastUpTime;           //最后更新时间

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getTradeCtgyCode() {
        return tradeCtgyCode;
    }

    public void setTradeCtgyCode(String tradeCtgyCode) {
        this.tradeCtgyCode = tradeCtgyCode;
    }

    public String getSendAuth() {
        return sendAuth;
    }

    public void setSendAuth(String sendAuth) {
        this.sendAuth = sendAuth;
    }

    public String getRecvAuth() {
        return recvAuth;
    }

    public void setRecvAuth(String recvAuth) {
        this.recvAuth = recvAuth;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getEffectiveType() {
        return effectiveType;
    }

    public void setEffectiveType(String effectiveType) {
        this.effectiveType = effectiveType;
    }

    public String getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(String effectDate) {
        this.effectDate = effectDate;
    }

    public String getInEffectiveDate() {
        return inEffectiveDate;
    }

    public void setInEffectiveDate(String inEffectiveDate) {
        this.inEffectiveDate = inEffectiveDate;
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
        return "AuthInfoToBeEffectiveDO{" +
                "partyId='" + partyId + '\'' +
                ", msgType='" + msgType + '\'' +
                ", tradeCtgyCode='" + tradeCtgyCode + '\'' +
                ", sendAuth='" + sendAuth + '\'' +
                ", recvAuth='" + recvAuth + '\'' +
                ", changeType='" + changeType + '\'' +
                ", effectiveType='" + effectiveType + '\'' +
                ", effectDate='" + effectDate + '\'' +
                ", inEffectiveDate='" + inEffectiveDate + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                '}';
    }
}

