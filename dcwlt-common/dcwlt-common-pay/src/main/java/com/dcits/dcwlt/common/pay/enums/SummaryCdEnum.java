package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum SummaryCdEnum {

    XSF("XSF","钱包充值","数字人民币钱包充值（兑出）"),
    XSG("XSG","快捷充值","数字人民币钱包快捷充值（汇款兑出）"),
    XSH("XSH","存入账户","数字人民币钱包资金存入银行账户（兑回）"),
    XSJ("XSJ","充值退回","数字人民币钱包充值失败退回（差错贷记调账）"),
    ;

    private static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(SummaryCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String value){
        return enumSet.contains(value);
    }


    private String code;

    private String value;

    private String desc;


    SummaryCdEnum(String code,String value, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getValue() {
        return value;
    }
}
