package com.springcloud.mysql;

import com.github.pagehelper.PageInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
public class MysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(MysqlApplication.class, args);
    }

    /**
     * 配置mybatis的分页插件pageHelper
     *
     * @return 分页
     */
    @Bean
    public PageInterceptor pageHelper() {
        PageInterceptor pageHelper = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }

}
