package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.PayCommCashboxWrng;

import java.util.List;

/**
 * 钱柜余额告警Mapper接口
 * 
 * @author dcwlt
 * @date 2021-06-01
 */
public interface PayCommCashboxWrngMapper 
{
    /**
     * 查询钱柜余额告警
     * 
     * @param coopbankWltid 钱柜余额告警ID
     * @return 钱柜余额告警
     */
    public PayCommCashboxWrng selectPayCommCashboxWrngById(String coopbankWltid);

    /**
     * 查询钱柜余额告警列表
     * 
     * @param payCommCashboxWrng 钱柜余额告警
     * @return 钱柜余额告警集合
     */
    public List<PayCommCashboxWrng> selectPayCommCashboxWrngList(PayCommCashboxWrng payCommCashboxWrng);

    /**
     * 新增钱柜余额告警
     * 
     * @param payCommCashboxWrng 钱柜余额告警
     * @return 结果
     */
    public int insertPayCommCashboxWrng(PayCommCashboxWrng payCommCashboxWrng);

    /**
     * 修改钱柜余额告警
     * 
     * @param payCommCashboxWrng 钱柜余额告警
     * @return 结果
     */
    public int updatePayCommCashboxWrng(PayCommCashboxWrng payCommCashboxWrng);

    /**
     * 删除钱柜余额告警
     * 
     * @param coopbankWltid 钱柜余额告警ID
     * @return 结果
     */
    public int deletePayCommCashboxWrngById(String coopbankWltid);

    /**
     * 批量删除钱柜余额告警
     * 
     * @param coopbankWltids 需要删除的数据ID
     * @return 结果
     */
    public int deletePayCommCashboxWrngByIds(String[] coopbankWltids);
}
