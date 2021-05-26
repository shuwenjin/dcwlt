package com.dcits.dcwlt.dcepgw.in;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.dcepgw.utils.DcspMsgUtil;
import com.dcits.dcwlt.dcepgw.utils.JsonXmlUtil;
import com.dcits.dcwlt.dcepgw.utils.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/*
   城银清接入，转发至应用网关
 */
@RestController
@Slf4j
public class FromDCEPController {

    @Value("${gateway.server-addr}")
    private String gateway_addr;

    @Value("${CoopBank.InstnId}")
    private String instnId_coopBank;

    @Value("${dcps.InstnId}")
    private String instnId_dcps;

    @PostMapping("/dcep")
    public String process(@RequestBody String reqmsg) {
        //处理请求报文
        String rspMsg = "";
        String msgId = "";
        try {
            //拆包
            JSONObject jsonObject = DcspMsgUtil.unPack(reqmsg);
            msgId = jsonObject.getJSONObject(JsonXmlUtil.HEAD).getString(DcspMsgUtil.HEAD_KEY_ARRAY[10]);
            //发送至应用网关
            rspMsg = RestUtil.getRsp(jsonObject.toJSONString(), gateway_addr);

            //组包
            rspMsg = DcspMsgUtil.pack(rspMsg);
        } catch (Exception e) {
            log.error("转发到应用处理失败！", e);
            //响应默认包
            try {
                JSONObject jsonObject = DcspMsgUtil.get900();
                jsonObject.getJSONObject(JsonXmlUtil.HEAD).put(DcspMsgUtil.HEAD_KEY_ARRAY[1], "04");
                jsonObject.getJSONObject(JsonXmlUtil.HEAD).put(DcspMsgUtil.HEAD_KEY_ARRAY[2], instnId_coopBank);
                jsonObject.getJSONObject(JsonXmlUtil.HEAD).put(DcspMsgUtil.HEAD_KEY_ARRAY[3], "DCPS");
                jsonObject.getJSONObject(JsonXmlUtil.HEAD).put(DcspMsgUtil.HEAD_KEY_ARRAY[4], instnId_dcps);
                jsonObject.getJSONObject(JsonXmlUtil.HEAD).put(DcspMsgUtil.HEAD_KEY_ARRAY[5], "DCPS");
                jsonObject.getJSONObject(JsonXmlUtil.HEAD).put(DcspMsgUtil.HEAD_KEY_ARRAY[6], DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN));
                jsonObject.getJSONObject(JsonXmlUtil.HEAD).put(DcspMsgUtil.HEAD_KEY_ARRAY[7], DateUtil.format(new Date(), DatePattern.PURE_TIME_PATTERN));
                jsonObject.getJSONObject(JsonXmlUtil.HEAD).put(DcspMsgUtil.HEAD_KEY_ARRAY[8], "XML");
                jsonObject.getJSONObject(JsonXmlUtil.HEAD).put(DcspMsgUtil.HEAD_KEY_ARRAY[10], msgId);
                jsonObject.getJSONObject(JsonXmlUtil.HEAD).put(DcspMsgUtil.HEAD_KEY_ARRAY[12], "3");
                jsonObject.getJSONObject(JsonXmlUtil.HEAD).put(DcspMsgUtil.HEAD_KEY_ARRAY[13], "U");
                rspMsg = DcspMsgUtil.pack(jsonObject.toJSONString());
            } catch (Exception exception) {
                log.error("组装默认响应包失败！", e);
            }
        }
        return rspMsg;
    }
}
