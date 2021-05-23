package com.dcits.dcwlt.pay.api.domain.dcep.chckRspn;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;


public class ChckRspnInf {
    @Length(max = 23)
    private String rspnDt; // 应答时间

    @Length(max = 4)
    private String rspnCd; // 应答状态

    @Length(max = 40)
    private String rspnNdNm; // 应答服务器名

    @JSONField(name = "RspnDt")
    public String getRspnDt() {
        return rspnDt;
    }

    public void setRspnDt(String rspnDt) {
        this.rspnDt = rspnDt;
    }

    @JSONField(name = "RspnCd")
    public String getRspnCd() {
        return rspnCd;
    }

    public void setRspnCd(String rspnCd) {
        this.rspnCd = rspnCd;
    }

    @JSONField(name = "RspnNdNm")
    public String getRspnNdNm() {
        return rspnNdNm;
    }

    public void setRspnNdNm(String rspnNdNm) {
        this.rspnNdNm = rspnNdNm;
    }

    @Override
    public String toString() {
        return "ChckRspnInf{" +
                "rspnDt='" + rspnDt + '\'' +
                ", rspnCd='" + rspnCd + '\'' +
                ", rspnNdNm='" + rspnNdNm + '\'' +
                '}';
    }
}
