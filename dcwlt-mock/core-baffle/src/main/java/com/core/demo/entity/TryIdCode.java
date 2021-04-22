package com.core.demo.entity;

/**
 * @author yuanxka
 * @Description: 接口编号
 */

public enum TryIdCode {
    E001("351100", "核心记账"),
    E002("358040", "核心账户信息查询"),
    E003("996666", "核心交易状态查询"),
    E004("998889", "核心冲正"),
    E005("SMS", "发送短信");

    private String code;
    private String desc;

    TryIdCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TryIdCode valueOfStatus(String code) {
        for (TryIdCode appLoginStatusEnum : values()) {
            if (appLoginStatusEnum.getCode() == code) {
                return appLoginStatusEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
