package com.dcits.dcpsclient.controller;

import com.dcits.dcpsclient.util.MsgUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Ashin
 * @Description TODO
 * @Date 2021/4/13 21:50
 * @Version 1.0
 */
@RestController
public class ReceiveMsgController {


    /**
     * 请求服务编码，对应的响应编码
     **/
    private Map<String, String> requestResponseMap = new HashMap() {
        {

            put("dcep.401.001.01.xml", "dcep.902.001.01.xml");
        }
    };

    @PostMapping("/dcps")
    public String doService(@RequestBody String reqMsg) {
        System.out.println("接收到的请求报文: " + reqMsg);

        //获取服务编码
        String msgType = reqMsg.substring(58, 73) + ".xml";

        String rspMsg = MsgUtil.getRspMsg(requestResponseMap.get(msgType));

        System.out.println("返回报文:" + rspMsg);

        return rspMsg;
    }
}
