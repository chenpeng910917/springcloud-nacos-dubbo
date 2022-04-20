package com.dubbo.consumer.controller;

import com.provider.rpc.HelloProviderRpc;
import com.provider.rpc.model.param.HelloProviderParam;
import com.provider.rpc.model.vo.HelloProviderVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenpeng
 */
@Slf4j
@RestController
public class HelloConsumerController {

    @DubboReference(version = "1.0.0", group = "provider", timeout = 3000)
    private HelloProviderRpc helloProviderRpc;

    @GetMapping(value = "hello")
    public HelloProviderVO hello(HelloProviderParam param) {
        log.info("HelloConsumerController param={}", param);
        HelloProviderVO helloProviderVO = helloProviderRpc.helloProvider(param);
        return helloProviderVO;
    }

}
