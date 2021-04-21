package com.dcits.dcwlt.dcepgw.in;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.dcepgw.utils.DcspMsgUtil;
import com.dcits.dcwlt.dcepgw.utils.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
   城银清接入，转发至应用网关
 */
@RestController
@Slf4j
public class FromDCEPController {

    @Value("${gateway.server-addr}")
    private String gateway_addr;

    @PostMapping("/dcep")
    public String process(@RequestBody String reqmsg){
        //处理请求报文
        String rspMsg = "";
        try {
            //拆包
            JSONObject jsonObject = DcspMsgUtil.unPack(reqmsg);

            //发送至应用网关
            rspMsg = RestUtil.getRsp(jsonObject.toJSONString(), gateway_addr);

            //组包
            rspMsg = DcspMsgUtil.pack(rspMsg);
        } catch (Exception e) {
            log.error("转发到应用处理失败！",e);
        }
        return rspMsg;
    }
}
