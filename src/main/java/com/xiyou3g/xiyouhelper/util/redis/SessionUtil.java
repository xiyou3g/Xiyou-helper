package com.xiyou3g.xiyouhelper.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * @author zeng
 */
@Component
public class SessionUtil {

    @Resource
    private RedisTemplate redisTemplate;

    @SuppressWarnings("unchecked")
    public boolean setSessionId(String barcode, String sessionId) {

        if (barcode == null || sessionId == null) {
            return false;
        }

        System.out.println("test here");

        redisTemplate.opsForValue().set(barcode, sessionId, 1, TimeUnit.DAYS);
        return true;
    }
}
