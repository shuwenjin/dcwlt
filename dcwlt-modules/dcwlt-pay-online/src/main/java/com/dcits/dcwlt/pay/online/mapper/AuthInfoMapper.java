package com.dcits.dcwlt.pay.online.mapper;


import com.dcits.dcwlt.pay.api.model.AccFlowDO;
import com.dcits.dcwlt.pay.api.model.AuthInfoDO;
import com.dcits.dcwlt.pay.api.model.AuthInfoToBeEffectiveDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface AuthInfoMapper {
    /*
     * 变更业务权限
     * */
    int replaceAuthInfo(AuthInfoDO authInfoDO);


    /*
     * 校验业务权限
     * */
    List<AuthInfoDO> validateAuthInfo(AuthInfoDO authInfoDO);

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


    List<AuthInfoDO> selectList(AuthInfoDO authInfoDO);
}
