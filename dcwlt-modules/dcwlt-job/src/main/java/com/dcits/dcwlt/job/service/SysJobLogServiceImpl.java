package com.dcits.dcwlt.job.service;

import java.util.List;
import java.util.UUID;

import com.dcits.dcwlt.common.pay.sequence.service.IGenerateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dcits.dcwlt.job.domain.SysJobLog;
import com.dcits.dcwlt.job.mapper.SysJobLogMapper;

/**
 * 定时任务调度日志信息 服务层
 * 
 * @author ruoyi
 */
@Service
public class SysJobLogServiceImpl implements ISysJobLogService
{
    @Autowired
    private SysJobLogMapper jobLogMapper;

    @Autowired
    private IGenerateCodeService generateCodeService;

    /**
     * 调度任务日志查询
     * 
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    @Override
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog)
    {
        return jobLogMapper.selectJobLogList(jobLog);
    }

    /**
     * 通过调度任务日志ID查询调度信息
     * 
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    @Override
    public SysJobLog selectJobLogById(Long jobLogId)
    {
        return jobLogMapper.selectJobLogById(jobLogId);
    }

    /**
     * 新增任务日志
     * 
     * @param jobLog 调度日志信息
     */
    @Override
    public void addJobLog(SysJobLog jobLog)
    {
        if (null == jobLog.getJobLogId() || "".equals(jobLog.getJobLogId())) {
            //jobLog.setJobLogId(UUID.randomUUID().toString());
            jobLog.setJobLogId(generateCodeService.generateCoreReqSerno());
        }
        jobLogMapper.insertJobLog(jobLog);
    }

    /**
     * 批量删除调度日志信息
     * 
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteJobLogByIds(Long[] logIds)
    {
        return jobLogMapper.deleteJobLogByIds(logIds);
    }

    /**
     * 删除任务日志
     * 
     * @param jobId 调度日志ID
     */
    @Override
    public int deleteJobLogById(String jobId)
    {
        return jobLogMapper.deleteJobLogById(jobId);
    }

    /**
     * 清空主任务日志
     */
    @Override
    public void cleanJobLog()
    {
        jobLogMapper.cleanJobLog();
    }

    /**
     * 清空失败重试任务日志
     */
    @Override
    public void cleanRetryJobLog()
    {
        jobLogMapper.cleanRetryJobLog();
    }
}
