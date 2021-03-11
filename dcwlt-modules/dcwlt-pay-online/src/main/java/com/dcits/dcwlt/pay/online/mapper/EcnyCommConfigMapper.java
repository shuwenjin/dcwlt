package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.EcnyCommConfigDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhangwang
 * @date  2021/1/2
 * @version 1.0.0
 * <p>ECNY配置参数DAO</p>
 */
@Mapper
public interface EcnyCommConfigMapper {
    List<EcnyCommConfigDO> getAllConfig();
}
