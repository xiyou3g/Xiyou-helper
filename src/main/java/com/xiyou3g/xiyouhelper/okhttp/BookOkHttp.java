package com.xiyou3g.xiyouhelper.okhttp;

import com.xiyou3g.xiyouhelper.processor.ReNewProcessor;
import okhttp3.*;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.*;


/**
 * @author zeng
 */
@Component
public class BookOkHttp {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

    public String login(String barcode, String password) {

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

    public String renew(String sessionId, String bookBarcode) {

        if (sessionId == null || bookBarcode == null) {
            return null;
        }

        ReNewProcessor reNewProcessor = new ReNewProcessor(sessionId, bookBarcode);

        us.codecraft.webmagic.Request request = new us.codecraft.webmagic.Request("http://222.24.3.7:8080/opac_two/reader/jieshuxinxi.jsp");
        request.setMethod(HttpConstant.Method.GET);
        request.setCharset("GBK")
                .addHeader("Host", BOOK_HOST)
                .addHeader("Cookie", reNewProcessor.getSessionId())
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36\n")
                .addHeader("Referer", "http://222.24.3.7:8080/opac_two/reader/infoList.jsp")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Upgrade-Insecure-Requests", "1");


        Spider.create(reNewProcessor)
                .addRequest(request)
                .run();


        String[] argsArray = reNewProcessor.getArgsInJsArray();

        for (String arg : argsArray) {
            arg.substring(1, arg.length() - 1);
        }




        FormBody formBody = new FormBody.Builder()
                .add(BOOK_RENEW_ACTION, "Renew")
                .add(BOOK_RENEW_BARCODE, argsArray[0])
                .add(DEPARTMENT_ID, argsArray[1])
                .add(LIBRARY_ID, argsArray[2])
                .build();

        final okhttp3.Request okHttpRequest = new okhttp3.Request.Builder()
                .addHeader("Cookie", sessionId)
                .url(BOOK_BORROWED_URL)
                .post(formBody)
                .build();


        Call call = client.newCall(okHttpRequest);
        try {

            Response response = call.execute();

            byte[] responseBytes = response.body().bytes();
            String responseBodyStr = new String(responseBytes, "GBK");

            Html html = new Html(responseBodyStr);
            String script = html.xpath("/html/body/script[3]").toString();
            String renewResponse = script.substring(script.indexOf("alert(\"") + 7, script.indexOf("\");"));

            return renewResponse;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


}
