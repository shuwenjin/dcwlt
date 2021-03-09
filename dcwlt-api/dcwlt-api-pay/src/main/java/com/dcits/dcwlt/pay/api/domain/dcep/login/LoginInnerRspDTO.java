package com.dcits.dcwlt.pay.api.domain.dcep.login;

import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspBody;

/**
 * 行内登录退出响应报文
 *
 * @author majun
 * @date 2021/1/4
 */
public class LoginInnerRspDTO extends ECNYRspBody {
    private String procResult;    //处理结果

    public String getProcResult() {
        return procResult;
    }

    public void setProcResult(String procResult) {
        this.procResult = procResult;
    }

    @Override
    public String toString() {
        return "LoginInnerRspDTO{" +
                "procResult='" + procResult + '\'' +
                '}';
    }
}
