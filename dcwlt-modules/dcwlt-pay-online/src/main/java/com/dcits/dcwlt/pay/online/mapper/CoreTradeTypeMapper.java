package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.CoreTradeTypeDO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CoreTradeTypeMapper {

    List<CoreTradeTypeDO> queryByAccMeth(Map<String, Object> param);
}


