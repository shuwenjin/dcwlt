package com.dcits.dcwlt.pay.api.domain.dcep.config;

import com.dcits.dcwlt.common.core.annotation.Excel;
import com.dcits.dcwlt.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 参数配置对象 pay_common_param
 * 
 * @author dcwlt
 * @date 2021-04-14
 */
public class PayCommonParam extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 参数类型 */
    @Excel(name = "参数类型")
    private String paramType;

    /** 参数id */
    @Excel(name = "参数id")
    private String paramKey;

    /** 参数数值 */
    @Excel(name = "参数数值")
    private String paramValue;

    /** 参数描述 */
    @Excel(name = "参数描述")
    private String paramDesc;

    /** 参数状态 */
    @Excel(name = "参数状态")
    private Integer paramStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setParamType(String paramType) 
    {
        this.paramType = paramType;
    }

    public String getParamType() 
    {
        return paramType;
    }
    public void setParamKey(String paramKey) 
    {
        this.paramKey = paramKey;
    }

    public String getParamKey() 
    {
        return paramKey;
    }
    public void setParamValue(String paramValue) 
    {
        this.paramValue = paramValue;
    }

    public String getParamValue() 
    {
        return paramValue;
    }
    public void setParamDesc(String paramDesc) 
    {
        this.paramDesc = paramDesc;
    }

    public String getParamDesc() 
    {
        return paramDesc;
    }
    public void setParamStatus(Integer paramStatus) 
    {
        this.paramStatus = paramStatus;
    }

    public Integer getParamStatus() 
    {
        return paramStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("paramType", getParamType())
            .append("paramKey", getParamKey())
            .append("paramValue", getParamValue())
            .append("paramDesc", getParamDesc())
            .append("paramStatus", getParamStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
