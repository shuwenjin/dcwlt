package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.pay.api.model.AuthInfoToBeEffectiveDO;
import com.dcits.dcwlt.pay.online.service.IAuthInfoToBeEffectiveRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthInfoToBeEffectiveRepository implements IAuthInfoToBeEffectiveRepository {
    private static final String REPLACE_AUTHINFO_TOBEEFFECTIVE_SQL = "authInfoToBeEffectiveMapper.replaceAuthInfoToBeEffective";
    private static final String QUERY_AUTHINFO_SQL = "authInfoToBeEffectiveMapper.queryAuthInfo";
    private static final String DELETE_AUTHINFO_SQL = "authInfoToBeEffectiveMapper.deleteAuthInfo";

    /*
     * 查询业务权限变更临时表
     * */
    @Override
    public List<AuthInfoToBeEffectiveDO> queryAuthInfo(AuthInfoToBeEffectiveDO authInfoToBeEffectiveDO) {
//        return DBUtil.selectList(QUERY_AUTHINFO_SQL, authInfoToBeEffectiveDO);
        return new ArrayList<>();
    }

    @Override
    public int deleteAuthInfo(AuthInfoToBeEffectiveDO authInfoToBeEffectiveDO) {
//        return DBUtil.delete(DELETE_AUTHINFO_SQL, authInfoToBeEffectiveDO);
        return 1;
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
