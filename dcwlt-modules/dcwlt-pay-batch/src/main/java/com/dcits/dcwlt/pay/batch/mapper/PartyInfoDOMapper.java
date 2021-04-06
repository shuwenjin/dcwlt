package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.PartyInfoDO;

import java.util.List;


/**
 * 机构Mapper接口
 * 
 * @author 
 * @date 2021-03-03
 */
public interface PartyInfoDOMapper 
{
    /**
     * 查询机构
     * 
     * @param partyid 机构ID
     * @return 机构
     */
    public PartyInfoDO selectPartyInfoDOById(String partyid);

    /**
     * 查询机构列表
     * 
     * @param partyInfoDO 机构
     * @return 机构集合
     */
    public List<PartyInfoDO> selectPartyInfoDOList(PartyInfoDO partyInfoDO);

    /**
     * 新增机构
     * 
     * @param partyInfoDO 机构
     * @return 结果
     */
    public int insertPartyInfoDO(PartyInfoDO partyInfoDO);

    /**
     * 修改机构
     * 
     * @param partyInfoDO 机构
     * @return 结果
     */
    public int updatePartyInfoDO(PartyInfoDO partyInfoDO);

    /**
     * 删除机构
     * 
     * @param partyid 机构ID
     * @return 结果
     */
    public int deletePartyInfoDOById(String partyid);

    /**
     * 批量删除机构
     * 
     * @param partyids 需要删除的数据ID
     * @return 结果
     */
    public int deletePartyInfoDOByIds(String[] partyids);
}
