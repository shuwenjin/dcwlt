package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>表示对账结果状态</p>
 */
public enum CheckStatusEnum {

    SAME("SAME","对平"),
    MORE("MORE","行内大额多"),
    LESS("LESS","核对报文多"),
    DIFF("DIFF","要素不符"),
    SANF("SANF","状态不符，我行成功，人行失败"),
    FANS("FANS","状态不符，我行失败，人行成功"),
    EANS("EANS","状态不符，我行异常，人行成功"),
    EANF("EANF","状态不符，我行异常，人行失败"),
    PROC("PROC","状态不符，人行处理中"),
    INIT("INIT","未对账")
    ;

    private static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(CheckStatusEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    CheckStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
    
    public boolean equalCode(String targetCode) {
    	return code.equals(targetCode);
    }
}
