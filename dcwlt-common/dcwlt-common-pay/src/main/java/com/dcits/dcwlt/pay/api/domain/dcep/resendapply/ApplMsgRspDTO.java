package com.dcits.dcwlt.pay.api.domain.dcep.resendapply;


import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;

/**
 * @desc 重发申请响应
 *
 *
 *
 */
public class ApplMsgRspDTO extends DCEPRspBody {
    private ApplMsg applMsg;

    public ApplMsg getApplMsg() {
        return applMsg;
    }

    public void setApplMsg(ApplMsg applMsg) {
        this.applMsg = applMsg;
    }

    @Override
    public String toString() {
        return "ApplMsgRspDTO{" +
                "applMsg=" + applMsg +
                '}';
    }
}
