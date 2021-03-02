package com.dcits.dcwlt.pay.api.domain.dcep.common;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 接收机构
 */
public class InstgPty {

	/**
	 * 发起机构
	 */
	@NotBlank(message = "发起机构不能为空")
	@Length(max = 14)
	private String instgDrctPty;
	
	public InstgPty(String instgDrctPty) {
		super();
		this.instgDrctPty = instgDrctPty;
	}

	@JSONField(name = "InstgDrctPty")
	public String getInstgDrctPty() {
		return instgDrctPty;
	}

	public void setInstgDrctPty(String instgDrctPty) {
		this.instgDrctPty = instgDrctPty;
	}

	@Override
	public String toString() {
		return "InstgPty [instgDrctPty=" + instgDrctPty + "]";
	}
}
