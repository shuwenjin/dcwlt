package com.dcits.dcwlt.job.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.dcits.dcwlt.common.core.annotation.Excel;
import com.dcits.dcwlt.common.core.web.domain.BaseEntity;
import org.springframework.data.annotation.Id;

/**
 * 定时任务调度日志表 sys_job_log
 * 
 * @author ruoyi
 */
public class SysJobLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Id
    @Excel(name = "日志编号")
    private String jobLogId;

    /** 任务ID */
    @Excel(name = "任务编号")
    private String jobId;

    /** 父实例ID */
    @Excel(name = "父实例编号")
    private String fid;

    /** 父任务ID*/
    @Excel(name = "父任务编号")
    private String fjobId;

    /** 主任务失败时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "主任务失败时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date failTime;

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

    /** 日志信息 */
    @Excel(name = "日志信息")
    private String jobMessage;

    /** 执行状态（0正常 1失败） */
    @Excel(name = "执行状态", readConverterExp = "0=正常,1=失败")
    private String status;

    /** 异常信息 */
    @Excel(name = "异常信息")
    private String exceptionInfo;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 停止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "停止时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date stopTime;

    /** 执行返回值 */
    @Excel(name = "执行返回值")
    private String excuteRet;

    public String getJobLogId()
    {
        return jobLogId;
    }

    public void setJobLogId(String jobLogId)
    {
        this.jobLogId = jobLogId;
    }

    public String getJobId()
    {
        return jobId;
    }

    public void setJobId(String jobId)
    {
        this.jobId = jobId;
    }

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

    public String getInvokeTarget()
    {
        return invokeTarget;
    }

    public void setInvokeTarget(String invokeTarget)
    {
        this.invokeTarget = invokeTarget;
    }

    public String getJobMessage()
    {
        return jobMessage;
    }

    public void setJobMessage(String jobMessage)
    {
        this.jobMessage = jobMessage;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getExceptionInfo()
    {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo)
    {
        this.exceptionInfo = exceptionInfo;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    public Date getStopTime()
    {
        return stopTime;
    }

    public void setStopTime(Date stopTime)
    {
        this.stopTime = stopTime;
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

    public Date getFailTime() {
        return failTime;
    }

    public void setFailTime(Date failTime) {
        this.failTime = failTime;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getExcuteRet() {
        return excuteRet;
    }

    public void setExcuteRet(String excuteRet) {
        this.excuteRet = excuteRet;
    }

    @Override
    public String toString() {
        return "SysJobLog{" +
                "jobLogId=" + jobLogId +
                ", jobId=" + jobId +
                ", fid=" + fid +
                ", fjobId=" + fjobId +
                ", jobType='" + jobType + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", invokeTarget='" + invokeTarget + '\'' +
                ", jobMessage='" + jobMessage + '\'' +
                ", status='" + status + '\'' +
                ", exceptionInfo='" + exceptionInfo + '\'' +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                '}';
    }
}
