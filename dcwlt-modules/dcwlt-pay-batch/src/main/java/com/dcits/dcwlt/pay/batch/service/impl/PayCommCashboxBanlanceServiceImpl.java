package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.pay.batch.domain.PayCommCashboxBanlance;
import com.dcits.dcwlt.pay.batch.mapper.PayCommCashboxBanlanceMapper;
import com.dcits.dcwlt.pay.batch.service.IPayCommCashboxBanlanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 钱柜余额对账通知Service业务层处理
 *
 * @author dcwlt
 * @date 2021-05-12
 */
@Service
public class PayCommCashboxBanlanceServiceImpl implements IPayCommCashboxBanlanceService {
    @Autowired
    private PayCommCashboxBanlanceMapper payCommCashboxBanlanceMapper;

    /**
     * 查询钱柜余额对账通知
     *
     * @param id 钱柜余额对账通知ID
     * @return 钱柜余额对账通知
     */
    @Override
    public PayCommCashboxBanlance selectPayCommCashboxBanlanceById(Long id) {
        return payCommCashboxBanlanceMapper.selectPayCommCashboxBanlanceById(id);
    }

    /**
     * 查询钱柜余额对账通知列表
     *
     * @param payCommCashboxBanlance 钱柜余额对账通知
     * @return 钱柜余额对账通知
     */
    @Override
    public List<PayCommCashboxBanlance> selectPayCommCashboxBanlanceList(PayCommCashboxBanlance payCommCashboxBanlance) {
        return payCommCashboxBanlanceMapper.selectPayCommCashboxBanlanceList(payCommCashboxBanlance);
    }

    /**
     * 新增钱柜余额对账通知
     *
     * @param payCommCashboxBanlance 钱柜余额对账通知
     * @return 结果
     */
    @Override
    public int insertPayCommCashboxBanlance(PayCommCashboxBanlance payCommCashboxBanlance) {
        return payCommCashboxBanlanceMapper.insertPayCommCashboxBanlance(payCommCashboxBanlance);
    }

    /**
     * 修改钱柜余额对账通知
     *
     * @param payCommCashboxBanlance 钱柜余额对账通知
     * @return 结果
     */
    @Override
    public int updatePayCommCashboxBanlance(PayCommCashboxBanlance payCommCashboxBanlance) {
        return payCommCashboxBanlanceMapper.updatePayCommCashboxBanlance(payCommCashboxBanlance);
    }

    /**
     * 批量删除钱柜余额对账通知
     *
     * @param ids 需要删除的钱柜余额对账通知ID
     * @return 结果
     */
    @Override
    public int deletePayCommCashboxBanlanceByIds(Long[] ids) {
        return payCommCashboxBanlanceMapper.deletePayCommCashboxBanlanceByIds(ids);
    }

    /**
     * 删除钱柜余额对账通知信息
     *
     * @param id 钱柜余额对账通知ID
     * @return 结果
     */
    @Override
    public int deletePayCommCashboxBanlanceById(Long id) {
        return payCommCashboxBanlanceMapper.deletePayCommCashboxBanlanceById(id);
    }
}
