package com.dcits.dcwlt.job.service;

import java.util.List;

import com.dcits.dcwlt.common.core.exception.job.TaskException;
import org.quartz.SchedulerException;
import com.dcits.dcwlt.job.domain.SysJob;

/**
 * 定时任务调度信息信息 服务层
 * 
 * @author ruoyi
 */
public interface ISysJobService
{
    /**
     * 获取quartz调度器的计划任务
     * 
     * @param job 调度信息
     * @return 调度任务集合
     */
    public List<SysJob> selectJobList(SysJob job);

    /**
     * 通过调度任务ID查询调度信息
     * 
     * @param jobId 调度任务ID
     * @return 调度任务对象信息
     */
    public SysJob selectJobById(String jobId);

    /**
     * 暂停任务
     * 
     * @param job 调度信息
     * @return 结果
     */
    public int pauseJob(SysJob job) throws SchedulerException;

    /**
     * 暂停失败重试任务
     *
     * @param job 调度信息
     * @return 结果
     */
    public int pauseRetryJob(SysJob job) throws SchedulerException;

    /**
     * 恢复任务
     * 
     * @param job 调度信息
     * @return 结果
     */
    public int resumeJob(SysJob job) throws SchedulerException;

    /**
     * 恢复失败重试任务
     *
     * @param job 调度信息
     * @return 结果
     */
    public int resumeRetryJob(SysJob job) throws SchedulerException;

    /**
     * 删除任务后，所对应的trigger也将被删除
     * 
     * @param job 调度信息
     * @return 结果
     */
    public int deleteJob(SysJob job) throws SchedulerException;

    /**
     * 删除quartz计划任务
     * @param job
     * @throws SchedulerException
     */
    public void deleteSchedulerJob(SysJob job) throws SchedulerException;

    /**
     * 批量删除调度信息
     * 
     * @param jobIds 需要删除的任务ID
     * @return 结果
     */
    public void deleteJobByIds(String[] jobIds) throws SchedulerException;

    /**
     * 任务调度状态修改
     * 
     * @param job 调度信息
     * @return 结果
     */
    public int changeStatus(SysJob job) throws SchedulerException;


    /**
     * 任务失败重试调度状态修改
     *
     * @param job 调度信息
     * @return 结果
     */
    public int changeRetryStatus(SysJob job) throws SchedulerException, TaskException;

    /**
     * 立即运行任务
     * 
     * @param job 调度信息
     * @return 结果
     */
    public void run(SysJob job) throws SchedulerException;

    /**
     * 新增任务
     * 
     * @param job 调度信息
     * @return 结果
     */
    public int insertJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * 更新任务
     * 
     * @param job 调度信息
     * @return 结果
     */
    public int updateJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * 校验cron表达式是否有效
     * 
     * @param cronExpression 表达式
     * @return 结果
     */
    public boolean checkCronExpressionIsValid(String cronExpression);

    /**
     * 更新失败重试任务
     *
     * @param job 任务对象
     */
    public void createSchedulerRetryJob(SysJob job) throws SchedulerException, TaskException;
}