package com.dcits.dcwlt.common.pay.sequence.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 缓存的号段对象
 * @author lanleifang-yfzx
 * @Time 2020年3月9日
 * @Version 1.0
 */
public class Segment {
    private AtomicLong value = new AtomicLong(0);	//号段的当前id值，每获取一次就加1
    private volatile long max;		//当前号段的最大值，value 需要小于max
    private volatile int step;		//步长
    private volatile long initId;	//初始值，如果是循环模式或者重置的话，会重写初始化为initId
    private volatile long maxTotal;	//号段的最大上限，0表示没有上限值，到达了上限会重写初始化为initId
    
    private SegmentBuffer buffer;

    public Segment(SegmentBuffer buffer) {
        this.buffer = buffer;
    }

    public AtomicLong getValue() {
        return value;
    }

    public void setValue(AtomicLong value) {
        this.value = value;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public SegmentBuffer getBuffer() {
        return buffer;
    }

    public long getIdle() {
        return this.getMax() - getValue().get();
    }    
    
    public long getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(long maxTotal) {
		this.maxTotal = maxTotal;
	}	
	public long getInitId() {
		return initId;
	}
	public void setInitId(long initId) {
		this.initId = initId;
	}

	public void init(){
		this.value.set(0);
        this.setMax(0);
        this.setStep(0);
        this.setMaxTotal(0);
        this.setInitId(0);
	}
	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Segment(");
        sb.append("value:");
        sb.append(value);
        sb.append(",max:");
        sb.append(max);
        sb.append(",step:");
        sb.append(step);
        sb.append(",maxTotal:");
        sb.append(maxTotal);
        sb.append(",initId:");
        sb.append(initId);
        sb.append(")");
        return sb.toString();
    }
}
