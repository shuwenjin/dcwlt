package com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;


import javax.validation.Valid;


/**
 *
 * @date  2020/12/30
 * @version 1.0.0
 * <p>账户挂接管理应答报文</p>
 */
public class PmtPtcMgmtRsp extends DCEPRspBody {

    /**
     * 业务头组件
     */
    @Valid
    private GrpHdr grpHdr;

    /**
     * 原报文主键组件
     */
    @Valid
    private OrgnlGrpHdr orgnlGrpHdr;

    /**
     *响应信息
     */
    @Valid
    private BankAttRspsnInf rspsnInf;

    /**
     * 签约人信息
     */
    @Valid
    private SgnInf sgnInf;

    /**
     * 钱包信息
     */
    @Valid
    private WltInf wltInf;

    public PmtPtcMgmtRsp() {
        this.grpHdr = new GrpHdr();
        this.orgnlGrpHdr = new OrgnlGrpHdr();
        this.rspsnInf = new BankAttRspsnInf();
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

    @JSONField(name = "OrgnlGrpHdr")
    public OrgnlGrpHdr getOrgnlGrpHdr() {
        return orgnlGrpHdr;
    }

    public void setOrgnlGrpHdr(OrgnlGrpHdr orgnlGrpHdr) {
        this.orgnlGrpHdr = orgnlGrpHdr;
    }

    @JSONField(name = "RspsnInf")
    public BankAttRspsnInf getRspsnInf() {
        return rspsnInf;
    }

    public void setRspsnInf(BankAttRspsnInf rspsnInf) {
        this.rspsnInf = rspsnInf;
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
        return "PmtPtcMgmtRsp{" +
                "grpHdr=" + grpHdr +
                ", orgnlGrpHdr=" + orgnlGrpHdr +
                ", rspsnInf=" + rspsnInf +
                ", sgnInf=" + sgnInf +
                ", wltInf=" + wltInf +
                '}';
    }
}
