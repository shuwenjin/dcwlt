package com.dcits.dcwlt.common.pay.constant;


import java.util.HashMap;
import java.util.Map;

public class EventConst {


    /**
     * 异常事件处理扭转状态
     */
    public static final String EVENT_DEAL_INIT = "INIT";    //初始化（待处理）
    public static final String EVENT_DEAL_SUCC = "SUCC";    //处理成功
    public static final String EVENT_DEAL_FAIL = "FAIL";    //处理失败（单次处理失败）
    public static final String EVENT_DEAL_PROC = "PROC";    //处理中
    public static final String EVENT_DEAL_EXPT = "EXPT";    //处理异常（处理失败次数超出配置的事件最大处理次数）

    /**
     * 异常事件处理类型
     */
    public static final String EVENT_DEAL_TYPE_AUTO = "AUTO";    //系统自动处理
    public static final String EVENT_DEAL_TYPE_MANU = "MANU";    //人工处理

    /**
     * 异常事件处理模式
     */
    public static final String EVENT_DEAL_MODE_LOCAL = "LOCAL_INVOKE";   //本地调用
    public static final String EVENT_DEAL_MODE_ASYNC = "ASYNC_INVOKE";   //异步线程处理
    public static final String EVENT_DEAL_MODE_DUBBO = "SERVICE_INVOKE"; //内部服务化调用

    /**
     * 是否尝试重试
     */
    public static final String EVENT_DEAL_RETRY_Y = "Y";        //重试交易
    public static final String EVENT_DEAL_RETRY_N = "N";        //不重试

    /**
     *
     */
    public static final Map<String, Integer> EVENT_INTERVAL_TIME = new HashMap<>();

    static {
        EVENT_INTERVAL_TIME.put("01S", 1);
        EVENT_INTERVAL_TIME.put("05S", 2);
        EVENT_INTERVAL_TIME.put("10S", 3);
        EVENT_INTERVAL_TIME.put("30S", 4);
        EVENT_INTERVAL_TIME.put("01M", 5);
        EVENT_INTERVAL_TIME.put("02M", 6);
        EVENT_INTERVAL_TIME.put("03M", 7);
        EVENT_INTERVAL_TIME.put("04M", 8);
        EVENT_INTERVAL_TIME.put("05M", 9);
        EVENT_INTERVAL_TIME.put("06M", 10);
        EVENT_INTERVAL_TIME.put("07M", 11);
        EVENT_INTERVAL_TIME.put("08M", 12);
        EVENT_INTERVAL_TIME.put("09M", 13);
        EVENT_INTERVAL_TIME.put("10M", 14);
        EVENT_INTERVAL_TIME.put("20M", 15);
        EVENT_INTERVAL_TIME.put("30M", 16);
        EVENT_INTERVAL_TIME.put("01H", 17);
        EVENT_INTERVAL_TIME.put("02H", 18);
    }


}

