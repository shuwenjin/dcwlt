package com.dcits.dcwlt.pay.batch.mapper;


import com.dcits.dcwlt.pay.batch.domain.PaySignSigninfoJrn;

import java.util.List;

/**
 * 签约流水Mapper接口
 *
 * @author dcwlt
 * @date 2021-05-07
 */
public interface PaySignSigninfoJrnMapper {
    /**
     * 查询签约流水
     *
     * @param paydate 签约流水ID
     * @return 签约流水
     */
    public PaySignSigninfoJrn selectPaySignSigninfoJrnById(String paydate);

    /**
     * 查询签约流水列表
     *
     * @param paySignSigninfoJrn 签约流水
     * @return 签约流水集合
     */
    public List<PaySignSigninfoJrn> selectPaySignSigninfoJrnList(PaySignSigninfoJrn paySignSigninfoJrn);

    /**
     * 新增签约流水
     *
     * @param paySignSigninfoJrn 签约流水
     * @return 结果
     */
    public int insertPaySignSigninfoJrn(PaySignSigninfoJrn paySignSigninfoJrn);

    /**
     * 修改签约流水
     *
     * @param paySignSigninfoJrn 签约流水
     * @return 结果
     */
    public int updatePaySignSigninfoJrn(PaySignSigninfoJrn paySignSigninfoJrn);

    /**
     * 删除签约流水
     *
     * @param paydate 签约流水ID
     * @return 结果
     */
    public int deletePaySignSigninfoJrnById(String paydate);

    /**
     * 批量删除签约流水
     *
     * @param paydates 需要删除的数据ID
     * @return 结果
     */
    public int deletePaySignSigninfoJrnByIds(String[] paydates);
}
