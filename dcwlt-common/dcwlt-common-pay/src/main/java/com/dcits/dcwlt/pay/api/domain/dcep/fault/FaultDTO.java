package com.dcits.dcwlt.pay.api.domain.dcep.fault;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;


import javax.validation.Valid;

/**
 * @desc 报文丢弃响应体
 *
 *
 *
 */
public class FaultDTO extends DCEPRspBody {
    @Valid
    private Fault fault;

    @JSONField(name = "Fault")
    public Fault getFault() {
        return fault;
    }

    public void setFault(Fault fault) {
        this.fault = fault;
    }

    @Override
    public String toString() {
        return "FaultDTO{" +
                "fault=" + fault +
                '}';
    }
}
