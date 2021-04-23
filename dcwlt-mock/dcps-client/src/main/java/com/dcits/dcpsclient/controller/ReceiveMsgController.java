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
            put("dcep.227.001.01.xml", "dcep.228.001.01.xml");
            put("dcep.411.001.01.xml", "dcep.412.001.01.xml");
            put("dcep.417.001.01.xml", "dcep.418.001.01.xml");
            put("dcep.801.001.01.xml", "dcep.802.001.01.xml");
            put("dcep.902.001.01.xml", "dcep.902.001.01.xml");
            put("dcep.920.001.01.xml", "dcep.900.001.01.xml");
            put("dcep.933.001.01.xml", "dcep.934.001.01.xml");
        }
    };

    @PostMapping("/dcps")
    public String doService(@RequestBody String reqMsg) {
        System.out.println("接收到的请求报文: " + reqMsg);

        //获取服务编码
        String requestMsgType = reqMsg.substring(58, 73) + ".xml";

        String responesMsgType = requestResponseMap.get(requestMsgType);
        if (responesMsgType == null) {
            // 如果找不到合适的返回报文，则返回丢弃通知报文
            responesMsgType = "dcep.911.001.01.xml";
        }


        String rspMsg = MsgUtil.getRspMsg(responesMsgType);

        System.out.println("返回报文:" + rspMsg);

        return rspMsg;
    }
}
