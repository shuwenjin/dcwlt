package com.dcits.dcwlt.pay.batch.domain;

import com.dcits.dcwlt.common.core.annotation.Excel;
import com.dcits.dcwlt.common.core.web.domain.BaseEntity;

/**
 * 签约流水对象 pay_sign_signinfo_jrn
 * 
 * @author dcwlt
 * @date 2021-05-07
 */
public class PaySignSigninfoJrn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 平台日期 */
    private String paydate;

    /** 平台流水 */
    private String payserno;

    /** 截至查询平台日期 */
    private String paydatestart;
    /** 截至查询平台日期 */
    private String paydateend;
    /** 平台时间 */
    private String paytime;

    /** 挂接协议号 */
    @Excel(name = "挂接协议号")
    private String signno;

    /** 签约人银行账户所属机构 */
    @Excel(name = "签约人银行账户所属机构")
    private String acctptyid;

    /** 签约人银行账户类型 */
    @Excel(name = "签约人银行账户类型")
    private String accttype;

    /** 签约人银行账户账号 */
    @Excel(name = "签约人银行账户账号")
    private String acctid;

    /** 签约人银行账户户名 */
    @Excel(name = "签约人银行账户户名")
    private String acctname;


    /** 报文标识号 */
    private String msgid;

    /** 发起机构 */
    private String instgpty;

    /** 接收机构 */
    private String instdpty;

    /** 往来send:发送，recv：接收 */
    private String direct;

    /** 管理类型MT01：身份认证,MT02：身份确认MT03：解约申请 */
    private String managetype;

    /** 签约类型SG00：不签约，SG01：签约 */
    private String signtype;


    /** 动态关联码：msg+应答报文流水 */
    private String msgsendcode;

    /** 动态验证码sm4加密存储 */
    private String msgverifycode;

    /** 业务处理状态：0-失败,1-成功,2-处理中 */
    private String trxstatus;

    /** 业务处理码 */
    private String trxretcode;

    /** 业务处理信息 */
    private String trxretmsg;

    /** 应答报文标识号 */
    private String rspmsgid;

    /** 应答回执状态 */
    private String rspstatus;

    /** 应答业务处理码 */
    private String rspcode;

    /** 应答业务处理信息 */
    private String rspmsg;

    /** 签约人证件类型 */
    private String idtype;

    /** 签约人证件号码 */
    private String idno;

    /** 银行预留手机号码 */
    private String telephone;

    /** 钱包开立所属机构编码 */
    private String walletptyid;

    /** 钱包id */
    private String walletid;

    /** 钱包类型 */
    private String wallettype;

    /** 钱包等级 */
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

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getInstgpty() {
        return instgpty;
    }

    public void setInstgpty(String instgpty) {
        this.instgpty = instgpty;
    }

    public String getInstdpty() {
        return instdpty;
    }

    public void setInstdpty(String instdpty) {
        this.instdpty = instdpty;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public String getManagetype() {
        return managetype;
    }

    public void setManagetype(String managetype) {
        this.managetype = managetype;
    }

    public String getSigntype() {
        return signtype;
    }

    public void setSigntype(String signtype) {
        this.signtype = signtype;
    }

    public String getSignno() {
        return signno;
    }

    public void setSignno(String signno) {
        this.signno = signno;
    }

    public String getMsgsendcode() {
        return msgsendcode;
    }

    public void setMsgsendcode(String msgsendcode) {
        this.msgsendcode = msgsendcode;
    }

    public String getMsgverifycode() {
        return msgverifycode;
    }

    public void setMsgverifycode(String msgverifycode) {
        this.msgverifycode = msgverifycode;
    }

    public String getTrxstatus() {
        return trxstatus;
    }

    public void setTrxstatus(String trxstatus) {
        this.trxstatus = trxstatus;
    }

    public String getTrxretcode() {
        return trxretcode;
    }

    public void setTrxretcode(String trxretcode) {
        this.trxretcode = trxretcode;
    }

    public String getTrxretmsg() {
        return trxretmsg;
    }

    public void setTrxretmsg(String trxretmsg) {
        this.trxretmsg = trxretmsg;
    }

    public String getRspmsgid() {
        return rspmsgid;
    }

    public void setRspmsgid(String rspmsgid) {
        this.rspmsgid = rspmsgid;
    }

    public String getRspstatus() {
        return rspstatus;
    }

    public void setRspstatus(String rspstatus) {
        this.rspstatus = rspstatus;
    }

    public String getRspcode() {
        return rspcode;
    }

    public void setRspcode(String rspcode) {
        this.rspcode = rspcode;
    }

    public String getRspmsg() {
        return rspmsg;
    }

    public void setRspmsg(String rspmsg) {
        this.rspmsg = rspmsg;
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
