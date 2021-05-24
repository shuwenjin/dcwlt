package com.dcits.dcwlt.pay.batch.service;

import com.dcits.dcwlt.pay.api.model.SignJrnDO;

import java.util.List;

public interface ISignJrnService {
    SignJrnDO selectByWalletIdAndMsgSndCd(String walletId, String  msgSndCd);
    int insert(SignJrnDO signJrnDO);
    int update(SignJrnDO signJrnDO);
    List<SignJrnDO> selectPartSignJrn(SignJrnDO signJrnDO);
}
