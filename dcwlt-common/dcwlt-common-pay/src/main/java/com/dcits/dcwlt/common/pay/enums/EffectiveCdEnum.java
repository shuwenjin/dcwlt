package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @date  2020/12/28
 * @version 1.0.0
 * <p>生效类型</p>
 */
public enum EffectiveCdEnum {


    EF00("EF00","立即生效"),
    EF01("EF01","指定日期生效")
    ;

    private static final Set<String> enumSet = new HashSet<>(4);

    static {
        Arrays.asList(EffectiveCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    EffectiveCdEnum(String code, String desc) {
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
