package com.dcits.dcwlt.common.pay.sequence.service;

import com.dcits.dcwlt.common.pay.sequence.model.Result;


/**
 * 号段模式序列生成器接口
 *
 * @author lanleifang-yfzx
 * @Time 2020年3月9日
 * @Version 1.0
 */
public interface SegmentIdGenerator {

    /**
     * 根据key获取对应的id，通过key来区分不同业务模块
     *
     * @param key
     * @return 返回包含id的封装结果对象
     */
    Result get(String key);

    /**
     * 重置号段，清空号段在内存中的缓存值，并且将数据库的号段值设置为init_id
     *
     * @param key 指定需要重置的号段，通过key来辨别
     */
    void reset(String key);

    /**
     * 关闭整个号段对象，包括内部的线程池
     */
    void shutdown();
}
