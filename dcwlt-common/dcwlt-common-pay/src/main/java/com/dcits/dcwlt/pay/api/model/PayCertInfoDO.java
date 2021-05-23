package com.dcits.dcwlt.pay.api.model;


import com.dcits.dcwlt.common.core.annotation.Excel;

/**
 * 密钥证书表实体类
 *
 *
 * @date 2021/04/29
 */
public class PayCertInfoDO {
    @Excel(name = "证书Id")
    protected String certid;               //证书Id
    @Excel(name = "密钥类型")
    protected String certtype;               //密钥类型
    @Excel(name = "证书内容")
    protected String certinfo;               //证书内容
    @Excel(name = "公钥")
    protected String publickey;             //公钥
    @Excel(name = "备注")
    protected String comment;               //备注
    @Excel(name = "最后更新日期")
    protected String lastUpDate;            //最后更新日期
    @Excel(name = "最后更新时间")
    protected String lastUpTime;            //最后更新时间
    @Excel(name = "有效标志")
    protected String status;                //有效标志
    @Excel(name = "SN")
    protected String sn;
    @Excel(name = "DN")
    protected String dn;

    public String getCertid() {
        return certid;
    }

    public void setCertid(String certid) {
        this.certid = certid;
    }

    public String getCerttype() {
        return certtype;
    }

    public void setCerttype(String certtype) {
        this.certtype = certtype;
    }

    public String getCertinfo() {
        return certinfo;
    }

    public void setCertinfo(String certinfo) {
        this.certinfo = certinfo;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLastUpDate() {
        return lastUpDate;
    }

    public void setLastUpDate(String lastUpDate) {
        this.lastUpDate = lastUpDate;
    }

    public String getLastUpTime() {
        return lastUpTime;
    }

    public void setLastUpTime(String lastUpTime) {
        this.lastUpTime = lastUpTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    @Override
    public String toString() {
        return "PayCertInfoDO{" +
                "certid='" + certid + '\'' +
                ", certtype=" + certtype +
                ", certinfo='" + certinfo + '\'' +
                ", comment='" + comment + '\'' +
                ", status='" + status + '\'' +
                ", lastUpDate='" + lastUpDate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                ", sn='" + sn + '\'' +
                ", dn='" + dn + '\'' +
                '}';
    }
}
