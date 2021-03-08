package com.dcits.dcwlt.pay.online.payconvertstsqry;


public class PayConvertStsQryRspDTO{

    private String coreAcctDate;// 核心记账日期
    private String coreSerno;// 核心记账流水
    private String payPathSerno;// 通道流水

    public PayConvertStsQryRspDTO(String coreAcctDate, String coreSerno, String payPathSerno) {
        this.coreAcctDate = coreAcctDate;
        this.coreSerno = coreSerno;
        this.payPathSerno = payPathSerno;
    }

    public PayConvertStsQryRspDTO() {

    }

    public String getCoreAcctDate() {
        return coreAcctDate;
    }

    public void setCoreAcctDate(String coreAcctDate) {
        this.coreAcctDate = coreAcctDate;
    }

    public String getCoreSerno() {
        return coreSerno;
    }

    public void setCoreSerno(String coreSerno) {
        this.coreSerno = coreSerno;
    }

    public String getPayPathSerno() {
        return payPathSerno;
    }

    public void setPayPathSerno(String payPathSerno) {
        this.payPathSerno = payPathSerno;
    }

    @Override
    public String toString() {
        return "PayConvertStsQryRspDTO{" +
                "coreAcctDate='" + coreAcctDate + '\'' +
                ", coreSerno='" + coreSerno + '\'' +
                ", payPathSerno='" + payPathSerno + '\'' +
                '}';
    }
}
