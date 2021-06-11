package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.pay.api.model.AuthInfoToBeEffectiveDO;
import com.dcits.dcwlt.pay.batch.mapper.AuthInfoMapper;
import com.dcits.dcwlt.pay.batch.service.IAuthInfoToBeEffectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthInfoToBeEffectiveRepository implements IAuthInfoToBeEffectiveRepository {

    @Autowired
    private AuthInfoMapper AuthInfoMapper;
    /*
     * 查询业务权限变更临时表
     * */
    @Override
    public List<AuthInfoToBeEffectiveDO> queryAuthInfo(AuthInfoToBeEffectiveDO authInfoToBeEffectiveDO) {
        return  AuthInfoMapper.queryAuthInfo(authInfoToBeEffectiveDO);
    }

    @Override
    public int deleteAuthInfo(AuthInfoToBeEffectiveDO authInfoToBeEffectiveDO) {
          return AuthInfoMapper.deleteAuthInfo(authInfoToBeEffectiveDO);
    }

    /*
     * 新增/更新变更业务权限临时表
     * */
    @Override
    public int replaceAuthInfoToBeEffective(AuthInfoToBeEffectiveDO authInfoToBeEffectiveDO) {
//        return DBUtil.update(REPLACE_AUTHINFO_TOBEEFFECTIVE_SQL, authInfoToBeEffectiveDO);
        return 1;
    }
}
