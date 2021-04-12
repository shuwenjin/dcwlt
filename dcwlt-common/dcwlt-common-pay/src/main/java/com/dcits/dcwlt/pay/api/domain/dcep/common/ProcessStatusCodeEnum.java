package com.dcits.dcwlt.pay.api.domain.dcep.common;




import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 业务处理状态
 *
 *
 * @date 2020/12/28
 */
public enum ProcessStatusCodeEnum {
    PR00("PR00", "成功"),
    PR01("PR01", "失败");

    public static final Set<String> enumSet = new HashSet<>(4);

    static {
        Arrays.asList(ProcessStatusCodeEnum.values()).forEach(e -> enumSet.add(e.getValue()));
    }

    public static boolean hasEnum(String value){
        return enumSet.contains(value);
    }

    private String value;
    private String desc;

    ProcessStatusCodeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
