package com.dcits.dcwlt.pay.online.controller;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.constant.ApiConstant;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;
import com.dcits.dcwlt.pay.api.domain.dcep.authinfo.AuthrtyChngNtfctn;
import com.dcits.dcwlt.pay.online.flow.DcepTransInTradeFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping(value = ApiConstant.DCEP_SERVICE_NAME,produces = {"application/json;charset=UTF-8"})
    public JSONObject list(@RequestBody JSONObject reqMsg) {
        return dcepTransInTradeFlow.execute(reqMsg);
    }
}
