package com.xiyou3g.xiyouhelper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author mengchen
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("com.xiyou3g.xiyouhelper.dao")
@EnableCaching
public class XiyouHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiyouHelperApplication.class, args);
    }
}
