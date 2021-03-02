package com.dcits.dcwlt.pay.api.domain.dcep.dspt;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;


import javax.validation.Valid;

/**
 * 差错调账请求报文
 */
public class DsptReqDTO extends DCEPReqBody {

    @Valid
    private DsptReq dsptReq;

    public DsptReqDTO() {
    }

    @JSONField(name = "DsptReq")
    public DsptReq getDsptReq() {
        return dsptReq;
    }

    public void setDsptReq(DsptReq dsptReq) {
        this.dsptReq = dsptReq;
    }

    @Override
    public String toString() {
        return "DsptReqDTO{" +
                "dsptReq=" + dsptReq +
                '}';
    }
}
