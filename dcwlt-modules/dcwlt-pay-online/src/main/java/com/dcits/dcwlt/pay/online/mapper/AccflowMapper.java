package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.AccFlowDO;
import org.mapstruct.Mapper;

import java.util.Map;

@Mapper
public interface AccflowMapper {

    int update(AccFlowDO updAccFlowDO);

    int insert(AccFlowDO accFlowDO);

    AccFlowDO query(Map<String, String> param);

	AccFlowDO selectCoreReqSerno(String queryByPayAccflowSql, Map<String, String> param);

	int updateCoreStatus(String updateStatusAccflowSql, AccFlowDO updAccFlowDO);
}




