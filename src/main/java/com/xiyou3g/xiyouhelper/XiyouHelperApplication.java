package com.xiyou3g.xiyouhelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// 去除Security的配置
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class XiyouHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiyouHelperApplication.class, args);
    }
}
