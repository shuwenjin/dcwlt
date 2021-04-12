package com.dcits.dcwlt.pay.api.model;

/**
 *
 * @date  2021/1/2
 * @version 1.0.0
 * <p>配置参数数据库实体</p>
 */
public class EcnyCommConfigDO {

    /**
     * 配置参数key
     */
    private String pamKey;

    /**
     * 配置参数value
     */
    private String pamValue;


    /**
     * 生效状态：0不生效，1生效
     */
    private String status;

    private String pamCode;


    public String getPamKey() {
        return pamKey;
    }

    public void setPamKey(String pamKey) {
        this.pamKey = pamKey;
    }

    public String getPamValue() {
        return pamValue;
    }

    public void setPamValue(String pamValue) {
        this.pamValue = pamValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPamCode() {
        return pamCode;
    }

    public void setPamCode(String pamCode) {
        this.pamCode = pamCode;
    }

    @Override
    public String toString() {
        return "EncyCommConfigDO{" +
                "pamKey='" + pamKey + '\'' +
                ", pamValue='" + pamValue + '\'' +
                ", status='" + status + '\'' +
                ", pamCode='" + pamCode + '\'' +
                '}';
    }
}
