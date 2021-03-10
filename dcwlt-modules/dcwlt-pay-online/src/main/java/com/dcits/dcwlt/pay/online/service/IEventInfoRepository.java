package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventInfoDO;


public interface IEventInfoRepository {

    int insertEventInfo(EventInfoDO eventInfoDO);

    int updateEventInfo(EventInfoDO eventInfoDO);

    int updateEventDealNum(String exceptEventCode, String exceptEventSeqNo, String maxDealNum);
}
