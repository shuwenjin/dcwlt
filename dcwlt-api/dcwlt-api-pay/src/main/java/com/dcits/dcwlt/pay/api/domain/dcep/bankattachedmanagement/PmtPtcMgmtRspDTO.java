package com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 *
 * @date  2020/12/30
 * @version 1.0.0
 * <p>账户挂接管理应答报文</p>
 */
public class PmtPtcMgmtRspDTO extends DCEPRspBody {

    @NotNull
    @Valid
    private PmtPtcMgmtRsp pmtPtcMgmtRsp;

    @JSONField(name = "PmtPtcMgmtRsp")
    public PmtPtcMgmtRsp getPmtPtcMgmtRsp() {
        return pmtPtcMgmtRsp;
    }

    public void setPmtPtcMgmtRsp(PmtPtcMgmtRsp pmtPtcMgmtRsp) {
        this.pmtPtcMgmtRsp = pmtPtcMgmtRsp;
    }

    @Override
    public String toString() {
        return "PmtPtcMgmtRspDTO{" +
                "pmtPtcMgmtRsp=" + pmtPtcMgmtRsp +
                '}';
    }
}
