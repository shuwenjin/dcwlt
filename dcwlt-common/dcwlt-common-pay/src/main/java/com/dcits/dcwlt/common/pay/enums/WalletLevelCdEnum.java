package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @date  2020/12/28
 * @version 1.0.0
 * <p>钱包等级</p>
 */
public enum WalletLevelCdEnum {

    WL01("WL01","一类钱包"),
    WL02("WL02","二类钱包"),
    WL03("WL03","三类钱包"),
    WL04("WL04","四类钱包")
    ;

    private static final Set<String> enumSet = new HashSet<>(8);

    static {
        Arrays.asList(WalletLevelCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    WalletLevelCdEnum(String code, String desc) {
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
