package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @date  2020/12/28
 * @version 1.0.0
 * <p>表示数据变更类型</p>
 */
public enum ChangeCdEnum {

    CC00("CC00","新增"),
    CC01("CC01","变更"),
    CC02("CC02","撤销")
    ;

    private static final Set<String> enumSet = new HashSet<>(4);

    static {
        Arrays.asList(ChangeCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    ChangeCdEnum(String code, String desc) {
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
