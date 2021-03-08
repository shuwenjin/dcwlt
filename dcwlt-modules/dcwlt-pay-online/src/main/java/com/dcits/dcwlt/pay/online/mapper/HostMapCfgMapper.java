package com.dcits.dcwlt.pay.online.mapper;

import org.mapstruct.Mapper;

import java.util.Map;

@Mapper
public interface HostMapCfgMapper {

    String query(Map<String, Object> selMap);
}


