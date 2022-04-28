package com.springcloud.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenpeng
 */
@Component
public class RocketMQProvider {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void sendMessage() {
        Message<String> message = MessageBuilder.withPayload("生成消息").setHeader("KEYS", "1234").build();

        rocketMQTemplate.send("topic-1", message);
    }
}
