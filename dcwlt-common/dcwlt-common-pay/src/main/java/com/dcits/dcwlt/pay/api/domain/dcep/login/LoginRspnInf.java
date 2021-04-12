package com.dcits.dcwlt.pay.api.domain.dcep.login;


import com.dcits.dcwlt.common.pay.enums.LoginOperationTpCdEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;

/**
 * 登录、退出响应信息
 *
 *
 * @date 2020/12/28
 */
public class LoginRspnInf {

    /**
     * （登录，退出）操作类型
     */
    private LoginOperationTpCdEnum loginOprTp;

    /**
     * 业务处理状态
     */
    private ProcessStsCdEnum prcSts;

    /**
     * 业务处理码
     */
    private String prcCd;

    /**
     * 业务拒绝信息
     */
    private String rjctInf;

    public LoginOperationTpCdEnum getLoginOprTp() {
        return loginOprTp;
    }

    public void setLoginOprTp(LoginOperationTpCdEnum loginOprTp) {
        this.loginOprTp = loginOprTp;
    }

    public ProcessStsCdEnum getPrcSts() {
        return prcSts;
    }

    public void setPrcSts(ProcessStsCdEnum prcSts) {
        this.prcSts = prcSts;
    }

    public String getPrcCd() {
        return prcCd;
    }

    public void setPrcCd(String prcCd) {
        this.prcCd = prcCd;
    }

    public String getRjctInf() {
        return rjctInf;
    }

    public void setRjctInf(String rjctInf) {
        this.rjctInf = rjctInf;
    }

    @Override
    public String toString() {
        return "LoginRspnInf{" +
                "loginOprTp=" + loginOprTp +
                ", prcSts=" + prcSts +
                ", prcCd='" + prcCd + '\'' +
                ", rjctInf='" + rjctInf + '\'' +
                '}';
    }
}
