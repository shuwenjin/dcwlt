package com.dcits.dcwlt.job.mapper;

import java.util.List;

import com.dcits.dcwlt.job.domain.SysJob;

/**
 * 调度任务信息 数据层
 * 
 * @author ruoyi
 */
public interface SysJobMapper
{
    /**
     * 查询调度任务集合
     * 
     * @param job 调度信息
     * @return 调度任务集合
     */
    public List<SysJob> selectJobList(SysJob job);

    /**
     * 查询失败重试调度任务集合
     * 
     * @param job 调度信息
     * @return 调度任务集合
     */
    public List<SysJob> selectRetryJobList(SysJob job);

    /**
     * 查询所有调度任务
     * 
     * @return 调度任务列表
     */
    public List<SysJob> selectJobAll();

    /**
     * 查询所有失败重试调度任务
     * 
     * @return 调度任务列表
     */
    public List<SysJob> selectRetryJobAll();

    /**
     * 通过调度ID查询调度任务信息
     * 
     * @param jobId 调度ID
     * @return 角色对象信息
     */
    public SysJob selectJobById(Long jobId);

    /**
     * 通过调度ID删除调度任务信息
     * 
     * @param jobId 调度ID
     * @return 结果
     */
    public int deleteJobById(Long jobId);

    /**
     * 批量删除调度任务信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteJobByIds(Long[] ids);

    /**
     * 修改调度任务信息
     * 
     * @param job 调度任务信息
     * @return 结果
     */
    public int updateJob(SysJob job);

    /**
     * 获取自增主键jobId的下一个自增值
     *
     * @return
     */
    public Long nextJobId();

    /**
     * 新增调度任务信息
     * 
     * @param job 调度任务信息
     * @return 结果
     */
    public int insertJob(SysJob job);
}
