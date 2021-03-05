package com.dcits.dcwlt.common.pay.sequence.entity;

import java.util.Date;

/**
 * 对应数据库号段模式配置表实体类
 *
 * @author lanleifang-yfzx
 * @Time 2020年3月9日
 * @Version 1.0
 */
public class SegmentAllocatorEntity {
    private long value;        //号段的起始值
    private String key;        //号段的tag
    private long maxId;        //号段的最大值
    private int step;        //步长
    private long initId;    //id的初始值
    private long maxTotal;    //整个序列的最大值，即上限值
    private Date createTime;
    private Date updateTime;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getMaxId() {
        return maxId;
    }

    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public long getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(long maxTotal) {
        this.maxTotal = maxTotal;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getInitId() {
        return initId;
    }

    public void setInitId(long initId) {
        this.initId = initId;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SegmentAllocatorEntity [value=" + value + ", key=" + key + ", maxId=" + maxId + ", step=" + step
                + ", initId=" + initId + ", maxTotal=" + maxTotal + ", createTime=" + createTime + ", updateTime="
                + updateTime + "]";
    }
}
