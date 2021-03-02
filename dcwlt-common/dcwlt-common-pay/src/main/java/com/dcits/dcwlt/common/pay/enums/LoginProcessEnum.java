package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录/退出处理状态
 *
 *
 * @date 2021/1/4
 */
public enum LoginProcessEnum {
    TIMEOUT("TIMEOUT", "请求超时"),
    SUCCESS("SUCCESS", "成功"),
    UNKNOW("UNKNOW", "未知错误"),
    SERVER_ERROR("SERVER_ERROR", "远程服务未处理"),
    NOT_OPERATE("NOT_OPERATE", "不能执行"),
    UNAVAIABLE("UNAVAIABLE", "机构不可用"),
    NETWORK_ERROR("NETWORK_ERROR", "远程调用失败"),
    PARAMS_ERROR("PARAMS_ERROR", "参数错误")
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

    LoginProcessEnum(String code, String desc) {
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
