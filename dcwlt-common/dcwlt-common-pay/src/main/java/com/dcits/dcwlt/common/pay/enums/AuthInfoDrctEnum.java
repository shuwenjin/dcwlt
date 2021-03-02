package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @desc 业务权限方向标识
 *
 *
 */
public enum AuthInfoDrctEnum {
    recvAuth("recvAuth","接收权限"),
    sendAuth("sendAuth","发送权限");

    public static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(AuthInfoDrctEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }

    private String code;
    private String desc;

    AuthInfoDrctEnum(String code, String desc){
        this.code=code;
        this.desc=desc;
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
