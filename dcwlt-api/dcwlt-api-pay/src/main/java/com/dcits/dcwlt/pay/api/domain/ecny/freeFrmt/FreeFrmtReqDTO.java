package com.dcits.dcwlt.pay.api.domain.ecny.freeFrmt;


import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqBody;

/**
 * @author zhanguohai
 * @Time 2021/1/3 19:08
 * @Version 1.0
 * Description:自由格式报文服务化接口请求
 */
public class FreeFrmtReqDTO extends ECNYReqBody {
    private String receiver;        //接收方机构
    private String msgCnt;          //自由格式信息


    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public FreeFrmtReqDTO() {
    }

    public String getMsgCnt() {
        return msgCnt;
    }

    public void setMsgCnt(String msgCnt) {
        this.msgCnt = msgCnt;
    }

    @Override
    public String toString() {
        return "FreeFrmtReqDTO{" +
                "receiver='" + receiver + '\'' +
                "msgCnt='" + msgCnt + '\'' +
                '}';
    }
}
