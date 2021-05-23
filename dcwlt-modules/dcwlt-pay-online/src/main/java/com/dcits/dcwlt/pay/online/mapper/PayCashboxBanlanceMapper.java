package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.PayCashboxBanlanceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayCashboxBanlanceMapper {
    int update(PayCashboxBanlanceDO payCashboxBanlanceDO);

    int insert(PayCashboxBanlanceDO payCashboxBanlanceDO);

    List<PayCashboxBanlanceDO> query(PayCashboxBanlanceDO payCashboxBanlanceDO);
}
