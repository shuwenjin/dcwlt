package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @Time 2021/1/2 16:56
 * @Version 1.0
 * Description:报文编号枚举
 */
public enum MsgTpEnum {
    DCEP900("dcep.900.001.01","通用处理确认报文"),
    DCEP902("dcep.902.001.01","通讯级确认报文"),
    DCEP401("dcep.401.001.01","自由格式报文"),
    DCEP433("dcep.433.001.01","银行账户挂接管理请求报文"),
    DCEP434("dcep.434.001.01","银行账户挂接管理应答报文"),
    DCEP222("dcep.222.001.01","兑回应答报文"),
    DCEP221("dcep.221.001.01","兑回请求报文"),
    DCEP225("dcep.225.001.01","兑出请求报文"),
    DCEP226("dcep.226.001.01","兑出应答报文"),
    DCEP227("dcep.227.001.01","汇款兑出请求报文"),
    DCEP228("dcep.228.001.01","汇款兑出应答报文"),
    DCEP411("dcep.411.001.01","交易状态查询请求报文"),
    DCEP412("dcep.412.001.01","交易状态查询应答报文"),
    DCEP417("dcep.417.001.01","交易明细查询请求报文"),
    DCEP418("dcep.418.001.01","交易明细查询应答报文"),
    DCEP801("dcep.801.001.01","差错贷记调账来账报文"),
    DCEP802("dcep.802.001.01","差错贷记调账应答报文"),
    DCEP920("dcep.920.001.01","重发申请请求报文"),
    DCEP911("dcep.911.001.01","报文丢弃应答报文"),
    DCEP933("dcep.933.001.01","登录退出请求报文"),
    DCEP934("dcep.934.001.01","登录退出应答报文"),
    DCEP909("dcep.909.001.01","终态通知请求报文"),
    DCEP713("dcep.713.001.01","资金调整汇总核对报文"),
    DCEP711("dcep.711.001.01","机构对账汇总核对报文")
    ;

    private static final Set<String> enumSet = new HashSet<>(50);

    static {
        Arrays.asList(PartyTpCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    MsgTpEnum(String code, String desc) {
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
