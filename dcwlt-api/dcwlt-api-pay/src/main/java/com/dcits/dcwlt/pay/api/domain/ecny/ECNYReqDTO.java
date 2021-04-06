package com.dcits.dcwlt.pay.api.domain.ecny;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.BaseReqDto;

import javax.validation.Valid;

/**
 * @author
 * @Time 2021/1/3 15:42
 * @Version 1.0
 * Description:五羊支付子应用请求报文
 */
public class ECNYReqDTO<T extends ECNYReqBody> extends BaseReqDto {

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
