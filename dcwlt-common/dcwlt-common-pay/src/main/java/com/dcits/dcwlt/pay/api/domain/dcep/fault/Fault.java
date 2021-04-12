package com.dcits.dcwlt.pay.api.domain.dcep.fault;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 *
 * @desc 报文丢弃通知
 */
public class Fault {
    /*
     * 业务故障代码
     * */
    @NotBlank(message = "业务故障代码不能为空")
    @Length(max = 10)
    private String faultCode;
    /*
     * 业务故障说明
     * */
    @NotBlank(message = "业务故障说明不能为空")
    @Length(max = 105)
    private String faultString;
    /*
     * 业务故障信息,可填写发生错误的机构编码
     * */
    @NotBlank(message = "业务故障信息不能为空")
    @Length(max = 14)
    private String faultActor;
    /*
     * 详细信息
     * */
    @Length(max = 512)
    private String detail;

    @JSONField(name = "FaultCode")
    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    @JSONField(name = "FaultString")
    public String getFaultString() {
        return faultString;
    }

    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }

    @JSONField(name = "FaultActor")
    public String getFaultActor() {
        return faultActor;
    }

    public void setFaultActor(String faultActor) {
        this.faultActor = faultActor;
    }

    @JSONField(name = "Detail")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Fault{" +
                "faultcode='" + faultCode + '\'' +
                ", faultstring='" + faultString + '\'' +
                ", faultactor='" + faultActor + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
