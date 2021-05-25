package com.dcits.dcwlt.pay.batch.service;

import com.dcits.dcwlt.pay.api.model.PayCommCashboxParty;

import java.util.List;

/**
 * 运营机构钱柜参数Service接口
 * 
 * @author dcwlt
 * @date 2021-05-12
 */
public interface IPayCommCashboxPartyService 
{
    /**
     * 查询运营机构钱柜参数
     * 
     * @param id 运营机构钱柜参数ID
     * @return 运营机构钱柜参数
     */
    public PayCommCashboxParty selectPayCommCashboxPartyById(Long id);

    /**
     * 查询运营机构钱柜参数列表
     * 
     * @param payCommCashboxParty 运营机构钱柜参数
     * @return 运营机构钱柜参数集合
     */
    public List<PayCommCashboxParty> selectPayCommCashboxPartyList(PayCommCashboxParty payCommCashboxParty);

    /**
     * 新增运营机构钱柜参数
     * 
     * @param payCommCashboxParty 运营机构钱柜参数
     * @return 结果
     */
    public int insertPayCommCashboxParty(PayCommCashboxParty payCommCashboxParty);

    /**
     * 修改运营机构钱柜参数
     * 
     * @param payCommCashboxParty 运营机构钱柜参数
     * @return 结果
     */
    public int updatePayCommCashboxParty(PayCommCashboxParty payCommCashboxParty);
    /**
     * 修改运营机构钱柜预警余额
     *
     * @param payCommCashboxParty 运营机构钱柜参数
     * @return 结果
     */
    public int updatePayCommCashboxPartyWarn(PayCommCashboxParty payCommCashboxParty);

    /**
     * 批量删除运营机构钱柜参数
     * 
     * @param ids 需要删除的运营机构钱柜参数ID
     * @return 结果
     */
    public int deletePayCommCashboxPartyByIds(Long[] ids);

    /**
     * 删除运营机构钱柜参数信息
     * 
     * @param id 运营机构钱柜参数ID
     * @return 结果
     */
    public int deletePayCommCashboxPartyById(Long id);
}
