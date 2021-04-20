package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.IdempotentDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IdempotentMapper {
    int insert(IdempotentDO idempotentDO);
}
