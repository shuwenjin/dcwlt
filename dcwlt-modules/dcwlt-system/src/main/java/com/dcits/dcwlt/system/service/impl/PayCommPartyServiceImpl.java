package com.dcits.dcwlt.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dcits.dcwlt.system.mapper.PayCommPartyMapper;
import com.dcits.dcwlt.system.domain.PayCommParty;
import com.dcits.dcwlt.system.service.IPayCommPartyService;

/**
 * 机构Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-25
 */
@Service
public class PayCommPartyServiceImpl implements IPayCommPartyService 
{
    @Autowired
    private PayCommPartyMapper payCommPartyMapper;

    /**
     * 查询机构
     * 
     * @param partyid 机构ID
     * @return 机构
     */
    @Override
    public PayCommParty selectPayCommPartyById(String partyid)
    {
        return payCommPartyMapper.selectPayCommPartyById(partyid);
    }

    /**
     * 查询机构列表
     * 
     * @param payCommParty 机构
     * @return 机构
     */
    @Override
    public List<PayCommParty> selectPayCommPartyList(PayCommParty payCommParty)
    {
        return payCommPartyMapper.selectPayCommPartyList(payCommParty);
    }

    /**
     * 新增机构
     * 
     * @param payCommParty 机构
     * @return 结果
     */
    @Override
    public int insertPayCommParty(PayCommParty payCommParty)
    {
        return payCommPartyMapper.insertPayCommParty(payCommParty);
    }

    /**
     * 修改机构
     * 
     * @param payCommParty 机构
     * @return 结果
     */
    @Override
    public int updatePayCommParty(PayCommParty payCommParty)
    {
        return payCommPartyMapper.updatePayCommParty(payCommParty);
    }

    /**
     * 批量删除机构
     * 
     * @param partyids 需要删除的机构ID
     * @return 结果
     */
    @Override
    public int deletePayCommPartyByIds(String[] partyids)
    {
        return payCommPartyMapper.deletePayCommPartyByIds(partyids);
    }

    /**
     * 删除机构信息
     * 
     * @param partyid 机构ID
     * @return 结果
     */
    @Override
    public int deletePayCommPartyById(String partyid)
    {
        return payCommPartyMapper.deletePayCommPartyById(partyid);
    }
}
