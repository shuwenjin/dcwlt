package com.dcits.dcwlt.pay.api.model;

/**
 *
 * @Time 2021/1/2 14:59
 * @Version 1.0
 * Description:幂等控制表
 */
public class IdempotentDO {
    private String msgId;       //报文标识号
    private String msgTp;       //报文编号
    private String sender;      //发送机构
    private String receiver;    //接收机构
    private String sndDtTm;     //报文发送时间
    private String direct;      //方向
    private String hostname;    //主机名

    public IdempotentDO() {
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgTp() {
        return msgTp;
    }

    public void setMsgTp(String msgTp) {
        this.msgTp = msgTp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSndDtTm() {
        return sndDtTm;
    }

    public void setSndDtTm(String sndDtTm) {
        this.sndDtTm = sndDtTm;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public String toString() {
        return "IdempotentDO{" +
                "msgId='" + msgId + '\'' +
                ", msgTp='" + msgTp + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", sndDtTm='" + sndDtTm + '\'' +
                ", direct='" + direct + '\'' +
                ", hostname='" + hostname + '\'' +
                '}';
    }
}
