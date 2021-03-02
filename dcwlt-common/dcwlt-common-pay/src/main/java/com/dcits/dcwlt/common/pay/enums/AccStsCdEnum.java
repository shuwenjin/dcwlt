package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @date  2020/12/28
 * @version 1.0.0
 * <p>表示账户状态</p>
 */
public enum AccStsCdEnum {

    AS00("AS00","带开户"),
    AS01("AS01","已开户"),
    AS02("AS02","待销户"),
    AS03("AS03","已销户"),
    AS04("AS04","借记控制"),
    AS05("AS05","贷记控制"),
    AS06("AS06","冻结"),
    AS07("AS07","已开户为Ⅰ类户"),
    AS08("AS08","已开户为Ⅱ类户"),
    AS09("AS09","已开户为Ⅲ类户"),
    AS10("AS10","无此户"),
    AS11("AS11","已开户为信用卡账户")
    ;

    private static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(AccStsCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    AccStsCdEnum(String code, String desc) {
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
