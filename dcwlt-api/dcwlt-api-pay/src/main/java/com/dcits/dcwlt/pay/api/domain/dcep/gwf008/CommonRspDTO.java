package com.dcits.dcwlt.pay.api.domain.dcep.gwf008;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.pay.api.domain.BaseRespDto;

/**
 * 开放平台响应报文
 *
 * @author majun
 * @date 2021/1/7
 */
public class CommonRspDTO<T extends CommonRspBody> extends BaseRespDto {
    private T body;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }


    /**
     * json对象转实体
     *
     * @param rspMsg 响应报文
     * @return
     */
    public static <T extends CommonRspBody> CommonRspDTO<T> jsonToDCEPRspDTO(JSONObject rspMsg, Class<T> clazz) {
        //Head head = JSONObject.toJavaObject(rspMsg.getJSONObject("head"), Head.class);      //服务化报文json对象-->Head实体
        CommonRspBody body = JSONObject.toJavaObject(rspMsg.getJSONObject("body"), clazz);//互联互通报文体json对象-->DCEPReqBody实体
        return (CommonRspDTO<T>) CommonRspDTO.newInstance(body);
    }

    public static <T extends CommonRspBody> CommonRspDTO<T> newInstance(T body) {
        CommonRspDTO<T> commonRspDTO = new CommonRspDTO<>();
        //commonRspDTO.setHead(head);
        commonRspDTO.setBody(body);

        return commonRspDTO;
    }

    @Override
    public String toString() {
        return "CommonRspDTO{" +
                "body=" + body +
                '}';
    }
}
