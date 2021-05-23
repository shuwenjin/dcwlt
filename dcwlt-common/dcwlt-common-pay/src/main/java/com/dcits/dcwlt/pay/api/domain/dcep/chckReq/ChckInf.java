package com.dcits.dcwlt.pay.api.domain.dcep.chckReq;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class ChckInf {
    @NotBlank(message = "发起机构不能为空")
    @Length(max = 14)
    private String instgDrctPty; // 发起机构

    @NotBlank(message = "请求时间不能为空")
    @Length(max = 23)
    private String reqDt; // 请求时间

    @NotBlank(message = "请求服务器名不能为空")
    @Length(max = 40)
    private String reqNdNm; // 请求服务器名

    @JSONField(name = "InstgDrctPty")
    public String getInstgDrctPty() {
        return instgDrctPty;
    }

    public void setInstgDrctPty(String instgDrctPty) {
        this.instgDrctPty = instgDrctPty;
    }

    @JSONField(name = "ReqDt")
    public String getReqDt() {
        return reqDt;
    }

    public void setReqDt(String reqDt) {
        this.reqDt = reqDt;
    }

    @JSONField(name = "ReqNdNm")
    public String getReqNdNm() {
        return reqNdNm;
    }

    public void setReqNdNm(String reqNdNm) {
        this.reqNdNm = reqNdNm;
    }

    @Override
    public String toString() {
        return "ChckInf{" +
                "instgDrctPty='" + instgDrctPty + '\'' +
                ", reqDt='" + reqDt + '\'' +
                ", reqNdNm='" + reqNdNm + '\'' +
                '}';
    }
}
