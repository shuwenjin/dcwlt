package com.dcits.dcwlt.pay.api.domain.dcep.party.chng;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;


import javax.validation.Valid;

/**
 * 机构变更报文（917）
 *
 *
 * @date 2020/12/28
 */
public class FinCdChngNtfctnDTO extends DCEPReqBody {

    @Valid
    @JSONField(name = "FinCdChngNtfctn")
    private FinCdChngNtfctn finCdChngNtfctn;

    public FinCdChngNtfctn getFinCdChngNtfctn() {
        return finCdChngNtfctn;
    }

    public void setFinCdChngNtfctn(FinCdChngNtfctn finCdChngNtfctn) {
        this.finCdChngNtfctn = finCdChngNtfctn;
    }

    @Override
    public String toString() {
        return "FinCdChngNtfctnDTO{" +
                "finCdChngNtfctn=" + finCdChngNtfctn +
                '}';
    }
}
