package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import com.dcits.dcwlt.pay.api.model.PartyToBeEffectiveDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 临时机构Mapper接口
 * 
 * @author dcwlt
 * @date 2021-03-03
 */
@Mapper
public interface PartyInfoOTMapper
{

    /**
     * 查询今天以前失效的机构信息
     *
     * @param partyToBeEffectiveDO 临时机构
     * @return 机构集合
     */
    public List<PartyToBeEffectiveDO> selectPartyTmpOfEffectiveToDay(PartyToBeEffectiveDO partyToBeEffectiveDO);

    /**
     * 查询机构临时数据
     * 
     * @param partyToBeEffectiveDO 临时机构
     * @return 机构集合
     */
    public List<PartyToBeEffectiveDO> queryPartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO);

    /**
     * 新增机构临时数据
     * 
     * @param partyToBeEffectiveDO 临时机构
     * @return 结果
     */
    public int insertPartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO);

    /**
     * 修改机构临时数据
     * 
     * @param partyToBeEffectiveDO 临时机构
     * @return 结果
     */
    public int updatePartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO);

    /**
     * 删除机构
     * 
     * @param partyid 机构ID
     * @return 结果
     */
    public int deletePartyTmp(String partyid);

    /**
     * 批量删除机构
     * 
     * @param partyids 需要删除的数据ID
     * @return 结果
     */
    public int deletePartyInfoDOByIds(String[] partyids);
}
