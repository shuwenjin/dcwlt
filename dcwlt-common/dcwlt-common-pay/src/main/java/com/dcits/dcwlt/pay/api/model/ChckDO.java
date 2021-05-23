package com.dcits.dcwlt.pay.api.model;


public class ChckDO {
    private String rspnDt; // 应答时间

    private String rspnCd; // 应答状态

    private String rspnNdNm; // 应答服务器名

    public String getRspnDt() {
        return rspnDt;
    }

    public void setRspnDt(String rspnDt) {
        this.rspnDt = rspnDt;
    }

    public String getRspnCd() {
        return rspnCd;
    }

    public void setRspnCd(String rspnCd) {
        this.rspnCd = rspnCd;
    }

    public String getRspnNdNm() {
        return rspnNdNm;
    }

    public void setRspnNdNm(String rspnNdNm) {
        this.rspnNdNm = rspnNdNm;
    }

    @Override
    public String toString() {
        return "ChckDO{" +
                "rspnDt='" + rspnDt + '\'' +
                ", rspnCd='" + rspnCd + '\'' +
                ", rspnNdNm='" + rspnNdNm + '\'' +
                '}';
    }
}
