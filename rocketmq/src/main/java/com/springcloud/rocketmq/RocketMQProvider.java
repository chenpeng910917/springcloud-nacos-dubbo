package com.springcloud.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenpeng
 */
@Component
public class RocketMQProvider {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void run() {
        rocketMQTemplate.convertAndSend("topic-1", "hello world");
    }
}
