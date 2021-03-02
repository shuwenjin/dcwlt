package com.dcits.dcwlt.pay.api.domain.dcep.common;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 响应信息
 * 
 *
 *
 */
public class RspsnInf {

	/**
	 * 业务状态
	 */
	private String prcSts;

	/**
	 * 业务回执状态
	 */
	private String rspsnSts;

	/**
	 * 业务拒绝码
	 */
	private String rjctCd;

	/**
	 * 业务拒绝信息
	 */
	private String rjctInf;

	/**
	 * 批次号
	 */
	private String batchId;


	@JSONField(name = "PrcSts")
	public String getPrcSts() {
		return prcSts;
	}

	public void setPrcSts(String prcSts) {
		this.prcSts = prcSts;
	}

	@JSONField(name = "RspsnSts")
	public String getRspsnSts() {
		return rspsnSts;
	}

	public void setRspsnSts(String rspsnSts) {
		this.rspsnSts = rspsnSts;
	}

	@JSONField(name = "RjctCd")
	public String getRjctCd() {
		return rjctCd;
	}

	public void setRjctCd(String rjctCd) {
		this.rjctCd = rjctCd;
	}

	@JSONField(name = "RjctInf")
	public String getRjctInf() {
		return rjctInf;
	}

	public void setRjctInf(String rjctInf) {
		this.rjctInf = rjctInf;
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
		return "RspsnInf [prcSts=" + prcSts + ", rspsnSts=" + rspsnSts + ", rjctCd=" + rjctCd + ", rjctInf=" + rjctInf
				+ ", batchId=" + batchId + "]";
	}

}
