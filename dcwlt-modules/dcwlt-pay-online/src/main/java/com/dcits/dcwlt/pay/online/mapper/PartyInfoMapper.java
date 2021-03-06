package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.PartyInfoDO;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

public interface PartyInfoMapper {
	List<PartyInfoDO> queryEffectiveParty(String queryEffectiveUrl, PartyInfoDO partyInfoDO);

	int insertParty(PartyInfoDO partyInfoDO);

	int updateParty(String updateUrl, PartyInfoDO partyInfoDO);

	List<PartyInfoDO> queryLoseEffectiveParty(String queryIneffectiveUrl, String inEffectiveDate);

	List<PartyInfoDO> queryEffectiveParty(PartyInfoDO partyInfoDO);


	int updateParty(PartyInfoDO partyInfoDO);

	PartyInfoDO queryParty(@Param("partyID") String partyID);

	List<PartyInfoDO> queryLoseEffectiveParty(String inEffectiveDate);

	List<PartyInfoDO> querysParty(PartyInfoDO partyInfoDO);
}
