package com.christli.study.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    protected static Logger logger = LoggerFactory.getLogger(Consumer.class);

//    @KafkaListener(topics = "testTopic")
//    public void onMessage(String message) {
//        logger.info("我是消费者，我接收到的消息是：" + message);
//        System.out.println("我是消费者，我接收到的消息是：" + message);
//    }
}
