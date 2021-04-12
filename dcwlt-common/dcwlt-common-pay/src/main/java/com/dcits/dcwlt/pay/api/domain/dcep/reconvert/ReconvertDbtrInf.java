package com.dcits.dcwlt.pay.api.domain.dcep.reconvert;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.enums.WalletLevelCdEnum;
import com.dcits.dcwlt.common.pay.enums.WalletTpCdEnum;
import com.dcits.dcwlt.common.pay.util.HiddenUtil;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 兑回钱包信息
 * 
 *
 *
 */
public class ReconvertDbtrInf {
	/**
	 * 付款人钱包所属机构
	 */
	@NotBlank(message = "付款人钱包所属机构不能为空")
	@Length(max = 14)
	private String dbtrPtyId;
	/**
	 * 付款人名称
	 */
	@NotBlank(message = "付款人名称不能为空")
	@Length(max = 60)
	private String dbtrNm;
	/**
	 * 付款人钱包ID
	 */
	@NotBlank(message = "付款人钱包ID不能为空")
	@Length(max = 34)
	private String dbtrWltId;
	/**
	 * 付款人钱包等级
	 */
	@NotBlank(message = "付款人钱包等级不能为空")
	@EnumValue(value = WalletLevelCdEnum.class, message = "付款人钱包等级有误")
	private String dbtrWltLvl ;
	/**
	 * 付款人钱包类型
	 */
	@NotBlank(message = "付款人钱包类型不能为空")
	@EnumValue(value = WalletTpCdEnum.class, message = "付款人钱包类型有误")
	private String dbtrWltTp ;
	/**
	 * 付款人钱包名称
	 */
	@NotBlank(message = "付款人钱包名称不能为空")
	@Length(max = 60)
	private String dbtrWltNm  ;

	@JSONField(name = "DbtrPtyId")
	public String getDbtrPtyId() {
		return dbtrPtyId;
	}

	public void setDbtrPtyId(String dbtrPtyId) {
		this.dbtrPtyId = dbtrPtyId;
	}

	@JSONField(name = "DbtrNm")
	public String getDbtrNm() {
		return dbtrNm;
	}

	public void setDbtrNm(String dbtrNm) {
		this.dbtrNm = dbtrNm;
	}

	@JSONField(name = "DbtrWltId")
	public String getDbtrWltId() {
		return dbtrWltId;
	}

	public void setDbtrWltId(String dbtrWltId) {
		this.dbtrWltId = dbtrWltId;
	}

	@JSONField(name = "DbtrWltLvl")
	public String getDbtrWltLvl() {
		return dbtrWltLvl;
	}

	public void setDbtrWltLvl(String dbtrWltLvl) {
		this.dbtrWltLvl = dbtrWltLvl;
	}

	@JSONField(name = "DbtrWltTp")
	public String getDbtrWltTp() {
		return dbtrWltTp;
	}

	public void setDbtrWltTp(String dbtrWltTp) {
		this.dbtrWltTp = dbtrWltTp;
	}

	@JSONField(name = "DbtrWltNm")
	public String getDbtrWltNm() {
		return dbtrWltNm;
	}

	public void setDbtrWltNm(String dbtrWltNm) {
		this.dbtrWltNm = dbtrWltNm;
	}

	@Override
	public String toString() {
		return "DbtrInf [ dbtrPtyId=" + dbtrPtyId +
				", dbtrNm=" + HiddenUtil.acNameHidden(dbtrNm) +
				", dbtrWltId=" + HiddenUtil.acHidden(dbtrWltId) +
				", dbtrWltLvl=" + dbtrWltLvl +
				", dbtrWltTp=" + dbtrWltTp +
				", dbtrWltNm=" + dbtrWltNm +
				"]";
	}
}
