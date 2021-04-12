package com.dcits.dcwlt.pay.api.domain.dcep.reconvert;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.common.pay.enums.TransFundSourceCdEnum;
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
public class TrxInfReconvert {
	/**
	 * 业务类型编码
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
	 * 交易资金来源
	 */
	@EnumValue(value = TransFundSourceCdEnum.class, message = "交易资金来源有误",required = false)
	private String trxFndSrc;
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

	@JSONField(name = "TrxFndSrc")
	public String getTrxFndSrc() {
		return trxFndSrc;
	}

	public void setTrxFndSrc(String trxFndSrc) {
		this.trxFndSrc = trxFndSrc;
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
				", trxFndSrc='" + trxFndSrc + '\'' +
				", batchId='" + batchId + '\'' +
				'}';
	}
}
