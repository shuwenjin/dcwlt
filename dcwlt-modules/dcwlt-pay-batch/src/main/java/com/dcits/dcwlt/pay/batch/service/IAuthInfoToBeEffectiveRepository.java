package com.dcits.dcwlt.pay.batch.service;

import com.dcits.dcwlt.pay.api.model.AuthInfoToBeEffectiveDO;

import java.util.List;

public interface IAuthInfoToBeEffectiveRepository {
    /*
     * 变更业务权限临时
     * */
    int replaceAuthInfoToBeEffective(AuthInfoToBeEffectiveDO authInfoToBeEffectiveDO);

    /*
     * 查询业务权限临时信息
     * */
    List<AuthInfoToBeEffectiveDO> queryAuthInfo(AuthInfoToBeEffectiveDO authInfoToBeEffectiveDO);

    /*
     * 删除业务权限临时信息
     * */
    int deleteAuthInfo(AuthInfoToBeEffectiveDO authInfoToBeEffectiveDO);
}
