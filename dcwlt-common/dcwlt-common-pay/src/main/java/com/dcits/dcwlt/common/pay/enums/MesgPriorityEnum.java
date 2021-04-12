package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/***
 * 报文优先级
 */
public enum MesgPriorityEnum {
    MSGP1("1","特急"),
    MSGP2("2","紧急"),
    MSGP3("3","普通"),
    ;
    private static final Set<String> enumSet = new HashSet<>(8);

    static {
        Arrays.asList(MesgPriorityEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    MesgPriorityEnum(String code, String desc) {
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
