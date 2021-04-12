package com.dcits.dcwlt.pay.api.mapper;

import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventConfigDO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EventConfigMapper {

	EventConfigDO queryEventConfig(String exceptEventCode);

}
