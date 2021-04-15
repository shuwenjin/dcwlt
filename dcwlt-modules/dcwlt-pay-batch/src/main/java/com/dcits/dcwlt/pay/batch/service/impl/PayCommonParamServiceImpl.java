package com.dcits.dcwlt.pay.batch.service.impl;

import java.util.List;
import com.dcits.dcwlt.common.core.utils.DateUtils;
import com.dcits.dcwlt.pay.api.domain.dcep.config.PayCommonParam;
import com.dcits.dcwlt.pay.batch.mapper.PayCommonParamMapper;
import com.dcits.dcwlt.pay.batch.service.IPayCommonParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 参数配置Service业务层处理
 * 
 * @author dcwlt
 * @date 2021-04-14
 */
@Service
public class PayCommonParamServiceImpl implements IPayCommonParamService
{
    @Autowired
    private PayCommonParamMapper payCommonParamMapper;

    /**
     * 查询参数配置
     * 
     * @param id 参数配置ID
     * @return 参数配置
     */
    @Override
    public PayCommonParam selectPayCommonParamById(Long id)
    {
        return payCommonParamMapper.selectPayCommonParamById(id);
    }

    /**
     * 查询参数配置列表
     * 
     * @param payCommonParam 参数配置
     * @return 参数配置
     */
    @Override
    public List<PayCommonParam> selectPayCommonParamList(PayCommonParam payCommonParam)
    {
        return payCommonParamMapper.selectPayCommonParamList(payCommonParam);
    }

    /**
     * 新增参数配置
     * 
     * @param payCommonParam 参数配置
     * @return 结果
     */
    @Override
    public int insertPayCommonParam(PayCommonParam payCommonParam)
    {
        payCommonParam.setCreateTime(DateUtils.getNowDate());
        return payCommonParamMapper.insertPayCommonParam(payCommonParam);
    }

    /**
     * 修改参数配置
     * 
     * @param payCommonParam 参数配置
     * @return 结果
     */
    @Override
    public int updatePayCommonParam(PayCommonParam payCommonParam)
    {
        payCommonParam.setUpdateTime(DateUtils.getNowDate());
        return payCommonParamMapper.updatePayCommonParam(payCommonParam);
    }

    /**
     * 批量删除参数配置
     * 
     * @param ids 需要删除的参数配置ID
     * @return 结果
     */
    @Override
    public int deletePayCommonParamByIds(Long[] ids)
    {
        return payCommonParamMapper.deletePayCommonParamByIds(ids);
    }

    /**
     * 删除参数配置信息
     * 
     * @param id 参数配置ID
     * @return 结果
     */
    @Override
    public int deletePayCommonParamById(Long id)
    {
        return payCommonParamMapper.deletePayCommonParamById(id);
    }
}
