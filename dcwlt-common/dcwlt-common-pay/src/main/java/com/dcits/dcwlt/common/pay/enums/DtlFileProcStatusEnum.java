package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangwang
 * @date  2020/12/28
 * @version 1.0.0
 * <p>生效类型</p>
 */
public enum DtlFileProcStatusEnum {


    INIT("INIT","未下载"),
    APLY("APLY","已申请"),
    RECE("RECE","已推送"),
    MOVE("MOVE", "已移动"),
    SUCC("SUCC","已处理")
    ;

    private static final Set<String> enumSet = new HashSet<>(4);

    static {
        Arrays.asList(DtlFileProcStatusEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    DtlFileProcStatusEnum(String code, String desc) {
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
