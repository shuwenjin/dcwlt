package com.dcits.dcwlt.pay.api.model;

/**
 *
 * @desc 业务权限变更表实体
 */
public class AuthInfoDO {
    /*
     * 机构编码
     * */
    private String partyId;
    /*
     * 报文编号
     * */
    private String msgType;
    /*
     * 业务类型
     * */
    private String tradeCtgyCode;
    /*
     * 发起权限标识
     * */
    private String sendAuth;
    /*
     * 接收权限标识
     * */
    private String recvAuth;
    /*
     * 撤销状态
     * */
    private String status;
    /*
     * 生效日期
     * */
    private String effectDate;
    /*
     * 失效日期
     * */
    private String inEffectiveDate;
    /*
     * 最后更新时间
     * */
    private String lastUpDate;
    /*
     * 最后更新时间
     * */
    private String lastUpTime;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "AuthInfoDO{" +
                "partyId='" + partyId + '\'' +
                ", msgType='" + msgType + '\'' +
                ", tradeCtgyCode='" + tradeCtgyCode + '\'' +
                ", sendAuth='" + sendAuth + '\'' +
                ", recvAuth='" + recvAuth + '\'' +
                ", status='" + status + '\'' +
                ", effectDate='" + effectDate + '\'' +
                ", inEffectiveDate='" + inEffectiveDate + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                '}';
    }
}
