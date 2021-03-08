package com.dcits.dcwlt.pay.online.bankcore358040;

import com.alibaba.fastjson.annotation.JSONField;

public class BankCore358040ArrayInfoRsp {
    private String nonStdAc; // 非标准账号
    private String nonstdActype; // 非标准账号类型


    @JSONField(name = "NON_STD_AC")
    public String getNonStdAc() {
        return nonStdAc;
    }

    public void setNonStdAc(String nonStdAc) {
        this.nonStdAc = nonStdAc;
    }

    @JSONField(name = "NON_STD_ACTYPE")
    public String getNonstdActype() {
        return nonstdActype;
    }

    public void setNonstdActype(String nonstdActype) {
        this.nonstdActype = nonstdActype;
    }

    @Override
    public String toString() {
        return "BankCore358040ArrayInfoRsp [nonStdAc=" + nonStdAc + ", nonstdActype=" + nonstdActype + "]";
    }

}
