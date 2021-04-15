package com.dcits.dcwlt.system.api;

import com.dcits.dcwlt.common.core.constant.ServiceNameConstants;
import com.dcits.dcwlt.system.api.factory.RemotePayBatchFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 支付批量服务
 *
 * @author zhangyd
 */
@FeignClient(contextId = "remotePayBatchService", value = ServiceNameConstants.PAY_BATCH_SERVICE, fallbackFactory = RemotePayBatchFallbackFactory.class)
public interface RemotePayBatchService {

    @PostMapping("/reportdata/statistics")
    public void statistics(@RequestParam("reportDate")  String reportDate) throws Exception;

    @PostMapping("/schedulerController")
    public String schedulerController(
            @RequestParam("settleDate")  String settleDate,
            @RequestParam("batchId")  String batchId,
            @RequestParam("serviceName")  String serviceName);

}