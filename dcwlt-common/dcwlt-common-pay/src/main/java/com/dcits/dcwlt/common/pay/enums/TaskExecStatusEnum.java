package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>表示对账结果状态</p>
 */
public enum TaskExecStatusEnum {

    INIT("INIT","初始化"),
    PROC("PROC","处理中"),
    SUCC("SUCC","成功"),
    FAIL("FAIL","失败"),
    EXPT("EXPT","异常")
    ;

    private static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(TaskExecStatusEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    TaskExecStatusEnum(String code, String desc) {
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
