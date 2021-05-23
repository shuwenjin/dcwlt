package com.dcits.dcwlt.pay.api.model;

import com.dcits.dcwlt.common.core.annotation.Excel;

public class PayCashboxPartyDO {
    @Excel(name = "id")
    protected String id;
    @Excel(name = "机构名称")
    protected String partyname;
    @Excel(name = "机构编码")
    protected String partyid;
    @Excel(name = "钱柜钱包id")
    protected String cashboxid;
    @Excel(name = "最后更新日期")
    protected String lastUpDate;            //最后更新日期
    @Excel(name = "最后更新时间")
    protected String lastUpTime;            //最后更新时间
    @Excel(name = "预警金额")
    protected String earlywarningamount;
    @Excel(name = "自动入库金额")
    protected String automaticstorage;
    @Excel(name = "是否自动入库")
    protected String automaticstuts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartyname() {
        return partyname;
    }

    public void setPartyname(String partyname) {
        this.partyname = partyname;
    }

    public String getPartyid() {
        return partyid;
    }

    public void setPartyid(String partyid) {
        this.partyid = partyid;
    }

    public String getCashboxid() {
        return cashboxid;
    }

    public void setCashboxid(String cashboxid) {
        this.cashboxid = cashboxid;
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

    public String getEarlywarningamount() {
        return earlywarningamount;
    }

    public void setEarlywarningamount(String earlywarningamount) {
        this.earlywarningamount = earlywarningamount;
    }

    public String getAutomaticstorage() {
        return automaticstorage;
    }

    public void setAutomaticstorage(String automaticstorage) {
        this.automaticstorage = automaticstorage;
    }

    public String getAutomaticstuts() {
        return automaticstuts;
    }

    public void setAutomaticstuts(String automaticstuts) {
        this.automaticstuts = automaticstuts;
    }

    @Override
    public String toString() {
        return "PayCashboxPartyDO{" +
                "id='" + id + '\'' +
                ", partyname=" + partyname +
                ", partyid='" + partyid + '\'' +
                ", cashboxid='" + cashboxid + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                ", earlywarningamount='" + earlywarningamount + '\'' +
                ", automaticstorage='" + automaticstorage + '\'' +
                ", automaticstuts='" + automaticstuts + '\'' +
                '}';
    }
}
