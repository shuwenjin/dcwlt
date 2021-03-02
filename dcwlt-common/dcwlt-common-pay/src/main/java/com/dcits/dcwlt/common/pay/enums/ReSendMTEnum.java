package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @date  2021/1/5
 * @version 1.0.0
 * <p>重发申请报文编号枚举</p>
 */
public enum ReSendMTEnum {
    DCEP711("dcep.711.001.01","机构对账汇总核对报文编号"),
    DCEP713("dcep.713.001.01","资金调整汇总核对报文编号")
    ;


    private static final Set<String> enumSet = new HashSet<>(4);

    static {
        Arrays.asList(ReSendMTEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    ReSendMTEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
