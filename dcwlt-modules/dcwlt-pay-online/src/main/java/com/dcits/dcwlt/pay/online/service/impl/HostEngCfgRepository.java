package com.dcits.dcwlt.pay.online.service.impl;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.dcits.dcwlt.pay.online.mapper.HostMapCfgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @Time 2020/10/14 16:08
 * @Version 1.0
 * Description:核心会计引擎方式配置数据层访问组件
 */
@Repository
public class HostEngCfgRepository {

    private static final String QUERY_HOST_ENG_CFG_SQL = "hostMapCfgMapper.query";
    @Autowired
    private HostMapCfgMapper hostMapCfgMapper;

    /**
     * 获取会计引擎方式
     *
     * @param serverId
     * @param acctMeth
     * @return
     */
    public String query(String serverId, String acctMeth) {
        Map<String, Object> selMap = new HashMap<>();
        selMap.put("serverId", serverId);
        selMap.put("acctMeth", acctMeth);
        return hostMapCfgMapper.query(selMap);
    }
}
