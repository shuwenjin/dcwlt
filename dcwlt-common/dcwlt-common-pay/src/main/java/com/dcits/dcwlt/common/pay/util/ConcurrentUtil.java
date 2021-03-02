/*********************************************
 * Copyright (c) 2019 LI-RTP.
 * All rights reserved.
 * Created on 2019年11月21日
 *
 * Contributors:
 *     rtp - initial implementation
 *********************************************/

package com.dcits.dcwlt.common.pay.util;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 并发工具
 * 
 *
 *
 */
public class ConcurrentUtil {
	/**
	 * 获取map对应值，若值存在则直接返回，否则加载并保存后返回
	 * 
	 * @param dataMap 数据
	 * @param key     键
	 * @param loader  加载器
	 * @return 值
	 */
	public static <K, V> V getMapValue(Map<K, V> dataMap, K key, Supplier<V> loader) {
		return getMapValue(dataMap, key, (value) -> value != null, loader);
	}
	/**
	 * 获取map对应值，若值判定通过则直接返回，否则加载并保存后返回
	 * 
	 * @param dataMap 数据
	 * @param key     键
	 * @param predicate 判定器
	 * @param loader  加载器
	 * @return 值
	 */
	public static <K, V> V getMapValue(Map<K, V> dataMap, K key, Predicate<V> predicate, Supplier<V> loader) {
		return getValueWithDoubleLock(dataMap, 
				() -> dataMap.get(key), 
				(value) -> dataMap.put(key, value), 
				predicate,
				loader);
	}

	/**
	 * 使用双锁方式获取值，若值判定通过则直接返回，否则重新加载
	 * @param lockObj 锁对象
	 * @param getter 值getter
	 * @param setter 值setter
	 * @param predicate 判定器
	 * @param loader 值loader
	 * @return 值
	 */
	public static <T> T getValueWithDoubleLock(Object lockObj, Supplier<T> getter, Consumer<T> setter, 
			Predicate<T> predicate, Supplier<T> loader) {
		// 获取值
		T value = getter.get();
		// 判定通过则直接返回
		if (predicate.test(value)) {
			return value;
		}
		// 同步加载
		synchronized (lockObj) {
			// 再次尝试
			value = getter.get();
			if (predicate.test(value)) {
				return value;
			}
			// 加载
			value = loader.get();
			// 保存
			setter.accept(value);
			// 返回
			return value;
		}
	}
}
