package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @Time 2021/1/5 22:39
 * @Version 1.0
 * Description:日志监控级别，
 * 1、ECNY_LOG_MONITOR_ERROR 应用于交易处理过处中出现的重大错误，可能会造成大范围的业务影响时，需要通知管理员及研发及时处理，如机构变更交易全部失败的情况，打印该级别
 * 2、ECNY_LOG_MONITOR_WARNNING 应用于交易处理过处中出现的错误，但影响范围小，不影响全局业务，需要通知管理员及研发，如机构变更交易部分失败的情况，打印该级别
 * 3、ECNY_LOG_MONITOR_NORMAL 应用于重点交易的执行，需要通知管理员，如机构的签退、签到，采用该级别
 */
public enum LogMonitorLevelCdEnum {
    ECNY_LOG_MONITOR_ERROR("ECNY_LOG_MONITOR_ERROR", "日志监控错误级别"),
    ECNY_LOG_MONITOR_WARNING("ECNY_LOG_MONITOR_WARNING", "日志监控告警级别"),
    ECNY_LOG_MONITOR_NORMAL("ECNY_LOG_MONITOR_NORMAL", "日志监控普通级别");


    private static final Set<String> enumSet = new HashSet<>(3);

    static {
        Arrays.asList(AccTpCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code) {
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    LogMonitorLevelCdEnum(String code, String desc) {
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
