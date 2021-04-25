package com.dcits.dcwlt.dcepgw.out;

import com.dcits.dcwlt.dcepgw.utils.DcspMsgUtil;
import com.dcits.dcwlt.dcepgw.utils.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
  应用接入，转发至城银清
 */
@RestController
@Slf4j
public class ToDCEPController {

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
            rspMsg = RestUtil.getRsp(msg, decp_addr);

            //拆包
            rspMsg = DcspMsgUtil.unPack(rspMsg).toJSONString();
        } catch (Exception e) {
            log.error("请求城银清异常！",e);
            //组错误响应
            rspMsg = DcspMsgUtil.get900().toJSONString();
        }

        return rspMsg;
    }

}
