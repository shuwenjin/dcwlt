package com.dcits.dcwlt.system.api.factory;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.system.api.RemotePayBatchService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付批量服务降级处理
 * 
 * @author zhangyd
 */
@Component
public class RemotePayBatchFallbackFactory implements FallbackFactory<RemotePayBatchService>
{
    private static final Logger log = LoggerFactory.getLogger(RemotePayBatchFallbackFactory.class);

    @Override
    public RemotePayBatchService create(Throwable throwable)
    {
        log.error("支付批量服务调用失败:  ", throwable.getMessage());
        return new RemotePayBatchService()
        {
            @Override
            public void statistics(String reportDate) throws Exception {
                throw new Exception(throwable.getMessage());
            }

            @Override
            public String schedulerController(JSONObject paramObj) {
                return "1";
            }
        };
    }
}
