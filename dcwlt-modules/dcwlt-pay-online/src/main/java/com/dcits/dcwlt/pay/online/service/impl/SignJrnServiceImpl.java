package com.dcits.dcwlt.pay.online.service.impl;


import com.dcits.dcwlt.pay.api.model.SignJrnDO;
import com.dcits.dcwlt.pay.online.mapper.SignJrnMapper;
import com.dcits.dcwlt.pay.online.service.ISignJrnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SignJrnServiceImpl implements ISignJrnService {

    @Autowired
    private SignJrnMapper signJrnMapper;

    @Override
    public SignJrnDO selectByWalletIdAndMsgSndCd(String walletId, String msgSndCd) {
        return null;
    }

    @Override
    public int insert(SignJrnDO signJrnDO) {
        return 0;
    }

    @Override
    public int update(SignJrnDO signJrnDO) {
        return 0;
    }

    @Override
    public List<SignJrnDO> selectPartSignJrn(SignJrnDO signJrnDO) {
        return signJrnMapper.selectPartSignJrn(signJrnDO);
    }
}
