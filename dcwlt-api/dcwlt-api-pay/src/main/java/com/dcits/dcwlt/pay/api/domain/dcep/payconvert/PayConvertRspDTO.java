package com.dcits.dcwlt.pay.api.domain.dcep.payconvert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;
import com.dcits.dcwlt.pay.api.domain.dcep.convert.ConvertRsp;


/**
 * 兑出响应
 *
 *
 */
public class PayConvertRspDTO extends DCEPRspBody {

    private ConvertRsp convertRsp;

    @JSONField(name = "ConvertRsp")
    public ConvertRsp getConvertRsp() {
        return convertRsp;
    }

    public void setConvertRsp(ConvertRsp convertRsp) {
        this.convertRsp = convertRsp;
    }

    @Override
    public String toString() {
        return "PayConvertRspDTO{" +
                "payConvertRsp=" + convertRsp +
                '}';
    }
}
