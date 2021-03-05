package com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

/**
 * @author wanyangwei
 * @desc 自由格式查询结果集
 */
public class FreeFrmtResultDTO {
    @Length(max = 32)
    private String paySerNO;      //平台流水

    @Length(max = 10)
    private String payTime;       //平台时间

    @Length(max = 35)
    private String msgId;         //报文标识号

    @Length(max = 14)
    private String instgDrctPty;  //发起机构

    @Length(max = 14)
    private String instdDrctPty;  //接收机构

    @Length(max = 1024)
    private String messageContext; //信息内容

    @JSONField(name = "PaySerNO")
    public String getPaySerNO() {
        return paySerNO;
    }

    public void setPaySerNO(String paySerNO) {
        this.paySerNO = paySerNO;
    }

    @JSONField(name = "PayTime")
    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    @JSONField(name = "MsgId")
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @JSONField(name = "InstgDrctPty")
    public String getInstgDrctPty() {
        return instgDrctPty;
    }

    public void setInstgDrctPty(String instgDrctPty) {
        this.instgDrctPty = instgDrctPty;
    }

    @JSONField(name = "InstdDrctPty")
    public String getInstdDrctPty() {
        return instdDrctPty;
    }

    public void setInstdDrctPty(String instdDrctPty) {
        this.instdDrctPty = instdDrctPty;
    }

    @JSONField(name = "MessageContext")
    public String getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
    }

    @Override
    public String toString() {
        return "FreeFrmtSult{" +
                ", paySerNO='" + paySerNO + '\'' +
                ", payTime='" + payTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", instgDrctPty='" + instgDrctPty + '\'' +
                ", instdDrctPty='" + instdDrctPty + '\'' +
                ", messageContext='" + messageContext + '\'' +
                '}';
    }
}
