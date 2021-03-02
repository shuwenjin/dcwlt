package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @date  2020/12/28
 * @version 1.0.0
 * <p>表示账户类型</p>
 */
public enum AccTpCdEnum {

    AT00("AT00","个人银行借记账户"),
    AT01("AT01","个人银行贷记账户"),
    AT02("AT02","个人银行准贷记账户"),
    AT03("AT03","单位银行结算账户"),
    AT04("AT04","基本存款账户"),
    AT05("AT05","一般存款账户"),
    AT06("AT06","临时存款账户"),
    AT07("AT07","DC/EP特殊存款账户")
    ;

    private static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(AccTpCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    AccTpCdEnum(String code, String desc) {
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
