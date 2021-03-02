package com.dcits.dcwlt.pay.api.domain.dcep.freefrmt;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;


import javax.validation.Valid;

/**
 *
 * @Time 2021/1/1 12:54
 * @Version 1.0
 * Description:自由格式请求报文体
 */
public class FreeFrmtDTO extends DCEPReqBody {

    @Valid
    private FreeFrmt freeFrmt;      //自由格式 message Root

    public FreeFrmtDTO() {
    }

    @JSONField(name = "FreeFrmt")
    public FreeFrmt getFreeFrmt() {
        return freeFrmt;
    }

    public void setFreeFrmt(FreeFrmt freeFrmt) {
        this.freeFrmt = freeFrmt;
    }

    @Override
    public String toString() {
        return "FreeFrmtReqDTO{" +
                "freeFrmt=" + freeFrmt +
                '}';
    }
}
