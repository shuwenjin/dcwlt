package com.dcits.dcwlt.common.pay.sequence.generator;

import com.dcits.dcwlt.common.pay.sequence.util.SeqDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 号段每日重置的定时器任务类，通过此类进行重置注册
 * @author lanleifang-yfzx
 * @Time 2020年3月9日
 * @Version 1.0
 */
public class SegmentIdResetScheduler {

	private static final Logger logger = LoggerFactory.getLogger(SegmentIdResetScheduler.class);

	//存储需要定时重置的标签的重置时间，例如：   orderTag -->  02:00:00
	private static Map<String, String> resetTimeMap = new HashMap<String, String>();
	//存储定时重置标签的到期更新时间
	private static Map<String, Long> expireTimeMap = new ConcurrentHashMap<String, Long>();

	private SegmentIdResetScheduler() { }

	/**
	 * 注册每天的过期时间，采用的是被动过期的策略，即每次获取id时，判断当前时间是否大于过期时间，如果是，则进行重置操作
	 * 注册格式：指定哪个tag，需要在每天的什么时间点
	 * 进行重置操作：重置操作确保有在数据库配置init_id，然后先重置数据库，再重置内存中的号段缓存
	 * 多台机器只需要最早的机器进行数据库重置即可，其他机器发现数据库被重置了，则只需要重置自己的缓存即可
	 * @param tag	业务标识
	 * @param timeExpr	表示每天需要重置的时刻点，日期格式字符串：HH:mm:ss，例如：08:23:04（表示早上8点23分4秒重置）
	 */
	public static void register(final String tag, final String timeExpr) {
		resetTimeMap.put(tag, timeExpr);
		expireTimeMap.put(tag, SeqDateUtils.getTodayDateByTimeStr(timeExpr).getTime());
        logger.info("segment [{}] registe reset scheduler({}) success", tag, timeExpr);
	}

	public static boolean isNeedSchedulReset(String tag) {
		return resetTimeMap.containsKey(tag);
	}

	public static boolean hasExpire(String tag) {
		return System.currentTimeMillis() > expireTimeMap.get(tag);
	}

	public static long getExpireTime(String tag) {
		return expireTimeMap.get(tag);
	}

	/**
     * 在判断出当前时间超过map中缓存的expireTime后，需要设置下一个过期时间
     * 下一个过期时间设置过程中，正常只要设置为第二天的时间即可，但是这里有特殊情况存在
     * 因为目前这里的过期是被动过期，即调用get()方法拿序列的时候才比较判断是否过期
     * 如果周五结束后，周六日都没有请求进来，这时候不会触发重置操作，直到周一凌晨2点又来了一个请求
     * 而过期时间是每天的04点，所以先触发一下重置操作，然后把过期时间设置为今天的04点
     * 如果请求是在周一的08点过来的，过期时间则需要设置为第二天的04点
     *
     * 即做一个判断，当前时间如果小于今天的过期时间，则下一个过期时间设置为今天的，
     * 当前时间如果大于或等于今天的过期时间，则下一个过期时间设置为明天的
     *
	 * @param tag
	 */
	public static void setNextExpireTime(String tag) {
		String expireTimeStr = resetTimeMap.get(tag);
		long todayExpireTime = SeqDateUtils.getTodayDateByTimeStr(expireTimeStr).getTime();

		if(System.currentTimeMillis() < todayExpireTime) {
			expireTimeMap.put(tag, todayExpireTime);
		}else {
			expireTimeMap.put(tag, SeqDateUtils.getTomorrowDateByTimeStr(expireTimeStr).getTime());
		}
		logger.info("tag[{}] 设置缓存中的下一个过期时间成功，设置后的过期时间为：{}", tag, new Date(expireTimeMap.get(tag)));
	}
}
