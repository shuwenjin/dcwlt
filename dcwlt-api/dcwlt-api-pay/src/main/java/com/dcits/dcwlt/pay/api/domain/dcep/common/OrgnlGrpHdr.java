package com.dcits.dcwlt.pay.api.domain.dcep.common;

import com.alibaba.fastjson.annotation.JSONField;


import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

/**
 * 原报文主键组件
 * 
 *
 *
 */
public class OrgnlGrpHdr {

	/**
	 * 原报文标识号
	 */
	@NotBlank(message = "原报文标识号不能为空")
	@Length(max = 35)
	private String orgnlMsgId;

	/**
	 * 原发起机构
	 */
	@NotBlank(message = "原发起机构不能为空")
	@Length(max = 14)
	private String orgnlInstgPty;

	/**
	 * 原报文编号
	 */
	@NotBlank(message = "原报文编号不能为空")
	@Length(max = 15)
	private String orgnlMT;

	public static OrgnlGrpHdr getInstance(GrpHdr grpHdr, DCEPReqDTO<?> reqMsg) {
		OrgnlGrpHdr orgnlGrpHdr = new OrgnlGrpHdr();
		orgnlGrpHdr.setOrgnlMsgId(grpHdr.getMsgId());
		orgnlGrpHdr.setOrgnlInstgPty(grpHdr.getInstgPty().getInstgDrctPty());
		orgnlGrpHdr.setOrgnlMT(reqMsg.getDcepHead().getMsgTp());
		return orgnlGrpHdr;
	}

	public OrgnlGrpHdr() {
		super();
	}

	public OrgnlGrpHdr(String orgnlMsgId, String orgnlInstgPty, String orgnlMT) {
		super();
		this.orgnlMsgId = orgnlMsgId;
		this.orgnlInstgPty = orgnlInstgPty;
		this.orgnlMT = orgnlMT;
	}


	@JSONField(name = "OrgnlMsgId")
	public String getOrgnlMsgId() {
		return orgnlMsgId;
	}

	public void setOrgnlMsgId(String orgnlMsgId) {
		this.orgnlMsgId = orgnlMsgId;
	}

	@JSONField(name = "OrgnlInstgPty")
	public String getOrgnlInstgPty() {
		return orgnlInstgPty;
	}

	public void setOrgnlInstgPty(String orgnlInstgPty) {
		this.orgnlInstgPty = orgnlInstgPty;
	}

	@JSONField(name = "OrgnlMT")
	public String getOrgnlMT() {
		return orgnlMT;
	}

	public void setOrgnlMT(String orgnlMT) {
		this.orgnlMT = orgnlMT;
	}

	@Override
	public String toString() {
		return "OrgnlGrpHdr [orgnlMsgId=" + orgnlMsgId + ", orgnlInstgPty=" + orgnlInstgPty + ", orgnlMT=" + orgnlMT
				+ "]";
	}

	
}
