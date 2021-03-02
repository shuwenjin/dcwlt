package com.dcits.dcwlt.pay.api.domain.dcep.common;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
/**
 * 接收机构
 */
public class InstdPty {

	/**
	 * 接收机构
	 */
	@NotBlank(message = "接收机构不能为空")
	@Length(max = 14)
	private String instdDrctPty;
	
	public InstdPty(String instdDrctPty) {
		super();
		this.instdDrctPty = instdDrctPty;
	}

	@JSONField(name = "InstdDrctPty")
	public String getInstdDrctPty() {
		return instdDrctPty;
	}

	public void setInstdDrctPty(String instdDrctPty) {
		this.instdDrctPty = instdDrctPty;
	}

	@Override
	public String toString() {
		return "InstdPty [instdDrctPty=" + instdDrctPty + "]";
	}
}
