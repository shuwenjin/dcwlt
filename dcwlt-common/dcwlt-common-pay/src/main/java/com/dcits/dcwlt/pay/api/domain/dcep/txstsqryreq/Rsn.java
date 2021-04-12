package com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *
 * @date  2021/1/3
 * @version 1.0.0
 * <p>原交易原因</p>
 */
public class Rsn {

    /**
     * 平台处理码：由平台处理，运营机构无需填写
     */
    private String prcCd;

    /**
     * 业务拒绝码：当原业务状态为“PR01”时必填
     */
    private String rjctCd;

    /**
     * 业务拒绝信息
     */
    private String rjctInf;

    @JSONField(name = "PrcCd")
    public String getPrcCd() {
        return prcCd;
    }

    public void setPrcCd(String prcCd) {
        this.prcCd = prcCd;
    }

    @JSONField(name = "RjctCd")
    public String getRjctCd() {
        return rjctCd;
    }

    public void setRjctCd(String rjctCd) {
        this.rjctCd = rjctCd;
    }

    @JSONField(name = "RjctInf")
    public String getRjctInf() {
        return rjctInf;
    }

    public void setRjctInf(String rjctInf) {
        this.rjctInf = rjctInf;
    }

    @Override
    public String toString() {
        return "Rsn{" +
                "prcCd='" + prcCd + '\'' +
                ", rjctCd='" + rjctCd + '\'' +
                ", rjctInf='" + rjctInf + '\'' +
                '}';
    }
}
