package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @date  2020/12/28
 * @version 1.0.0
 * <p>表示协议管理类型</p>
 */
public enum ManagementTpCdEnum {

    MT01("MT01","身份认证"),
    MT02("MT02","身份确认"),
    MT03("MT03","解约申请"),
    MT04("MT04","解约通知"),
    MT05("MT05","网关签约"),
    MT06("MT06","普通签约")
    ;

    private static final Set<String> enumSet = new HashSet<>(8);

    static {
        Arrays.asList(ManagementTpCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    ManagementTpCdEnum(String code, String desc) {
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
