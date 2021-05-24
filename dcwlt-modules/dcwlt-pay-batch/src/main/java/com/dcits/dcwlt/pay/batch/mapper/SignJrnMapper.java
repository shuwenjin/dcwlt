package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.SignJrnDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SignJrnMapper {

    SignJrnDO selectByWalletIdAndMsgSndCd(@Param("walletId")String walletId,@Param("msgSendCode") String  msgSendCode);
    int insert(SignJrnDO signJrnDO);
    int update(SignJrnDO signJrnDO);
    List<SignJrnDO> selectPartSignJrn(SignJrnDO signJrnDO);
}
