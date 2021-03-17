package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.model.PartyToBeEffectiveDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 机构信息组件接口
 *
 * @author majun
 * @date 2020/12/30
 */
@Component
public interface IPartyToBeEffectiveRepository {

    int addPartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO);

    int addOrUpdatePartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO);

    boolean partyTmpExist(PartyToBeEffectiveDO partyToBeEffectiveDO);

    int updatePartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO);

    List<PartyToBeEffectiveDO> queryPartyTmp(PartyToBeEffectiveDO partyToBeEffectiveDO);

    List<PartyToBeEffectiveDO> queryPartyTmpByEffectiveDate(PartyToBeEffectiveDO partyToBeEffectiveDO);

    PartyToBeEffectiveDO queryPartyTmpByPartyId(String partyId);

    int deletePartyTemp(String partyId);
}