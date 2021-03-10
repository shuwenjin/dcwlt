package com.dcits.dcwlt.job.util;

import java.util.Date;

import com.dcits.dcwlt.common.core.constant.ScheduleConstants;
import com.dcits.dcwlt.common.core.utils.SpringUtils;
import com.dcits.dcwlt.common.core.utils.bean.BeanUtils;
import com.dcits.dcwlt.job.domain.SysJob;
import com.dcits.dcwlt.job.domain.SysJobLog;
import com.dcits.dcwlt.job.service.ISysJobLogService;
import com.dcits.dcwlt.job.service.ISysJobService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dcits.dcwlt.common.core.utils.ExceptionUtil;
import com.dcits.dcwlt.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象quartz调用
 *
 * @author ruoyi
 */
public abstract class AbstractQuartzJob implements Job
{
    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    @Autowired
    ISysJobService jobService;

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        SysJob sysJob = new SysJob();
        BeanUtils.copyBeanProp(sysJob, context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES));
        try
        {
            before(context, sysJob);
            if (sysJob != null)
            {
                doExecute(context, sysJob);
            }
            after(context, sysJob, null);
        }
        catch (Exception e)
        {
            // 任务执行失败
            // 获取任务信息
            Long jobId = sysJob.getJobId();
            String jobGroup = sysJob.getJobGroup();
            JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);

            if(jobKey.getName().equals(context.getJobDetail().getKey().getName())) {
                log.error("任务执行异常  - ：" + e + ", jobId: " + jobKey.getName()
                        + ", curJobId: " + context.getJobDetail().getKey().getName());
            } else {
                log.error("重试任务执行异常  - ：" + e + ", jobId: " + jobKey.getName()
                        + ", curJobId: " + context.getJobDetail().getKey().getName());
            }

            // 如果当前任务是主任务，启动失败重启定时任务
            if(jobKey.getName().equals(context.getJobDetail().getKey().getName())) {
                try {
                    jobService.updateSchedulerRetryJob(sysJob, jobGroup);
                } catch (Exception e1) {
                    log.error("失败重试任务启动异常  - ：", e1);
                }
            } else {
                // 失败重启任务执行异常， 失败次数达到最大时删除当前重试任务
            }
            after(context, sysJob, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param sysJob 系统计划任务
     */
    protected void before(JobExecutionContext context, SysJob sysJob)
    {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param sysJob 系统计划任务
     */
    protected void after(JobExecutionContext context, SysJob sysJob, Exception e)
    {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setJobId(sysJob.getJobId());
        sysJobLog.setJobName(sysJob.getJobName());
        sysJobLog.setJobGroup(sysJob.getJobGroup());
        sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
        sysJobLog.setStartTime(startTime);
        sysJobLog.setStopTime(new Date());
        long runMs = sysJobLog.getStopTime().getTime() - sysJobLog.getStartTime().getTime();
        sysJobLog.setJobMessage(sysJobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null)
        {
            sysJobLog.setStatus("1");
            String errorMsg = StringUtils.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
            sysJobLog.setExceptionInfo(errorMsg);
        }
        else
        {
            sysJobLog.setStatus("0");
        }

        // 获取任务信息
        Long jobId = sysJob.getJobId();
        String jobGroup = sysJob.getJobGroup();
        JobKey jobKey = ScheduleUtils.getRetryJobKey(jobId, jobGroup);
        // 如果当前任务是主任务，设置jobId为失败重试任务jobId
        if(jobKey.getName().equals(context.getJobDetail().getKey().getName())) {
            sysJobLog.setJobId(sysJob.getJobId() + ScheduleConstants.RETRY_BASE_NUM);
        }

        // 写入数据库当中
        SpringUtils.getBean(ISysJobLogService.class).addJobLog(sysJobLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysJob 系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;
}
