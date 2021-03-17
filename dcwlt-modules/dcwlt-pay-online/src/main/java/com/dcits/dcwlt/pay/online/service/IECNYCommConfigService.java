package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.pay.api.model.EcnyCommConfigDO;

import java.util.List;

/**
 * @author zhangwang
 * @date  2021/1/2
 * @version 1.0.0
 * <p>ECNY配置参数DAO</p>
 */
public interface IECNYCommConfigService {

    List<EcnyCommConfigDO> getAllConfig();
}
