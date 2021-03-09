package com.dcits.dcwlt.pay.online.mapper;


import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PartyMapper {

    List<PartyInfoDO> selectList(PartyInfoDO partyInfoDO);
}


