package com.dubbo.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenpeng
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    /**
     * http://localhost:10800/config/get
     *
     * 手动添加配置中心配置
     * curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=dubbo-spring-cloud-provider&group=DEFAULT_GROUP&content=useLocalCache=true"
     */
    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }
}
