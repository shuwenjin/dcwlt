package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.model.AuthInfoDO;

import java.util.List;

public interface IAuthInfoRepository {
    /*
     * 变更业务权限
     * */
    int replaceAuthInfo(AuthInfoDO authInfoDO);


    /*
     * 校验业务权限
     * */
    List<AuthInfoDO> validateAuthInfo(AuthInfoDO authInfoDO);

}
