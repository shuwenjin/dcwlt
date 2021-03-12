package com.dcits.dcwlt.pay.api.domain.dcep.login;

import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqBody;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 行内登录请求参数
 *
 * @author majun
 * @date 2021/1/4
 */
public class LoginInnerReqDTO extends ECNYReqBody {

    @NotBlank
    @Size(max = 4, message = "操作类型最大值4位")
    private String opterationType;       //登录退出操作类型

    @NotBlank
    @Size(max = 16, message = "柜员号最大16位")
    private String tlrNo;                //柜员号


    public String getOpterationType() {
        return opterationType;
    }

    public void setOpterationType(String opterationType) {
        this.opterationType = opterationType;
    }

    public String getTlrNo() {
        return tlrNo;
    }

    public void setTlrNo(String tlrNo) {
        this.tlrNo = tlrNo;
    }

    @Override
    public String toString() {
        return "LoginInnerReqDTO{" +
                "opterationType='" + opterationType + '\'' +
                ", tlrNo='" + tlrNo + '\'' +
                '}';
    }
}
