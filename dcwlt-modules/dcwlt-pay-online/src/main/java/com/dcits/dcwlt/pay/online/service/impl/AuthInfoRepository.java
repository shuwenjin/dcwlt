package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.pay.api.model.AuthInfoDO;
import com.dcits.dcwlt.pay.online.service.IAuthInfoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthInfoRepository implements IAuthInfoRepository {
    private static final String VALIDATE_AUTH_INFO_SQL = "authInfoMapper.validateAuthInfo";
    private static final String REPLACE_AUTH_INFO_SQL = "authInfoMapper.replaceAuthInfo";

    private static final String TRANSFORM_AUTHINFO_SQL = "authInfoMapper.transformAuthInfo";

    /*
     * partyId:机构编码
     * msgType:报文编号
     * */
    @Override
    public List<AuthInfoDO> validateAuthInfo(AuthInfoDO authInfoDO) {
//        return DBUtil.selectList(VALIDATE_AUTH_INFO_SQL, authInfoDO);
        //todo
        //先把数值写死，后面从库里查
        authInfoDO.setStatus("1");
        authInfoDO.setMsgType("dcep.221.001.01");
        authInfoDO.setPartyId("132");
        authInfoDO.setTradeCtgyCode("1");
        authInfoDO.setEffectDate("20210101");
        authInfoDO.setInEffectiveDate("20220101");
        authInfoDO.setLastUpDate("20210101");
        authInfoDO.setLastUpTime("142356");
        authInfoDO.setRecvAuth("1");
        authInfoDO.setSendAuth("AS00");
        return new ArrayList(){{
            add(authInfoDO);
        }};
    }

    /*
     * 新增/更新业务权限信息
     * */
    @Override
    public int replaceAuthInfo(AuthInfoDO authInfoDO) {
//        return DBUtil.update(REPLACE_AUTH_INFO_SQL, authInfoDO);
        return 1;
    }

}