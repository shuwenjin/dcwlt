package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import com.dcits.dcwlt.pay.api.model.PartyToBeEffectiveDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface PartyInfoMapper {
	int insertParty(PartyInfoDO partyInfoDO);

    int updateParty(PartyInfoDO partyInfoDO);

	PartyInfoDO queryParty(@Param("partyID") String partyID);
	/**
	 * 查询今天以前失效的机构信息
	 *
	 * @param partyToBeEffectiveDO 临时机构
	 * @return 机构集合
	 */
	public List<PartyToBeEffectiveDO> selectPartyTmpOfEffectiveToDay(PartyToBeEffectiveDO partyToBeEffectiveDO);
}
