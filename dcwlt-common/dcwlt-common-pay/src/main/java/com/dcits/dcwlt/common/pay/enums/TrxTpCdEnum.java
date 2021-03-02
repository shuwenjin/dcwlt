package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @date  2020/12/28
 * @version 1.0.0
 * <p>表示账户状态</p>
 */
public enum  TrxTpCdEnum {

    COMMON("C","通用类"),
    SIGN("S","签约类"),
    FINANCE("F","金融类");

    private static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(TrxTpCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    TrxTpCdEnum(String code, String desc) {
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
