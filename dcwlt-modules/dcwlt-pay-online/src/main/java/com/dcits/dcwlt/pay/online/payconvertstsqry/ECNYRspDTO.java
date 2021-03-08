package com.dcits.dcwlt.pay.online.payconvertstsqry;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author zhanguohai
 * @Time 2021/1/3 15:51
 * @Version 1.0
 * Description:五羊支付响应报文体
 */
public class ECNYRspDTO<T extends ECNYRspBody> {

    private ECNYRspHead ecnyRspHead;
    private PayConvertStsQryRspDTO body;


    /**
     * 响应渠道使用
     *
     * @param ecnyReqDTO  原请求报文
     * @param ecnyRspHead 响应报文头
     * @param body        报文体
     */
    public static <T extends ECNYRspBody> ECNYRspDTO<T> newInstance(
            ECNYReqDTO ecnyReqDTO, ECNYRspHead ecnyRspHead, T body) {
        ECNYRspDTO<T> msg = new ECNYRspDTO<>();
        //设置响应服务化报文头
//        Head rspHead = ecnyReqDTO.getHead();
//        rspHead.setRetCode("000000");
//        rspHead.setRetInfo("交易成功");
//        rspHead.setTranDate(DateTimeUtil.getCurrentDateStr());
//        rspHead.setTranTime(DateTimeUtil.getCurrentTimeStr());
//        msg.setHead(rspHead);
        msg.setEcnyRspHead(ecnyRspHead);
        msg.setBody(body);
        return msg;
    }

    private void setBody(T body) {
    }

    /**
     * 响应渠道使用
     *
     * @param ecnyReqDTO  原请求报文
     * @param ecnyRspHead 响应报文头
     * @param body        报文体
     */
    public static <T extends ECNYRspBody> ECNYRspDTO<T> newInstance(
            ECNYReqDTO ecnyReqDTO, ECNYRspHead ecnyRspHead, PayConvertStsQryRspDTO body, String retCode, String retInfo) {
        ECNYRspDTO<T> msg = new ECNYRspDTO<>();
        //设置响应服务化报文头
//        Head rspHead = ecnyReqDTO.getHead();
//        rspHead.setRetCode(retCode);
//        rspHead.setRetInfo(retInfo);
//        rspHead.setTranDate(DateTimeUtil.getCurrentDateStr());
//        rspHead.setTranTime(DateTimeUtil.getCurrentTimeStr());
//        msg.setHead(rspHead);
        msg.setEcnyRspHead(ecnyRspHead);
        msg.setBody(body);
        return msg;
    }

    public ECNYRspDTO() {
    }

    @JSONField(name = "ecnyHead")
    public ECNYRspHead getEcnyRspHead() {
        return ecnyRspHead;
    }

    public void setEcnyRspHead(ECNYRspHead ecnyRspHead) {
        this.ecnyRspHead = ecnyRspHead;
    }

    @JSONField(name = "body")
    public PayConvertStsQryRspDTO getBody() {
        return body;
    }

    public void setBody(PayConvertStsQryRspDTO body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ECNYRspDTO{" +
                "ecnyRspHead=" + ecnyRspHead +
                ", body=" + body +
                '}';
    }
}
