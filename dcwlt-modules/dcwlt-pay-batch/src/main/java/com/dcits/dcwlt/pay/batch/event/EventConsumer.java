package com.dcits.dcwlt.pay.batch.event;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.constant.EventConst;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.api.mq.event.IEventDealAppService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "topic-dcwlt",
        selectorExpression = "*",
        consumerGroup = "consumer-group-1")
public class EventConsumer implements RocketMQListener<String> {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    @Autowired
    private IEventDealAppService eventDealAppService;
    @Override
    public void onMessage(String o) {
        try {
            JSONObject msgBody = JSONObject.parseObject(o);
            logger.info("接收mq消息：{}",msgBody.toJSONString());
            EventDealReqMsg eventDealReqMsg = JSONObject.toJavaObject(msgBody, EventDealReqMsg.class);
            if (EventConst.BATCH.equals(eventDealReqMsg.getExceptEventMsgTag())) {
                eventDealAppService.eventDeal(eventDealReqMsg);
            } else {
                logger.info("dcwlt-pay-batch支付批量服务只处理BATCH消息，其它消息由dcwlt-pay-online支付联机服务处理");
            }
            eventDealAppService.eventDeal(eventDealReqMsg);
        } catch (Exception e) {
            logger.error("事件登记异常:{}", e.getMessage(), e);
            throw e;
        }
    }
}