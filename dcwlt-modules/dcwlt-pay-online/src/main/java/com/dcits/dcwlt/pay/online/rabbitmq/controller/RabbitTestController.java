//package com.dcits.dcwlt.pay.online.rabbitmq.controller;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/mq")
//public class RabbitTestController {
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//
//
//    @GetMapping("/send1")
//    public String sendTopicMessage2() {
//        String messageId = String.valueOf(UUID.randomUUID());
//        String messageData = "message: woman is all ";
//        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        Map<String, Object> womanMap = new HashMap<>();
//        womanMap.put("messageId", messageId);
//        womanMap.put("messageData", messageData);
//        womanMap.put("createTime", createTime);
//        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap);
//        return "ok";
//    }
//}
