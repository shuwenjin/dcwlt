package com.dcits.dcwlt.pay.api.domain.dcep.freefrmt;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;


import javax.validation.Valid;

/**
 *
 * @Time 2021/1/1 12:55
 * @Version 1.0
 * Description:自由格式
 */
public class FreeFrmt {
    @Valid
    private GrpHdr grpHdr;
    //业务报文头
    @Valid
    private FreeFrmtInf freeFrmtInf;    //自由格式消息

    public FreeFrmt() {
    }

    @JSONField(name = "GrpHdr")
    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    @JSONField(name = "FreeFrmtInf")
    public FreeFrmtInf getFreeFrmtInf() {
        return freeFrmtInf;
    }

    public void setFreeFrmtInf(FreeFrmtInf freeFrmtInf) {
        this.freeFrmtInf = freeFrmtInf;
    }

    @Override
    public String toString() {
        return "FreeFrmt{" +
                "grpHdr=" + grpHdr +
                ", freeFrmtInf=" + freeFrmtInf +
                '}';
    }
}
