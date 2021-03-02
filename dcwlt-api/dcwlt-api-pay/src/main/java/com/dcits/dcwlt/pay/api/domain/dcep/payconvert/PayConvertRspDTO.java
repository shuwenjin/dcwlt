package com.dcits.dcwlt.pay.api.domain.dcep.payconvert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;


/**
 * 兑出响应
 *
 *
 */
public class PayConvertRspDTO extends DCEPRspBody {

    private PayConvertRsp convertRsp;

    @JSONField(name = "ConvertRsp")
    public PayConvertRsp getConvertRsp() {
        return convertRsp;
    }

    public void setConvertRsp(PayConvertRsp convertRsp) {
        this.convertRsp = convertRsp;
    }

    @Override
    public String toString() {
        return "PayConvertRspDTO{" +
                "payConvertRsp=" + convertRsp +
                '}';
    }
}
