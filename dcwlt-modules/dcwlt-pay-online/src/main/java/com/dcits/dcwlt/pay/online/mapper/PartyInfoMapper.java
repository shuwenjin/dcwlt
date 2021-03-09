package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.PartyInfoDO;

import java.util.List;

public interface PartyInfoMapper {
	List<PartyInfoDO> queryEffectiveParty(String queryEffectiveUrl, PartyInfoDO partyInfoDO);

	int insertParty(String insertUrl, PartyInfoDO partyInfoDO);

	int updateParty(String updateUrl, PartyInfoDO partyInfoDO);

	List<PartyInfoDO> queryParty(String queryUrl, PartyInfoDO partyInfoDO);

	List<PartyInfoDO> queryLoseEffectiveParty(String queryIneffectiveUrl, String inEffectiveDate);

	List<PartyInfoDO> queryEffectiveParty(PartyInfoDO partyInfoDO);
}
