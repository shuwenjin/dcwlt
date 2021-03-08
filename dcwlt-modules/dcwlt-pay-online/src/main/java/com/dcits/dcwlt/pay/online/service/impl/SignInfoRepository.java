/*********************************************
 * Copyright (c) 2020 LI-RTP.
 * All rights reserved.
 * Created on 2020年12月28日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.pay.online.service.impl;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.dcits.dcwlt.pay.api.model.SignInfoDO;
import com.dcits.dcwlt.pay.online.mapper.SignInfoMapper;
import com.dcits.dcwlt.pay.online.service.ISignInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SignInfoRepository implements ISignInfoRepository {

    @Autowired
    private SignInfoMapper signInfoMapper;

    @Override
    public int insert(SignInfoDO signInfoDO) {
        return signInfoMapper.insert(signInfoDO);
    }

    public SignInfoDO queryBySignNo(String signNo) {
        return signInfoMapper.selectByWltIdAndAcctId(signNo);
    }
}
