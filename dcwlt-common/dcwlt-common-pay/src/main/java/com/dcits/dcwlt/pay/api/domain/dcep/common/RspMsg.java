package com.dcits.dcwlt.pay.api.domain.dcep.common;

/**
 * 响应报文
 *
 *
 * @date 2020/12/31
 */
public class RspMsg<R> {

    private GrpHdr grpHdr;
    private R body;

    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    public R getBody() {
        return body;
    }

    public void setBody(R body) {
        this.body = body;
    }
}
