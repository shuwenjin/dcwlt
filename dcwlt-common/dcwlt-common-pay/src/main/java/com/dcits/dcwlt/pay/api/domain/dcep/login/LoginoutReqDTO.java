package com.dcits.dcwlt.pay.api.domain.dcep.login;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;

public class LoginoutReqDTO extends DCEPReqBody {
	/**
	 * 登录退出报文体
	 */
	private LoginReq loginReq;

	@JSONField(name = "LoginReq")
	public LoginReq getLoginReq() {
		return loginReq;
	}

	public void setLoginReq(LoginReq loginReq) {
		this.loginReq = loginReq;
	}

	@Override
	public String toString() {
		return "LoginReqDTO{" +
				"loginReq=" + loginReq +
				'}';
	}
}
