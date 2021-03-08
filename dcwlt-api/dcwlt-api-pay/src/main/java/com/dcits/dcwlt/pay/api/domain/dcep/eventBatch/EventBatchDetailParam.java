package com.dcits.dcwlt.pay.api.domain.dcep.eventBatch;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 应用间参数传第对象格式
 * @author maozewu-yfzx
 */
public class EventBatchDetailParam {
    /**
     * 对账日期
     */
    private String batchDate;

    /**
     * 对账批次号
     */
    private String batchId;
    
    /**
     * 数字信封
     */
    private String digitalEnvelope;
    
    @JSONField(name = "BatchDate")    
	public String getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}
	
	@JSONField(name = "BatchId")
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	@JSONField(name = "DigitalEnvelope")
	public String getDigitalEnvelope() {
		return digitalEnvelope;
	}

	public void setDigitalEnvelope(String digitalEnvelope) {
		this.digitalEnvelope = digitalEnvelope;
	}

	@Override
	public String toString() {
		return "EventBatchDetailParam [batchDate=" + batchDate + ", batchId=" + batchId + ", digitalEnvelope=***]";
	}
	
}
