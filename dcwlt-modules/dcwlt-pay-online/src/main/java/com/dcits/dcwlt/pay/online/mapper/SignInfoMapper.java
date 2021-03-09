package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.SignInfoDO;
import org.mapstruct.Mapper;

@Mapper
public interface SignInfoMapper {

    int insert(SignInfoDO signInfoDO);

    SignInfoDO selectByWltIdAndAcctId(SignInfoDO signInfoDO);
    int updateBySignNo(SignInfoDO signInfoDO);
    int updateByWltIdAndAcctId(SignInfoDO signInfoDO);

    SignInfoDO selectBySignNo(String signNo);
}


