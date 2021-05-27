package com.dcits.dcwlt.pay.batch.domain;

import com.dcits.dcwlt.common.core.annotation.Excel;
import com.dcits.dcwlt.common.core.web.domain.BaseEntity;

/**
 * 钱柜余额对账通知对象 pay_comm_cashbox_banlance
 *
 * @author dcwlt
 * @date 2021-05-12
 */
public class PayCommCashboxBanlance extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 对账日期
     */
    @Excel(name = "对账日期")
    private String chckngdt;

    /**
     * 合作银行机构编码
     */
    @Excel(name = "合作银行机构编码")
    private String coopbankinstnid;

    /**
     * 合作银行钱柜ID
     */
    @Excel(name = "合作银行钱柜ID")
    private String coopbankwltid;

    /**
     * 钱柜所属运营机构
     */
    @Excel(name = "钱柜所属运营机构")
    private String cshboxinstnid;

    /**
     * 期初余额货币符号
     */
    @Excel(name = "期初余额货币符号")
    private String initlamtccy;

    /**
     * 期初余额
     */
    @Excel(name = "期初余额")
    private String initlamtvalue;

    /**
     * 借贷标识
     */
    @Excel(name = "借贷标识")
    private String cdtdbtind;

    /**
     * 借方金额货币符号
     */
    @Excel(name = "借方金额货币符号")
    private String dbtcntamtccy;

    /**
     * 借方金额
     */
    @Excel(name = "借方金额")
    private String dbtcntamtvalue;

    /**
     * 贷方金额货币符号
     */
    @Excel(name = "贷方金额货币符号")
    private String cdtcntamtccy;

    /**
     * 贷方金额
     */
    @Excel(name = "贷方金额")
    private String cdtcntamtvalue;

    /**
     * 期末余额货币符号
     */
    @Excel(name = "期末余额货币符号")
    private String fnlamtccy;

    /**
     * 期末余额
     */
    @Excel(name = "期末余额")
    private String fnlamtvalue;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setChckngdt(String chckngdt) {
        this.chckngdt = chckngdt;
    }

    public String getChckngdt() {
        return chckngdt;
    }

    public void setCoopbankinstnid(String coopbankinstnid) {
        this.coopbankinstnid = coopbankinstnid;
    }

    public String getCoopbankinstnid() {
        return coopbankinstnid;
    }

    public void setCoopbankwltid(String coopbankwltid) {
        this.coopbankwltid = coopbankwltid;
    }

    public String getCoopbankwltid() {
        return coopbankwltid;
    }

    public void setCshboxinstnid(String cshboxinstnid) {
        this.cshboxinstnid = cshboxinstnid;
    }

    public String getCshboxinstnid() {
        return cshboxinstnid;
    }

    public void setInitlamtccy(String initlamtccy) {
        this.initlamtccy = initlamtccy;
    }

    public String getInitlamtccy() {
        return initlamtccy;
    }

    public void setInitlamtvalue(String initlamtvalue) {
        this.initlamtvalue = initlamtvalue;
    }

    public String getInitlamtvalue() {
        return initlamtvalue;
    }

    public void setCdtdbtind(String cdtdbtind) {
        this.cdtdbtind = cdtdbtind;
    }

    public String getCdtdbtind() {
        return cdtdbtind;
    }

    public void setDbtcntamtccy(String dbtcntamtccy) {
        this.dbtcntamtccy = dbtcntamtccy;
    }

    public String getDbtcntamtccy() {
        return dbtcntamtccy;
    }

    public void setDbtcntamtvalue(String dbtcntamtvalue) {
        this.dbtcntamtvalue = dbtcntamtvalue;
    }

    public String getDbtcntamtvalue() {
        return dbtcntamtvalue;
    }

    public void setCdtcntamtccy(String cdtcntamtccy) {
        this.cdtcntamtccy = cdtcntamtccy;
    }

    public String getCdtcntamtccy() {
        return cdtcntamtccy;
    }

    public void setCdtcntamtvalue(String cdtcntamtvalue) {
        this.cdtcntamtvalue = cdtcntamtvalue;
    }

    public String getCdtcntamtvalue() {
        return cdtcntamtvalue;
    }

    public void setFnlamtccy(String fnlamtccy) {
        this.fnlamtccy = fnlamtccy;
    }

    public String getFnlamtccy() {
        return fnlamtccy;
    }

    public void setFnlamtvalue(String fnlamtvalue) {
        this.fnlamtvalue = fnlamtvalue;
    }

    public String getFnlamtvalue() {
        return fnlamtvalue;
    }

}
