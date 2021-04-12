package com.dcits.dcwlt.pay.api.domain.dcep.login;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.enums.LoginOperationTpCdEnum;


/**
 *
 * @Time 2020/12/31 16:17
 * @Version 1.0
 * Description:登录信息
 */
public class LoginInf {
    /**
     * 操作类型
     */
    private LoginOperationTpCdEnum loginOprTp;

    @JSONField(name = "LoginOprTp")
    public LoginOperationTpCdEnum getLoginOprTp() {
        return loginOprTp;
    }

    public void setLoginOprTp(LoginOperationTpCdEnum loginOprTp) {
        this.loginOprTp = loginOprTp;
    }

    @Override
    public String toString() {
        return "LoginInf{" +
                "loginOprTp='" + loginOprTp + '\'' +
                '}';
    }
}
