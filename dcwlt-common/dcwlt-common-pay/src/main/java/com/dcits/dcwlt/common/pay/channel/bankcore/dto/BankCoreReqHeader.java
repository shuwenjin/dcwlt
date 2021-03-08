package com.dcits.dcwlt.common.pay.channel.bankcore.dto;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author zhanguohai
 * @Time 2020年3月30日下午7:31:05
 * @Version v1.0
 *          <p>
 * 			Description: 核心服务化请求报文头
 *          </p>
 */
public class BankCoreReqHeader {

	@NotBlank
	@Length(max = 1)
	private String reqType; // 请求类型

	@NotBlank
	@Length(max = 10)
	private String trId; // 渠道交易码

	@NotBlank
	@Length(max = 4)
	private String reqChnl; // 渠道大类

	@NotBlank
	@Length(max = 8)
	private String reqChnlTate; // 渠道系统交易日期

	@NotBlank
	@Length(max = 6)
	private String reqChnlTime; // 渠道系统交易时间

	@NotBlank
	@Length(max = 12)
	private String termId; // 渠道系统终端号

	@NotBlank
	@Length(max = 3)
	private String trBank; // 交易银行号

	@NotBlank
	@Length(max = 6)
	private String trBranch; // 交易机构

	@NotBlank
	@Length(max = 8)
	private String tlId; // 柜员

	@Length(max = 4)
	private String mercId; // 商户号

	@Length(max = 8)
	private String clearDate; // 清算日期

	@Length(max = 4)
	private String reqChnl2; // 渠道中类

	@Length(max = 2)
	private String chnlDtl; // 渠道小类

	@Length(max = 32)
	private String syncId; // 同步标识

	@Length(max = 16)
	private String mac; // MAC校验值

	@JSONField(name = "REQ_TYPE")
	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	@JSONField(name = "TR_ID")
	public String getTrId() {
		return trId;
	}

	public void setTrId(String trId) {
		this.trId = trId;
	}

	@JSONField(name = "REQ_CHNL")
	public String getReqChnl() {
		return reqChnl;
	}

	public void setReqChnl(String reqChnl) {
		this.reqChnl = reqChnl;
	}

	@JSONField(name = "REQ_CHNL_DATE")
	public String getReqChnlTate() {
		return reqChnlTate;
	}

	public void setReqChnlTate(String reqChnlTate) {
		this.reqChnlTate = reqChnlTate;
	}

	@JSONField(name = "REQ_CHNL_TIME")
	public String getReqChnlTime() {
		return reqChnlTime;
	}

	public void setReqChnlTime(String reqChnlTime) {
		this.reqChnlTime = reqChnlTime;
	}

	@JSONField(name = "TERM_ID")
	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	@JSONField(name = "TR_BANK")
	public String getTrBank() {
		return trBank;
	}

	public void setTrBank(String trBank) {
		this.trBank = trBank;
	}

	@JSONField(name = "TR_BRANCH")
	public String getTrBranch() {
		return trBranch;
	}

	public void setTrBranch(String trBranch) {
		this.trBranch = trBranch;
	}

	@JSONField(name = "TL_ID")
	public String getTlId() {
		return tlId;
	}

	public void setTlId(String tlId) {
		this.tlId = tlId;
	}

	@JSONField(name = "MERC_ID")
	public String getMercId() {
		return mercId;
	}

	public void setMercId(String mercId) {
		this.mercId = mercId;
	}

	@JSONField(name = "CLEAR_DATE")
	public String getClearDate() {
		return clearDate;
	}

	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}

	@JSONField(name = "REQ_CHNL2")
	public String getReqChnl2() {
		return reqChnl2;
	}

	public void setReqChnl2(String reqChnl2) {
		this.reqChnl2 = reqChnl2;
	}

	@JSONField(name = "CHNL_DTL")
	public String getChnlDtl() {
		return chnlDtl;
	}

	public void setChnlDtl(String chnlDtl) {
		this.chnlDtl = chnlDtl;
	}

	@JSONField(name = "SYNC_ID")
	public String getSyncId() {
		return syncId;
	}

	public void setSyncId(String syncId) {
		this.syncId = syncId;
	}

	@JSONField(name = "MAC")
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Override
	public String toString() {
		return "BankCoreHeader [reqType=" + reqType + ", trId=" + trId + ", reqChnl=" + reqChnl + ", reqChnlTate="
				+ reqChnlTate + ", reqChnlTime=" + reqChnlTime + ", termId=" + termId + ", trBank=" + trBank
				+ ", trBranch=" + trBranch + ", tlId=" + tlId + ", mercId=" + mercId + ", clearDate=" + clearDate
				+ ", reqChnl2=" + reqChnl2 + ", chnlDtl=" + chnlDtl + ", syncId=" + syncId + ", mac=" + mac + "]";
	}

}
