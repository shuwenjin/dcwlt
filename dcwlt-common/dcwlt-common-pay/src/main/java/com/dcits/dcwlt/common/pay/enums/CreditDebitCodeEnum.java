package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @desc 借记/贷记标识
 *
 *
 */
public enum CreditDebitCodeEnum {
    CRDT("CRDT","贷记"),
    DBIT("DBIT","借记");

    public static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(CreditDebitCodeEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }

    private String code;
    private String desc;
    CreditDebitCodeEnum(String code,String desc){
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
