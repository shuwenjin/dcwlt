package com.dcits.dcwlt.pay.api.domain.dcep.login;

/**
 * 原始组信息
 *
 *
 * @date 2020/12/28
 */
public class OrgnlGrpInf {

    /**
     * 原始组信息id
     */
    private String orgnlMsgId;

    public OrgnlGrpInf(String orgnlMsgId) {
        this.orgnlMsgId = orgnlMsgId;
    }

    public OrgnlGrpInf() {
    }

    public String getOrgnlMsgId() {
        return orgnlMsgId;
    }

    public void setOrgnlMsgId(String orgnlMsgId) {
        this.orgnlMsgId = orgnlMsgId;
    }

    @Override
    public String toString() {
        return "OrgnlGrpInf{" +
                "orgnlMsgId='" + orgnlMsgId + '\'' +
                '}';
    }
}
