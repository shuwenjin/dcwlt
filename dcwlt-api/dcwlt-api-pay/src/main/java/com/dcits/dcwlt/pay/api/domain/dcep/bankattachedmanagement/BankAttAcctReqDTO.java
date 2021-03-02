package com.dcits.dcwlt.pay.api.domain.dcep.bankattachedmanagement;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @date  2020/12/30
 * @version 1.0.0
 * <p>账户挂接管理请求报文</p>
 */
public class BankAttAcctReqDTO extends DCEPReqBody {

    @NotNull
    @Valid
    private BankAttAcctReq bankAttAcctReq;

    @JSONField(name = "BankAttAcctReq")
    public BankAttAcctReq getBankAttAcctReq() {
        return bankAttAcctReq;
    }

    public void setBankAttAcctReq(BankAttAcctReq bankAttAcctReq) {
        this.bankAttAcctReq = bankAttAcctReq;
    }

    @Override
    public String toString() {
        return "BankAttAcctReqDTO{" +
                "bankAttAcctReq=" + bankAttAcctReq +
                '}';
    }
}
