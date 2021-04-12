package com.dcits.dcwlt.pay.api.domain.dcep.dcepprcinf;

/**
 *
 * @date 2020/12/28
 * @version 1.0.0
 * <p>平台处理信息组件</p>
 */
public class DCEPPrcInf {

    /**
     * 业务状态
     */
    private String prcSts;

    /**
     * 业务处理码
     */
    private String PrcCd;

    /**
     * 业务拒绝信息
     */
    private String rjctInf;


    public DCEPPrcInf() {
    }

    public String getPrcSts() {
        return prcSts;
    }

    public void setPrcSts(String prcSts) {
        this.prcSts = prcSts;
    }

    public String getPrcCd() {
        return PrcCd;
    }

    public void setPrcCd(String prcCd) {
        PrcCd = prcCd;
    }

    public String getRjctInf() {
        return rjctInf;
    }

    public void setRjctInf(String rjctInf) {
        this.rjctInf = rjctInf;
    }

    @Override
    public String toString() {
        return "DCEPPrcInf{" +
                "prcSts='" + prcSts + '\'' +
                ", PrcCd='" + PrcCd + '\'' +
                ", rjctInf='" + rjctInf + '\'' +
                '}';
    }
}
