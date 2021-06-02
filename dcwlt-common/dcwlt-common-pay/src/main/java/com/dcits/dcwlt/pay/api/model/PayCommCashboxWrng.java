package com.dcits.dcwlt.pay.api.model;

import com.dcits.dcwlt.common.core.annotation.Excel;
import com.dcits.dcwlt.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 钱柜余额告警对象 pay_comm_cashbox_wrng
 * 
 * @author dcwlt
 * @date 2021-06-01
 */
public class PayCommCashboxWrng extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 合作银行机构编码 */
    private String coopbankInstnid;

    /** 合作银行钱柜ID */
    private String coopbankWltid;

    /** 钱柜所属运营机构 */
    @Excel(name = "钱柜所属运营机构")
    private String cshboxInstnid;

    /** 钱柜余额预警值 */
    @Excel(name = "钱柜余额预警值")
    private String wrngVal;

    /** 当前钱柜余额 */
    @Excel(name = "当前钱柜余额")
    private String amtValue;

    /** 预警内容 */
    @Excel(name = "预警内容")
    private String msgCnt;

    /** 告警时间 */
    @Excel(name = "告警时间")
    private String wrngTm;

    public void setCoopbankInstnid(String coopbankInstnid) 
    {
        this.coopbankInstnid = coopbankInstnid;
    }

    public String getCoopbankInstnid() 
    {
        return coopbankInstnid;
    }
    public void setCoopbankWltid(String coopbankWltid) 
    {
        this.coopbankWltid = coopbankWltid;
    }

    public String getCoopbankWltid() 
    {
        return coopbankWltid;
    }
    public void setCshboxInstnid(String cshboxInstnid) 
    {
        this.cshboxInstnid = cshboxInstnid;
    }

    public String getCshboxInstnid() 
    {
        return cshboxInstnid;
    }
    public void setWrngVal(String wrngVal) 
    {
        this.wrngVal = wrngVal;
    }

    public String getWrngVal() 
    {
        return wrngVal;
    }
    public void setAmtValue(String amtValue) 
    {
        this.amtValue = amtValue;
    }

    public String getAmtValue() 
    {
        return amtValue;
    }
    public void setMsgCnt(String msgCnt) 
    {
        this.msgCnt = msgCnt;
    }

    public String getMsgCnt() 
    {
        return msgCnt;
    }
    public void setWrngTm(String wrngTm) 
    {
        this.wrngTm = wrngTm;
    }

    public String getWrngTm() 
    {
        return wrngTm;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("coopbankInstnid", getCoopbankInstnid())
            .append("coopbankWltid", getCoopbankWltid())
            .append("cshboxInstnid", getCshboxInstnid())
            .append("wrngVal", getWrngVal())
            .append("amtValue", getAmtValue())
            .append("msgCnt", getMsgCnt())
            .append("wrngTm", getWrngTm())
            .toString();
    }
}
