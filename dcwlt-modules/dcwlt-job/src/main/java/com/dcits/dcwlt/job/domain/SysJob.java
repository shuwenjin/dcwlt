package com.dcits.dcwlt.job.domain;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.dcits.dcwlt.common.core.constant.ScheduleConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.dcits.dcwlt.common.core.annotation.Excel;
import com.dcits.dcwlt.common.core.utils.StringUtils;
import com.dcits.dcwlt.common.core.web.domain.BaseEntity;
import com.dcits.dcwlt.job.util.CronUtils;
import org.springframework.data.annotation.Id;

/**
 * 定时任务调度表 sys_job
 * 
 * @author dcwlt
 */
public class SysJob extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    @Id
    @Excel(name = "任务编号")
    private String jobId;

    /** 父实例ID */
    @Excel(name = "父实例编号")
    private String fid;

    /** 父任务ID*/
    @Excel(name = "父任务编号")
    private String fjobId;

    /** 任务类型（0父任务 1子任务） */
    @Excel(name = "任务类型", readConverterExp = "0=父任务,1=子任务")
    private String jobType;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String jobName;

    /** 任务组名 */
    @Excel(name = "任务组名")
    private String jobGroup;

    /** 调用目标字符串 */
    @Excel(name = "调用目标字符串")
    private String invokeTarget;

    /** cron执行表达式 */
    @Excel(name = "执行表达式 ")
    private String cronExpression;

    /** cron计划策略 */
    @Excel(name = "计划策略 ", readConverterExp = "0=默认,1=立即触发执行,2=触发一次执行,3=不触发立即执行")
    private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

    /** 是否并发执行（0允许 1禁止） */
    @Excel(name = "并发执行", readConverterExp = "0=允许,1=禁止")
    private String concurrent;

    /** 任务状态（0正常 1暂停） */
    @Excel(name = "任务状态", readConverterExp = "0=正常,1=暂停")
    private String status;

    /** 失败重试cron */
    @Excel(name = "失败重试cron")
    private String retryCron;

    /** 失败重试状态（0正常 1暂停） */
    @Excel(name = "失败重试状态", readConverterExp = "0=正常,1=暂停")
    private String retryStatus;

    /** 主任务失败时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "主任务失败时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date failTime;

    /** 重试最大次数 */
    @Excel(name = "重试最大次数")
    private Integer retryMaxNum;

    /** 重试是否成功（0成功 1失败） */
    @Excel(name = "重试是否成功", readConverterExp = "0=成功,1=失败")
    private String retryJobStatus;

    /** 当前重试次数 */
    @Excel(name = "当前重试次数")
    private Integer retryNum;

    public String getJobId()
    {
        return jobId;
    }

    public void setJobId(String jobId)
    {
        this.jobId = jobId;
    }

    @NotBlank(message = "任务名称不能为空")
    @Size(min = 0, max = 64, message = "任务名称不能超过64个字符")
    public String getJobName()
    {
        return jobName;
    }

    public void setJobName(String jobName)
    {
        this.jobName = jobName;
    }

    public String getJobGroup()
    {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup)
    {
        this.jobGroup = jobGroup;
    }

    @NotBlank(message = "调用目标字符串不能为空")
    @Size(min = 0, max = 500, message = "调用目标字符串长度不能超过500个字符")
    public String getInvokeTarget()
    {
        return invokeTarget;
    }

    public void setInvokeTarget(String invokeTarget)
    {
        this.invokeTarget = invokeTarget;
    }

    @NotBlank(message = "Cron执行表达式不能为空")
    @Size(min = 0, max = 255, message = "Cron执行表达式不能超过255个字符")
    public String getCronExpression()
    {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression)
    {
        this.cronExpression = cronExpression;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getNextValidTime()
    {
        if (StringUtils.isNotEmpty(cronExpression))
        {
            return CronUtils.getNextExecution(cronExpression);
        }
        return null;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getNextRetryValidTime()
    {
        if (StringUtils.isNotEmpty(retryCron))
        {
            return CronUtils.getNextExecution(retryCron);
        }
        return null;
    }

    public String getMisfirePolicy()
    {
        return misfirePolicy;
    }

    public void setMisfirePolicy(String misfirePolicy)
    {
        this.misfirePolicy = misfirePolicy;
    }

    public String getConcurrent()
    {
        return concurrent;
    }

    public void setConcurrent(String concurrent)
    {
        this.concurrent = concurrent;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setRetryCron(String retryCron)
    {
        this.retryCron = retryCron;
    }

    public String getRetryCron()
    {
        return retryCron;
    }

    public void setRetryStatus(String retryStatus)
    {
        this.retryStatus = retryStatus;
    }

    public String getRetryStatus()
    {
        return retryStatus;
    }

    public Date getFailTime() {
        return failTime;
    }

    public void setFailTime(Date failTime) {
        this.failTime = failTime;
    }

    public void setRetryMaxNum(Integer retryMaxNum)
    {
        this.retryMaxNum = retryMaxNum;
    }

    public Integer getRetryMaxNum()
    {
        return retryMaxNum;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFjobId() {
        return fjobId;
    }

    public void setFjobId(String fjobId) {
        this.fjobId = fjobId;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getRetryJobStatus() {
        return retryJobStatus;
    }

    public void setRetryJobStatus(String retryJobStatus) {
        this.retryJobStatus = retryJobStatus;
    }

    public Integer getRetryNum() {
        return retryNum;
    }

    public void setRetryNum(Integer retryNum) {
        this.retryNum = retryNum;
    }

    @Override
    public String toString() {
        return "SysJob{" +
                "jobId=" + jobId +
                ", fid=" + fid +
                ", fjobId=" + fjobId +
                ", jobType='" + jobType + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", invokeTarget='" + invokeTarget + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", misfirePolicy='" + misfirePolicy + '\'' +
                ", concurrent='" + concurrent + '\'' +
                ", status='" + status + '\'' +
                ", retryCron='" + retryCron + '\'' +
                ", retryStatus='" + retryStatus + '\'' +
                ", retryMaxNum=" + retryMaxNum +
                ", retryJobStatus='" + retryJobStatus + '\'' +
                ", retryNum=" + retryNum +
                '}';
    }
}