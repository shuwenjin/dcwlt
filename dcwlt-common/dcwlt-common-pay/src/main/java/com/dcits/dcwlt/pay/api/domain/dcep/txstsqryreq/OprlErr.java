package com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq;


import com.alibaba.fastjson.annotation.JSONField;

/**
 *
 * @date  2021/1/3
 * @version 1.0.0
 * <p>应答拒绝信息</p>
 */
public class OprlErr {

    /**
     * 错误信息
     */
    private Err err;


    /**
     * 业务拒绝信息
     */
    private String RjctInf;

    @JSONField(name = "Err")
    public Err getErr() {
        return err;
    }

    public void setErr(Err err) {
        this.err = err;
    }

    @JSONField(name = "RjctInf")
    public String getRjctInf() {
        return RjctInf;
    }

    public void setRjctInf(String rjctInf) {
        RjctInf = rjctInf;
    }

    @Override
    public String toString() {
        return "OprlErr [ " +
                "err=" + err +
                ", RjctInf='" + RjctInf + '\'' +
                ']';
    }
}
