package com.lry.xxs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.lry.xxs.mapper")
@SpringBootApplication
public class XxsApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxsApplication.class, args);
    }

}
