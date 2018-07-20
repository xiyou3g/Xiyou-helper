package com.xiyou3g.xiyouhelper.okhttp;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * mengchen
 * 18-7-20 下午7:37
 */
@Component
public class EduOkHttp {
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

    public OkHttpClient getClient() {
        return client;
    }
}
