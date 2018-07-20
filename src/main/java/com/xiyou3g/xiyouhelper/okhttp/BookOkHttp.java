package com.xiyou3g.xiyouhelper.okhttp;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.BOOK_LOGIN_PASSWORD;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.BOOK_LOGIN_URL;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.BOOK_LOGIN_USERNAME;

/**
 * @author zeng
 */
public class BookOkHttp {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

    public static boolean login(String barcode, String password) {

        if (barcode == null || password == null) {
            return false;
        }

        FormBody formBody = new FormBody.Builder()
                .add(BOOK_LOGIN_USERNAME, barcode)
                .add(BOOK_LOGIN_PASSWORD, password)
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(BOOK_LOGIN_URL)
                .post(formBody)
                .build();

//        创建Call
        Call call = client.newCall(request);
//        加入队列 异步
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("连接失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("连接成功");
                System.out.println(response.toString());
                System.out.println(response.headers());
            }
        });

        return true;
    }


    public static String getMyBorrowedBooks() {
        return null;
    }

    public static void main(String[] args) {
        login("04163209", "163209");
    }
}
