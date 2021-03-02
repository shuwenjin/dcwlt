package com.dcits.dcwlt.pay.api.domain.dcep.login;


import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;

/**
 *
 * @Time 2020/12/31 16:21
 * @Version 1.0
 * Description:
 */
public class LoginRspDTO extends DCEPRspBody {

    private LoginRspn loginRspn;      //登录响应报文

    public LoginRspn getLoginRspn() {
        return loginRspn;
    }

    public void setLoginRspn(LoginRspn loginRspn) {
        this.loginRspn = loginRspn;
    }

    @Override
    public String toString() {
        return "LoginRspDTO{" +
                "loginRsp=" + loginRspn +
                '}';
    }
}
