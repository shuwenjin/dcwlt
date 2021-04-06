package com.dcits.dcwlt.pay.api.domain.dcep.resendapply;


import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspBody;

/**
 * @author
 * @date  2021/1/5
 * @version 1.0.0
 * <p>重发申请响应报文</p>
 */
public class ReSendApyRspDTO extends ECNYRspBody {

    /**
     * 业务状态
     */
    private String prcSts;

    /**
     * 业务处理信息
     */
    private String prcDesc;

    public String getPrcSts() {
        return prcSts;
    }

    public void setPrcSts(String prcSts) {
        this.prcSts = prcSts;
    }

    public String getPrcDesc() {
        return prcDesc;
    }

    public void setPrcDesc(String prcDesc) {
        this.prcDesc = prcDesc;
    }

    @Override
    public String toString() {
        return "ReSendApyRspDTO{" +
                "prcSts='" + prcSts + '\'' +
                ", prcDesc='" + prcDesc + '\'' +
                '}';
    }
}
