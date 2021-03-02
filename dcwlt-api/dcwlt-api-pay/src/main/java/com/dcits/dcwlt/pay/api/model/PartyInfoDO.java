package com.dcits.dcwlt.pay.api.model;


import com.dcits.dcwlt.common.pay.enums.PartyTpCdEnum;
import com.dcits.dcwlt.common.pay.enums.StatusTpCdEnum;
import com.dcits.dcwlt.common.pay.util.HiddenUtil;

/**
 * 机构信息表实体类
 *
 *
 * @date 2020/12/30
 */
public class PartyInfoDO {

    protected String partyID;               //机构编码
    protected PartyTpCdEnum partyType;      //机构类型
    protected String partyName;             //机构名称
    protected String partyAlias;            //机构标识
    protected StatusTpCdEnum partyStatus;   //机构状态
    protected String status;                //撤销状态
    protected String contact;               //联系人
    protected String telephone;             //电话
    protected String mail;                  //邮件
    protected String fax;                   //传真号
    protected String effectDate;            //生效日期
    protected String inEffectiveDate;       //失效日期
    protected long changeNumber;              //变更期数
    protected long changeCircleTimes;        //变更记录条目
    protected String lastUpDate;            //最后更新日期
    protected String lastUpTime;            //最后更新时间

    public String getPartyID() {
        return partyID;
    }

    public void setPartyID(String partyID) {
        this.partyID = partyID;
    }

    public PartyTpCdEnum getPartyType() {
        return partyType;
    }

    public void setPartyType(PartyTpCdEnum partyType) {
        this.partyType = partyType;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyAlias() {
        return partyAlias;
    }

    public void setPartyAlias(String partyAlias) {
        this.partyAlias = partyAlias;
    }

    public StatusTpCdEnum getPartyStatus() {
        return partyStatus;
    }

    public void setPartyStatus(StatusTpCdEnum partyStatus) {
        this.partyStatus = partyStatus;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public long getChangeNumber() {
        return changeNumber;
    }

    public void setChangeNumber(long changeNumber) {
        this.changeNumber = changeNumber;
    }

    public long getChangeCircleTimes() {
        return changeCircleTimes;
    }

    public void setChangeCircleTimes(long changeCircleTimes) {
        this.changeCircleTimes = changeCircleTimes;
    }

    @Override
    public String toString() {
        return "PartyInfoDO{" +
                "partyID='" + partyID + '\'' +
                ", partyType=" + partyType +
                ", partyName='" + partyName + '\'' +
                ", partyAlias='" + partyAlias + '\'' +
                ", partyStatus=" + partyStatus +
                ", contact='" + HiddenUtil.acNameHidden(contact) + '\'' +
                ", telephone='" + HiddenUtil.telNoHidden(telephone) + '\'' +
                ", mail='" + HiddenUtil.emailHidden(mail) + '\'' +
                ", fax='" + fax + '\'' +
                ", status='" + status + '\'' +
                ", effectDate='" + effectDate + '\'' +
                ", inEffectiveDate='" + inEffectiveDate + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                ", changeNumber=" + changeNumber +
                ", changeCircleTimes=" + changeCircleTimes +
                '}';
    }
}
