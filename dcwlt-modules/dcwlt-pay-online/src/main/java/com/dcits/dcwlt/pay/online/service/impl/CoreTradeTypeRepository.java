package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.pay.api.model.CoreTradeTypeDO;
import com.dcits.dcwlt.pay.online.mapper.CoreTradeTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanguohai
 * @Time 2020/10/14 15:24
 * @Version 1.0
 * Description:核算规则配置表数据访问组件
 */
@Repository
public class CoreTradeTypeRepository {

    @Autowired
    private CoreTradeTypeMapper coreTradeTypeMapper;

    /**
     * 获取核心会计参数
     *
     * @param payPath
     * @param acctMeth
     * @return
     */
    public List<CoreTradeTypeDO> query(String payPath, String acctMeth) {
        Map<String, Object> param = new HashMap<>();
        param.put("payPath", payPath);
        param.put("acctMeth", acctMeth);
        return coreTradeTypeMapper.queryByAccMeth(param);
    }

}
