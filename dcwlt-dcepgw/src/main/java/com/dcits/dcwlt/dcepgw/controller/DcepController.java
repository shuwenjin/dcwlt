package com.dcits.dcwlt.dcepgw.controller;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.dcepgw.utils.DcspMsgUtil;
import com.dcits.dcwlt.dcepgw.utils.RestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
   城银清接入，转发至应用网关
 */
@RestController
public class DcepController {

    @Value("${gateway.server-addr}")
    private String gateway_addr;

    @PostMapping("/dcep")
    public String process(@RequestBody String reqmsg){
        //处理请求报文
        JSONObject jsonObject = null;
        String rspMsg = "";
        try {
            //拆包
            jsonObject = DcspMsgUtil.unPack(reqmsg);

            //发送至应用网关
            rspMsg = RestUtil.getRsp(jsonObject.toJSONString(), gateway_addr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rspMsg;
    }
}
