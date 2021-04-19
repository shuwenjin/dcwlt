package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.pay.api.model.IdempotentDO;
import com.dcits.dcwlt.pay.online.mapper.IdempotentMapper;
import com.dcits.dcwlt.pay.online.service.IIdempotentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author zhanguohai
 * @Time 2021/1/2 15:05
 * @Version 1.0
 * Description:幂等控制表DAO层
 */
@Repository
public class IdempotentRepository implements IIdempotentRepository {

    @Autowired
    IdempotentMapper idempotentMapper;

    @Override
    public int insert(IdempotentDO idempotentDO) {
        return idempotentMapper.insert(idempotentDO);
    }
}
