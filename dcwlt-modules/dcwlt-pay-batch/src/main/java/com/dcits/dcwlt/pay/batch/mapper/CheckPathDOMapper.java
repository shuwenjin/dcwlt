package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.CheckPathDO;

import java.util.List;


/**
 * 对账汇总Mapper接口
 * 
 * @author 
 * @date 2021-03-09
 */
public interface CheckPathDOMapper 
{
    /**
     * 查询对账汇总
     * 
     * @param paydate 对账汇总ID
     * @return 对账汇总
     */
    public CheckPathDO selectCheckPathDOById(String paydate);

    /**
     * 查询对账汇总列表
     * 
     * @param checkPathDO 对账汇总
     * @return 对账汇总集合
     */
    public List<CheckPathDO> selectCheckPathDOList(CheckPathDO checkPathDO);

    /**
     * 新增对账汇总
     * 
     * @param checkPathDO 对账汇总
     * @return 结果
     */
    public int insertCheckPathDO(CheckPathDO checkPathDO);

    /**
     * 修改对账汇总
     * 
     * @param checkPathDO 对账汇总
     * @return 结果
     */
    public int updateCheckPathDO(CheckPathDO checkPathDO);

    /**
     * 删除对账汇总
     * 
     * @param paydate 对账汇总ID
     * @return 结果
     */
    public int deleteCheckPathDOById(String paydate);

    /**
     * 批量删除对账汇总
     * 
     * @param paydates 需要删除的数据ID
     * @return 结果
     */
    public int deleteCheckPathDOByIds(String[] paydates);
}
