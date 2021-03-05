package com.dcits.dcwlt.common.pay.sequence.model;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 双buffer缓存类，内部缓存了两个Segement号段对象
 *
 * @author lanleifang-yfzx
 * @Time 2020年3月9日
 * @Version 1.0
 */
public class SegmentBuffer {
    private String key;
    private Segment[] segments; //双buffer
    private volatile int currentPos; //当前的使用的segment的index
    private volatile boolean nextReady; //下一个segment是否处于可切换状态
    private volatile boolean initOk; //是否初始化完成
    private final AtomicBoolean threadRunning; //拉取下一个号段的线程是否在运行中
    private final ReadWriteLock lock;

    private volatile int step;        //实际步长
    private volatile int minStep;    //数据库中的步长
    //当前号段开始消耗的起始时间，用于记录号段消耗的总耗时，动态调整步长
    private volatile long updateTimestamp;

    public SegmentBuffer() {
        segments = new Segment[]{new Segment(this), new Segment(this)};
        currentPos = 0;
        nextReady = false;
        initOk = false;
        threadRunning = new AtomicBoolean(false);
        lock = new ReentrantReadWriteLock();
        updateTimestamp = 0;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Segment[] getSegments() {
        return segments;
    }

    public Segment getCurrent() {
        return segments[currentPos];
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public int nextPos() {
        return (currentPos + 1) % 2;
    }

    public void switchPos() {
        currentPos = nextPos();
    }

    public boolean isInitOk() {
        return initOk;
    }

    public void setInitOk(boolean initOk) {
        this.initOk = initOk;
    }

    public boolean isNextReady() {
        return nextReady;
    }

    public void setNextReady(boolean nextReady) {
        this.nextReady = nextReady;
    }

    public AtomicBoolean getThreadRunning() {
        return threadRunning;
    }

    public Lock rLock() {
        return lock.readLock();
    }

    public Lock wLock() {
        return lock.writeLock();
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getMinStep() {
        return minStep;
    }

    public void setMinStep(int minStep) {
        this.minStep = minStep;
    }

    public long getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(long updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    /**
     * 重置号段，先将数据库的max_id更新为init_id，然后清空内存中的值
     */
    public void reset() {
        //判断是否在更新中，如果在异步更新中，需要等待，直到异步线程更新完成，才开始此处的重置归零操作
        if (threadRunning.compareAndSet(false, true)) {
            for (Segment seg : segments) {
                seg.init();
            }

            currentPos = 0;
            nextReady = false;
            initOk = false;
            threadRunning.set(false);
            updateTimestamp = 0;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SegmentBuffer{");
        sb.append("key='").append(key).append('\'');
        sb.append(", segments=").append(Arrays.toString(segments));
        sb.append(", currentPos=").append(currentPos);
        sb.append(", nextReady=").append(nextReady);
        sb.append(", initOk=").append(initOk);
        sb.append(", threadRunning=").append(threadRunning);
        sb.append(", step=").append(step);
        sb.append(", minStep=").append(minStep);
        sb.append(", updateTimestamp=").append(updateTimestamp);
        sb.append('}');
        return sb.toString();
    }
}
