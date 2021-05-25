package com.dcits.dcwlt.pay.api.model;

import com.dcits.dcwlt.common.core.annotation.Excel;
import com.dcits.dcwlt.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 运营机构钱柜参数对象 pay_comm_cashbox_party
 * 
 * @author dcwlt
 * @date 2021-05-13
 */
public class PayCommCashboxParty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 机构名称 */
    @Excel(name = "机构名称")
    private String partyname;

    /** 机构编码 */
    @Excel(name = "机构编码")
    private String partyid;

    /** 钱柜钱包id */
    @Excel(name = "钱柜钱包id")
    private String cashboxid;

    /** 最后更新日期 */
    @Excel(name = "最后更新日期")
    private String lastupdate;

    /** 最后更新时间 */
    @Excel(name = "最后更新时间")
    private String lastuptime;

    /** 创建人 */
    @Excel(name = "创建人")
    private String cretername;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cretertime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updatename;

    /** 预警金额 */
    @Excel(name = "预警金额")
    private String earlywarningamount;

    /** 自动入库金额 */
    @Excel(name = "自动入库金额")
    private String automaticstorage;

    /** 是否自动入库，0为否，1为是 */
    @Excel(name = "是否自动入库，0为否，1为是")
    private String automaticstuts;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPartyname(String partyname) 
    {
        this.partyname = partyname;
    }

    public String getPartyname() 
    {
        return partyname;
    }
    public void setPartyid(String partyid) 
    {
        this.partyid = partyid;
    }

    public String getPartyid() 
    {
        return partyid;
    }
    public void setCashboxid(String cashboxid) 
    {
        this.cashboxid = cashboxid;
    }

    public String getCashboxid() 
    {
        return cashboxid;
    }
    public void setLastupdate(String lastupdate) 
    {
        this.lastupdate = lastupdate;
    }

    public String getLastupdate() 
    {
        return lastupdate;
    }
    public void setLastuptime(String lastuptime) 
    {
        this.lastuptime = lastuptime;
    }

    public String getLastuptime() 
    {
        return lastuptime;
    }
    public void setCretername(String cretername) 
    {
        this.cretername = cretername;
    }

    public String getCretername() 
    {
        return cretername;
    }
    public void setCretertime(Date cretertime) 
    {
        this.cretertime = cretertime;
    }

    public Date getCretertime() 
    {
        return cretertime;
    }
    public void setUpdatename(String updatename) 
    {
        this.updatename = updatename;
    }

    public String getUpdatename() 
    {
        return updatename;
    }
    public void setEarlywarningamount(String earlywarningamount) 
    {
        this.earlywarningamount = earlywarningamount;
    }

    public String getEarlywarningamount() 
    {
        return earlywarningamount;
    }
    public void setAutomaticstorage(String automaticstorage) 
    {
        this.automaticstorage = automaticstorage;
    }

    public String getAutomaticstorage() 
    {
        return automaticstorage;
    }
    public void setAutomaticstuts(String automaticstuts) 
    {
        this.automaticstuts = automaticstuts;
    }

    public String getAutomaticstuts() 
    {
        return automaticstuts;
    }

}
