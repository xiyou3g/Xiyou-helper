package com.xiyou3g.xiyouhelper.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: zeng
 * @Date: 2018/7/20 17:29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void setString() {
        redisTemplate.opsForValue().set("123", "123");
    }

    @Test
    public void getString() {
        System.out.println(redisTemplate.opsForValue().get("123"));
    }
}
