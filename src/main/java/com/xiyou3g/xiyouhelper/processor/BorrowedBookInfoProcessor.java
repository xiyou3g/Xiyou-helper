package com.xiyou3g.xiyouhelper.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;


import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.BOOK_HOST;

/**
 * @Author: zeng
 * @Date: 2018/7/22 9:44
 */
public class BorrowedBookInfoProcessor implements PageProcessor {


    private Logger logger = LoggerFactory.getLogger(getClass());

    private String sessionId;


    public BorrowedBookInfoProcessor(String sessionId) {
        this.sessionId = sessionId;
    }

    private Site site = Site.me().setRetryTimes(3).setTimeOut(10000);


    @Override
    public void process(Page page) {

        System.out.println(page.getHtml());

    }

    @Override
    public Site getSite() {
        return site;
    }



    public static void main(String[] args) {


        BorrowedBookInfoProcessor bookStatusProcessor = new BorrowedBookInfoProcessor(
                "JSESSIONID=EFC1DFEE9D50903DB6C618AC851AFA5C");


        Request request = new Request("http://222.24.3.7:8080/opac_two/reader/jieshuxinxi.jsp");
        request.setMethod(HttpConstant.Method.GET);

        request.setCharset("GBK")
                .addHeader("Host", BOOK_HOST)
                .addHeader("Cookie", bookStatusProcessor.sessionId)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36\n")
                .addHeader("Referer", "http://222.24.3.7:8080/opac_two/reader/infoList.jsp")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Upgrade-Insecure-Requests", "1");


        Spider.create(bookStatusProcessor)
                .addRequest(request)
                .thread(5).run();
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
