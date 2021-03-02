package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum QueryPageFlagEnum {
    FIRST("0", "首页"),
    UP("1", "上翻"),
    DOWN("2", "下翻")
    ;
    private static final Set<String> enumSet = new HashSet<>(4);

    static {
        Arrays.asList(QueryPageFlagEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code) {
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    QueryPageFlagEnum(String code, String desc) {
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
