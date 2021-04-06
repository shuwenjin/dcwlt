package com.dcits.dcwlt.pay.online.service.impl;


import ch.qos.logback.core.db.dialect.DBUtil;
import com.dcits.dcwlt.pay.api.model.EcnyCommConfigDO;
import com.dcits.dcwlt.pay.online.mapper.EcnyCommConfigMapper;
import com.dcits.dcwlt.pay.online.service.IECNYCommConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @date  2021/1/2
 * @version 1.0.0
 * <p>ECNY配置参数DAO</p>
 */
@Service
public class ECNYCommConfigServiceimpl implements IECNYCommConfigService {
    @Autowired
    private EcnyCommConfigMapper ecnyCommConfigMapper;
    @Override
    public List<EcnyCommConfigDO> getAllConfig() {
        return ecnyCommConfigMapper.getAllConfig();
    }
}
