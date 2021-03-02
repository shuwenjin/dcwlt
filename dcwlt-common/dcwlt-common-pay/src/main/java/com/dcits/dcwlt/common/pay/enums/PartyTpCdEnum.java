package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @date  2020/12/28
 * @version 1.0.0
 * <p>表示机构类型</p>
 */
public enum PartyTpCdEnum {

    PT00("PT00","运营机构-银行机构"),
    PT01("PT01","运营机构-非银行机构"),
    PT02("PT02","非运营机构-合作银行"),
    PT03("PT03","非运营机构-代理清算机构")
    ;

    private static final Set<String> enumSet = new HashSet<>(8);

    static {
        Arrays.asList(PartyTpCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    PartyTpCdEnum(String code, String desc) {
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
