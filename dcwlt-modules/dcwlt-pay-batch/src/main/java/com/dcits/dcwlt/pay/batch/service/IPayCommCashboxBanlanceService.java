package com.dcits.dcwlt.pay.batch.service;

import com.dcits.dcwlt.pay.batch.domain.PayCommCashboxBanlance;

import java.util.List;

/**
 * 钱柜余额对账通知Service接口
 * 
 * @author dcwlt
 * @date 2021-05-12
 */
public interface IPayCommCashboxBanlanceService 
{
    /**
     * 查询钱柜余额对账通知
     * 
     * @param id 钱柜余额对账通知ID
     * @return 钱柜余额对账通知
     */
    public PayCommCashboxBanlance selectPayCommCashboxBanlanceById(Long id);

    /**
     * 查询钱柜余额对账通知列表
     * 
     * @param payCommCashboxBanlance 钱柜余额对账通知
     * @return 钱柜余额对账通知集合
     */
    public List<PayCommCashboxBanlance> selectPayCommCashboxBanlanceList(PayCommCashboxBanlance payCommCashboxBanlance);

    /**
     * 新增钱柜余额对账通知
     * 
     * @param payCommCashboxBanlance 钱柜余额对账通知
     * @return 结果
     */
    public int insertPayCommCashboxBanlance(PayCommCashboxBanlance payCommCashboxBanlance);

    /**
     * 修改钱柜余额对账通知
     * 
     * @param payCommCashboxBanlance 钱柜余额对账通知
     * @return 结果
     */
    public int updatePayCommCashboxBanlance(PayCommCashboxBanlance payCommCashboxBanlance);

    /**
     * 批量删除钱柜余额对账通知
     * 
     * @param ids 需要删除的钱柜余额对账通知ID
     * @return 结果
     */
    public int deletePayCommCashboxBanlanceByIds(Long[] ids);

    /**
     * 删除钱柜余额对账通知信息
     * 
     * @param id 钱柜余额对账通知ID
     * @return 结果
     */
    public int deletePayCommCashboxBanlanceById(Long id);
}
