package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.SignJrnDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignJrnMapper {

    SignJrnDO selectByWalletIdAndMsgSndCd(String walletId,String  msgSndCd);
    int insert(SignJrnDO signJrnDO);
    int update(SignJrnDO signJrnDO);
}
