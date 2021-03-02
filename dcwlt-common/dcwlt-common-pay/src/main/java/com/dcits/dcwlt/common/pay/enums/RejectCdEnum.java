package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 业务拒绝码
 *
 *
 * @date 2021/1/3
 */
public enum RejectCdEnum {
    PR02("PR02","处理中"),
    PR03("PR03","推定成功"),
    PR04("PR04","推定失败"),
    R004("R004", "请求非法"),
    R005("R005", "请求报文格式有误"),
    R006("R006", "请求报文必填参数缺失"),
    R007("R007", "请求报文参数有误"),
    R008("R008", "请求报文字段长度超限"),
    R999("R999","机构自定义原因失败说明"),
    SUCCESS("000000","交易成功")
    ;

    private static final Set<String> enumSet = new HashSet<>(4);

    static {
        Arrays.asList(ChangeCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    RejectCdEnum(String code, String desc) {
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
