package com.dcits.dcwlt.pay.batch.fegin;//package com.dcits.dcwlt.pay.batch.fegin;

import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.cashbox.EcnyCashboxRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.cashboxWarning.EcnyCashboxWarningRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("dcwlt-pay-online")
public interface PayBatchCheckStatisticsFegin {
    @RequestMapping(value = "/dcwlt/sendCashbox",method = RequestMethod.POST)
    public ECNYRspDTO<EcnyCashboxRspDTO> sendCashbox(@RequestBody Map map);


    @RequestMapping(value = "/dcwlt/sendCashboxWarning",method = RequestMethod.POST)
    public ECNYRspDTO<EcnyCashboxWarningRspDTO> sendCashboxWarning(@RequestBody Map map);

    @RequestMapping(value = "/dcwlt/chckReq",method = RequestMethod.GET)
    public AjaxResult chckReq(@RequestParam(value="requestNodeName") String requestNodeName);

    @RequestMapping(value = "/dcwlt/loginoutFmtMsgSnd",method = RequestMethod.GET)
    public AjaxResult loginOut(@RequestParam(value="opType") String opType);
}
