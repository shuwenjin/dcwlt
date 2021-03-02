package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @desc 权限标识
 *
 *
 */
public enum AuthoritySignCodeEnum {
    AS00("AS00","允许"),
    AS01("AS01","禁止") ;

    public static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(AuthoritySignCodeEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }

    private String code;
    private String desc;
    AuthoritySignCodeEnum(String code, String desc){
        this.code=code;
        this.desc=desc;
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
