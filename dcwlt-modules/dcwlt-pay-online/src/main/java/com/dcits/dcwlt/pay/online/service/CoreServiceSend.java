package com.dcits.dcwlt.pay.online.service;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 核心服务调用
 * 
 * @author ruoyi
 */
@FeignClient(name="coreservice",url = ServiceNameConstants.CORE_PATH_SERVICE)
public interface CoreServiceSend
{
    /**
     * 调用核心接口（模拟）
     *
     * @param
     * @return 结果
     */
    @GetMapping(value = "/core/sendBack/{tryId}")
    JSONObject result(@PathVariable("tryId") String tryId);
}
