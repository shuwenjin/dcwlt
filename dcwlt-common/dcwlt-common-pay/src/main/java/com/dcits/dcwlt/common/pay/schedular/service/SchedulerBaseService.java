package com.dcits.dcwlt.common.pay.schedular.service;

import java.util.Map;

/**
 * 定时任务统一接口
 * @author fushiren-yfzx
 * @data 2020 -04-01 10:31
 **/
public interface SchedulerBaseService {

    public String dealTask(Map<String, String> param) throws Exception;
    
}
