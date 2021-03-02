package com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *
 * @date  2021/1/3
 * @version 1.0.0
 * <p>错误信息</p>
 */
public class Err {

    /**
     * 平台处理码
     */
    private String prcCd;

    /**
     * 业务拒绝码
     */
    private String rjctCd;


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


    @Override
    public String toString() {
        return "Err{" +
                "prcCd='" + prcCd + '\'' +
                ", rjctCd='" + rjctCd + '\'' +
                '}';
    }
}
