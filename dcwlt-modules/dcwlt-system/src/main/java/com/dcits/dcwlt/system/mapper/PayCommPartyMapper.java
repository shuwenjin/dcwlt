package com.dcits.dcwlt.system.mapper;

import java.util.List;
import com.dcits.dcwlt.system.domain.PayCommParty;

/**
 * 机构Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-25
 */
public interface PayCommPartyMapper 
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
     * 删除机构
     * 
     * @param partyid 机构ID
     * @return 结果
     */
    public int deletePayCommPartyById(String partyid);

    /**
     * 批量删除机构
     * 
     * @param partyids 需要删除的数据ID
     * @return 结果
     */
    public int deletePayCommPartyByIds(String[] partyids);
}
