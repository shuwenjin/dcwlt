package com.dcits.dcwlt.job.service;

import java.util.List;
import com.dcits.dcwlt.job.domain.SysJobLog;

/**
 * 定时任务调度日志信息信息 服务层
 * 
 * @author ruoyi
 */
public interface ISysJobLogService
{
    /**
     * 调度任务日志查询
     * 
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog);

    /**
     * 通过调度任务日志ID查询调度信息
     * 
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    public SysJobLog selectJobLogById(Long jobLogId);

    /**
     * 新增任务日志
     * 
     * @param jobLog 调度日志信息
     */
    public void addJobLog(SysJobLog jobLog);

    /**
     * 批量删除调度日志信息
     * 
     * @param logIds 需要删除的日志ID
     * @return 结果
     */
    public int deleteJobLogByIds(Long[] logIds);

    /**
     * 删除任务日志
     * 
     * @param jobId 调度日志ID
     * @return 结果
     */
    public int deleteJobLogById(String jobId);

    /**
     * 清空主任务日志
     */
    public void cleanJobLog();

    /**
     * 清空失败重试任务日志
     */
    public void cleanRetryJobLog();
}
