package com.dcits.dcwlt.pay.api.domain.dcep.login;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;


/**
 * 登录/退出请求报文
 *
 *
 *
 */
public class LoginReq {

    /**
     * 业务头组件
     */
    private GrpHdr grpHdr;

    /**
     * 登录信息
     */
    private LoginInf loginInf;

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "LoginInf")
    public LoginInf getLoginInf() {
        return loginInf;
    }

    public void setLoginInf(LoginInf loginInf) {
        this.loginInf = loginInf;
    }

    @Override
    public String toString() {
        return "LoginReq{" +
                "grpHdr=" + grpHdr +
                ", loginInf=" + loginInf +
                '}';
    }
}
