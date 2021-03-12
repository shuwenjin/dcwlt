/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年12月28日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.pay.api.model.SignInfoDO;
import com.dcits.dcwlt.pay.online.mapper.SignInfoMapper;
import com.dcits.dcwlt.pay.online.service.ISignInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SignInfoServiceImpl implements ISignInfoService {

    @Autowired
    private SignInfoMapper signInfoMapper;
	
    private static final String QUERY_SIGN_INFO_SQL                = "signInfoMapper.selectBySignNo";

    private static final String INSERT_SIGN_INFO_SQL               = "signInfoMapper.insert";

    private static final String UPDATE_SIGN_INFO_SQL               = "signInfoMapper.updateBySignNo";

    private static final String UNIQUE_QUERY_SIGN_SQL              = "signInfoMapper.selectByWltIdAndAcctId";

    private static final String UPDATE_SIGN_INFO_BY_UNIQUE_KEY_SQL = "signInfoMapper.updateByWltIdAndAcctId";

	@Override
    public SignInfoDO queryBySignNo(String signNo) {
        return signInfoMapper.selectBySignNo(signNo);
	}

    @Override
    public int insert(SignInfoDO signInfoDO) {
        return signInfoMapper.insert(signInfoDO);
    }

    @Override
    public int updateBySignNo(SignInfoDO signNo) {
       return signInfoMapper.updateBySignNo(signNo);
    }

    @Override
    public SignInfoDO selectByWltIdAndAcctId(SignInfoDO signInfoDO) {
        return signInfoMapper.selectByWltIdAndAcctId(signInfoDO);
    }

    @Override
    public int updateByWltIdAndAcctId(SignInfoDO signInfoDO) {
        return signInfoMapper.updateByWltIdAndAcctId(signInfoDO);
    }
}
