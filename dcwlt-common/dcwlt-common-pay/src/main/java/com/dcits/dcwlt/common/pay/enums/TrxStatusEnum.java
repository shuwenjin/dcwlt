package com.dcits.dcwlt.common.pay.enums;

/**
 *
 * @version 1.0.0
 * <p>业务处理状态(数据库状态记录)</p>
 * @date 2021/1/4
 */
public enum TrxStatusEnum {

    SUCCESS("1", "成功"),
    FAIL("0", "失败"),
    PROCESSING("2", "处理中");

    /**
     * 枚举值
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

    TrxStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
