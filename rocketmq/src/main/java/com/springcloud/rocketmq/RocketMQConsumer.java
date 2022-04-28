package com.springcloud.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author chenpeng
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "my-group", topic = "topic-1")
public class RocketMQConsumer implements RocketMQListener {

    @Override
    public void onMessage(Object o) {
        log.info("消费topic-1接收参数={}", o);
    }
}
