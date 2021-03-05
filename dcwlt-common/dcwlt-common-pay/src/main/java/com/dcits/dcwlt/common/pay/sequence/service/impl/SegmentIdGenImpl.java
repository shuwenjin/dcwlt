package com.dcits.dcwlt.common.pay.sequence.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.dcits.dcwlt.common.pay.sequence.entity.SegmentAllocatorEntity;
import com.dcits.dcwlt.common.pay.sequence.exception.SequenceException;
import com.dcits.dcwlt.common.pay.sequence.generator.SegmentIdResetScheduler;
import com.dcits.dcwlt.common.pay.sequence.mapper.SegAllocMapper;
import com.dcits.dcwlt.common.pay.sequence.model.Result;
import com.dcits.dcwlt.common.pay.sequence.model.Segment;
import com.dcits.dcwlt.common.pay.sequence.model.SegmentBuffer;
import com.dcits.dcwlt.common.pay.sequence.model.Status;
import com.dcits.dcwlt.common.pay.sequence.service.SegmentIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 号段模式id生成器实现类
 *
 * @author lanleifang-yfzx
 * @Time 2020年3月9日
 * @Version 1.0
 */
@Service
@EnableScheduling
public class SegmentIdGenImpl implements SegmentIdGenerator {
    private static final Logger logger = LoggerFactory.getLogger(SegmentIdGenImpl.class);

    /**
     * 最大步长不超过100,0000
     */
    private int maxStep = 1000000;

    /**
     * 一个Segment维持时间为15分钟
     */
    private static final long SEGMENT_DURATION = 15 * 60 * 1000L;

    @Autowired
    private SegAllocMapper segAllocMapper;

