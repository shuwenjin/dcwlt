package com.dcits.dcwlt.pay.api.model;

import com.dcits.dcwlt.common.core.annotation.Excel;
import com.dcits.dcwlt.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 钱柜出库入库异常处理对象 pay_comm_cashbox_process
 * 
 * @author dcwlt
 * @date 2021-05-11
 */
public class PayCommCashboxProcess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 入库出库类型 */
    @Excel(name = "入库出库类型")
    private String oprtp;

    /** 入库出库借贷标识 */
    @Excel(name = "入库出库借贷标识")
    private String cdtdbtind;

    /** 出入库金额货币符号 */
    @Excel(name = "出入库金额货币符号")
    private String amtccy;

    /** 出入库金额 */
    @Excel(name = "出入库金额")
    private String amtvalue;

    /** 合作银行机构编码 */
    @Excel(name = "合作银行机构编码")
    private String coopbankinstnid;

    /** 合作银行钱柜ID */
    @Excel(name = "合作银行钱柜ID")
    private String coopbankwltid;

    /** 钱柜所属运营机构 */
    @Excel(name = "钱柜所属运营机构")
    private String cshboxinstnid;

    /** 额度凭证 */
    @Excel(name = "额度凭证")
    private String cert;

    /** 报文标识号 */
    @Excel(name = "报文标识号")
    private String msgid;

    /** 业务请求时间 */
    @Excel(name = "业务请求时间")
    private String credttms;

    /** 业务响应时间 */
    @Excel(name = "业务响应时间")
    private String credttmr;

    /** 业务状态 */
    @Excel(name = "业务状态")
    private String prcsts;

    /** 业务回执状态 */
    @Excel(name = "业务回执状态")
    private String prccd;

    /** 业务回执状态 */
    @Excel(name = "业务回执状态")
    private String rspsnsts;

    /** 业务拒绝 */
    @Excel(name = "业务拒绝")
    private String rjctcd;

    /** 业务拒绝信息 */
    @Excel(name = "业务拒绝信息")
    private String rjctinf;

    /** 清算报文标识号 */
    @Excel(name = "清算报文标识号")
    private String clrreptflg;

    /** 清算金额货币符号 */
    @Excel(name = "清算金额货币符号")
    private String clearamountccy;

    /** 清算金额 */
    @Excel(name = "清算金额")
    private String clearamountvalue;

    /** 核心处理状态 */
    @Excel(name = "核心处理状态")
    private String corests;


    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOprtp(String oprtp) 
    {
        this.oprtp = oprtp;
    }

    public String getOprtp() 
    {
        return oprtp;
    }
    public void setCdtdbtind(String cdtdbtind) 
    {
        this.cdtdbtind = cdtdbtind;
    }

    public String getCdtdbtind() 
    {
        return cdtdbtind;
    }
    public void setAmtccy(String amtccy) 
    {
        this.amtccy = amtccy;
    }

    public String getAmtccy() 
    {
        return amtccy;
    }
    public void setAmtvalue(String amtvalue) 
    {
        this.amtvalue = amtvalue;
    }

    public String getAmtvalue() 
    {
        return amtvalue;
    }
    public void setCoopbankinstnid(String coopbankinstnid) 
    {
        this.coopbankinstnid = coopbankinstnid;
    }

    public String getCoopbankinstnid() 
    {
        return coopbankinstnid;
    }
    public void setCoopbankwltid(String coopbankwltid) 
    {
        this.coopbankwltid = coopbankwltid;
    }

    public String getCoopbankwltid() 
    {
        return coopbankwltid;
    }
    public void setCshboxinstnid(String cshboxinstnid) 
    {
        this.cshboxinstnid = cshboxinstnid;
    }

    public String getCshboxinstnid() 
    {
        return cshboxinstnid;
    }
    public void setCert(String cert) 
    {
        this.cert = cert;
    }

    public String getCert() 
    {
        return cert;
    }
    public void setMsgid(String msgid) 
    {
        this.msgid = msgid;
    }

    public String getMsgid() 
    {
        return msgid;
    }
    public void setCredttms(String credttms) 
    {
        this.credttms = credttms;
    }

    public String getCredttms() 
    {
        return credttms;
    }
    public void setCredttmr(String credttmr) 
    {
        this.credttmr = credttmr;
    }

    public String getCredttmr() 
    {
        return credttmr;
    }
    public void setPrcsts(String prcsts) 
    {
        this.prcsts = prcsts;
    }

    public String getPrcsts() 
    {
        return prcsts;
    }
    public void setPrccd(String prccd) 
    {
        this.prccd = prccd;
    }

    public String getPrccd() 
    {
        return prccd;
    }
    public void setRspsnsts(String rspsnsts) 
    {
        this.rspsnsts = rspsnsts;
    }

    public String getRspsnsts() 
    {
        return rspsnsts;
    }
    public void setRjctcd(String rjctcd) 
    {
        this.rjctcd = rjctcd;
    }

    public String getRjctcd() 
    {
        return rjctcd;
    }
    public void setRjctinf(String rjctinf) 
    {
        this.rjctinf = rjctinf;
    }

    public String getRjctinf() 
    {
        return rjctinf;
    }
    public void setClrreptflg(String clrreptflg) 
    {
        this.clrreptflg = clrreptflg;
    }

    public String getClrreptflg() 
    {
        return clrreptflg;
    }
    public void setClearamountccy(String clearamountccy) 
    {
        this.clearamountccy = clearamountccy;
    }

    public String getClearamountccy() 
    {
        return clearamountccy;
    }
    public void setClearamountvalue(String clearamountvalue) 
    {
        this.clearamountvalue = clearamountvalue;
    }

    public String getClearamountvalue() 
    {
        return clearamountvalue;
    }
    public void setCorests(String corests) 
    {
        this.corests = corests;
    }

    public String getCorests() 
    {
        return corests;
    }

}
