package com.dcits.dcwlt.pay.api.model;

import com.dcits.dcwlt.common.core.annotation.Excel;
import com.dcits.dcwlt.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 金融交易统计报表对象 pay_batch_finance_report
 * 
 * @author
 * @date 2021-03-11
 */
public class FinanceReportDO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 兑出金额 */
    @Excel(name = "兑出金额")
    private String cashoutAmount;

    /** 兑出成功笔数 */
    @Excel(name = "兑出成功笔数")
    private String cashoutSuccessNumber;

    /** 兑出总笔数 */
    @Excel(name = "兑出总笔数")
    private String cashoutTotalNumber;

    /** 兑回金额 */
    @Excel(name = "兑回金额")
    private String cashinAmount;

    /** 兑回成功笔数 */
    @Excel(name = "兑回成功笔数")
    private String cashinSuccessNumber;

    /** 兑回总笔数 */
    @Excel(name = "兑回总笔数")
    private String cashinTotalNumber;

    /** 汇款兑出金额 */
    @Excel(name = "汇款兑出金额")
    private String remitoutAmount;

    /** 汇款兑出成功笔数 */
    @Excel(name = "汇款兑出成功笔数")
    private String remitoutSuccessNumber;

    /** 汇款兑出总笔数 */
    @Excel(name = "汇款兑出总笔数")
    private String remitoutTotalNumber;

    /** 报表日期 */
    @Excel(name = "报表日期")
    private String reportDate;

    public void setCashoutAmount(String cashoutAmount) 
    {
        this.cashoutAmount = cashoutAmount;
    }

    public String getCashoutAmount() 
    {
        return cashoutAmount;
    }
    public void setCashoutSuccessNumber(String cashoutSuccessNumber) 
    {
        this.cashoutSuccessNumber = cashoutSuccessNumber;
    }

    public String getCashoutSuccessNumber() 
    {
        return cashoutSuccessNumber;
    }
    public void setCashoutTotalNumber(String cashoutTotalNumber) 
    {
        this.cashoutTotalNumber = cashoutTotalNumber;
    }

    public String getCashoutTotalNumber() 
    {
        return cashoutTotalNumber;
    }
    public void setCashinAmount(String cashinAmount) 
    {
        this.cashinAmount = cashinAmount;
    }

    public String getCashinAmount() 
    {
        return cashinAmount;
    }
    public void setCashinSuccessNumber(String cashinSuccessNumber) 
    {
        this.cashinSuccessNumber = cashinSuccessNumber;
    }

    public String getCashinSuccessNumber() 
    {
        return cashinSuccessNumber;
    }
    public void setCashinTotalNumber(String cashinTotalNumber) 
    {
        this.cashinTotalNumber = cashinTotalNumber;
    }

    public String getCashinTotalNumber() 
    {
        return cashinTotalNumber;
    }
    public void setRemitoutAmount(String remitoutAmount) 
    {
        this.remitoutAmount = remitoutAmount;
    }

    public String getRemitoutAmount() 
    {
        return remitoutAmount;
    }
    public void setRemitoutSuccessNumber(String remitoutSuccessNumber) 
    {
        this.remitoutSuccessNumber = remitoutSuccessNumber;
    }

    public String getRemitoutSuccessNumber() 
    {
        return remitoutSuccessNumber;
    }
    public void setRemitoutTotalNumber(String remitoutTotalNumber) 
    {
        this.remitoutTotalNumber = remitoutTotalNumber;
    }

    public String getRemitoutTotalNumber() 
    {
        return remitoutTotalNumber;
    }
    public void setReportDate(String reportDate) 
    {
        this.reportDate = reportDate;
    }

    public String getReportDate() 
    {
        return reportDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("cashoutAmount", getCashoutAmount())
            .append("cashoutSuccessNumber", getCashoutSuccessNumber())
            .append("cashoutTotalNumber", getCashoutTotalNumber())
            .append("cashinAmount", getCashinAmount())
            .append("cashinSuccessNumber", getCashinSuccessNumber())
            .append("cashinTotalNumber", getCashinTotalNumber())
            .append("remitoutAmount", getRemitoutAmount())
            .append("remitoutSuccessNumber", getRemitoutSuccessNumber())
            .append("remitoutTotalNumber", getRemitoutTotalNumber())
            .append("reportDate", getReportDate())
            .toString();
    }
}
