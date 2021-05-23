package com.dcits.dcwlt.pay.api.domain.dcep.chckReq;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.Valid;

public class CheckReq {
    @Valid
    private ChckInf chckInf;

    @JSONField(name = "ChckInf")
    public ChckInf getChckInf() {
        return chckInf;
    }

    public void setChckInf(ChckInf chckInf) {
        this.chckInf = chckInf;
    }

    @Override
    public String toString() {
        return "CheckReq{" +
                "chckInf=" + chckInf +
                '}';
    }
}
