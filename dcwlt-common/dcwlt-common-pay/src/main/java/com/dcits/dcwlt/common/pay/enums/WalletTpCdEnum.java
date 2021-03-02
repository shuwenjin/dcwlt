package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @date  2020/12/28
 * @version 1.0.0
 * <p>钱包类型</p>
 */
public enum WalletTpCdEnum {

    WT01("WT01","个人钱包"),
    WT02("WT02","子个人钱包"),
    WT03("WT03","硬件钱包"),
    WT09("WT09","对公钱包"),
    WT10("WT10","子对公钱包")
    ;

    private static final Set<String> enumSet = new HashSet<>(8);

    static {
        Arrays.asList(WalletTpCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    WalletTpCdEnum(String code, String desc) {
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
