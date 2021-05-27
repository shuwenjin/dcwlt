package com.dcits.dcwlt.pay.batch.mapper;


import com.dcits.dcwlt.pay.api.model.PayCommCashboxProcess;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author dcwlt
 * @date 2021-05-11
 */
public interface PayCommCashboxProcessMapper {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public PayCommCashboxProcess selectPayCommCashboxProcessById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param payCommCashboxProcess 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<PayCommCashboxProcess> selectPayCommCashboxProcessList(PayCommCashboxProcess payCommCashboxProcess);

    /**
     * 新增【请填写功能名称】
     *
     * @param payCommCashboxProcess 【请填写功能名称】
     * @return 结果
     */
    public int insertPayCommCashboxProcess(PayCommCashboxProcess payCommCashboxProcess);

    /**
     * 修改【请填写功能名称】
     *
     * @param payCommCashboxProcess 【请填写功能名称】
     * @return 结果
     */
    public int updatePayCommCashboxProcess(PayCommCashboxProcess payCommCashboxProcess);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deletePayCommCashboxProcessById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePayCommCashboxProcessByIds(Long[] ids);
}
