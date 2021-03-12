package com.dcits.dcwlt.common.pay.constant;

/**
 * @author fsr
 * @data 2020 -03-18 11:26
 **/
public class Result {
    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code ;
    private String msg ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
