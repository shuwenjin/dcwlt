package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.pay.api.model.EcnyCommConfigDO;
import com.dcits.dcwlt.pay.online.service.IECNYCommConfigRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangwang
 * @date  2021/1/2
 * @version 1.0.0
 * <p>ECNY配置参数DAO</p>
 */
@Repository
public class ECNYCommConfigRepository implements IECNYCommConfigRepository {

    private static final String sqlId = "encyCommConfigMapper.selectAll";

    @Override
    public List<EcnyCommConfigDO> getAllConfig() {
//        return DBUtil.selectList(sqlId,null);
        //todo
        //这里需要从库里取数据
        EcnyCommConfigDO ecnyCommConfigDO = new EcnyCommConfigDO();
        ecnyCommConfigDO.setPamCode("BIZTP");
        ecnyCommConfigDO.setPamKey("C201");
        ecnyCommConfigDO.setPamValue("03011");
        ecnyCommConfigDO.setStatus("1");
        return new ArrayList(){{
            add(ecnyCommConfigDO);
        }};
    }
}
