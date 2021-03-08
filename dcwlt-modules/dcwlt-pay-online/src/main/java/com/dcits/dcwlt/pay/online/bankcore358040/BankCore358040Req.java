package com.dcits.dcwlt.pay.online.bankcore358040;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.util.HiddenUtil;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class BankCore358040Req{

	@NotBlank
	@Length(max = 25)
	private String ac; // 卡号或账号

	@Length(max = 25)
	private String acNo; // 卡下账号

	@Length(max = 6)
	private String acSeq; // 定期子合约序号

	@Length(max = 3)
	private String ccy; // 币种

	@Length(max = 1)
	private String ccyTyp; // 钞汇标志

	@Length(max = 1)
	private String chkPsw; // 校验密码标识

	private String psw; // 密码

	@Length(max = 1)
	private String chkId; // 校验证件标识

	@Length(max = 5)
	private String idType; // 证件类型

	@Length(max = 15)
	private String idNo; // 证件号码

	@Length(max = 1)
	private String chkMag; // 校验磁条标识

	private String trk2Dat; // 第二磁道信息

	private String trk3Dat; // 第三磁道信息

	@Length(max = 1)
	private String chkCvn2; // 校验CVN2标识

	@Length(max = 3)
	private String cvn2; // CVN2

	@Length(max = 1)
	private String chkExpDate; // 交易介质标识

	private String expDate; // 预留栏位

	@Length(max = 1)
	private String chkName; // 校验户名标识

	private String name; // 户名

	@Length(max = 1)
	private String chkTel; // 校验手机号码标识

	@Length(max = 18)
	private String telNo; // 手机号码

	@JSONField(name = "AC")
	public String getAc() {
		return ac;
	}

	public void setAc(String ac) {
		this.ac = ac;
	}

	@JSONField(name = "AC_NO")
	public String getAcNo() {
		return acNo;
	}

	public void setAcNo(String acNo) {
		this.acNo = acNo;
	}

	@JSONField(name = "AC_SEQ")
	public String getAcSeq() {
		return acSeq;
	}

	public void setAcSeq(String acSeq) {
		this.acSeq = acSeq;
	}

	@JSONField(name = "CCY")
	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	@JSONField(name = "CCY_TYP")
	public String getCcyTyp() {
		return ccyTyp;
	}

	public void setCcyTyp(String ccyTyp) {
		this.ccyTyp = ccyTyp;
	}

	@JSONField(name = "CHK_PSW")
	public String getChkPsw() {
		return chkPsw;
	}

	public void setChkPsw(String chkPsw) {
		this.chkPsw = chkPsw;
	}

	@JSONField(name = "PSW")
	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	@JSONField(name = "CHK_ID")
	public String getChkId() {
		return chkId;
	}

	public void setChkId(String chkId) {
		this.chkId = chkId;
	}

	@JSONField(name = "ID_TYPE")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	@JSONField(name = "ID_NO")
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@JSONField(name = "CHK_MAG")
	public String getChkMag() {
		return chkMag;
	}

	public void setChkMag(String chkMag) {
		this.chkMag = chkMag;
	}

	@JSONField(name = "TRK2_DAT")
	public String getTrk2Dat() {
		return trk2Dat;
	}

	public void setTrk2Dat(String trk2Dat) {
		this.trk2Dat = trk2Dat;
	}

	@JSONField(name = "TRK3_DAT")
	public String getTrk3Dat() {
		return trk3Dat;
	}

	public void setTrk3Dat(String trk3Dat) {
		this.trk3Dat = trk3Dat;
	}

	@JSONField(name = "CHK_CVN2")
	public String getChkCvn2() {
		return chkCvn2;
	}

	public void setChkCvn2(String chkCvn2) {
		this.chkCvn2 = chkCvn2;
	}

	@JSONField(name = "CVN2")
	public String getCvn2() {
		return cvn2;
	}

	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}

	@JSONField(name = "CHK_EXP_DATE")
	public String getChkExpDate() {
		return chkExpDate;
	}

	public void setChkExpDate(String chkExpDate) {
		this.chkExpDate = chkExpDate;
	}

	@JSONField(name = "EXP_DATE")
	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	@JSONField(name = "CHK_NAME")
	public String getChkName() {
		return chkName;
	}

	public void setChkName(String chkName) {
		this.chkName = chkName;
	}

	@JSONField(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JSONField(name = "CHK_TEL")
	public String getChkTel() {
		return chkTel;
	}

	public void setChkTel(String chkTel) {
		this.chkTel = chkTel;
	}

	@JSONField(name = "TEL_NO")
	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	@Override
	public String toString() {
		return "BankCore358040Req [ac=" + HiddenUtil.acHidden(ac) + ", acNo=" + HiddenUtil.acHidden(acNo) + ", acSeq=" + acSeq + ", ccy=" + ccy + ", ccyTyp="
				+ ccyTyp + ", chkPsw=" + chkPsw + ", psw= '******', chkId=" + chkId + ", idType=" + idType + ", idNo="
				+ HiddenUtil.certIdHidden(idNo) + ", chkMag=" + chkMag + ", trk2Dat=" + trk2Dat + ", trk3Dat=" + trk3Dat
				+ ", chkCvn2=" + chkCvn2 + ", cvn2=" + cvn2 + ", chkExpDate=" + chkExpDate
				+ ", expDate=" + expDate + ", chkName=" + chkName + ", name=" + HiddenUtil.acNameHidden(name)
				+ ", chkTel=" + chkTel + ", telNo=" + HiddenUtil.telNoHidden(telNo) + "]";
	}

}
