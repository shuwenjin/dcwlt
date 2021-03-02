package com.dcits.dcwlt.pay.api.domain.dcep.login;


import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;

/**
 * 登录/退出应答报文
 *
 *
 * @date 2020/12/28
 */
public class LoginRspn extends DCEPRspBody {

    /**
     * 业务头组件
     */
    private GrpHdr grpHdr;

    /**
     * 原始组信息
     */
    private OrgnlGrpInf orgnlGrpInf;

    /**
     * 登录/退出响应信息
     */
    private LoginRspnInf loginRspnInf;

    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    public OrgnlGrpInf getOrgnlGrpInf() {
        return orgnlGrpInf;
    }

    public void setOrgnlGrpInf(OrgnlGrpInf orgnlGrpInf) {
        this.orgnlGrpInf = orgnlGrpInf;
    }

    public LoginRspnInf getLoginRspnInf() {
        return loginRspnInf;
    }

    public void setLoginRspnInf(LoginRspnInf loginRspnInf) {
        this.loginRspnInf = loginRspnInf;
    }

    @Override
    public String toString() {
        return "LoginRspn{" +
                "grpHdr=" + grpHdr +
                ", orgnlGrpInf=" + orgnlGrpInf +
                ", loginRspnInf=" + loginRspnInf +
                '}';
    }
}
