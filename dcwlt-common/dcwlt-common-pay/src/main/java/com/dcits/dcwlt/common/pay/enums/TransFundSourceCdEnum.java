package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @date  2020/12/28
 * @version 1.0.0
 * <p>交易资金来源</p>
 */
public enum TransFundSourceCdEnum {

    TF00("TF00","销售款"),
    TF01("TF01","借款"),
    TF02("TF02","还款"),
    TF03("TF03","专用资金"),
    TF04("TF04","财政拨款"),
    TF99("TF99","其他")
    ;

    private static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(TransFundSourceCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String value){
        return enumSet.contains(value);
    }


    private String code;

    private String desc;

    TransFundSourceCdEnum(String code, String desc) {
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
