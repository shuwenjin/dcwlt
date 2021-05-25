package com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt;


import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqBody;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;

/**
 * @author
 * @Time 2021/1/3 19:08
 * @Version 1.0
 * Description:自由格式报文服务化接口请求
 */
public class FreeFrmtReqDTO extends ECNYReqBody {
    @Length(max = 14)
    @Valid
    private String instdDrctPty;            //接收机构

    @Length(max = 1024)
    @Valid
    private String msgContext;              //消息内容

    @Length(max = 16)
    private String tlrNO;                   //柜员号

    @JSONField(name = "InstdDrctPty")
    public String getInstdDrctPty() {
        return instdDrctPty;
    }

    public void setInstdDrctPty(String instdDrctPty) {
        this.instdDrctPty = instdDrctPty;
    }

    @JSONField(name = "MsgContext")
    public String getMsgContext() {
        return msgContext;
    }

    public void setMsgContext(String msgContext) {
        this.msgContext = msgContext;
    }

    @JSONField(name = "TlrNO")
    public String getTlrNO() {
        return tlrNO;
    }

    public void setTlrNO(String tlrNO) {
        this.tlrNO = tlrNO;
    }

    @Override
    public String toString() {
        return "FreeFrmtReqDTO{" +
                "instdDrctPty='" + instdDrctPty + '\'' +
                ", msgContext='" + msgContext + '\'' +
                ", tlrNO='" + tlrNO + '\'' +
                '}';
    }
}
