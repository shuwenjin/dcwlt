package com.dcits.dcwlt.dcepgw.controller;

import com.dcits.dcwlt.dcepgw.utils.DcspMsgUtil;
import com.dcits.dcwlt.dcepgw.utils.RestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
  接入，转发至城银清
 */
@RestController
public class GatewayController {

    @Value("${decp.server-addr}")
    private String decp_addr;

    @PostMapping("/dcepgw")
    public String process(@RequestBody String reqmsg){
        //处理请求报文
        String rspMsg = "";
        System.out.println(reqmsg);
        try {
            //组包
            String msg = DcspMsgUtil.pack(reqmsg);
            //发送至城银清
            rspMsg = RestUtil.getRsp(reqmsg, decp_addr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rspMsg;
    }

}
