package com.dcits.dcwlt.pay.api.model;

import com.dcits.dcwlt.common.pay.util.HiddenUtil;

/**
 * @author
 * @Time 2020/10/14 9:32
 * @Version 1.0
 * Description:账务流水表数据实体
 */
public class AccFlowDO {

    private String coreReqDate;      //请求核心日期
    private String coreReqSerno;     //请求核心流水
    private String bookType;         //记账类型
    private String payDate;          //业务日期
    private String paySerno;         //业务流水
    private String brno;             //行所号
    private String tellerNo;         //柜员号
    private String acctBrno;         //记账网所
    private String currency;         //币种
    private String amount;           //金额
    private String feeAmount;        //手续费
    private String coreSysId;        //核心系统标识
    private String coreIntFlag;      //核心接口标志
    private String revTranFlag;      //反交易标志
    private String coreTime;         //核心时间
    private String coreTrxType;      //核心交易类型
    private String coreTrxCode;      //核心交易代码
    private String coreProcStatus;   //核心状态
    private String coreRetCode;      //核心返回代码
    private String coreRetMsg;       //核心返回信息
    private String coreAcctDate;     //核心返回日期
    private String coreSerno;        //核心返回流水
    private String payerAcct;        //付款人账号
    private String payerName;        //付款人名称
    private String payeeAcct;        //收款人账号
    private String payeeName;        //收款人名称
    private String origCoreReqDate;  //原请求核心日期
    private String origCoreReqSerno; //原请求核心流水
    private String origPayDate;      //原业务日期
    private String origPaySerno;     //原业务流水
    private String lastUpDate;       //最后更新日期
    private String lastUpTime;       //最后更新日期
    private String lastMicroSecond;  //最后更新毫秒


    public String getAcctBrno() {
        return acctBrno;
    }

    public void setAcctBrno(String acctBrno) {
        this.acctBrno = acctBrno;
    }

    public String getCoreReqDate() {
        return coreReqDate;
    }

    public void setCoreReqDate(String coreReqDate) {
        this.coreReqDate = coreReqDate;
    }

    public String getCoreReqSerno() {
        return coreReqSerno;
    }

    public void setCoreReqSerno(String coreReqSerno) {
        this.coreReqSerno = coreReqSerno;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

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

    public String getBrno() {
        return brno;
    }

    public void setBrno(String brno) {
        this.brno = brno;
    }

    public String getTellerNo() {
        return tellerNo;
    }

    public void setTellerNo(String tellerNo) {
        this.tellerNo = tellerNo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getCoreSysId() {
        return coreSysId;
    }

    public void setCoreSysId(String coreSysId) {
        this.coreSysId = coreSysId;
    }

    public String getCoreIntFlag() {
        return coreIntFlag;
    }

    public void setCoreIntFlag(String coreIntFlag) {
        this.coreIntFlag = coreIntFlag;
    }

    public String getRevTranFlag() {
        return revTranFlag;
    }

    public void setRevTranFlag(String revTranFlag) {
        this.revTranFlag = revTranFlag;
    }

    public String getCoreTime() {
        return coreTime;
    }

    public void setCoreTime(String coreTime) {
        this.coreTime = coreTime;
    }

    public String getCoreTrxType() {
        return coreTrxType;
    }

    public void setCoreTrxType(String coreTrxType) {
        this.coreTrxType = coreTrxType;
    }

    public String getCoreTrxCode() {
        return coreTrxCode;
    }

    public void setCoreTrxCode(String coreTrxCode) {
        this.coreTrxCode = coreTrxCode;
    }

    public String getCoreProcStatus() {
        return coreProcStatus;
    }

    public void setCoreProcStatus(String coreProcStatus) {
        this.coreProcStatus = coreProcStatus;
    }

    public String getCoreRetCode() {
        return coreRetCode;
    }

    public void setCoreRetCode(String coreRetCode) {
        this.coreRetCode = coreRetCode;
    }

    public String getCoreRetMsg() {
        return coreRetMsg;
    }

    public void setCoreRetMsg(String coreRetMsg) {
        this.coreRetMsg = coreRetMsg;
    }

    public String getCoreAcctDate() {
        return coreAcctDate;
    }

    public void setCoreAcctDate(String coreAcctDate) {
        this.coreAcctDate = coreAcctDate;
    }

    public String getCoreSerno() {
        return coreSerno;
    }

    public void setCoreSerno(String coreSerno) {
        this.coreSerno = coreSerno;
    }

    public String getPayerAcct() {
        return payerAcct;
    }

    public void setPayerAcct(String payerAcct) {
        this.payerAcct = payerAcct;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayeeAcct() {
        return payeeAcct;
    }

    public void setPayeeAcct(String payeeAcct) {
        this.payeeAcct = payeeAcct;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getOrigCoreReqDate() {
        return origCoreReqDate;
    }

    public void setOrigCoreReqDate(String origCoreReqDate) {
        this.origCoreReqDate = origCoreReqDate;
    }

    public String getOrigCoreReqSerno() {
        return origCoreReqSerno;
    }

    public void setOrigCoreReqSerno(String origCoreReqSerno) {
        this.origCoreReqSerno = origCoreReqSerno;
    }

    public String getOrigPayDate() {
        return origPayDate;
    }

    public void setOrigPayDate(String origPayDate) {
        this.origPayDate = origPayDate;
    }

    public String getOrigPaySerno() {
        return origPaySerno;
    }

    public void setOrigPaySerno(String origPaySerno) {
        this.origPaySerno = origPaySerno;
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
        return "AccFlowDO [coreReqDate=" + coreReqDate + ", coreReqSerno=" + coreReqSerno + ", bookType=" + bookType
                + ", payDate=" + payDate + ", paySerno=" + paySerno + ", brno=" + brno
                + ", tellerNo=" + tellerNo + ", acctBrno=" + acctBrno + ", currency=" + currency + ", amount=" + amount
                + ", feeAmount=" + feeAmount + ", coreSysid=" + coreSysId + ", coreIntFlag=" + coreIntFlag
                + ", revTranFlag=" + revTranFlag + ", coreTime=" + coreTime + ", coreTrxType=" + coreTrxType
                + ", coreTrxCode=" + coreTrxCode + ", coreProcStatus=" + coreProcStatus + ", coreRetCode=" + coreRetCode
                + ", coreRetMsg=" + coreRetMsg + ", coreAcctDate=" + coreAcctDate + ", coreSerno=" + coreSerno
                + ", payerAcct=" + payerAcct + ", payerName=" + payerName
                + ", payeeAcct=" + payerAcct + ", payeeName=" + payerName
                + ", origCoreReqDate=" + origCoreReqDate + ", origCoreReqSerno=" + origCoreReqSerno
                + ", origPayDate=" + origPayDate + ", origPaySerno=" + origPaySerno + ", lastUpDate=" + lastUpDate
                + ", lastUpTime=" + lastUpTime + ", lastMicroSecond=" + lastMicroSecond + "]";
    }


}
