//package com.dcits.dcwlt.common.mq;
//
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EventProducer {
//
//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;
//
//    public void sendMsg(String topic, String msg) {
//        this.rocketMQTemplate.convertAndSend(topic, msg);
//    }
//}
