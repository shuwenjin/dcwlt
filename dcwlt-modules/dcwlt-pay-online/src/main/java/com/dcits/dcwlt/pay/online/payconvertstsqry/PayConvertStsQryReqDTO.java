package com.dcits.dcwlt.pay.online.payconvertstsqry;


public class PayConvertStsQryReqDTO extends ECNYReqBody {

    private String origBusiSysSerno;// 原渠道请求流水

    public String getOrigBusiSysSerno() {
        return origBusiSysSerno;
    }

    public void setOrigBusiSysSerno(String origBusiSysSerno) {
        this.origBusiSysSerno = origBusiSysSerno;
    }

    @Override
    public String toString() {
        return "PayConvertStsQryReqDTO{" +
                "origBusiSysSerno='" + origBusiSysSerno + '\'' +
                '}';
    }
}
