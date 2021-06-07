package com.dcits.dcwlt.pay.online.task;


import com.dcits.dcwlt.pay.api.domain.dcep.config.PayCommonParam;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.mapper.PayCommonParamMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Time:2021-04-29 20:27
 * @Author:liuyong2
 * @Description:加载应用参数并定时刷新
 */
@Component
@EnableScheduling
public class PayCommParamTask {

    private static final Logger logger = LoggerFactory.getLogger(PayCommParamTask.class);

    @Autowired
    private PayCommonParamMapper payCommParamMapper;

    private static Map<String, String> paramMap = new HashMap<String, String>();

    @PostConstruct
    @Scheduled(cron = "0 0/1 * * * ?")
    public void loadData() {
        List<PayCommonParam> payCommParamList = payCommParamMapper.selectPayCommonParamList();
        if (null == payCommParamList) {
            return;
        }
        logger.info("加载应用参数数量：{}", payCommParamList.size());
        for (PayCommonParam param : payCommParamList) {
            //key组成规则：参数类型_参数id
            Integer status = param.getParamStatus();
            String key = String.format("%s_%s", param.getParamType(), param.getParamKey());
            if ("1".equals(status.toString())) {
                paramMap.put(key, param.getParamValue());
            } else {
                paramMap.remove(key);               //如果状态为停用，从缓存中移除该参数
            }

        }
    }

    /**
     * 获取应用参数配置值
     *
     * @param paramType
     * @param paramKey
     * @return
     */
    public static String getPayCommParamVal(String paramType, String paramKey) {
        //判断入参是否为空或null值
        if (StringUtils.isAnyBlank(paramType, paramKey)) {
            logger.error("请求参数存在空或nulL值");
            throw new EcnyTransException(EcnyTransError.PARAM_NOT_INIT_ERROR);
        }
        if (null == paramMap) {
            logger.error("ErrorCodeCacheMap缓存容器未初始化");
            throw new EcnyTransException(EcnyTransError.PARAM_NOT_INIT_ERROR);
        }
        String resultVal = "";
        if (!paramMap.isEmpty()) {
            String key = String.format("%s_%s", paramType, paramKey);
            resultVal = paramMap.get(key);
        }
        return resultVal;
    }
}
