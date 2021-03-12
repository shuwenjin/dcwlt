package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum ApplyTypeEnum {
	ERROR("ERROR","不平记录重对账"),
	DETAIL("DETAIL","明细重对账"),
	SECOND("SECOND","二次对账"),
	CORE("CORE","核心对账")
    ;

    private static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(ApplyTypeEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    ApplyTypeEnum(String code, String desc) {
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
