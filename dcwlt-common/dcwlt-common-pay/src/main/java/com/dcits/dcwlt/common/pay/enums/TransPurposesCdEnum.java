package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @date  2020/12/28
 * @version 1.0.0
 * <p>交易用途</p>
 */
public enum TransPurposesCdEnum {

    TP00("TP00","工资或津贴"),
    TP01("TP01","劳务报酬"),
    TP02("TP02","奖金"),
    TP03("TP03","劳保福利费"),
    TP04("TP04","向个人收购农副产品"),
    TP05("TP05","差旅费"),
    TP06("TP06","零星支出"),
    TP99("TP99","其他")
    ;

    private static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(TransPurposesCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    TransPurposesCdEnum(String code, String desc) {
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
