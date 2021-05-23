package com.dcits.dcwlt.pay.api.domain.dcep.chckRspn;

import com.alibaba.fastjson.annotation.JSONField;

public class ChckRspn {
    private ChckRspnInf chckRspnInf;

    @JSONField(name = "ChckRspnInf")
    public ChckRspnInf getChckRspnInf() {
        return chckRspnInf;
    }

    public void setChckRspnInf(ChckRspnInf chckRspnInf) {
        this.chckRspnInf = chckRspnInf;
    }

    @Override
    public String toString() {
        return "ChckRspn{" +
                "chckRspnInf=" + chckRspnInf +
                '}';
    }
}
