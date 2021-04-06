package com.dcits.dcwlt.pay.api.domain.dcep.txstsqry;


import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqBody;

/**
 * @author
 * @Time 2021/1/3 15:59
 * @Version 1.0
 * Description:交易查询接口请求报文
 */
public class TxStsQrySReqDTO extends ECNYReqBody {

    /**
     * 原报文标识号
     */
    private String orgnlMsgId;

    public String getOrgnlMsgId() {
        return orgnlMsgId;
    }

    public void setOrgnlMsgId(String orgnlMsgId) {
        this.orgnlMsgId = orgnlMsgId;
    }

    @Override
    public String toString() {
        return "TxStsQryReqDTO{" +
                "orgnlMsgId='" + orgnlMsgId + '\'' +
                '}';
    }
}
