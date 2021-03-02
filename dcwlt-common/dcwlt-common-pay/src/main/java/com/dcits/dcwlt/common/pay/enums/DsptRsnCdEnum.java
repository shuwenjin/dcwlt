package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @date  2020/12/28
 * @version 1.0.0
 * <p>差错原因码</p>
 */
public enum DsptRsnCdEnum {

    DR01("DR01","交易资金无法入账"),
    DR02("DR02","交易失败被扣款"),
    DR03("DR03","交易成功未扣款"),
    DR04("DR04","交易失败资金到账"),
    DR05("DR05","交易成功自己未到账"),
    DR06("DR06","账务金额与交易金额不一致"),
    DR10("DR10","贷记调账失误")
    ;

    private static final Set<String> enumSet = new HashSet<>(8);

    static {
        Arrays.asList(DsptRsnCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    DsptRsnCdEnum(String code, String desc) {
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
