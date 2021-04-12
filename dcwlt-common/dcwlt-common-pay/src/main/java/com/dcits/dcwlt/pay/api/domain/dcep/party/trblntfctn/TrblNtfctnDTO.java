package com.dcits.dcwlt.pay.api.domain.dcep.party.trblntfctn;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 机构状态变更报文（931）
 *
 *
 * @date 2020/12/31
 */
public class TrblNtfctnDTO extends DCEPReqBody {

    @Valid
    @NotNull
    @JSONField(name = "TrblNtfctn")
    private TrblNtfctn trblNtfctn;  //机构变更报文数据

    public TrblNtfctn getTrblNtfctn() {
        return trblNtfctn;
    }

    public void setTrblNtfctn(TrblNtfctn trblNtfctn) {
        this.trblNtfctn = trblNtfctn;
    }

    @Override
    public String toString() {
        return "TrblNtfctnDTO{" +
                "trblNtfctn=" + trblNtfctn +
                '}';
    }
}
