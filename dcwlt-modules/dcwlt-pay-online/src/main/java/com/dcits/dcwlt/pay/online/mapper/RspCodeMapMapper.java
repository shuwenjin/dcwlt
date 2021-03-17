package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RspCodeMapMapper {
    List<RspCodeMapDO> getAllRspCodeMap();
}
