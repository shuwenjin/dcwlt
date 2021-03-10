package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>批量任务组</p>
 */
public enum TaskGroupEnum {

	CHK_DTL("CHK_DTL","明细对账"),
	CHK_SUM("CHK_SUM","汇总对账"),
	CHK_DATA("CHK_DATA","抽数批次"),
	CHK_WRON("CHK_WRON","明细对账不平结果重对账")
    ;

    private static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(TaskGroupEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    TaskGroupEnum(String code, String desc) {
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
