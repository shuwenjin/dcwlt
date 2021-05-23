package com.dcits.dcwlt.pay.api.domain.dcep.chckRspn;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;

public class ChckRspnDTO extends DCEPRspBody {
    private ChckRspn chckRspn;

    @JSONField(name = "ChckRspn")
    public ChckRspn getChckRspn() {
        return chckRspn;
    }

    public void setChckRspn(ChckRspn chckRspn) {
        this.chckRspn = chckRspn;
    }

    @Override
    public String toString() {
        return "ChckRspnDTO{" +
                "chckRspn=" + chckRspn +
                '}';
    }
}
