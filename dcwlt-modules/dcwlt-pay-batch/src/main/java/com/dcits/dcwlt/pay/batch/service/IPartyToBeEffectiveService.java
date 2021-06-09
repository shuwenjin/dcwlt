package com.dcits.dcwlt.pay.batch.service;

import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import com.dcits.dcwlt.pay.api.model.PartyToBeEffectiveDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构信息组件接口
 *
 * @author
 * @date 2020/12/30
 */
public interface IPartyToBeEffectiveService {

    int insertParty(PartyInfoDO partyInfoDO);

    int updateParty(PartyInfoDO partyInfoDO);

    PartyInfoDO queryParty(String partyID);

    List<PartyToBeEffectiveDO> queryPartyTmpByEffectiveDate(PartyToBeEffectiveDO partyToBeEffectiveDO);

}
