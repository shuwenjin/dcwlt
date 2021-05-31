package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 992应答状态
 */
public enum RspnCdEnum {
    ONLN("ONLN","在线（健康）"),
    OFLN("OFLN","离线（异常）"),
    UNLG("UNLG","没有登录"),
    AUTH("AUTH","无权访问")
    ;

    public static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(RspnCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }

    private String code;
    private String desc;
    RspnCdEnum(String code, String desc){
        this.code=code;
        this.desc=desc;
    }

    public static String getValue(String code){
        RspnCdEnum[] rspnCdEnums = values();
        for (RspnCdEnum rspnCdEnum : rspnCdEnums) {
            if (rspnCdEnum.code.equals(code)){
                return rspnCdEnum.desc;
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
