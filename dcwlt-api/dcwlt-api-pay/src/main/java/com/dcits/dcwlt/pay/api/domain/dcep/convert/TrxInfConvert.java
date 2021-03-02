package com.dcits.dcwlt.pay.api.domain.dcep.convert;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.enums.TransPurposesCdEnum;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import com.dcits.dcwlt.pay.api.domain.dcep.common.TrxAmt;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 交易信息
 * 
 *
 *
 */
public class TrxInfConvert {

	/**
	 * 交易类型编码
	 */
	@NotBlank(message = "业务类型编码不能为空")
	@Length(max = 4)
	private String trxBizTp;

	/**
	 * 业务种类编码
	 */
	@NotBlank(message = "业务种类编码不能为空")
	@Length(max = 5)
	private String trxCtgyPurpCd;

	/**
	 * 交易金额
	 */
	@Valid
	private TrxAmt trxAmt;

	/**
	 * 交易用途
	 */
	@EnumValue(value = TransPurposesCdEnum.class, message = "交易用途有误", required = false)
	private String trxPrps;

	/**
	 * 交易批次号
	 */
	@NotBlank(message = "交易批次号不能为空")
	@Length(max = 13)
	private String batchId;

	@JSONField(name = "TrxBizTp")
	public String getTrxBizTp() {
		return trxBizTp;
	}

	public void setTrxBizTp(String trxBizTp) {
		this.trxBizTp = trxBizTp;
	}

	@JSONField(name = "TrxCtgyPurpCd")
	public String getTrxCtgyPurpCd() {
		return trxCtgyPurpCd;
	}

	public void setTrxCtgyPurpCd(String trxCtgyPurpCd) {
		this.trxCtgyPurpCd = trxCtgyPurpCd;
	}

	@JSONField(name = "TrxAmt")
	public TrxAmt getTrxAmt() {
		return trxAmt;
	}

	public void setTrxAmt(TrxAmt trxAmt) {
		this.trxAmt = trxAmt;
	}

	@JSONField(name = "TrxPrps")
	public String getTrxPrps() {
		return trxPrps;
	}

	public void setTrxPrps(String trxPrps) {
		this.trxPrps = trxPrps;
	}

	@JSONField(name = "BatchId")
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	@Override
	public String toString() {
		return "TrxInf{" +
				"trxBizTp='" + trxBizTp + '\'' +
				", trxCtgyPurpCd='" + trxCtgyPurpCd + '\'' +
				", trxAmt=" + trxAmt +
				", trxPrps='" + trxPrps + '\'' +
				", batchId='" + batchId + '\'' +
				'}';
	}
}
