package com.dcits.dcwlt.pay.online.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.mq.EventProducer;
import com.dcits.dcwlt.common.pay.constant.ApiConstant;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventConfigDO;
import com.dcits.dcwlt.pay.online.flow.DcepTransInTradeFlow;
import com.dcits.dcwlt.pay.online.mapper.MonitorMapper;
import com.dcits.dcwlt.pay.online.service.impl.FreeFormatServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping(value = "/test")
    public String test(@RequestBody JSONObject reqMsg) {
        System.out.println(reqMsg.toJSONString());
        return "{\n" +
                "\t\"sysHead\": {\n" +
                "\t\t\"svcCd\": \"111\",\n" +
                "\t\t\"svcScn\": \"222\"\n" +
                "\t},\n" +
                "\t\"body\": {\n" +
                "\t\t\"nextDate\": \"222\"\n" +
                "\t}\n" +
                "}";
    }

    @Autowired
    private EventProducer eventProducer;

    @GetMapping(value = "/mq")
    public String rocktmq() {
        EventConfigDO config = new EventConfigDO();
        config.setExceptDealMode("1");
        config.setExceptEventCode("2");
        config.setExceptEventDealIntervalMin("3");
        config.setExceptEventDealMaxCount("4");
        eventProducer.sendMsg("queue_test_topic", JSONObject.toJSONString(config));
        return "ok";
    }
}
