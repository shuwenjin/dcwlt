package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @date  2020/12/28
 * @version 1.0.0
 * <p>差错原因码</p>
 */
public enum DisputeReasonCdEnum {

    DR01("DR01","交易资金无法入账"),
    DR02("DR02","交易失败被扣款"),
    DR03("DR03","交易成功未扣款"),
    DR04("DR04","交易失败资金到账"),
    DR05("DR05","交易成功资金未到账"),
    DR06("DR06","账户金额与交易金额不一致"),
    DR10("DR10","贷记调账失误")
    ;

    private static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(DisputeReasonCdEnum.values()).forEach(e -> enumSet.add(e.getValue()));
    }

    public static boolean hasEnum(String value){
        return enumSet.contains(value);
    }


    private String value;

    private String desc;

    DisputeReasonCdEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}
