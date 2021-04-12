package com.dcits.dcwlt.pay.api.domain.dcep.payconvert;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.util.AmountUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.common.TrxAmt;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 交易信息
 * 
 *
 *
 */
public class TrxInf {

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
	 * 交易批次号
	 */
	@NotBlank(message = "交易批次号不能为空")
	@Length(max = 13)
	private String batchId;

	/**
	 * 附言
	 */
	@Length(max = 120)
	private String postScript;

	public static TrxInf getInstance(PayTransDtlInfoDO payTransDtlInfoDO) {
		TrxInf trxInf = new TrxInf();
		trxInf.setTrxBizTp(payTransDtlInfoDO.getBusiType());
		trxInf.setTrxCtgyPurpCd(payTransDtlInfoDO.getBusiKind());
		TrxAmt trxAmt = new TrxAmt();
		trxAmt.setCcy(AppConstant.CURRENCY_SYMBOL);
		trxAmt.setValue(AmountUtil.fenToYuan(payTransDtlInfoDO.getAmount()));
		trxInf.setTrxAmt(trxAmt);
		trxInf.setBatchId(payTransDtlInfoDO.getBatchId());
		// 附言为空不Set
		if (StringUtils.isNotBlank(payTransDtlInfoDO.getNarraTive())) {
			trxInf.setPostScript(payTransDtlInfoDO.getNarraTive());
		}
		return trxInf;
	}

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

	@JSONField(name = "BatchId")
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	@JSONField(name = "Postscript")
	public String getPostScript() {
		return postScript;
	}

	public void setPostScript(String postScript) {
		this.postScript = postScript;
	}

	@Override
	public String toString() {
		return "TrxInf{" +
				"trxBizTp='" + trxBizTp + '\'' +
				", trxCtgyPurpCd='" + trxCtgyPurpCd + '\'' +
				", trxAmt=" + trxAmt +
				", batchId='" + batchId + '\'' +
				", postScript='" + postScript + '\'' +
				'}';
	}
}
