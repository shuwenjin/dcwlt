package com.dcits.dcwlt.pay.api.domain.dcep.resendapply;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;


import javax.validation.Valid;

/**
 *
 * @date  2021/1/5
 * @version 1.0.0
 * Description:自由格式请求报文体
 */
public class EcnyReSendApyReqDTO extends DCEPReqBody {

    @Valid
    private ApplMsg applMsg;

    public EcnyReSendApyReqDTO() {
    }

    public EcnyReSendApyReqDTO(ApplMsg applMsg) {
        this.applMsg = applMsg;
    }

    @JSONField(name = "ApplMsg")
    public ApplMsg getApplMsg() {
        return applMsg;
    }

    public void setApplMsg(ApplMsg applMsg) {
        this.applMsg = applMsg;
    }

    @Override
    public String toString() {
        return "ReSendApyDTO{" +
                "applMsg=" + applMsg +
                '}';
    }
}
