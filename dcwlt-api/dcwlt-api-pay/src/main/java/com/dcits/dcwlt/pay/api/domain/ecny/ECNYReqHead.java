package com.dcits.dcwlt.pay.api.domain.ecny;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author zhanguohai
 * @Time 2021/1/3 15:38
 * @Version 1.0
 */
public class ECNYReqHead {
    @NotBlank
    @Length(max = 4)
    private String busiChnl;          //请求系统标识（渠道大类）

    @Length(max = 4)
    private String busiChnl2;         //请求子系统标识

    @Length(max = 6)
    private String zoneno;            //分行号

    @NotBlank
    @Length(max = 6)
    private String brno;              //交易行所号

    @Length(max = 10)
    private String tellerno;          //柜员号

    @NotBlank
    @Length(max = 3)
    private String origChnl;          //源发起渠道大类

    @Length(max = 4)
    private String origChnl2;         //源发起渠道中类

    @Length(max = 2)
    private String origChnlDtl;       //源发起渠道细分

    public ECNYReqHead() {
    }

    public String getBusiChnl() {
        return busiChnl;
    }

    public void setBusiChnl(String busiChnl) {
        this.busiChnl = busiChnl;
    }

    public String getBusiChnl2() {
        return busiChnl2;
    }

    public void setBusiChnl2(String busiChnl2) {
        this.busiChnl2 = busiChnl2;
    }

    public String getZoneno() {
        return zoneno;
    }

    public void setZoneno(String zoneno) {
        this.zoneno = zoneno;
    }

    public String getBrno() {
        return brno;
    }

    public void setBrno(String brno) {
        this.brno = brno;
    }

    public String getTellerno() {
        return tellerno;
    }

    public void setTellerno(String tellerno) {
        this.tellerno = tellerno;
    }

    public String getOrigChnl() {
        return origChnl;
    }

    public void setOrigChnl(String origChnl) {
        this.origChnl = origChnl;
    }

    public String getOrigChnl2() {
        return origChnl2;
    }

    public void setOrigChnl2(String origChnl2) {
        this.origChnl2 = origChnl2;
    }

    public String getOrigChnlDtl() {
        return origChnlDtl;
    }

    public void setOrigChnlDtl(String origChnlDtl) {
        this.origChnlDtl = origChnlDtl;
    }

    @Override
    public String toString() {
        return "ECNYReqHead{" +
                "busiChnl='" + busiChnl + '\'' +
                ", busiChnl2='" + busiChnl2 + '\'' +
                ", zoneno='" + zoneno + '\'' +
                ", brno='" + brno + '\'' +
                ", tellerno='" + tellerno + '\'' +
                ", origChnl='" + origChnl + '\'' +
                ", origChnl2='" + origChnl2 + '\'' +
                ", origChnlDtl='" + origChnlDtl + '\'' +
                '}';
    }
}
