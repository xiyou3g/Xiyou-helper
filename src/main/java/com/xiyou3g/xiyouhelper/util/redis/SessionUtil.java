package com.xiyou3g.xiyouhelper.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zeng
 */
@Component
public class SessionUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    @SuppressWarnings("unchecked")
    public boolean setSessionId(String prefix, String barcode, String sessionId) {

        if (barcode == null || sessionId == null) {
            return false;
        }

        String finalKey = prefix + barcode;

        redisTemplate.opsForValue().set(finalKey, sessionId, 10, TimeUnit.MINUTES);
        return true;
    }

    public boolean setSessionIdLong(String prefix, String barcode, String sessionId) {
        if (barcode == null || sessionId == null) {
            return false;
        }

        String finalKey = prefix + barcode;

        redisTemplate.opsForValue().set(finalKey, sessionId, 2, TimeUnit.HOURS);
        return true;
    }

    public String getSessionId(String prefix, String key) {

        String finalKey = prefix + key;

        return (String) redisTemplate.opsForValue().get(finalKey);
    }


    public boolean removeSessionId(String prefix, String key) {
        String finalKey = prefix + key;

        return redisTemplate.delete(finalKey);
    }

}
