package com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.enums.ManagementTpCdEnum;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @date  2020/12/30
 * @version 1.0.0
 * <p>账户挂接管理请求报文</p>
 */
public class BankAttAcctReq{

    /**
     * 业务组件头
     */
    @NotNull
    @Valid
    private GrpHdr grpHdr;

    /**
     * 管理类型
     * MT01: 身份认证
     * MT02: 身份确认
     * MT03: 解约申请
     */
    @NotNull
    @EnumValue(value = ManagementTpCdEnum.class)
    private String mgmtTp;

    /**
     * 协议信息
     */
    @Valid
    private PtcInf ptcInf;

    /**
     * 签约人信息
     */
    @Valid
    @NotNull
    private SgnInf sgnInf;

    @Valid
    @NotNull
    private WltInf wltInf;

    public BankAttAcctReq() {
        this.grpHdr = new GrpHdr();
        this.ptcInf = new PtcInf();
        this.sgnInf = new SgnInf();
        this.wltInf = new WltInf();
    }


    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "MgmtTp")
    public String getMgmtTp() {
        return mgmtTp;
    }

    public void setMgmtTp(String mgmtTp) {
        this.mgmtTp = mgmtTp;
    }

    @JSONField(name = "PtcInf")
    public PtcInf getPtcInf() {
        return ptcInf;
    }

    public void setPtcInf(PtcInf ptcInf) {
        this.ptcInf = ptcInf;
    }

    @JSONField(name = "SgnInf")
    public SgnInf getSgnInf() {
        return sgnInf;
    }

    public void setSgnInf(SgnInf sgnInf) {
        this.sgnInf = sgnInf;
    }

    @JSONField(name = "WltInf")
    public WltInf getWltInf() {
        return wltInf;
    }

    public void setWltInf(WltInf wltInf) {
        this.wltInf = wltInf;
    }

    @Override
    public String toString() {
        return "BankAttAcctReq{" +
                "grpHdr=" + grpHdr +
                ", mgmtTp='" + mgmtTp + '\'' +
                ", ptcInf=" + ptcInf +
                ", sgnInf=" + sgnInf +
                ", wltInf=" + wltInf +
                '}';
    }


}
