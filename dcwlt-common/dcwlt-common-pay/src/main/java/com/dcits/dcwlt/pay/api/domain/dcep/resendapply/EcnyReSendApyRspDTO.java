package com.dcits.dcwlt.pay.api.domain.dcep.resendapply;


import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;
import com.dcits.dcwlt.pay.api.domain.dcep.cmonconf.CmonConf;

/**
 *
 * @date 2021/1/8
 * @version 1.00
 * <p>重发申请响应报文</p>
 */
public class EcnyReSendApyRspDTO extends DCEPRspBody {

    private CmonConf cmonConf;

    public CmonConf getCmonConf() {
        return cmonConf;
    }

    public void setCmonConf(CmonConf cmonConf) {
        this.cmonConf = cmonConf;
    }

    @Override
    public String toString() {
        return "ReSendApyRspDTO{" +
                "cmonConf=" + cmonConf +
                '}';
    }
}
