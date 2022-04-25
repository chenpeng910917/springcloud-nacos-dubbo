package com.dubbo.provider.controller;

import com.consumer.rpc.HelloConsumerRpc;
import com.consumer.rpc.model.param.HelloConsumerParam;
import com.consumer.rpc.model.vo.HelloConsumerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenpeng
 */
@Api(tags = "HelloProviderController")
@Slf4j
@RestController
public class HelloProviderController {

    @DubboReference(version = "1.0.0", group = "consumer", timeout = 3000)
    private HelloConsumerRpc helloConsumerRpc;

    /**
     * http://localhost:10800/hello?name=李四&age=11
     *
     * @param param 入参
     * @return 反参
     */
    @ApiOperation("你好")
    @GetMapping(value = "/hello")
    public HelloConsumerVO hello(HelloConsumerParam param) {
        log.info("HelloProviderController param={}", param);
        HelloConsumerVO helloConsumerVO = helloConsumerRpc.helloConsumer(param);
        return helloConsumerVO;
    }
}
