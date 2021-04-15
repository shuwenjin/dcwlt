package com.dcits.dcwlt.pay.batch.mapper;

import java.util.List;

import com.dcits.dcwlt.pay.api.domain.dcep.config.PayCommonParam;

/**
 * 参数配置Mapper接口
 * 
 * @author dcwlt
 * @date 2021-04-14
 */
public interface PayCommonParamMapper 
{
    /**
     * 查询参数配置
     * 
     * @param id 参数配置ID
     * @return 参数配置
     */
    public PayCommonParam selectPayCommonParamById(Long id);

    /**
     * 查询参数配置列表
     * 
     * @param payCommonParam 参数配置
     * @return 参数配置集合
     */
    public List<PayCommonParam> selectPayCommonParamList(PayCommonParam payCommonParam);

    /**
     * 新增参数配置
     * 
     * @param payCommonParam 参数配置
     * @return 结果
     */
    public int insertPayCommonParam(PayCommonParam payCommonParam);

    /**
     * 修改参数配置
     * 
     * @param payCommonParam 参数配置
     * @return 结果
     */
    public int updatePayCommonParam(PayCommonParam payCommonParam);

    /**
     * 删除参数配置
     * 
     * @param id 参数配置ID
     * @return 结果
     */
    public int deletePayCommonParamById(Long id);

    /**
     * 批量删除参数配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePayCommonParamByIds(Long[] ids);
}
