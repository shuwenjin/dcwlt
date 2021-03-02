package com.dcits.dcwlt.pay.api.domain.dcep.convert;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.enums.AccTpCdEnum;
import com.dcits.dcwlt.common.pay.util.HiddenUtil;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 付款账户信息
 * 
 *
 *
 */
public class ConvertDbtrInf {

	/**
	 * 付款人账户所属机构
	 */
	@NotBlank(message = "付款人账号所属机构不能为空")
	@Length(max = 14)
	private String dbtrPtyId;

	/**
	 * 付款人账户类型
	 */
	@NotBlank(message = "付款人账号类型不能为空")
	@EnumValue(value = AccTpCdEnum.class, message = "付款人账号类型有误")
	private String dbtAcctTp;

	/**
	 * 付款人名称
	 */
	@NotBlank(message = "付款人名称不能为空")
	@Length(max = 60)
	private String dbtrNm;

	/**
	 * 付款人账户账号
	 */
	@NotBlank(message = "付款人账户账号不能为空")
	@Length(max = 64)
	private String dbtrAcct;

	/**
	 * 挂接协议号
	 */
	@NotBlank(message = "挂接协议号不能为空")
	@Length(max = 34)
	private String sgnNo;

	@JSONField(name = "DbtrPtyId")
	public String getDbtrPtyId() {
		return dbtrPtyId;
	}

	public void setDbtrPtyId(String dbtrPtyId) {
		this.dbtrPtyId = dbtrPtyId;
	}

	@JSONField(name = "DbtAcctTp")
	public String getDbtAcctTp() {
		return dbtAcctTp;
	}

	public void setDbtAcctTp(String dbtAcctTp) {
		this.dbtAcctTp = dbtAcctTp;
	}

	@JSONField(name = "DbtrNm")
	public String getDbtrNm() {
		return dbtrNm;
	}

	public void setDbtrNm(String dbtrNm) {
		this.dbtrNm = dbtrNm;
	}

	@JSONField(name = "DbtrAcct")
	public String getDbtrAcct() {
		return dbtrAcct;
	}

	public void setDbtrAcct(String dbtrAcct) {
		this.dbtrAcct = dbtrAcct;
	}

	@JSONField(name = "SgnNo")
	public String getSgnNo() {
		return sgnNo;
	}

	public void setSgnNo(String sgnNo) {
		this.sgnNo = sgnNo;
	}

	@Override
	public String toString() {
		return "DbtrInf [dbtrPtyId=" + dbtrPtyId + ", dbtAcctTp=" + dbtAcctTp + ", dbtrNm=" + HiddenUtil.acNameHidden(dbtrNm) + ", dbtrAcct="
				+ HiddenUtil.acHidden(dbtrAcct) + ", sgnNo=" + sgnNo + "]";
	}

}
