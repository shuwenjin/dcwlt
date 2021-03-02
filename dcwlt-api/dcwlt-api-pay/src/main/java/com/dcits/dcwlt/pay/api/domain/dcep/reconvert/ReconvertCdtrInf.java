package com.dcits.dcwlt.pay.api.domain.dcep.reconvert;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.enums.AccTpCdEnum;
import com.dcits.dcwlt.common.pay.util.HiddenUtil;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 收款人信息
 * 
 *
 *
 */
public class ReconvertCdtrInf {
	/**
	 * 收款人账号所属机构
	 */
	@NotBlank(message = "收款人账号所属机构不能为空")
	@Length(max = 14)
	private String cdtrPtyId;
	/**
	 * 收款人账号类型
	 */
	@NotBlank(message = "收款人账号类型不能为空")
	@EnumValue(value = AccTpCdEnum.class, message = "收款人账号类型有误")
	private String cdtrAcctTp;
	/**
	 * 收款人名称
	 */
	@NotBlank(message = "收款人名称不能为空")
	@Length(max = 60)
	private String cdtrNm;
	/**
	 * 收款人账户账号
	 */
	@NotBlank(message = "收款人账户账号不能为空")
	@Length(max = 32)
	private String cdtrAcct;

	@JSONField(name = "CdtrPtyId")
	public String getCdtrPtyId() {
		return cdtrPtyId;
	}

	public void setCdtrPtyId(String cdtrPtyId) {
		this.cdtrPtyId = cdtrPtyId;
	}

	@JSONField(name = "CdtrAcctTp")
	public String getCdtrAcctTp() {
		return cdtrAcctTp;
	}

	public void setCdtrAcctTp(String cdtrAcctTp) {
		this.cdtrAcctTp = cdtrAcctTp;
	}

	@JSONField(name = "CdtrNm")
	public String getCdtrNm() {
		return cdtrNm;
	}

	public void setCdtrNm(String cdtrNm) {
		this.cdtrNm = cdtrNm;
	}

	@JSONField(name = "CdtrAcct")
	public String getCdtrAcct() {
		return cdtrAcct;
	}

	public void setCdtrAcct(String cdtrAcct) {
		this.cdtrAcct = cdtrAcct;
	}

	@Override
	public String toString() {
		return "CdtrInf [" +
				"cdtrPtyId=" + cdtrPtyId +
				", cdtrAcctTp=" + cdtrAcctTp +
				", cdtrNm=" + HiddenUtil.acNameHidden(cdtrNm) +
				", cdtrAcct=" + HiddenUtil.acHidden(cdtrAcct) +
				"]";
	}
}
