package com.dcits.dcwlt.pay.api.model;

/**
 *
 * @Time 2020/9/10 10:58
 * @Version 1.0
 * Description:枚举映射数据库实体
 */
public class EnumsMapDO {
    private String origSysId;        //源系统标识
    private String enumsType;        //枚举类型
    private String enumsCode;        //源枚举值
    private String destSysId;        //目标系统标识
    private String destEnumsCode;    //目标枚举值
    private String enumsDesc;        //枚举值说明
    private String rsv1;            //预留1

    public String getDestEnumsCode() {
        return destEnumsCode;
    }

    public void setDestEnumsCode(String destEnumsCode) {
        this.destEnumsCode = destEnumsCode;
    }

    public String getOrigSysId() {
        return origSysId;
    }

    public void setOrigSysId(String origSysId) {
        this.origSysId = origSysId;
    }

    public String getEnumsType() {
        return enumsType;
    }

    public void setEnumsType(String enumsType) {
        this.enumsType = enumsType;
    }

    public String getEnumsCode() {
        return enumsCode;
    }

    public void setEnumsCode(String enumsCode) {
        this.enumsCode = enumsCode;
    }

    public String getDestSysId() {
        return destSysId;
    }

    public void setDestSysId(String destSysId) {
        this.destSysId = destSysId;
    }

    public String getEnumsDesc() {
        return enumsDesc;
    }

    public void setEnumsDesc(String enumsDesc) {
        this.enumsDesc = enumsDesc;
    }

    public String getRsv1() {
        return rsv1;
    }

    public void setRsv1(String rsv1) {
        this.rsv1 = rsv1;
    }


    @Override
    public String toString() {
        return "EnumsMapDO{" +
                "origSysId='" + origSysId + '\'' +
                ", enumsType='" + enumsType + '\'' +
                ", enumsCode='" + enumsCode + '\'' +
                ", destSysId='" + destSysId + '\'' +
                ", destEnumsCode='" + destEnumsCode + '\'' +
                ", enumsDesc='" + enumsDesc + '\'' +
                ", rsv1='" + rsv1 + '\'' +
                '}';
    }
}