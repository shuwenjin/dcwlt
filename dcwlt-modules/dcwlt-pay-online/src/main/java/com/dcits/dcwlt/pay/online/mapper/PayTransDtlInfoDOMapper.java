package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayTransDtlInfoDOMapper {


    int insert(PayTransDtlInfoDO payTransDtlInfoDO);

}
