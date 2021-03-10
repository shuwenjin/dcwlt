package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface EventMapper {
	int insertEventInfo(EventInfoDO eventInfoDO);

	int updateEventInfo(EventInfoDO eventInfoDO);

	int updateEventDealNum(Map<String, Object> param);
}
