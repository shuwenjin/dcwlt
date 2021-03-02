package com.dcits.dcwlt.pay.api.domain.dcep;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.BaseRespDto;


/**
 *
 * @Time 2020/12/31 20:48
 * @Version 1.0
 * Description:互联互通来账响应报文
 */
public class DCEPRspDTO<T extends DCEPRspBody> extends BaseRespDto {

    /**
     * 互联互通报文头
     */
    private DCEPHeader dcepHead;

    /**
     * 互联互通报文体
     */
    private T body;

    /**
     * @param dcepHead 互联互通报文头
     * @param body     报文体
     */
    public static <T extends DCEPRspBody> DCEPRspDTO<T> newInstance(
            DCEPHeader dcepHead, T body) {
        DCEPRspDTO<T> msg = new DCEPRspDTO<>();
        msg.setDcepHead(dcepHead);
        msg.setBody(body);
        return msg;
    }

    /**
     * 响应互联互通使用
     *
     * @param dcepReqDTO 原请求报文
     * @param msgTp      响应报文编号
     * @param body       报文体
     */
    public static <T extends DCEPRspBody> DCEPRspDTO<T> newInstance(
            DCEPReqDTO dcepReqDTO, String msgTp, T body) {
        DCEPRspDTO<T> msg = new DCEPRspDTO<>();
        //设置业务报文头
        DCEPHeader dcepReqHeader = dcepReqDTO.getDcepHead();
        DCEPHeader dcepRspHeader = DCEPHeader.getDefaultRspHead(dcepReqHeader, msgTp);
        msg.setDcepHead(dcepRspHeader);
        msg.setBody(body);
        return msg;
    }

    /**
     * json对象转实体
     *
     * @param rspMsg 响应报文
     * @return
     */
    public static <T extends DCEPRspBody> DCEPRspDTO<T> jsonToDCEPRspDTO(JSONObject rspMsg, Class<T> clazz) {
        DCEPHeader dcepHeader = JSONObject.toJavaObject(rspMsg.getJSONObject("ecnyHead"), DCEPHeader.class);    //互联互通报文头json对象-->DCEPHeader实体
        DCEPRspBody body = (DCEPRspBody) JSONObject.toJavaObject(rspMsg.getJSONObject("body"), clazz);//互联互通报文体json对象-->DCEPReqBody实体
        return (DCEPRspDTO<T>) DCEPRspDTO.newInstance(dcepHeader, body);
    }

    @JSONField(name = "ecnyHead")
    public DCEPHeader getDcepHead() {
        return dcepHead;
    }

    public void setDcepHead(DCEPHeader dcepHead) {
        this.dcepHead = dcepHead;
    }


    @JSONField(name = "body")
    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return "DCEPRspDTO{ dcepHead=" + dcepHead +
                ", body=" + body +
                '}';
    }
}
