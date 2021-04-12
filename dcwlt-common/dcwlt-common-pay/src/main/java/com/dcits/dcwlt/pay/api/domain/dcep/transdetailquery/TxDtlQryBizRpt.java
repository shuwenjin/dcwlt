package com.dcits.dcwlt.pay.api.domain.dcep.transdetailquery;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq.Rsn;


/**
 *
 * @version 1.0.0
 * <p>应答的原业务信息</p>
 * @date 2021/1/6
 */
public class TxDtlQryBizRpt {

    /**
     * 原业务状态
     */
    private String trnRs;

    /**
     * 原交易原因：当原业务状态为非PR00或PR02时
     */
    private Rsn rsn;

    /**
     * 原业务信息
     */
    private TxDtlQryOrgnlMsgCntt orgnlMsgCntt;

    @JSONField(name = "TrnRs")
    public String getTrnRs() {
        return trnRs;
    }

    public void setTrnRs(String trnRs) {
        this.trnRs = trnRs;
    }

    @JSONField(name = "Rsn")
    public Rsn getRsn() {
        return rsn;
    }

    public void setRsn(Rsn rsn) {
        this.rsn = rsn;
    }

    @JSONField(name = "OrgnlMsgCntt")
    public TxDtlQryOrgnlMsgCntt getOrgnlMsgCntt() {
        return orgnlMsgCntt;
    }

    public void setOrgnlMsgCntt(TxDtlQryOrgnlMsgCntt orgnlMsgCntt) {
        this.orgnlMsgCntt = orgnlMsgCntt;
    }

    @Override
    public String toString() {
        return "BizRpt{" +
                "trnRs='" + trnRs + '\'' +
                ", rsn=" + rsn +
                ", orgnlMsgCntt=" + orgnlMsgCntt +
                '}';
    }
}
