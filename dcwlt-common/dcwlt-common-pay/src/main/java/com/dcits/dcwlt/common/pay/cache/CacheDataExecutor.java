package com.dcits.dcwlt.common.pay.cache;

/**
 * 所有实现该接口的参数加载都将被加载
 */
public interface CacheDataExecutor {

    String getTableName();

    void cacheData();
}
