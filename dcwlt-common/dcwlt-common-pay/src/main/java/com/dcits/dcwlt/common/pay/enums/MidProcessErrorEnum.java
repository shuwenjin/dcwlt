package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 中间运行过程异常状态及描述
 *
 *
 * @date 2021/1/6
 */
public enum  MidProcessErrorEnum {
    PACKAGE_ERROR("PACKAGE_ERROR", "报文格式错误");

    private static final Set<String> enumSet = new HashSet<>(16);
    static {
        Arrays.asList(AccStsCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    MidProcessErrorEnum(String code, String desc) {
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
