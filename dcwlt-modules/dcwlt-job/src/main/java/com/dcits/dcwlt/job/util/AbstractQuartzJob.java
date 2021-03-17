package com.dcits.dcwlt.job.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.constant.ScheduleConstants;
import com.dcits.dcwlt.common.core.constant.SysJobConstants;
import com.dcits.dcwlt.common.core.utils.bean.BeanUtils;
import com.dcits.dcwlt.job.domain.SysJob;
import com.dcits.dcwlt.job.domain.SysJobLog;
import com.dcits.dcwlt.job.service.ISysJobLogService;
import com.dcits.dcwlt.job.service.ISysJobService;
import com.dcits.dcwlt.job.task.TaskResult;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
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

    @Autowired
    ISysJobLogService jobLogService;

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
            boolean isSuccess = false;
            TaskResult taskResult = null;
            before(context, sysJob);
            if (sysJob != null)
            {
                try {
                    taskResult = (TaskResult) doExecute(context, sysJob);
                    log.info("执行结果" + taskResult.toString());
                } catch (ClassCastException castException) {
                    log.error("TaskResult 强转异常", castException);
                }
                if (null == taskResult) {
                    throw new JobExecutionException("TaskResult对象为null, 请检查执行方法");
                } else {
                    isSuccess = taskResult.isSuccess();
                    if (isSuccess) {
                        if (SysJobConstants.RETRYJOB.equals(sysJob.getJobType())) {
                            handleSysRetryJobSuccess(sysJob);
                        }
                    }
                }
            }
            after(context, sysJob, isSuccess, taskResult, null);
        }
        catch (Exception e)
        {
            after(context, sysJob, false, null, e);
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
    protected void after(JobExecutionContext context, SysJob sysJob, boolean isSuccess, TaskResult taskResult, Exception e)
    {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final SysJobLog sysJobLog = new SysJobLog();
        BeanUtils.copyBeanProp(sysJobLog, sysJob);
        String jobLogId = UUID.randomUUID().toString();
        sysJobLog.setJobLogId(jobLogId);
        sysJobLog.setStartTime(startTime);
        sysJobLog.setStopTime(new Date());

        long runMs = sysJobLog.getStopTime().getTime() - sysJobLog.getStartTime().getTime();
        sysJobLog.setJobMessage(sysJobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null || isSuccess == false) {
            sysJobLog.setStatus(SysJobConstants.FAIL);
            String errorMsg = StringUtils.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
            sysJobLog.setExceptionInfo(errorMsg);

            // 任务执行失败
            if (SysJobConstants.MAINJOB.equals(sysJob.getJobType())) {
                // 主任务执行失败时间为当前任务开始时间
                sysJobLog.setFailTime(startTime);
                log.error("任务执行异常  - ：" + e + ", jobId: " + sysJob.getJobId());
            } else {
                // 失败重试任务的失败时间为主任务实例的失败时间
                sysJobLog.setFailTime(sysJob.getFailTime());
                log.error("重试任务执行异常  - ：" + e + ", jobId: " + sysJob.getJobId());
            }

            handleSysJobException(sysJob, jobLogId, startTime, e);
        } else {
            log.info("任务执行成功  -  jobId: " + sysJob.getJobId() + ", taskResult: " + taskResult.toString());
            sysJobLog.setStatus(SysJobConstants.SUCCESS);
            if (null != taskResult) {
                if (null != taskResult.getRet()) {
                    sysJobLog.setExcuteRet(taskResult.getRet().toString());
                }
            }
        }

        // 写入数据库当中
        jobLogService.addJobLog(sysJobLog);
    }

    /**
     * 任务执行失败处理
     * @param sysJob 系统计划任务
     * @param jobLogId  主任务日志Id, 作为重试任务的fid
     */
    protected void handleSysJobException(SysJob sysJob, String jobLogId, Date startTime, Exception e) {
        if (null == sysJob) {
            return;
        }

        // 如果当前任务是主任务，启动失败重启定时任务
        if(SysJobConstants.MAINJOB.equals(sysJob.getJobType())) {
            try {
                SysJob sysRetryJob = new SysJob();
                BeanUtils.copyBeanProp(sysRetryJob, sysJob);
                // 生成新的jobId
                String retryJobId = UUID.randomUUID().toString();
                sysRetryJob.setJobId(retryJobId);
                // 设置父任务Id
                sysRetryJob.setFjobId(sysJob.getJobId());
                // 设置父任务实例Id
                sysRetryJob.setFid(jobLogId);
                // 父任务实例执行时间
                sysRetryJob.setFailTime(startTime);
                // 设置任务类型为1重试任务
                sysRetryJob.setJobType(SysJobConstants.RETRYJOB);
                // 初始化重试重试状态为1失败
                sysRetryJob.setRetryJobStatus(SysJobConstants.FAIL);
                // 初始化重试次数为0
                sysRetryJob.setRetryNum(0);
                // 重新生成调用参数
                // 从InvocationTargetException的message中反序列化TaskResult对象
                if (e instanceof InvocationTargetException) {
                    String str = ((InvocationTargetException) e).getTargetException().getMessage();
                    if (null != str) {
                        TaskResult taskResult = JSONObject.toJavaObject(JSONObject.parseObject(str), TaskResult.class);
                        if (null != taskResult) {
                            log.debug("TaskResult.getInvokeTarget: " + taskResult.getInvokeTarget());
                            sysRetryJob.setInvokeTarget(taskResult.getInvokeTarget());
                        }
                    }
                }

                // 写入数据库当中
                jobService.insertJob(sysRetryJob);
                if (ScheduleConstants.Status.NORMAL.getValue().equals(sysJob.getRetryStatus())) {
                    // 创建失败重试计划任务
                    jobService.createSchedulerRetryJob(sysRetryJob);
                }
            } catch (Exception e1) {
                log.error("失败重试任务启动异常  - ：", e1);
            }
        } else {
            Integer retryNum = 0;
            // 失败重启任务执行异常， 失败次数达到最大时暂停当前重试任务
            SysJob sysJobDb = jobService.selectJobById(sysJob.getJobId());
            if (null != sysJobDb) {
                retryNum = sysJobDb.getRetryNum();
                if (null == retryNum) {
                    retryNum = 0;
                }
            }

            ++retryNum;
            sysJob.setRetryNum(retryNum);
            Integer retryMaxNum = sysJob.getRetryMaxNum();
            if (null == retryMaxNum) {
                retryMaxNum = 0;
                log.error("任务失败重试次数未设置  - retryJobId：" + sysJob.getJobId());
            }
            if (retryNum >= retryMaxNum) {
                try {
                    // 修改重试状态为暂停
                    sysJob.setRetryStatus(ScheduleConstants.Status.PAUSE.getValue());
                    // 重试次数达到最大时暂停重试定时任务
                    jobService.pauseRetryJob(sysJob);
                } catch (Exception deleteException) {
                    log.error("暂停重试任务异常： ", deleteException);
                }
            } else {
                try {
                    jobService.updateJob(sysJob);
                } catch (Exception updateException) {
                    log.error("更新重试任务异常： ", updateException);
                }
            }
        }
    }

    /**
     * 失败重试任务执行成功处理
     * @param sysRetryJob
     */
    protected void handleSysRetryJobSuccess(SysJob sysRetryJob) {
        if (null == sysRetryJob) {
            return;
        }
        // 如果当前任务是重试任务，暂停重试任务，修改重试任务重试成功状态为成功
        if(SysJobConstants.RETRYJOB.equals(sysRetryJob.getJobType())) {
            try {
                Integer retryNum = 0;
                SysJob sysRetryJobDb = jobService.selectJobById(sysRetryJob.getJobId());
                if (null != sysRetryJobDb) {
                    retryNum = sysRetryJobDb.getRetryNum();
                    if (null == retryNum) {
                        retryNum = 0;
                    }
                }
                ++retryNum;
                sysRetryJob.setRetryNum(retryNum);
                // 修改重试重试状态为成功
                sysRetryJob.setRetryJobStatus(SysJobConstants.SUCCESS);
                // 修改重试状态为暂停
                sysRetryJob.setRetryStatus(ScheduleConstants.Status.PAUSE.getValue());
                // 暂停重试任务
                jobService.pauseRetryJob(sysRetryJob);
            } catch (Exception deleteJobException) {
                log.error("暂停失败重试任务异常  - ：", deleteJobException);
            }
        }
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysJob 系统计划任务
     * @return Object 执行返回值
     * @throws Exception 执行过程中的异常
     */
    protected abstract Object doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;
}
