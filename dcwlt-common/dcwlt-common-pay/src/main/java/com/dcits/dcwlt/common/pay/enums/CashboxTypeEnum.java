package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @date  2020/12/28
 * @version 1.0.0
 * <p>钱柜涉及类型</p>
 */
public enum CashboxTypeEnum {
    OT00("OT00","钱柜入库"),
    OT01("OT01","钱柜出库"),
    CRDT("CRDT","贷记"),
    DBIT("DBIT","借记"),
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

    CashboxTypeEnum(String code, String desc) {
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
