package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.pay.batch.domain.PaySignSigninfoJrn;
import com.dcits.dcwlt.pay.batch.mapper.PaySignSigninfoJrnMapper;
import com.dcits.dcwlt.pay.batch.service.IPaySignSigninfoJrnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 签约流水Service业务层处理
 *
 * @author dcwlt
 * @date 2021-05-07
 */
@Service
public class PaySignSigninfoJrnServiceImpl implements IPaySignSigninfoJrnService {
    @Autowired
    private PaySignSigninfoJrnMapper paySignSigninfoJrnMapper;

    /**
     * 查询签约流水
     *
     * @param paydate 签约流水ID
     * @return 签约流水
     */
    @Override
    public PaySignSigninfoJrn selectPaySignSigninfoJrnById(String paydate) {
        return paySignSigninfoJrnMapper.selectPaySignSigninfoJrnById(paydate);
    }

    /**
     * 查询签约流水列表
     *
     * @param paySignSigninfoJrn 签约流水
     * @return 签约流水
     */
    @Override
    public List<PaySignSigninfoJrn> selectPaySignSigninfoJrnList(PaySignSigninfoJrn paySignSigninfoJrn) {
        return paySignSigninfoJrnMapper.selectPaySignSigninfoJrnList(paySignSigninfoJrn);
    }

    /**
     * 新增签约流水
     *
     * @param paySignSigninfoJrn 签约流水
     * @return 结果
     */
    @Override
    public int insertPaySignSigninfoJrn(PaySignSigninfoJrn paySignSigninfoJrn) {
        return paySignSigninfoJrnMapper.insertPaySignSigninfoJrn(paySignSigninfoJrn);
    }

    /**
     * 修改签约流水
     *
     * @param paySignSigninfoJrn 签约流水
     * @return 结果
     */
    @Override
    public int updatePaySignSigninfoJrn(PaySignSigninfoJrn paySignSigninfoJrn) {
        return paySignSigninfoJrnMapper.updatePaySignSigninfoJrn(paySignSigninfoJrn);
    }

    /**
     * 批量删除签约流水
     *
     * @param paydates 需要删除的签约流水ID
     * @return 结果
     */
    @Override
    public int deletePaySignSigninfoJrnByIds(String[] paydates) {
        return paySignSigninfoJrnMapper.deletePaySignSigninfoJrnByIds(paydates);
    }

    /**
     * 删除签约流水信息
     *
     * @param paydate 签约流水ID
     * @return 结果
     */
    @Override
    public int deletePaySignSigninfoJrnById(String paydate) {
        return paySignSigninfoJrnMapper.deletePaySignSigninfoJrnById(paydate);
    }
}
