package com.dcits.dcwlt.pay.batch.service.impl;

import java.util.List;

import com.dcits.dcwlt.pay.api.model.PayCommCashboxWrng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dcits.dcwlt.pay.batch.mapper.PayCommCashboxWrngMapper;
import com.dcits.dcwlt.pay.batch.service.IPayCommCashboxWrngService;

/**
 * 钱柜余额告警Service业务层处理
 * 
 * @author dcwlt
 * @date 2021-06-01
 */
@Service
public class PayCommCashboxWrngServiceImpl implements IPayCommCashboxWrngService 
{
    @Autowired
    private PayCommCashboxWrngMapper payCommCashboxWrngMapper;

    /**
     * 查询钱柜余额告警
     * 
     * @param coopbankWltid 钱柜余额告警ID
     * @return 钱柜余额告警
     */
    @Override
    public PayCommCashboxWrng selectPayCommCashboxWrngById(String coopbankWltid)
    {
        return payCommCashboxWrngMapper.selectPayCommCashboxWrngById(coopbankWltid);
    }

    /**
     * 查询钱柜余额告警列表
     * 
     * @param payCommCashboxWrng 钱柜余额告警
     * @return 钱柜余额告警
     */
    @Override
    public List<PayCommCashboxWrng> selectPayCommCashboxWrngList(PayCommCashboxWrng payCommCashboxWrng)
    {
        return payCommCashboxWrngMapper.selectPayCommCashboxWrngList(payCommCashboxWrng);
    }

    /**
     * 新增钱柜余额告警
     * 
     * @param payCommCashboxWrng 钱柜余额告警
     * @return 结果
     */
    @Override
    public int insertPayCommCashboxWrng(PayCommCashboxWrng payCommCashboxWrng)
    {
        return payCommCashboxWrngMapper.insertPayCommCashboxWrng(payCommCashboxWrng);
    }

    /**
     * 修改钱柜余额告警
     * 
     * @param payCommCashboxWrng 钱柜余额告警
     * @return 结果
     */
    @Override
    public int updatePayCommCashboxWrng(PayCommCashboxWrng payCommCashboxWrng)
    {
        return payCommCashboxWrngMapper.updatePayCommCashboxWrng(payCommCashboxWrng);
    }

    /**
     * 批量删除钱柜余额告警
     * 
     * @param coopbankWltids 需要删除的钱柜余额告警ID
     * @return 结果
     */
    @Override
    public int deletePayCommCashboxWrngByIds(String[] coopbankWltids)
    {
        return payCommCashboxWrngMapper.deletePayCommCashboxWrngByIds(coopbankWltids);
    }

    /**
     * 删除钱柜余额告警信息
     * 
     * @param coopbankWltid 钱柜余额告警ID
     * @return 结果
     */
    @Override
    public int deletePayCommCashboxWrngById(String coopbankWltid)
    {
        return payCommCashboxWrngMapper.deletePayCommCashboxWrngById(coopbankWltid);
    }
}
