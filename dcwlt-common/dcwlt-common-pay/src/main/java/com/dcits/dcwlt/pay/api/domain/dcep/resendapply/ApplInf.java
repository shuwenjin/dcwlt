package com.dcits.dcwlt.pay.api.domain.dcep.resendapply;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.enums.ReSendMTEnum;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @date  2021/1/5
 * @version 1.0.0
 * <p>申请信息</p>
 */
public class ApplInf {

    /**
     * 报文编号
     */
    @EnumValue(value = ReSendMTEnum.class, message = "不支持该业务的重发申请")
    @Length(max = 15)
    private String mt;

    /**
     * 报文描述信息
     * 当申请报文为"机构对账汇总核对报文(dcep.711.001.01)"时，填写”交易批次号“或对账日期（ISODate）
     * 当申请报文为"资金调整汇总核对报文(dcep.713.001.01)"时，填写清算日期（ISODate）
     */
    @Length(max = 128)
    private String msgDesc;

    @JSONField(name = "MT")
    public String getMt() {
        return mt;
    }

    @JSONField(name = "MsgDesc")
    public String getMsgDesc() {
        return msgDesc;
    }

    public void setMt(String mt) {
        this.mt = mt;
    }

    public void setMsgDesc(String msgDesc) {
        this.msgDesc = msgDesc;
    }

    @Override
    public String toString() {
        return "ApplInf{" +
                "mt='" + mt + '\'' +
                ", msgDesc='" + msgDesc + '\'' +
                '}';
    }
}
