package com.dcits.dcwlt.common.pay.enums;

/**
 * @version 1.0.0
 * <p>往来方向</p>
 * @date 2021/1/4
 */
public enum DirectEnum {

    SEND("SEND", "发送"),
    RECV("RECV", "接收");

    /**
     * 枚举值
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    DirectEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
