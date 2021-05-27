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

    //查询客户信息 todo 后续增加核心接口是再添加
    //@RequestMapping(value = "/dcwlt/queryCustInfo",method = RequestMethod.POST)
    //public BankCore20130001Rsp queryCustInfo(@RequestBody BankCore20130001Req bankCore20130001Req);


    // 核心冲正
    //@RequestMapping(value = "/dcwlt/bankRev",method = RequestMethod.POST)
    //public BankCore3041000204Rsp bankRev(@RequestBody BankCore3041000204Req bank);


    // 回查核心状态
    //@RequestMapping(value = "/dcwlt/queryCoreStatus",method = RequestMethod.POST)
    //public BankCore30430001Rsp queryCoreStatus(@RequestBody BankCore30430001Req bank);

}
