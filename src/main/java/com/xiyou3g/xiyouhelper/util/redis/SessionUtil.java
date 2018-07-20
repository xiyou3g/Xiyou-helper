package com.xiyou3g.xiyouhelper.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zeng
 */
@Component
public class SessionUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    @SuppressWarnings("unchecked")
    public boolean setSessionId(String barcode, String sessionId) {

        if (barcode == null || sessionId == null) {
            return false;
        }

        System.out.println(barcode + " " + sessionId);

        redisTemplate.opsForValue().set(barcode, sessionId);
        return true;
    }

    public String getSessionId(String key) {

        return (String) redisTemplate.opsForValue().get(key);
    }
}
