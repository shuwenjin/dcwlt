package com.dcits.dcwlt.pay.api.domain.dcep.login;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqBody;


/**
 *
 * @Time 2021/01/08
 * @Version 1.0
 * Description:登录/退出请求报文
 */
public class LoginReqDTO extends ECNYReqBody {
    //DCEPReqBody
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
