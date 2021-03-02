package com.dcits.dcwlt.pay.api.domain.dcep.decryptverify;


import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;

public class DecryptRspDTO extends DCEPRspBody {

    private String plaintext;     //明文
    private String verifyResult;  //验签结果
    private String errorCode;     //状态码
    private String errorMsg;      //状态信息

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    public String getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(String verifyResult) {
        this.verifyResult = verifyResult;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "decryptRspDTO{" +
                "plaintext='" + plaintext + '\'' +
                ", verifyResult='" + verifyResult + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
