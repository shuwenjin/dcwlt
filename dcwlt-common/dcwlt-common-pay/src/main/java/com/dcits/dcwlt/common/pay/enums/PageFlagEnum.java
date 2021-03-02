package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @desc 页码标识
 *
 *
 *
 */
public enum PageFlagEnum {
    Y("Y","作为首页标识表示第一页,作为尾页标识表示最后一页"),
    N("N","作为首页标识表示非第一页,作为尾页标识表示非最后一页");
    private static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(PageFlagEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }

    private String code;
    private String desc;

    PageFlagEnum(String code,String desc){
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
