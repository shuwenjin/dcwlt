package com.dcits.dcwlt.pay.api.domain.dcep.reconvert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;


import javax.validation.Valid;

/**
 * 兑回业务请求报文
 *
 */
public class ReconvertReqDTO extends DCEPReqBody {

    @Valid
    private ReconvertReq reconvertReq;

    @JSONField(name = "ReconvertReq")
    public ReconvertReq getReconvertReq() {
        return reconvertReq;
    }

    public void setReconvertReq(ReconvertReq reconvertReq) {
        this.reconvertReq = reconvertReq;
    }

    @Override
    public String toString() {
        return "ReconvertReqDTO{" +
                "reconvertReq=" + reconvertReq +
                '}';
    }
}
