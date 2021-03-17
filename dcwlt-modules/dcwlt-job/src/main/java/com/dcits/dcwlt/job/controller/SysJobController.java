package com.dcits.dcwlt.job.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.constant.SysJobConstants;
import com.dcits.dcwlt.common.core.exception.job.TaskException;
import com.dcits.dcwlt.common.core.utils.ExceptionUtil;
import com.dcits.dcwlt.job.task.TaskResult;
import com.dcits.dcwlt.job.util.JobInvokeUtil;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dcits.dcwlt.common.core.utils.SecurityUtils;
import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.job.domain.SysJob;
import com.dcits.dcwlt.job.service.ISysJobService;
import com.dcits.dcwlt.job.util.CronUtils;

/**
 * 调度任务信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/job")
public class SysJobController extends BaseController
{
    @Autowired
    private ISysJobService jobService;

    /**
     * 查询定时任务列表
     */
    @PreAuthorize(hasPermi = "monitor:job:list")
    @GetMapping("/list")
    public TableDataInfo list(SysJob sysJob)
    {
        startPage();
        sysJob.setJobType(SysJobConstants.MAINJOB);
        List<SysJob> list = jobService.selectJobList(sysJob);
        return getDataTable(list);
    }

    /**
     * 查询重试定时任务列表
     */
    @PreAuthorize(hasPermi = "monitor:job:list")
    @GetMapping("/retryList")
    public TableDataInfo retryList(SysJob sysJob)
    {
        startPage();
        sysJob.setJobType(SysJobConstants.RETRYJOB);
        List<SysJob> list = jobService.selectJobList(sysJob);
        return getDataTable(list);
    }

    /**
     * 导出定时任务列表
     */
    @PreAuthorize(hasPermi = "monitor:job:export")
    @Log(title = "定时任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysJob sysJob) throws IOException
    {
        sysJob.setJobType(SysJobConstants.MAINJOB);
        List<SysJob> list = jobService.selectJobList(sysJob);
        ExcelUtil<SysJob> util = new ExcelUtil<SysJob>(SysJob.class);
        util.exportExcel(response, list, "定时任务");
    }

    /**
     * 导出失败重试定时任务列表
     */
    @PreAuthorize(hasPermi = "monitor:job:export")
    @Log(title = "定时任务", businessType = BusinessType.EXPORT)
    @PostMapping("/retryExport")
    public void retryExport(HttpServletResponse response, SysJob sysJob) throws IOException
    {
        sysJob.setJobType(SysJobConstants.RETRYJOB);
        List<SysJob> list = jobService.selectJobList(sysJob);
        ExcelUtil<SysJob> util = new ExcelUtil<SysJob>(SysJob.class);
        util.exportExcel(response, list, "定时任务");
    }

    /**
     * 获取定时任务详细信息
     */
    @PreAuthorize(hasPermi = "monitor:job:query")
    @GetMapping(value = "/{jobId}")
    public AjaxResult getInfo(@PathVariable("jobId") String jobId)
    {
        return AjaxResult.success(jobService.selectJobById(jobId));
    }

    /**
     * 新增定时任务
     */
    @PreAuthorize(hasPermi = "monitor:job:add")
    @Log(title = "定时任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysJob sysJob) throws SchedulerException, TaskException
    {
        if (!CronUtils.isValid(sysJob.getCronExpression()))
        {
            return AjaxResult.error("cron表达式不正确");
        }
        if (!CronUtils.isValid(sysJob.getRetryCron()))
        {
            return AjaxResult.error("失败重试cron表达式不正确");
        }
        sysJob.setCreateBy(SecurityUtils.getUsername());
        // 设置任务类型为主任务
        sysJob.setJobType(SysJobConstants.MAINJOB);
        return toAjax(jobService.insertJob(sysJob));
    }

    /**
     * 修改定时任务
     */
    @PreAuthorize(hasPermi = "monitor:job:edit")
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysJob sysJob) throws SchedulerException, TaskException
    {
        if (!CronUtils.isValid(sysJob.getCronExpression()))
        {
            return AjaxResult.error("cron表达式不正确");
        }
        if (!CronUtils.isValid(sysJob.getRetryCron()))
        {
            return AjaxResult.error("失败重试cron表达式不正确");
        }
        sysJob.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(jobService.updateJob(sysJob));
    }

    /**
     * 定时任务状态修改
     */
    @PreAuthorize(hasPermi = "monitor:job:changeStatus")
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysJob job) throws SchedulerException
    {
        SysJob newJob = jobService.selectJobById(job.getJobId());
        newJob.setStatus(job.getStatus());
        return toAjax(jobService.changeStatus(newJob));
    }

    /**
     * 定时任务状态修改
     */
    @PreAuthorize(hasPermi = "monitor:job:changeStatus")
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping("/changeRetryStatus")
    public AjaxResult changeRetryStatus(@RequestBody SysJob job) throws SchedulerException, TaskException
    {
        SysJob newJob = jobService.selectJobById(job.getJobId());
        newJob.setRetryStatus(job.getRetryStatus());
        return toAjax(jobService.changeRetryStatus(newJob));
    }

    /**
     * 定时任务立即执行一次
     */
    @PreAuthorize(hasPermi = "monitor:job:changeStatus")
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping("/run")
    public AjaxResult run(@RequestBody SysJob job) throws SchedulerException
    {
        jobService.run(job);
        return AjaxResult.success();
    }

    /**
     * 手动执行方法
     */
    @PreAuthorize(hasPermi = "monitor:job:changeStatus")
    @Log(title = "手动执行方法", businessType = BusinessType.OTHER)
    @GetMapping("/manualRun/{invokeTarget}")
    public TaskResult manualRun(@PathVariable("invokeTarget") String invokeTarget) throws Exception
    {
        try {
            return (TaskResult) JobInvokeUtil.invokeMethod(invokeTarget);
        } catch (InvocationTargetException e) {
            TaskResult taskResult = new TaskResult();
            String str = e.getTargetException().getMessage();
            if (null != str) {
                taskResult = JSONObject.toJavaObject(JSONObject.parseObject(str), TaskResult.class);
                return taskResult;
            }
            taskResult.setSuccess(false);
            taskResult.setMessage(ExceptionUtil.getExceptionMessage(e));
            return taskResult;
        }
    }

    /**
     * 删除定时任务
     */
    @PreAuthorize(hasPermi = "monitor:job:remove")
    @Log(title = "定时任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jobIds}")
    public AjaxResult remove(@PathVariable String[] jobIds) throws SchedulerException, TaskException
    {
        jobService.deleteJobByIds(jobIds);
        return AjaxResult.success();
    }
}
