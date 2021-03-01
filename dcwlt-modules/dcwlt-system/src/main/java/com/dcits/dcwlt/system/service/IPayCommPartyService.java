package com.dcits.dcwlt.system.service;

import java.util.List;
import com.dcits.dcwlt.system.domain.PayCommParty;

/**
 * 机构Service接口
 * 
 * @author ruoyi
 * @date 2021-02-25
 */
public interface IPayCommPartyService 
{
    /**
     * 查询机构
     * 
     * @param partyid 机构ID
     * @return 机构
     */
    public PayCommParty selectPayCommPartyById(String partyid);

    /**
     * 查询机构列表
     * 
     * @param payCommParty 机构
     * @return 机构集合
     */
    public List<PayCommParty> selectPayCommPartyList(PayCommParty payCommParty);

    /**
     * 新增机构
     * 
     * @param payCommParty 机构
     * @return 结果
     */
    public int insertPayCommParty(PayCommParty payCommParty);

    /**
     * 修改机构
     * 
     * @param payCommParty 机构
     * @return 结果
     */
    public int updatePayCommParty(PayCommParty payCommParty);

    /**
     * 批量删除机构
     * 
     * @param partyids 需要删除的机构ID
     * @return 结果
     */
    public int deletePayCommPartyByIds(String[] partyids);

    /**
     * 删除机构信息
     * 
     * @param partyid 机构ID
     * @return 结果
     */
    public int deletePayCommPartyById(String partyid);
}
