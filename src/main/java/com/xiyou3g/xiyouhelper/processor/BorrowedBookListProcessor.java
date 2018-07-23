package com.xiyou3g.xiyouhelper.processor;

import com.xiyou3g.xiyouhelper.model.BookStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;


import java.util.ArrayList;
import java.util.List;

import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.BOOK_BORROWED_URL;
import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.BOOK_HOST;

/**
 * @Author: zeng
 * @Date: 2018/7/22 9:44
 */
public class BorrowedBookListProcessor implements PageProcessor {


    private Logger logger = LoggerFactory.getLogger(getClass());

    private String sessionId;

    private Site site = Site.me().setRetryTimes(3).setTimeOut(10000).setCharset("GBK");

    private List<BookStatus> borrowedBooks;


    public BorrowedBookListProcessor(String sessionId) {
        this.sessionId = sessionId;
    }




    @Override
    public void process(Page page) {

        String flag = page.getHtml().xpath("/html/body/form[1]/table/tbody/tr[1]").toString();

        if (flag == null) {
            return;
        }


        this.borrowedBooks = new ArrayList<>();

        for (int i = 2; i <= 12; i++) {

//            是否有借书结点
            String innerFlag = page.getHtml().xpath("/html/body/form[1]/table/tbody/tr[" + i + "]").toString();

            if (innerFlag == null) {
                break;
            }

            BookStatus bookStatus = new BookStatus();

            bookStatus.setBookName(
                    page.getHtml().xpath("/html/body/form[1]/table/tbody/tr[" + i + "]/td[3]/text()").toString());
            bookStatus.setShouldReturnDay(
                    page.getHtml().xpath("/html/body/form[1]/table/tbody/tr[" + i +"]/td[7]/text()").toString());
            bookStatus.setBookCode(
                    page.getHtml().xpath("/html/body/form[1]/table/tbody/tr[" + i + "]/td[4]/text()").toString());

            this.borrowedBooks.add(bookStatus);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }


    public List<BookStatus> getMyBorrowedBooks() {

        Request request = new Request(BOOK_BORROWED_URL);
        request.setMethod(HttpConstant.Method.GET);

        request.setCharset("GBK")
                .addHeader("Host", BOOK_HOST)
                .addHeader("Cookie", this.sessionId)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36\n")
                .addHeader("Referer", "http://222.24.3.7:8080/opac_two/reader/infoList.jsp")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Upgrade-Insecure-Requests", "1");

        Spider.create(this)
                .addRequest(request)
                .run();

        return this.borrowedBooks;

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

    public List<BookStatus> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BookStatus> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

}
