package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.PayCashboxPartyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayCashboxPartyMapper {


    int update(PayCashboxPartyDO payCashboxPartyDO);

    int insert(PayCashboxPartyDO payCashboxPartyDO);

    List<PayCashboxPartyDO> query(PayCashboxPartyDO payCashboxPartyDO);

}




