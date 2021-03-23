package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.enums.TaskGroupEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventBatchDetailParam;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventBatchTotalParam;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskExecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 各种事件处理服务
 * @author maozewu-yfzx
 */
@Service
@Component("batch")
public class EventHandlerService {
	private static final Logger logger = LoggerFactory.getLogger(EventHandlerService.class);
	@Autowired
	private ISettleTaskExecService execService;
	private static final String SUCCESS = Constant.EVENT_SUCC;
	/**
	 * 对总账事件处理里入口
	 * @param eventDealReqMsg
	 */
	public String handleCheckGeneralLedger(EventDealReqMsg eventDealReqMsg) {
		//获取evenInfo参数
		EventBatchTotalParam param = JSON.parseObject(eventDealReqMsg.getExceptEventContext(), EventBatchTotalParam.class);
		logger.info("handle check start by param={}", param);
		try {
			execService.runTaskGroup(param.getBatchDate(), param.getBatchId(), TaskGroupEnum.CHK_SUM.getCode(), null);
		} catch (SettleTaskException e) {
			logger.error("执行对总账任务抛出异常：{}",  e.getMessage());
		} catch (Exception e) {
			logger.error("执行对总账任务抛出异常, ", e);
		}
		return SUCCESS;
	}

	/**
	 * 对明细账事件处理里入口
	 * @param eventDealReqMsg
	 */
	public String handleCheckDetail(EventDealReqMsg eventDealReqMsg) {
		EventBatchDetailParam param = JSON.parseObject(eventDealReqMsg.getExceptEventContext(), EventBatchDetailParam.class);
		logger.info("handle check start by param={}", param);
		try {
			execService.runTaskGroup(param.getBatchDate(), param.getBatchId(), TaskGroupEnum.CHK_DTL.getCode(), param.getDigitalEnvelope());
		} catch (SettleTaskException e) {
			logger.error("执行对明细账任务抛出异常：{}",  e.getMessage());
		} catch (Exception e) {
			logger.error("执行明细账任务抛出异常：", e);
		}
		return SUCCESS;
	}
}
