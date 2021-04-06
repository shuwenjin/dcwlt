package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.model.PartyToBeEffectiveDO;

import java.util.List;

/**
 * 机构信息组件接口
 *
 * @author
 * @date 2020/12/30
 */
public interface IPartyToBeEffectiveService {

    int addPartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO);

    int addOrUpdatePartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO);

    boolean partyTmpExist(PartyToBeEffectiveDO partyToBeEffectiveDO);

    int updatePartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO);

    List<PartyToBeEffectiveDO> queryPartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO);

    List<PartyToBeEffectiveDO> queryPartyTmpByEffectiveDate(PartyToBeEffectiveDO partyToBeEffectiveDO);

    PartyToBeEffectiveDO queryPartyTmpByPartyId(String partyId);

    int deletePartyTemp(String partyId);
}
