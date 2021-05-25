package com.dcits.dcwlt.pay.api.domain.ecny.cashboxWarning;

import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspBody;

public class EcnyCashboxWarningRspDTO extends ECNYRspBody {

    /**
     * 业务状态
     */
    private String prcSts;

    /**
     * 业务处理信息
     */
    private String prcDesc;

    public String getPrcSts() {
        return prcSts;
    }

    public void setPrcSts(String prcSts) {
        this.prcSts = prcSts;
    }

    public String getPrcDesc() {
        return prcDesc;
    }

    public void setPrcDesc(String prcDesc) {
        this.prcDesc = prcDesc;
    }

    @Override
    public String toString() {
        return "EcnyCashboxWarningRspDTO{" +
                "prcSts='" + prcSts + '\'' +
                ", prcDesc='" + prcDesc + '\'' +
                '}';
    }
}
