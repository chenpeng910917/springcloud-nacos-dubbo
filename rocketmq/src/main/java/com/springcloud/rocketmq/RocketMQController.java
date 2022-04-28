package com.springcloud.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenpeng
 */
@Slf4j
@RestController
public class RocketMQController {

    @Resource
    private RocketMQProvider RocketMQProvider;

    @RequestMapping("/sendSmg")
    public void sendSmg() {
        RocketMQProvider.sendMessage();
    }
}
