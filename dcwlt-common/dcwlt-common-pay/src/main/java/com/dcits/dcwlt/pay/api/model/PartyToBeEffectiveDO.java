package com.dcits.dcwlt.pay.api.model;


import com.dcits.dcwlt.common.pay.util.HiddenUtil;

/**
 * 机构信息临时表实体类
 *
 *
 * @date 2020/12/30
 */
public class PartyToBeEffectiveDO extends PartyInfoDO{

    private String changeType;        //变更类型
    private String effectiveType;     //生效类型

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

    @Override
    public String toString() {
        return "PartyInfoTmpDO{" +
                "effectiveType=" + effectiveType +
                ", partyID='" + partyID + '\'' +
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
