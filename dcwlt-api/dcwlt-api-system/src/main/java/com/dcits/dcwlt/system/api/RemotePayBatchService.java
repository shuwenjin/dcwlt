package com.dcits.dcwlt.system.api;

import com.dcits.dcwlt.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 支付批量服务
 *
 * @author zhangyd
 */
@FeignClient(ServiceNameConstants.PAY_BATCH_SERVICE)
public interface RemotePayBatchService {

    @PostMapping("/reportdata/statistics")
    public void statistics(@RequestParam("reportDate")  String reportDate) throws Exception;

}