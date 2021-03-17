package com.dcits.dcwlt.pay.online.mq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "queue_test_topic",
        selectorExpression = "*",
        consumerGroup = "queue_group_test")
public class EventConsumer implements RocketMQListener {
    @Override
    public void onMessage(Object o) {

    }
}
