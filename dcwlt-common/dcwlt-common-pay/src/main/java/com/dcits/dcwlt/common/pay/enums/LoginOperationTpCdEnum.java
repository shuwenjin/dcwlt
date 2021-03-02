package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @date  2020/12/28
 * @version 1.0.0
 * <p>登录退出操作类型</p>
 */
public enum LoginOperationTpCdEnum {
    OT00("OT00","登录"),
    OT01("OT01","退出")
    ;

    private static final Set<String> enumSet = new HashSet<>(4);

    static {
        Arrays.asList(LoginOperationTpCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    LoginOperationTpCdEnum(String code, String desc) {
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
