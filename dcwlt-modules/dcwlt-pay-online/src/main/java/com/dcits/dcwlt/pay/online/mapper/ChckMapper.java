package com.dcits.dcwlt.pay.online.mapper;


import com.dcits.dcwlt.pay.api.model.ChckDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChckMapper {
    int insert(ChckDO chckDO);
}
