package com.dcits.dcwlt.pay.api.model;

/**
 *
 * @date  2020/12/30
 * @version 1.0.0
 * <p>协议表实体</p>
 */
public class SignInfoDO {

    /**
     * 平台日期
     */
    private String payDate;

    /**
     * 平台流水
     */
    private String paySerNo;

    /**
     * 平台时间
     */
    private String payTime;

    /**
     * 挂接协议号
     */
    private String signNo;

    /**
     * 协议状态：N(签约状态),C(解约状态)
     */
    private String signStatus;

    /**
     * 签约人银行账户所属机构
     */
    private String acctPtyId;

    /**
     * 签约人银行账户类型
     */
    private String acctType;

    /**
     * 签约人银行账户账号
     */
    private String acctId;

    /**
     * 签约人银行账户户名
     */
    private String acctName;

    /**
     * 签约人证件类型
     */
    private String idType;

    /**
     * 签约人证件号码
     */
    private String idNo;

    /**
     * 银行预留手机号码
     */
    private String telephone;

    /**
     * 钱包开立所属机构编码
     */
    private String walletPtyId;

    /**
     * 钱包ID
     */
    private String walletId;

    /**
     * 钱包类型
     */
    private String walletType;

    /**
     *钱包等级
     */
    private String walletLevel;

    /**
     * 最后更新流水
     */
    private String lastUpJrnNo;

    /**
     * 最后更新日期
     */
    private String lastUpDate;

    /**
     * 最后更新时间
     */
    private String lastUpTime;

    public SignInfoDO() {
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPaySerNo() {
        return paySerNo;
    }

    public void setPaySerNo(String paySerNo) {
        this.paySerNo = paySerNo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public String getAcctPtyId() {
        return acctPtyId;
    }

    public void setAcctPtyId(String acctPtyId) {
        this.acctPtyId = acctPtyId;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWalletPtyId() {
        return walletPtyId;
    }

    public void setWalletPtyId(String walletPtyId) {
        this.walletPtyId = walletPtyId;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    public String getWalletLevel() {
        return walletLevel;
    }

    public void setWalletLevel(String walletLevel) {
        this.walletLevel = walletLevel;
    }

    public String getLastUpJrnNo() {
        return lastUpJrnNo;
    }

    public void setLastUpJrnNo(String lastUpJrnNo) {
        this.lastUpJrnNo = lastUpJrnNo;
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
        return "SignInfoDO{" +
                "payDate='" + payDate + '\'' +
                ", paySerNo='" + paySerNo + '\'' +
                ", payTime='" + payTime + '\'' +
                ", signNo='" + signNo + '\'' +
                ", signStatus='" + signStatus + '\'' +
                ", acctPtyId='" + acctPtyId + '\'' +
                ", acctType='" + acctType + '\'' +
                ", acctId='" + acctId + '\'' +
                ", acctName='" + acctName + '\'' +
                ", idType='" + idType + '\'' +
                ", idNo='" + idNo + '\'' +
                ", telephone='" + telephone + '\'' +
                ", walletPtyId='" + walletPtyId + '\'' +
                ", walletId='" + walletId + '\'' +
                ", walletType='" + walletType + '\'' +
                ", walletLevel='" + walletLevel + '\'' +
                ", lastUpJrnNo='" + lastUpJrnNo + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                '}';
    }
}
