package com.dcits.dcwlt.pay.online.task;


import com.dcits.dcwlt.pay.api.model.EcnyCommConfigDO;
import com.dcits.dcwlt.pay.online.mapper.EcnyCommConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author
 * @version 1.0.0
 * <p>配置参数服务</p>
 * @date 2021/1/2
 */

@Component
@EnableScheduling
public class ParamConfigCheckTask {

    private static final Logger logger = LoggerFactory.getLogger(ParamConfigCheckTask.class);

    private static final String EFFECTIVE_STATUS = "1";

    private static final String ECNY_COMM_CONFIG_TABLE_NAME = "PAY_COMM_PARAM_CONFIG";

    @Autowired
    private EcnyCommConfigMapper commConfigRepository;


    /**
     * 一级分类配置，对应pamCode下所有的配置
     */
    private static Map<String, Set<String>> topCategoryMap;

    /**
     * 二级分类配置，以一级分类（pamCode） + 二级分类（pamKey）为键（同时pamCode + pamKey为唯一索引）
     */
    private static Map<String, String> subCategoryMap;

    /**
     * 校验是否有配置参数
     * @param topCategory   一级分类码
     * @param subCategory   二级分类码
     * @return <tt>true</tt> 当数据库表含当前配置时
     */
    public static boolean checkConfigValue(String topCategory, String subCategory, String value) {
        String key = topCategory + subCategory;
        return subCategoryMap.getOrDefault(key,"").contains(value);
    }

    /**
     * 获取顶级分类下的所有配置
     * @param topCategory 一级分类码
     * @return Set<String>
     */
    public Set<String> getConfig(String topCategory){
        return topCategoryMap.getOrDefault(topCategory,null);
    }

    @PostConstruct
    public void cacheData() {
        Map<String, Set<String>> innerTopCategoryMap = new HashMap<>();
        Map<String, String> innerSubCategoryMap = new HashMap<>();
        List<EcnyCommConfigDO> list = commConfigRepository.getAllConfig();
        list.stream()
                .filter(e -> EFFECTIVE_STATUS.equals(e.getStatus()))
                .forEach(e -> {
                    String pamCode = e.getPamCode();
                    String pamKey = e.getPamKey();
                    String pamValue = e.getPamValue();
                    //初始化顶级分类配置信息
                    Set<String> tempSet = getConfigSet(innerTopCategoryMap, pamCode);
                    tempSet.add(pamValue);
                    //初始化二级分类配置信息
                    innerSubCategoryMap.put(pamCode + pamKey, pamValue);

                });
        topCategoryMap = innerTopCategoryMap;
        subCategoryMap = innerSubCategoryMap;
        logger.info("初始化一级分类配置：{}", topCategoryMap);
        logger.info("初始化二级分类配置：{}", subCategoryMap);
    }

    /**
     * @param pamCode 一级分类码
     * @return 该一级分类码下所有的配置信息
     */
    private Set<String> getConfigSet(Map<String, Set<String>> topCategoryMap, String pamCode) {
        return topCategoryMap.computeIfAbsent(pamCode, k -> new HashSet<>());
    }

}