    //异步从数据库拉取新号段的线程池
    private ExecutorService service = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new UpdateThreadFactory());
    ScheduledExecutorService scheduleService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("Segment-check-idCache-thread");
            t.setDaemon(true);
            return t;
        }
    });
    private Map<String, SegmentBuffer> cache = new ConcurrentHashMap<>();

    public static class UpdateThreadFactory implements ThreadFactory {
        private static int threadInitNumber = 0;

        private static synchronized int nextThreadNum() {
            return threadInitNumber++;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Thread-Segment-Update-" + nextThreadNum());
        }
    }


    //线程池，定时每分钟扫描数据库，加载或者删除变化的biz_tag，实现biz_tag的热部署，线程为后台线程
    @Scheduled(cron = "0 0/1 * * * ?")
    private void updateCacheFromDbAtEveryMinute() {
        scheduleService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                updateCacheFromDb();
            }
        }, 60, 60, TimeUnit.SECONDS);
    }

    //通过定时任务每分钟扫描一次数据库，动态更新加载biz_tag的变化，实现热更新
    @PostConstruct
    private void updateCacheFromDb() {
        logger.info("update cache from db");
        try {
            List<String> dbTags = segAllocMapper.getAllTags();
            if (dbTags == null || dbTags.isEmpty()) {
                return;
            }
            List<String> cacheTags = new ArrayList<String>(cache.keySet());
            List<String> insertTags = new ArrayList<String>(dbTags);
            List<String> removeTags = new ArrayList<String>(cacheTags);
            //db中新加的tags灌进cache
            insertTags.removeAll(cacheTags);
            for (String tag : insertTags) {
                SegmentBuffer buffer = new SegmentBuffer();
                buffer.setKey(tag);
                Segment segment = buffer.getCurrent();
                segment.init();
                cache.put(tag, buffer);
                logger.info("Add tag {} from db to IdCache, SegmentBuffer {}", tag, buffer);
            }
            //cache中已失效的tags从cache删除
            removeTags.removeAll(dbTags);
            for (String tag : removeTags) {
                cache.remove(tag);
                logger.info("Remove tag {} from IdCache", tag);
            }
        } catch (Exception e) {
            logger.error("update cache from db exception", e);
        }
    }

    @Override
    public Result get(final String key) {

        checkHasExpire(key);

        checkBufferHasInit(key);

        return getIdFromSegmentBuffer(cache.get(key));
    }

    /**
     * 判断缓存号段的状态是否初始化，如果未未初始化状态，则加双重检查锁进行判断
     *
     * @param key 号段名称
     */
    private void checkBufferHasInit(String key) {
        SegmentBuffer buffer = cache.get(key);
        if (!buffer.isInitOk()) {
            synchronized (buffer) {
                if (!buffer.isInitOk()) {
                    updateSegmentFromDb(key, buffer.getCurrent());
                    logger.info("Init buffer. Update leafkey {} {} from db", key, buffer.getCurrent());
                    buffer.setInitOk(true);
                }
            }
        }
    }

    /**
     * 判断当前号段是否需要进行定时重置（条件是有配置定时重置时间且当前时间有超过重置时间）
     *
     * @param key
     * @return
     */
    private boolean needToReset(String key) {
        if (SegmentIdResetScheduler.isNeedSchedulReset(key) && SegmentIdResetScheduler.hasExpire(key)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否需要完成定时重置功能
     * 1、检查该key对应的号段是否需要执行定时重置
     * 2、判断定时重置时间是否到了：当前时间 > 定时重置时间
     * 3、如果到期了，则更新数据库进行重置，然后清空缓存
     *
     * @param key 号段标识
     */
    private void checkHasExpire(String key) {
        SegmentBuffer buffer = cache.get(key);
        if (needToReset(key)) {
            synchronized (buffer) {
                if (needToReset(key)) {
                    //对当前号段进行重置
                    logger.info("tag [{}] 超过定时重置时间点，开始重置.", key);
                    reset(key);
                    logger.info("tag [{}] 定时重置成功.", key);
                }
            }
        }
    }

    /*
     * 	重置号段，清空号段在内存中的缓存值，并且将数据库的号段值设置为init_id
     * 	此处更新有这么几个关键点：
     * 1、更新之前，需要先获得写锁，因为这里用的是ReentrantReadWriteLock，
     * 	获得写锁的前提是没有别的线程读和写，即读锁和写锁都没有线程占用，这里担心别的异步线程会更新号段，提前去拿取缓存下一个号段
     * 2、更新的时候，先更新数据库，再清空内存
     * 3、finally中务必释放锁
     */
    public void reset(String key) {
        SegmentBuffer buffer = cache.get(key);
        buffer.wLock().lock();
        try {
            //将数据库的maxId更新为initId
            long expireTime = SegmentIdResetScheduler.getExpireTime(key);
            resetMaxId(key, expireTime);

            //归零号段，清空缓存
            buffer.reset();

            //重新设置下一个过期时间点
            SegmentIdResetScheduler.setNextExpireTime(key);
        } finally {
            buffer.wLock().unlock();
        }
    }

    private void updateSegmentFromDb(String key, Segment segment) {
        SegmentBuffer buffer = segment.getBuffer();
        SegmentAllocatorEntity segAllocEntity;
        if (!buffer.isInitOk()) {
            segAllocEntity = updateMaxIdAndGetSegAlloc(key, null);
            buffer.setStep(segAllocEntity.getStep());
            buffer.setMinStep(segAllocEntity.getStep());//leafAlloc中的step为DB中的step
        } else if (buffer.getUpdateTimestamp() == 0) {
            segAllocEntity = updateMaxIdAndGetSegAlloc(key, null);
            buffer.setUpdateTimestamp(System.currentTimeMillis());
            buffer.setStep(segAllocEntity.getStep());
            buffer.setMinStep(segAllocEntity.getStep());//leafAlloc中的step为DB中的step
        } else {
            long duration = System.currentTimeMillis() - buffer.getUpdateTimestamp();
            int nextStep = buffer.getStep();
            if (duration < SEGMENT_DURATION) {
                if (nextStep * 2 > maxStep) {
                    //do nothing
                } else {
                    nextStep = nextStep * 2;
                }
            } else if (duration < SEGMENT_DURATION * 2) {
                //do nothing with nextStep
            } else {
                nextStep = nextStep / 2 >= buffer.getMinStep() ? nextStep / 2 : nextStep;
            }

            logger.info("leafKey[{}], step[{}], duration[{}mins], nextStep[{}]", key, buffer.getStep(), String.format("%.2f", ((double) duration / (1000 * 60))), nextStep);
            segAllocEntity = updateMaxIdAndGetSegAlloc(key, nextStep);
            buffer.setUpdateTimestamp(System.currentTimeMillis());
            buffer.setStep(nextStep);
            buffer.setMinStep(segAllocEntity.getStep());//leafAlloc的step为DB中的step
        }
        // must set value before set max
        long value = segAllocEntity.getValue();
        segment.getValue().set(value);
        segment.setMax(segAllocEntity.getMaxId());
        segment.setStep(buffer.getStep());
        segment.setInitId(segAllocEntity.getInitId());
        segment.setMaxTotal(segAllocEntity.getMaxTotal());
        logger.info("key:{} updateSegmentFromDb finish, {}", key, segment);
    }

    private Result getIdFromSegmentBuffer(final SegmentBuffer buffer) {
        //当号段消耗过快时，异步拉取号段可能还没回来，这时候需要线程等待
        //while循环每执行一次，waitAndSleep()方法中就会休眠10毫秒，这里设置最多休眠40次，即等待最多400毫秒
        //如果超过400毫秒还无法获取序列，则抛出异常，说缓存中的号段都消耗完了
        //注意：之前设置的是count=20，即休眠200毫秒，经过测试，单线程跑100w，本地数据库，是没有问题的，不会抛异常
        //但是，改为多线程并发，100个线程并发访问，每个线程生成1w个id，共计100w，本地数据库，会抛异常，估计是数据库加锁访问并发的时候性能损耗，
        //无法在200ms内完成，所以改成了300ms后就好了，为了保险起见，设置成为了400
        int count = 40;
        while (count-- > 0) {
            buffer.rLock().lock();
            try {
                final Segment segment = buffer.getCurrent();
                tryToBufferNextSegment(buffer, segment);

                long value = segment.getValue().getAndIncrement();
                if (value < segment.getMax()) {
                    return new Result(value, Status.SUCCESS);
                }
            } finally {
                buffer.rLock().unlock();
            }
            waitAndSleep(buffer);

            Result result = changeToNextSegment(buffer);
            if (result != null) {
                return result;
            }
        }

        //循环等待了多次，即共count * 10 毫秒，如果都获取不到号段，则抛出异常
        throw new SequenceException("Both two segments in " + buffer + " are not ready!", SequenceException.EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL);
    }

    /**
     * 当下一个号段未准备好，且当前号段消耗大于10%，并且没有线程在获取下一个号段的时候，开始启动异步线程更新下一个号段
     *
     * @param buffer
     * @param segment
     */
    private void tryToBufferNextSegment(final SegmentBuffer buffer, Segment segment) {
        if (!buffer.isNextReady() && (segment.getIdle() < 0.9 * segment.getStep()) && buffer.getThreadRunning().compareAndSet(false, true)) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    Segment next = buffer.getSegments()[buffer.nextPos()];
                    boolean updateOk = false;
                    try {
                        updateSegmentFromDb(buffer.getKey(), next);
                        updateOk = true;
                        logger.info("update segment {} from db {}", buffer.getKey(), next);
                    } catch (Exception e) {
                        logger.error(buffer.getKey() + " updateSegmentFromDb exception", e);
                    } finally {
                        if (updateOk) {
                            buffer.wLock().lock();
                            buffer.setNextReady(true);
                            buffer.wLock().unlock();
                        }
                        buffer.getThreadRunning().set(false);
                    }
                }
            });
        }
    }

    /**
     * 因为是双号段缓存，所以当前一个号段用完后，切换到下一个号段
     *
     * @param buffer
     */
    private Result changeToNextSegment(SegmentBuffer buffer) {
        buffer.wLock().lock();
        try {
            final Segment segment = buffer.getCurrent();
            long value = segment.getValue().getAndIncrement();
            if (value < segment.getMax()) {
                return new Result(value, Status.SUCCESS);
            }
            if (buffer.isNextReady()) {
                buffer.switchPos();
                buffer.setNextReady(false);
            }
        } finally {
            buffer.wLock().unlock();
        }
        return null;
    }

    //如果号段用完，且从数据库拉取新号段还未完成时，会调用此方法进行自旋等待，每次10毫秒
    private void waitAndSleep(SegmentBuffer buffer) {
        int roll = 0;
        while (buffer.getThreadRunning().get()) {
            roll += 1;
            if (roll > 10000) {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    logger.error("Thread {} Interrupted", Thread.currentThread().getName());
                }
                break;
            }
        }
    }

    public Map<String, SegmentBuffer> getCache() {
        return cache;
    }

    /**
     * 关闭定时更新的线程池，以及关闭定时重置的线程池
     */
    @Override
    public void shutdown() {
        service.shutdown();
        scheduleService.shutdown();
        logger.info("SegmentIdGenerator shutdown ok!");
    }





    //--------------------------------------------------------------------------------------------
    /*
     * 1、查询当前biz_tag对应的详细信息，使用for update锁住记录
     * 2、如果传入的step有值且大于0，则使用传入的step，否则使用数据库读取到的的step，传入的step是动态step
     * 3、判断max_total是否等于0，如果等于0，说明没有最高上限，直接按原计划递增，即max_id = max_id + step
     * 4、如果max_total大于0，说明有最高上限
     * 5、有最高上限的话，用当前max_id 加上 step，如果大于或等于totalMax，说明已经到达上限了
     * 6、如果未到达最高上限，则继续按原计划递增，即max_id = max_id + step
     * 7、到达上限了则剩余多少就取多少，将内存中的max_id设置为totalMax，然后更新数据库的max_id为initId，表示重新来过
     * 8、为了让最后一个上限值能使用完，需要在maxTotal + 1
     */
    public SegmentAllocatorEntity updateMaxIdAndGetSegAlloc(String tag, Integer step) {
        try {
            SegmentAllocatorEntity result = segAllocMapper.getLeafAlloc(tag);
            result.setUpdateTime(new Date());

            if (step != null && step > 0) {
                result.setStep(step);
            }

            if (result.getMaxTotal() > 0 && (result.getMaxId() + result.getStep() >= result.getMaxTotal())) {
                logger.info("segment of tag {} has arrived max_total {}, then will init max_id.", tag, result.getMaxTotal());

                //说明超过了上限或者正好到达上限，用完余下的号段，并重新来过
                result.setValue(result.getMaxId());    //设置起始值就是上一个号段的最大值
                result.setMaxId(result.getMaxTotal() + 1);    //为了耗尽最后一个maxTotal，需要加1

                if (result.getInitId() > 0) {
                    segAllocMapper.initMaxId(result);
                } else {
                    throw new SequenceException("init_id of biz_tag [" + result.getKey() + "] can not be less than 1", SequenceException.EXCEPTION_INIT_ID_LESS_THAN_ONE);
                }

            } else if (result.getMaxTotal() < 0) {
                throw new SequenceException("max_total of biz_tag [" + result.getKey() + "] can not be less than zero", SequenceException.EXCEPTION_MAX_TOTAL_LESS_THAN_ZERO);
            } else {
                result.setValue(result.getMaxId());    //设置起始值就是上一个号段的最大值
                result.setMaxId(result.getMaxId() + result.getStep());
                segAllocMapper.updateMaxId(result);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重置max_id为init_id(因为是分布式模式，多台机器中只需要一台机器修改即可，所以需要通过时间来判断)
     * 1、查询数据库对应tag记录，通过for update的方式锁住数据行，保证数据的准确性
     * 2、接着判断：UPDATE_TIME是否有 >= 定时重置的时间
     * 3、如果是，说明已经被别的机器改过了，直接跳过即可
     * 4、如果没有，则说明没有机器改过，则进行UPDATE操作，且UPDATE_TIME修改为当前Java服务器系统时间（统一取Java服务器时间，不取数据库时间）
     */
    public void resetMaxId(String tag, long expireTime) {
        logger.info("tag[{}]定时重置触发，开始进行数据库的重置操作.", tag);

        try {
            SegmentAllocatorEntity result = segAllocMapper.getLeafAlloc(tag);
            if (result.getUpdateTime().getTime() >= expireTime) {
                logger.info("tag[{}] 已经被定时重置过了，无需重置.", tag);
                return;
            }
            if (result.getInitId() > 0) {
                result.setUpdateTime(new Date());
                segAllocMapper.initMaxId(result);
                logger.info("tag[{}]定时重置数据库操作成功", tag);
            } else {
                throw new SequenceException("init_id of biz_tag [" + result.getKey() + "] can not be less than 1", SequenceException.EXCEPTION_INIT_ID_LESS_THAN_ONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
