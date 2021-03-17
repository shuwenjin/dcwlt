package com.dcits.dcwlt.pay.online.payconvertstsqry;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.Valid;

/**
 * @author zhanguohai
 * @Time 2021/1/3 15:42
 * @Version 1.0
 * Description:五羊支付子应用请求报文
 */
public class ECNYReqDTO<T extends ECNYReqBody> {

    @Valid
    private ECNYReqHead ecnyHead;

    @Valid
    private T body;

    @JSONField(name = "ecnyHead")
    public ECNYReqHead getEcnyHead() {
        return ecnyHead;
    }

    public void setEcnyHead(ECNYReqHead ecnyHead) {
        this.ecnyHead = ecnyHead;
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
        return "ECNYReqDTO{" +
                "ecnyHead=" + ecnyHead +
                ", body=" + body +
                '}';
    }
}