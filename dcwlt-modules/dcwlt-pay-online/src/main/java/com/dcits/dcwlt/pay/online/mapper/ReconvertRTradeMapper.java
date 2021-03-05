package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReconvertRTradeMapper {

    int insertReconvertRTrade(PayTransDtlInfoDO payTransDtlInfoDO);

    int updateReconvertRTrade(PayTransDtlInfoDO updateDO, StateMachine oldStatus);
}
