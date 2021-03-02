package com.dcits.dcwlt.pay.online.controller;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.constant.ApiConstant;
import com.dcits.dcwlt.pay.online.flow.DcepTransInTradeFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 供互联互通调用入口
 */
@RestController
@RequestMapping("/dcep")
public class DcepController {
    @Autowired
    private DcepTransInTradeFlow dcepTransInTradeFlow;

    @GetMapping(ApiConstant.DCEP_SERVICE_NAME)
    public JSONObject list(JSONObject reqMsg)
    {
        return dcepTransInTradeFlow.execute(reqMsg);
    }
}
