package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;


/**
 * 机构Mapper接口
 * 
 * @author dcwlt
 * @date 2021-03-03
 */
@Mapper
public interface PartyInfoMapper
{
    /**
     * 根据机构指定信息查询信息
     * 
     * @param partyInfoDO 机构
     * @return 机构集合
     */
    public List<PartyInfoDO> queryEffectiveParty(PartyInfoDO partyInfoDO);

    /**
     * 查询今天以前失效的机构信息
     *
     * @param inEffectiveDate 日期
     * @return 机构集合
     */
    public List<PartyInfoDO> queryLoseEffectiveParty(String inEffectiveDate);

    /**
     * 查询报文登记信息
     * 
     * @param partyInfoDO 机构
     * @return 机构集合
     */
    public List<PartyInfoDO> queryParty(PartyInfoDO partyInfoDO);

    /**
     * 新增报文登记信息
     * 
     * @param partyInfoDO 机构
     * @return 结果
     */
    public int insertParty(PartyInfoDO partyInfoDO);

    /**
     * 修改报文登记信息
     * 
     * @param partyInfoDO 机构
     * @return 结果
     */
    public int updateParty(PartyInfoDO partyInfoDO);

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

    PartyInfoDO queryParty(@Param("partyID") String partyID);
}
