package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @date  2020/12/29
 * @version 1.0.0
 * <p>业务状态枚举</p>
 */
public enum ProcessStsCdEnum {

    PR00("PR00","成功"),
    PR01("PR01","失败"),
    PR02("PR02","处理中"),
    PR03("PR03","推定成功"),
    PR04("PR04","推定失败")
    ;

    private static final Set<String> enumSet = new HashSet<>(8);

    static {
        Arrays.asList(ProcessStsCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    ProcessStsCdEnum(String code, String desc) {
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
