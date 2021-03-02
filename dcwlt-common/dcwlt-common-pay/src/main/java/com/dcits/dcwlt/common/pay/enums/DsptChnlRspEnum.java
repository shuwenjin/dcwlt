package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @date  2020/12/28
 * @version 1.0.0
 * <p>差错调账服务化接口返回信息码</p>
 */
public enum DsptChnlRspEnum {

    DSPT01("DSPT01","提交成功"),
    DSPT02("DSPT02","提交失败"),
    DSPT03("DSPT03","差错贷记调账"),
    R127("R127","差错贷记调账失败"),
    R044("R044","原交易不存在"),
    ;

    private static final Set<String> enumSet = new HashSet<>(8);

    static {
        Arrays.asList(DsptChnlRspEnum.values()).forEach(e -> enumSet.add(e.getDesc()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    DsptChnlRspEnum(String code, String desc) {
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
