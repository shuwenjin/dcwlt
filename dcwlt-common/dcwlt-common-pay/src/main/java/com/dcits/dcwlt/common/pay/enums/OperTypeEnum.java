package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum OperTypeEnum {
    OT01("OT01", "单笔核心回查"),
    OT02("OT02", "单笔核心冲正"),
    OT03("OT03", "单笔核心补入账"),
    OT04("OT04", "单笔差错贷记调整"),
    OT05("OT05","对账批次差错");
    private static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(OperTypeEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code) {
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    OperTypeEnum(String code, String desc) {
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
