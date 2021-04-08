package com.dcits.dcwlt.common.mq;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendMsg(String tags, String topic, String msg, int delayTimeLevel) {
        Message message = new Message();
        message.setTags(tags);
        message.setTopic(topic);
        message.setBody(msg.getBytes());
        message.setDelayTimeLevel(delayTimeLevel);
        try {
            this.rocketMQTemplate.getProducer().send(message);
        } catch (Exception e) {
            throw new ServiceMQException("消息发送队列异常:" + e.getMessage());
        }
    }
}
