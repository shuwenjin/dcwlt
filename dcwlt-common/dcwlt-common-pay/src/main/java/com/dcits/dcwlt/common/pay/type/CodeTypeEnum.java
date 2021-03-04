package com.dcits.dcwlt.common.pay.type;

/**
 * 数据库生成码时的类型
 */
public enum CodeTypeEnum {
    /**
     * 流水号
     */
    FLOWNO(1, "flowno", "流水号"),

    /**
     * 报文标识号
     */
    MSG_ID(2, "msg-flowno", "报文标识号"),

    /**
     * 签约协议号
     */
    AGREEMENT(3, "agreement-flowno", "签约协议号"),

    /**
     * 平台流水
     */
    PLATFORM_FLOWNO(4, "platform-flowno", "平台流水号"),

    /**
     * 核心请求流水
     */
    COREREQUEST_FLOWNO(5, "corereq-flowno", "核心请求流水");

    private Integer type;
    private String tag;
    private String desc;


    CodeTypeEnum(Integer type, String tag, String desc) {
        this.type = type;
        this.tag = tag;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }


    public String getTag() {
        return tag;
    }


    public String getDesc() {
        return desc;
    }

}
