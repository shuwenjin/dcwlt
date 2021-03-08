package com.dcits.dcwlt.common.pay.channel.bankcore.dto;


public class LSFK43ReqMsg{

    private String schannel;                                     // 来源渠道
    private String senderSn;                                     // 请求流水号
    private String searchParams;                                 // 查询参数信息
    private String tradeOrgNo;                                   // 交易机构编号
    private String acctOrgNo;                                    // 账户归属机构
    private String tradeTime;                                    // 交易时间
    private String custId;                                       // 客户号
    private String custType;                                     // 客户类型
    private String custName;                                     // 客户名称
    private String custIdNo;                                     // 客户证件号
    private String sysFlag;                                      // 源系统标识
    private String channel;                                      // 涉及渠道标识
    private String tradeAccount;                                 // 交易账户
    private String currCd;                                       // 交易币种
    private String tradeAmount;                                  // 交易金额
    private String alertReason;                                  // 预警原因
    private String tradeType;                                    // 交易种类
    private String alertType;                                    // 预警类型
    private String oppAcctOrgNo;                                 // 交易对手行所名称
    private String oppCustType;                                  // 交易对手类型
    private String oppTradeAccount;                              // 交易对手账号
    private String oppCustName;                                  // 交易对手姓名
    private String oppCustIdNo;                                  // 交易对手证件号码
    private String rtType;                                       // 阻断类型
    private String versionNo;                                    // 阻断类型

    public String getSchannel() {
        return schannel;
    }

    public void setSchannel(String schannel) {
        this.schannel = schannel;
    }

    public String getSenderSn() {
        return senderSn;
    }

    public void setSenderSn(String senderSn) {
        this.senderSn = senderSn;
    }

    public String getSearchParams() {
        return searchParams;
    }

    public void setSearchParams(String searchParams) {
        this.searchParams = searchParams;
    }

    public String getTradeOrgNo() {
        return tradeOrgNo;
    }

    public void setTradeOrgNo(String tradeOrgNo) {
        this.tradeOrgNo = tradeOrgNo;
    }

    public String getAcctOrgNo() {
        return acctOrgNo;
    }

    public void setAcctOrgNo(String acctOrgNo) {
        this.acctOrgNo = acctOrgNo;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustIdNo() {
        return custIdNo;
    }

    public void setCustIdNo(String custIdNo) {
        this.custIdNo = custIdNo;
    }

    public String getSysFlag() {
        return sysFlag;
    }

    public void setSysFlag(String sysFlag) {
        this.sysFlag = sysFlag;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    public String getCurrCd() {
        return currCd;
    }

    public void setCurrCd(String currCd) {
        this.currCd = currCd;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getAlertReason() {
        return alertReason;
    }

    public void setAlertReason(String alertReason) {
        this.alertReason = alertReason;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getOppAcctOrgNo() {
        return oppAcctOrgNo;
    }

    public void setOppAcctOrgNo(String oppAcctOrgNo) {
        this.oppAcctOrgNo = oppAcctOrgNo;
    }

    public String getOppCustType() {
        return oppCustType;
    }

    public void setOppCustType(String oppCustType) {
        this.oppCustType = oppCustType;
    }

    public String getOppTradeAccount() {
        return oppTradeAccount;
    }

    public void setOppTradeAccount(String oppTradeAccount) {
        this.oppTradeAccount = oppTradeAccount;
    }

    public String getOppCustName() {
        return oppCustName;
    }

    public void setOppCustName(String oppCustName) {
        this.oppCustName = oppCustName;
    }

    public String getOppCustIdNo() {
        return oppCustIdNo;
    }

    public void setOppCustIdNo(String oppCustIdNo) {
        this.oppCustIdNo = oppCustIdNo;
    }

    public String getRtType() {
        return rtType;
    }

    public void setRtType(String rtType) {
        this.rtType = rtType;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    @Override
    public String toString() {
        return "LSFK43ReqMsg{" +
                "schannel='" + schannel + '\'' +
                ", senderSn='" + senderSn + '\'' +
                ", searchParams='" + searchParams + '\'' +
                ", tradeOrgNo='" + tradeOrgNo + '\'' +
                ", acctOrgNo='" + acctOrgNo + '\'' +
                ", tradeTime='" + tradeTime + '\'' +
                ", custId='" + custId + '\'' +
                ", custType='" + custType + '\'' +
                ", custName='" + custName + '\'' +
                ", custIdNo='" + custIdNo + '\'' +
                ", sysFlag='" + sysFlag + '\'' +
                ", channel='" + channel + '\'' +
                ", tradeAccount='" + tradeAccount + '\'' +
                ", currCd='" + currCd + '\'' +
                ", tradeAmount='" + tradeAmount + '\'' +
                ", alertReason='" + alertReason + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", alertType='" + alertType + '\'' +
                ", oppAcctOrgNo='" + oppAcctOrgNo + '\'' +
                ", oppCustType='" + oppCustType + '\'' +
                ", oppTradeAccount='" + oppTradeAccount + '\'' +
                ", oppCustName='" + oppCustName + '\'' +
                ", oppCustIdNo='" + oppCustIdNo + '\'' +
                ", rtType='" + rtType + '\'' +
                ", versionNo='" + versionNo + '\'' +
                '}';
    }


}
