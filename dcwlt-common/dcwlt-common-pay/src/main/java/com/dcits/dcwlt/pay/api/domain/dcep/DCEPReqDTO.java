package com.dcits.dcwlt.pay.api.domain.dcep;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.pay.api.domain.BaseReqDto;

import javax.validation.Valid;

/**
 *
 * @Time 2020/12/31 20:48
 * @Version 1.0
 * Description:互联互通来账请求报文
 */
public class DCEPReqDTO<T extends DCEPReqBody> extends BaseReqDto {

    /**
     * 互联互通报文头
     */
    @Valid
    private DCEPHeader dcepHead;

    /**
     * 互联互通报文体
     */
    @Valid
    private T body;


    /**
     * 来账请求互联互通时使用
     *
     * @param dcepHead 互联互通报文头
     * @param body     报文体
     */
    public static <T extends DCEPReqBody> DCEPReqDTO<T> newInstance(DCEPHeader dcepHead, T body) {
        DCEPReqDTO<T> msg = new DCEPReqDTO<>();
        msg.setDcepHead(dcepHead);
        msg.setBody(body);
        return msg;
    }


    /**
     * 请求互联互通使用
     *
     * @param msgSn 请求报文标识号
     * @param msgTp 请求报文编号
     * @param body  报文体
     */
    public static <T extends DCEPReqBody> DCEPReqDTO<T> newInstance(String msgTp, String msgSn, String receiver, T body) {
        DCEPHeader dcepHeader = DCEPHeader.getDefaultReqHead(msgSn, receiver, msgTp);   //获取请求报文头
        return newInstance(msgTp, dcepHeader, body);
    }

    /**
     * 请求互联互通使用
     *
     * @param msgSn 请求报文标识号
     * @param msgTp 请求报文编号
     * @param body  报文体
     */
    public static <T extends DCEPReqBody> DCEPReqDTO<T> newInstance(String msgTp, String msgSn, T body) {
        DCEPHeader dcepHeader = DCEPHeader.getDefaultReqHead(msgSn, AppConstant.DCEP_FINANCIAL_INSTITUTION_CD, msgTp);   //获取请求报文头
        return newInstance(msgTp, dcepHeader, body);
    }


    /**
     * 请求互联互通使用
     *
     * @param dcepHeader 请求报文头
     * @param msgTp      请求报文编号
     * @param body       报文体
     */
    public static <T extends DCEPReqBody> DCEPReqDTO<T> newInstance(String msgTp, DCEPHeader dcepHeader, T body) {
        //返回互联互通请求报文
        return newInstance(dcepHeader, body);
    }

    @JSONField(name = AppConstant.DCEP_HEAD)
    public DCEPHeader getDcepHead() {
        return dcepHead;
    }

    public void setDcepHead(DCEPHeader dcepHead) {
        this.dcepHead = dcepHead;
    }


    @JSONField(name = AppConstant.DCEP_BODY)
    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return "DCEPReqDTO{ " +
                ", dcepHead=" + dcepHead +
                ", body=" + body +
                '}';
    }
}
