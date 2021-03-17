package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.pay.api.model.AuthInfoDO;
import com.dcits.dcwlt.pay.online.mapper.AuthInfoMapper;
import com.dcits.dcwlt.pay.online.service.IAuthInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthInfoRepository implements IAuthInfoRepository {
    private static final String VALIDATE_AUTH_INFO_SQL = "authInfoMapper.validateAuthInfo";
    private static final String REPLACE_AUTH_INFO_SQL = "authInfoMapper.replaceAuthInfo";

    private static final String TRANSFORM_AUTHINFO_SQL = "authInfoMapper.transformAuthInfo";


    @Autowired
    private AuthInfoMapper authInfoMapper;
    /*
     * partyId:机构编码
     * msgType:报文编号
     * */
    @Override
    public List<AuthInfoDO> validateAuthInfo(AuthInfoDO authInfoDO) {
        return authInfoMapper.validateAuthInfo(authInfoDO);
    }

    /*
     * 新增/更新业务权限信息
     * */
    @Override
    public int replaceAuthInfo(AuthInfoDO authInfoDO) {
        return authInfoMapper.replaceAuthInfo(authInfoDO);
    }

}