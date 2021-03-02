package com.dcits.dcwlt.pay.api.domain.dcep.cmonconf;


import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 *
 * @desc 通用处理确认信息
 */
public class CmonConfInf {

    /**
     * 业务状态,默认为PR00：报文接收成功
     */
    @NotBlank(message = "业务状态不能为空")
    private String prcSts;

    /**
     * 业务处理码
     */
    @Length(max = 10)
    private String prcCd;

    /**
     * 业务处理信息
     */
    @Length(max = 105)
    private String rjctInf;

    /**
     * 交易批次号
     */
    @Length(max = 13)
    private String batchId;

    @JSONField(name = "PrcSts")
    public String getPrcSts() {
        return prcSts;
    }

    public void setPrcSts(String prcSts) {
        this.prcSts = prcSts;
    }

    @JSONField(name = "PrcCd")
    public String getPrcCd() {
        return prcCd;
    }

    public void setPrcCd(String prcCd) {
        this.prcCd = prcCd;
    }

    @JSONField(name = "RjctInf")
    public String getRjctInf() {
        return rjctInf;
    }

    public void setRjctInf(String rjctInf) {
        this.rjctInf = rjctInf;
    }

    @JSONField(name = "BatchId")
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @Override
    public String toString() {
        return "CmonConfInf{" +
                "prcSts='" + prcSts + '\'' +
                ", prcCd='" + prcCd + '\'' +
                ", rjctInf='" + rjctInf + '\'' +
                ", batchId='" + batchId + '\'' +
                '}';
    }

}
