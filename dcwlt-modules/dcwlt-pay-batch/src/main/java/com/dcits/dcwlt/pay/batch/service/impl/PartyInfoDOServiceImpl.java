package com.dcits.dcwlt.pay.batch.service.impl;

import java.util.List;

import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dcits.dcwlt.pay.batch.mapper.PartyInfoDOMapper;

import com.dcits.dcwlt.pay.batch.service.IPartyInfoDOService;

/**
 * 机构Service业务层处理
 * 
 * @author 
 * @date 2021-03-03
 */
@Service
public class PartyInfoDOServiceImpl implements IPartyInfoDOService 
{
    @Autowired
    private PartyInfoDOMapper partyInfoDOMapper;

    /**
     * 查询机构
     * 
     * @param partyid 机构ID
     * @return 机构
     */
    @Override
    public PartyInfoDO selectPartyInfoDOById(String partyid)
    {
        return partyInfoDOMapper.selectPartyInfoDOById(partyid);
    }

    /**
     * 查询机构列表
     * 
     * @param partyInfoDO 机构
     * @return 机构
     */
    @Override
    public List<PartyInfoDO> selectPartyInfoDOList(PartyInfoDO partyInfoDO)
    {
        return partyInfoDOMapper.selectPartyInfoDOList(partyInfoDO);
    }

    /**
     * 新增机构
     * 
     * @param partyInfoDO 机构
     * @return 结果
     */
    @Override
    public int insertPartyInfoDO(PartyInfoDO partyInfoDO)
    {
        return partyInfoDOMapper.insertPartyInfoDO(partyInfoDO);
    }

    /**
     * 修改机构
     * 
     * @param partyInfoDO 机构
     * @return 结果
     */
    @Override
    public int updatePartyInfoDO(PartyInfoDO partyInfoDO)
    {
        return partyInfoDOMapper.updatePartyInfoDO(partyInfoDO);
    }

    /**
     * 批量删除机构
     * 
     * @param partyids 需要删除的机构ID
     * @return 结果
     */
    @Override
    public int deletePartyInfoDOByIds(String[] partyids)
    {
        return partyInfoDOMapper.deletePartyInfoDOByIds(partyids);
    }

    /**
     * 删除机构信息
     * 
     * @param partyid 机构ID
     * @return 结果
     */
    @Override
    public int deletePartyInfoDOById(String partyid)
    {
        return partyInfoDOMapper.deletePartyInfoDOById(partyid);
    }
}
