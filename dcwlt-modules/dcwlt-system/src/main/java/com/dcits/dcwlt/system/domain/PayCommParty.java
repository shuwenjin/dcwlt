package com.dcits.dcwlt.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.dcits.dcwlt.common.core.annotation.Excel;
import com.dcits.dcwlt.common.core.web.domain.BaseEntity;

/**
 * 机构对象 pay_comm_party
 * 
 * @author ruoyi
 * @date 2021-02-25
 */
public class PayCommParty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 机构编码 */
    private String partyid;

    /** 机构类型 */
    @Excel(name = "机构类型")
    private String partytype;

    /** 机构名称 */
    @Excel(name = "机构名称")
    private String partyname;

    /** 机构标识 */
    @Excel(name = "机构标识")
    private String partyalias;

    /** 机构状态 */
    @Excel(name = "机构状态")
    private String partystatus;

    /** 撤销状态 */
    @Excel(name = "撤销状态")
    private String status;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contact;

    /** 电话 */
    @Excel(name = "电话")
    private String telephone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String mail;

    /** 传真号 */
    @Excel(name = "传真号")
    private String fax;

    /** 失效日期 */
    @Excel(name = "失效日期")
    private String effectdate;

    /** 失效日期 */
    @Excel(name = "失效日期")
    private String ineffectivedate;

    /** 变更期数 */
    @Excel(name = "变更期数")
    private String changenumber;

    /** 变更记录条目 */
    @Excel(name = "变更记录条目")
    private String changecircletimes;

    /** 最后更新日期 */
    @Excel(name = "最后更新日期")
    private String lastupdate;

    /** 最后更新时间 */
    @Excel(name = "最后更新时间")
    private String lastuptime;

    public void setPartyid(String partyid) 
    {
        this.partyid = partyid;
    }

    public String getPartyid() 
    {
        return partyid;
    }
    public void setPartytype(String partytype) 
    {
        this.partytype = partytype;
    }

    public String getPartytype() 
    {
        return partytype;
    }
    public void setPartyname(String partyname) 
    {
        this.partyname = partyname;
    }

    public String getPartyname() 
    {
        return partyname;
    }
    public void setPartyalias(String partyalias) 
    {
        this.partyalias = partyalias;
    }

    public String getPartyalias() 
    {
        return partyalias;
    }
    public void setPartystatus(String partystatus) 
    {
        this.partystatus = partystatus;
    }

    public String getPartystatus() 
    {
        return partystatus;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setContact(String contact) 
    {
        this.contact = contact;
    }

    public String getContact() 
    {
        return contact;
    }
    public void setTelephone(String telephone) 
    {
        this.telephone = telephone;
    }

    public String getTelephone() 
    {
        return telephone;
    }
    public void setMail(String mail) 
    {
        this.mail = mail;
    }

    public String getMail() 
    {
        return mail;
    }
    public void setFax(String fax) 
    {
        this.fax = fax;
    }

    public String getFax() 
    {
        return fax;
    }
    public void setEffectdate(String effectdate) 
    {
        this.effectdate = effectdate;
    }

    public String getEffectdate() 
    {
        return effectdate;
    }
    public void setIneffectivedate(String ineffectivedate) 
    {
        this.ineffectivedate = ineffectivedate;
    }

    public String getIneffectivedate() 
    {
        return ineffectivedate;
    }
    public void setChangenumber(String changenumber) 
    {
        this.changenumber = changenumber;
    }

    public String getChangenumber() 
    {
        return changenumber;
    }
    public void setChangecircletimes(String changecircletimes) 
    {
        this.changecircletimes = changecircletimes;
    }

    public String getChangecircletimes() 
    {
        return changecircletimes;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("partyid", getPartyid())
            .append("partytype", getPartytype())
            .append("partyname", getPartyname())
            .append("partyalias", getPartyalias())
            .append("partystatus", getPartystatus())
            .append("status", getStatus())
            .append("contact", getContact())
            .append("telephone", getTelephone())
            .append("mail", getMail())
            .append("fax", getFax())
            .append("effectdate", getEffectdate())
            .append("ineffectivedate", getIneffectivedate())
            .append("changenumber", getChangenumber())
            .append("changecircletimes", getChangecircletimes())
            .append("lastupdate", getLastupdate())
            .append("lastuptime", getLastuptime())
            .toString();
    }
}
