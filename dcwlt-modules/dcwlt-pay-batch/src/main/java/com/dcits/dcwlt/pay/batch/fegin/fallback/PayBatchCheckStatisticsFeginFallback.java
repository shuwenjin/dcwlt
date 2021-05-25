package com.dcits.dcwlt.pay.batch.fegin.fallback;//package com.dcits.dcwlt.pay.batch.fegin.fallback;

import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.cashboxWarning.EcnyCashboxWarningRspDTO;
import com.dcits.dcwlt.pay.batch.fegin.PayBatchCheckStatisticsFegin;
import feign.hystrix.FallbackFactory;

import java.util.Map;

public class PayBatchCheckStatisticsFeginFallback implements FallbackFactory<PayBatchCheckStatisticsFegin> {
    @Override
    public PayBatchCheckStatisticsFegin create(Throwable throwable) {
        return new PayBatchCheckStatisticsFegin(){
            @Override
            public AjaxResult loginOut(String opType) {
                return AjaxResult.error(999,"连接超时");
            }

            @Override
            public ECNYRspDTO sendCashbox(Map map) {
                return null;
            }

            @Override
            public ECNYRspDTO<EcnyCashboxWarningRspDTO> sendCashboxWarning(Map map) {
                return null;
            }

            @Override
            public AjaxResult chckReq(String requestNodeName) {
                return AjaxResult.error(999,"连接超时");
            }

        };
    }
}
