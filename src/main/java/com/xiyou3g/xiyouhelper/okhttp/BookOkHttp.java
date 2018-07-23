package com.xiyou3g.xiyouhelper.okhttp;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.BOOK_LOGIN_PASSWORD;
import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.BOOK_LOGIN_URL;
import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.BOOK_LOGIN_USERNAME;


/**
 * @author zeng
 */
public class BookOkHttp {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

    public static String login(String barcode, String password) {

        if (barcode == null || password == null) {
            return null;
        }

        FormBody formBody = new FormBody.Builder()
                .add(BOOK_LOGIN_USERNAME, barcode)
                .add(BOOK_LOGIN_PASSWORD, password)
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(BOOK_LOGIN_URL)
                .post(formBody)
                .build();


        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String setCookie = response.headers().get("Set-Cookie");
            String sessionId = setCookie.substring(0, setCookie.indexOf(";"));

            if (sessionId != null) {
                return sessionId;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


}
