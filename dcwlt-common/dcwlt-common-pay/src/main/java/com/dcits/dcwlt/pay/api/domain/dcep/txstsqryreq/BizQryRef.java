package com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *
 * @date  2021/1/3
 * @version 1.0.0
 * <p>业务查询信息</p>
 */
public class BizQryRef {

    /**
     * 原查询报文标识号
     */
    private String qryRef;


    /**
     * 原查询发起运营机构
     */
    private String qryNm;

    /**
     * 查询处理状态
     */
    private String qryRs;

    @JSONField(name = "QryRef")
    public String getQryRef() {
        return qryRef;
    }

    public void setQryRef(String qryRef) {
        this.qryRef = qryRef;
    }

    @JSONField(name = "QryNm")
    public String getQryNm() {
        return qryNm;
    }

    public void setQryNm(String qryNm) {
        this.qryNm = qryNm;
    }

    @JSONField(name = "QryRs")
    public String getQryRs() {
        return qryRs;
    }

    public void setQryRs(String qryRs) {
        this.qryRs = qryRs;
    }

    @Override
    public String toString() {
        return "BizQryRef{" +
                "qryRef='" + qryRef + '\'' +
                ", qryNm='" + qryNm + '\'' +
                ", qryRs='" + qryRs + '\'' +
                '}';
    }
}
