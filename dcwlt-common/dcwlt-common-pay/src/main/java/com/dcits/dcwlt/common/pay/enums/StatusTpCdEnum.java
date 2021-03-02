package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @date  2020/12/28
 * @version 1.0.0
 * <p>状态枚举类型</p>
 */
public enum StatusTpCdEnum {

    ST00("ST00","设置故障"),
    ST01("ST01","恢复运行"),
    ST02("ST02","已登录"),
    ST03("ST03","已退出"),
    ST04("ST04","强制退出")
    ;

    private static final Set<String> enumSet = new HashSet<>(8);

    static {
        Arrays.asList(StatusTpCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    StatusTpCdEnum(String code, String desc) {
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
