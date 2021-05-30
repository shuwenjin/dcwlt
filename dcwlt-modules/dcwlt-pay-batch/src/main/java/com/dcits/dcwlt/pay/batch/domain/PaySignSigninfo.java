package com.dcits.dcwlt.pay.batch.domain;

import com.dcits.dcwlt.common.core.annotation.Excel;
import com.dcits.dcwlt.common.core.web.domain.BaseEntity;

/**
 * 协议对象 pay_sign_signinfo
 * 
 * @author dcwlt
 * @date 2021-05-08
 */
public class PaySignSigninfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 挂接协议号 */
    @Excel(name = "挂接协议号")
    private String signno;

    /** 协议状态N：签约状态C：解约状态 */
    @Excel(name = "签约状态")
    private String signstatus;

    /** 签约人银行账户所属机构 */
    @Excel(name = "签约人银行")
    private String acctptyid;

    /** 签约人银行账户账号 */
    @Excel(name = "签约卡号")
    private String acctid;

    /** 签约人银行账户户名 */
    @Excel(name = "账户名称")
    private String acctname;
    /** 签约人证件号码 */
    @Excel(name = "证件号码")
    private String idno;

    /** 截至查询平台日期 */
    private String paydatestart;
    /** 截至查询平台日期 */
    private String paydateend;
    /** 平台日期 */
    private String paydate;

    /** 平台流水 */
    private String payserno;

    /** 平台时间 */
    private String paytime;


    /** 签约人银行账户类型 */
    private String accttype;



    /** 签约人证件类型 */
    private String idtype;


    /** 银行预留手机号码 */
    private String telephone;

    /** 钱包开立所属机构编码 */
    private String walletptyid;

    /** 钱包id */
    private String walletid;

    /** 钱包类型，WT01：个人钱包，WT02：子个人钱包，WT03：硬件钱包，WT09：对公钱包，WT10：子对公钱包 */
    private String wallettype;

    /** 钱包等级WL01：一类钱包，WL02：二类钱包，WL03：三类钱包，WL04：四类钱包 */
    private String walletlevel;

    /** 最后更新流水 */
    private String lastupjrnno;

    /** 最后更新日期 */
    private String lastupdate;

    /** 最后更新时间 */
    private String lastuptime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public String getPayserno() {
        return payserno;
    }

    public void setPayserno(String payserno) {
        this.payserno = payserno;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getSignno() {
        return signno;
    }

    public void setSignno(String signno) {
        this.signno = signno;
    }

    public String getSignstatus() {
        return signstatus;
    }

    public void setSignstatus(String signstatus) {
        this.signstatus = signstatus;
    }

    public String getAcctptyid() {
        return acctptyid;
    }

    public void setAcctptyid(String acctptyid) {
        this.acctptyid = acctptyid;
    }

    public String getAccttype() {
        return accttype;
    }

    public void setAccttype(String accttype) {
        this.accttype = accttype;
    }

    public String getAcctid() {
        return acctid;
    }

    public void setAcctid(String acctid) {
        this.acctid = acctid;
    }

    public String getAcctname() {
        return acctname;
    }

    public void setAcctname(String acctname) {
        this.acctname = acctname;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWalletptyid() {
        return walletptyid;
    }

    public void setWalletptyid(String walletptyid) {
        this.walletptyid = walletptyid;
    }

    public String getWalletid() {
        return walletid;
    }

    public void setWalletid(String walletid) {
        this.walletid = walletid;
    }

    public String getWallettype() {
        return wallettype;
    }

    public void setWallettype(String wallettype) {
        this.wallettype = wallettype;
    }

    public String getWalletlevel() {
        return walletlevel;
    }

    public void setWalletlevel(String walletlevel) {
        this.walletlevel = walletlevel;
    }

    public String getLastupjrnno() {
        return lastupjrnno;
    }

    public void setLastupjrnno(String lastupjrnno) {
        this.lastupjrnno = lastupjrnno;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getLastuptime() {
        return lastuptime;
    }

    public void setLastuptime(String lastuptime) {
        this.lastuptime = lastuptime;
    }

    public String getPaydatestart() {
        return paydatestart;
    }

    public void setPaydatestart(String paydatestart) {
        this.paydatestart = paydatestart;
    }

    public String getPaydateend() {
        return paydateend;
    }

    public void setPaydateend(String paydateend) {
        this.paydateend = paydateend;
    }
}
