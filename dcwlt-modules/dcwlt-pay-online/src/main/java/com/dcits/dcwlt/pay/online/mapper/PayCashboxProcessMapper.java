package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.PayCashboxProcessDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayCashboxProcessMapper {
    int update(PayCashboxProcessDO payCashboxProcessDO);

    int insert(PayCashboxProcessDO payCashboxProcessDO);

    List<PayCashboxProcessDO> query(PayCashboxProcessDO payCashboxProcessDO);
}
