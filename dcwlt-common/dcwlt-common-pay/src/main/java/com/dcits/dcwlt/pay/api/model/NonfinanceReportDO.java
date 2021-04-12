package com.dcits.dcwlt.pay.api.model;

import com.dcits.dcwlt.common.core.annotation.Excel;
import com.dcits.dcwlt.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 非金融交易统计报表对象 pay_batch_nonfinance_report
 * 
 * @author
 * @date 2021-03-11
 */
public class NonfinanceReportDO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 绑卡数量 */
    @Excel(name = "绑卡数量")
    private String tiedCardNumber;

    /** 解绑数量 */
    @Excel(name = "解绑数量")
    private String untieNumber;

    /** 报表日期 */
    @Excel(name = "报表日期")
    private String reportDate;

    public void setTiedCardNumber(String tiedCardNumber) 
    {
        this.tiedCardNumber = tiedCardNumber;
    }

    public String getTiedCardNumber() 
    {
        return tiedCardNumber;
    }
    public void setUntieNumber(String untieNumber) 
    {
        this.untieNumber = untieNumber;
    }

    public String getUntieNumber() 
    {
        return untieNumber;
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
            .append("tiedCardNumber", getTiedCardNumber())
            .append("untieNumber", getUntieNumber())
            .append("reportDate", getReportDate())
            .toString();
    }
}
