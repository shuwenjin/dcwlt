package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/***
 * 报文传输方向
 */
public enum MesgDirectionEnum {
    SEND("U","由参与机构发出"),
    RECV("D","由城银清算发出"),
    ;
    private static final Set<String> enumSet = new HashSet<>(8);

    static {
        Arrays.asList(MesgDirectionEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    MesgDirectionEnum(String code, String desc) {
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
