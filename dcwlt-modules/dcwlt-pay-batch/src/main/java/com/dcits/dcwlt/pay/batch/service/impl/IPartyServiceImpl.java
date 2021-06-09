package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import com.dcits.dcwlt.pay.api.model.PartyToBeEffectiveDO;
import com.dcits.dcwlt.pay.batch.mapper.PartyInfoMapper;
import com.dcits.dcwlt.pay.batch.service.IPartyToBeEffectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IPartyServiceImpl implements IPartyToBeEffectiveService {
    @Autowired
    private PartyInfoMapper PartyInfoMapper;

    @Override
    public int insertParty(PartyInfoDO partyInfoDO) {
        return PartyInfoMapper.insertParty(partyInfoDO);
    }

    @Override
    public int updateParty(PartyInfoDO partyInfoDO) {
        return PartyInfoMapper.updateParty(partyInfoDO);
    }

    @Override
    public PartyInfoDO queryParty(String partyID) {
        return PartyInfoMapper.queryParty(partyID);
    }

    @Override
    public List<PartyToBeEffectiveDO> queryPartyTmpByEffectiveDate(PartyToBeEffectiveDO partyToBeEffectiveDO) {

        return  PartyInfoMapper.selectPartyTmpOfEffectiveToDay(partyToBeEffectiveDO);
    }


}
