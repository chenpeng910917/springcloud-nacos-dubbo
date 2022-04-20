package com.dubbo.provider.controller;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenpeng
 */
@RestController
@RequestMapping("/config")
public class ConfigUpdateOrInsertController {

    /**
     * http://localhost:10800/config/getConfig
     */
    @SneakyThrows
    @RequestMapping(value = "/getConfig")
    public String getConfig() {
        ConfigService configService = NacosFactory.createConfigService("127.0.0.1:8848");
        String config = configService.getConfig("dubbo-spring-cloud-provider", "DEFAULT_GROUP", 3000);
        return config;
    }

    /**
     * http://localhost:10800/config/insert
     */
    @SneakyThrows
    @RequestMapping(value = "/insert")
    public Boolean insert() {
        ConfigService configService = NacosFactory.createConfigService("127.0.0.1:8848");
        boolean config = configService.publishConfig("dubbo-spring-cloud-provider", "DEFAULT_GROUP", "useLocalCache=true");
        return config;
    }

    /**
     * http://localhost:10800/config/update
     */
    @SneakyThrows
    @RequestMapping(value = "/update")
    public Boolean update() {
        ConfigService configService = NacosFactory.createConfigService("127.0.0.1:8848");
        boolean config = configService.publishConfig("dubbo-spring-cloud-provider", "DEFAULT_GROUP", "useLocalCache=false");
        return config;
    }

    /**
     * http://localhost:10800/config/delete
     */
    @SneakyThrows
    @RequestMapping(value = "/delete")
    public Boolean delete() {
        ConfigService configService = NacosFactory.createConfigService("127.0.0.1:8848");
        boolean config = configService.removeConfig("dubbo-spring-cloud-provider", "DEFAULT_GROUP");
        return config;
    }


}
