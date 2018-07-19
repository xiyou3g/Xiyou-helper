package com.xiyou3g.xiyouhelper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// 去除Security的配置
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("com.xiyou3g.xiyouhelper.dao")
public class XiyouHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiyouHelperApplication.class, args);
    }
}
