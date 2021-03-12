package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 广发内部交易业务状态
 * @author maozewu-yfzx
 *
 */
public enum TranstionStatusCodeEnum {
	FAILED("0","失败"),
	SUCCESS("1","成功"),
	ABEND("2","异常"),
	REVERSED("3","已冲正"),
	RETURNED("5","已退回"),
	RECIPE("7","已收妥"),
	UNDO("8","已撤销"),
	INIT("9","未处理"),
	PRECREDITSUCCESS("A","贷记调账状态")
    ;

    private static final Set<String> enumSet = new HashSet<>(8);

    static {
        Arrays.asList(TranstionStatusCodeEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    TranstionStatusCodeEnum(String code, String desc) {
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

