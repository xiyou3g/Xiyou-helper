package com.xiyou3g.xiyouhelper.parse;

import com.xiyou3g.xiyouhelper.model.SearchBookResult;
import com.xiyou3g.xiyouhelper.processor.ReNewProcessor;
import okhttp3.*;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.*;


/**
 * @author zeng
 */
public class BookOkHttp {

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
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


    public List<SearchBookResult> searchBook(String suchenType, String suchenWord,
                                             String libraryId) {

        if (suchenWord == null) {
            return null;
        }


        FormBody formBody = new FormBody.Builder()
                .add("suchen_type", suchenType)
                .add("suchen_word", suchenWord)
                .add("library_id", libraryId)
                .add("search_no_type", "Y")
                .add("snumber_type", "Y")
                .add("suchen_match", "mh")
                .add("recordtype", "all")
                .build();


        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SEARCH_BOOK_URL)
                .addHeader("Host", BOOK_HOST)
                .addHeader("Referer", "http://222.24.3.7:8080/opac_two/search2/search_simple.jsp?search_no_type=Y&snumber_type=Y&show_type=Z")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36")
                .addHeader("Origin", BOOK_HOST)
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .post(formBody)
                .build();


        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();

            String responseBody = new String(response.body().bytes(), "GBK");
            if (responseBody != null) {
                Html html = new Html(responseBody);
                System.out.println(html);

                Integer hasResultFlag =
                        Integer.valueOf(html.xpath("/html/body/center/form/table[1]/tbody/tr[2]/td/span[1]/strong/text()").toString());

//        检索结果记录为0
                if (hasResultFlag == 0) {
                    return null;
                }

                List<SearchBookResult> searchBookResultList = new ArrayList<>();

                String tdFlag = null;
//        一页默认20条记录 爬取10条测试
                for (int i = 2; i <= 11; i++) {

//            行信息标记 为null的话停止爬取
                    tdFlag = html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[1]/text()").toString();
                    if (tdFlag == null) {
                        break;
                    }

                    SearchBookResult searchBookResult = new SearchBookResult();

                    searchBookResult.setBookName(html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[2]/a/text()").toString());
                    searchBookResult.setAuthor(html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[3]/text()").toString());
                    searchBookResult.setPublishingHouse(html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[4]/text()").toString());
                    searchBookResult.setStandardNumber(html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[5]/text()").toString());
                    searchBookResult.setPublishingYear(html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[6]/text()").toString());
                    searchBookResult.setIndexNumber(html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[7]/text()").toString());

                    String totalLeftStr = html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[8]/text()").toString();
                    String[] splitStr = totalLeftStr.split(" ");
                    searchBookResult.setTotal(Integer.valueOf(splitStr[0].substring(splitStr[0].length() - 1)));
                    searchBookResult.setLeft(Integer.valueOf(splitStr[1].substring(splitStr[1].length() - 1)));

                    System.out.println(searchBookResult.getAuthor());
                    System.out.println(searchBookResult.getBookName());

                    searchBookResultList.add(searchBookResult);
                }

                return searchBookResultList;
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
