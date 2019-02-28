package com.lry.xxs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.lry.xxs.mapper")       //加载mybatis
@SpringBootApplication
@EnableCaching                          //开启缓存功能
@EnableScheduling                       //定时任务
public class XxsApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxsApplication.class, args);
    }

}
