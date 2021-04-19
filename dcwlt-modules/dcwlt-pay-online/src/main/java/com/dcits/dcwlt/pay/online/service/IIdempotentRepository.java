package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.pay.api.model.IdempotentDO;

public interface IIdempotentRepository {
    int insert(IdempotentDO idempotentDO);
}
