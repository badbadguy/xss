package com.lry.xxs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.lry.xxs.mapper")       //加载mybatis
@SpringBootApplication                  //启动标签
@EnableCaching                          //开启缓存功能
@EnableScheduling                       //定时任务
public class XxsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(XxsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(XxsApplication.class);
    }
}
