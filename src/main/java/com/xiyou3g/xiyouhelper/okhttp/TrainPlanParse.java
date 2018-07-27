package com.xiyou3g.xiyouhelper.okhttp;

import okhttp3.*;
import org.springframework.util.StreamUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_HOST;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_SESSION_KEY;
import static com.xiyou3g.xiyouhelper.util.constant.EduConstant.TRAINPLAN_URL;

/**
 * @author mengchen
 * @time 18-7-26 下午8:40
 */
public class TrainPlanParse implements PageProcessor {

    private Site site = Site.me()
            .setDomain(XYE_HOST)
                .addHeader("Host", XYE_HOST)
                .addHeader("Referer", "http://222.24.62.120/pyjh.aspx?xh=04161031&xm=%C3%CF%B3%BF&gnmkdm=N121607")
                .addCookie(XYE_SESSION_KEY, "a0psffq3leu23zvynm2ihp55");
//    public String getHidden(String studentNum, String name, String sessionId) throws IOException {
//        String url = String.format(TRAINPLAN_URL, studentNum, name);
//        Request request = new Request.Builder().url("http://222.24.62.120/pyjh.aspx?xh=04161031&xm=%C3%CF%B3%BF&gnmkdm=N121607")
//                .addHeader("Host", "222.24.62.120")
//                .addHeader("Refer", "http://222.24.62.120/pyjh.aspx?xh=04161031&xm=%C3%CF%B3%BF&gnmkdm=N121607")
//                .addHeader("Cookie", "ASP.NET_SessionId=a0psffq3leu23zvynm2ihp55")
//                .build();
//        Call call = client.newCall(request);
//        Response response = call.execute();
//        InputStream inputStream = response.body().byteStream();
//        String htmlStr = StreamUtils.copyToString(inputStream, Charset.forName("GBK"));
//        Html html = new Html(htmlStr);
//        System.out.println(htmlStr);
//        String hidden = html.xpath("//*[@id=\"Form1\"]/input[3]/@value").get();
//
//        return hidden;
//    }

    @Override
    public void process(Page page) {
        System.out.println(page);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new TrainPlanParse()).addUrl("http://222.24.62.120/pyjh.aspx?xh=04161031&xm=%C3%CF%B3%BF&gnmkdm=N121607")
                .run();
    }
}
