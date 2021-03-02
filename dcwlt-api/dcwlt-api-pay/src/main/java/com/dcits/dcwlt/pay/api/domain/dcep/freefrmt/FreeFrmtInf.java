package com.dcits.dcwlt.pay.api.domain.dcep.freefrmt;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 *
 * @Time 2021/1/1 12:57
 * @Version 1.0
 * Description:自由格式信息
 */
public class FreeFrmtInf {
    @NotBlank(message = "消息内容不能为空")
    @Length(max = 1024)
    private String msgCnt;      //信息内容

    public FreeFrmtInf() {
    }

    public FreeFrmtInf(String msgCnt) {
        this.msgCnt = msgCnt;
    }

    @JSONField(name = "MsgCnt")
    public String getMsgCnt() {
        return msgCnt;
    }

    public void setMsgCnt(String msgCnt) {
        this.msgCnt = msgCnt;
    }

    @Override
    public String toString() {
        return "FreeFrmtInf{" +
                "msgCnt='" + msgCnt + '\'' +
                '}';
    }
}
