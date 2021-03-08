package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 临时机构组件
 *
 * @author majun
 * @date 2020/12/30
 */
@Component
public interface IPartyInfoRepository {

    int addParty(PartyInfoDO partyInfoDO);

    int addOrUpdateParty(PartyInfoDO partyInfoDO);

    boolean partyExist(PartyInfoDO partyInfoDO);

    int updateParty(PartyInfoDO partyInfoDO);

    List<PartyInfoDO> queryParty(PartyInfoDO partyInfoDO);

    PartyInfoDO queryPartyInfoByPartyId(String partyId);

    boolean isAvailable(PartyInfoDO partyInfoDO);

    boolean isAvailable(String partyId);

    boolean sendReceiveAble(String partyId);

    boolean sendReceiveAble(PartyInfoDO partyInfoDO);

    PartyInfoDO getPartyInfo(String partyId);

    List<PartyInfoDO> getEffectivePartyByCondition(PartyInfoDO partyInfoDO);

    List<PartyInfoDO> getInEffectiveParty();
}
