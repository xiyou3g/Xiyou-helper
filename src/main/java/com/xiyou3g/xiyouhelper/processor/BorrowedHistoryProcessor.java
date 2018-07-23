package com.xiyou3g.xiyouhelper.processor;

import com.xiyou3g.xiyouhelper.pipeline.BookStatusPipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.BOOK_BORROWED_URL;
import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.BOOK_HOST;

/**
 * @Author: zeng
 * @Date: 2018/7/23 17:04
 */
public class BorrowedHistoryProcessor implements PageProcessor {

    private String sessionId;

    private String bookCode;

    private String borrowDay;


    private Site site = Site.me().setRetryTimes(3).setTimeOut(1000).setCharset("GBK");

    public BorrowedHistoryProcessor(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void process(Page page) {

        for (int i = 2; i < 20; i++) {

            String tdFlag = page.getHtml().xpath("/html/body/table/tbody/tr[1]/td/table/tbody/tr[" + i + "]").toString();

            if (tdFlag == null) {
                break;
            }

            String typeFlag = page.getHtml().xpath("/html/body/table/tbody/tr[1]/td/table/tbody/tr[" + i + "]/td[3]/text()").toString();

            if (this.bookCode.equals(typeFlag)
                    && "借书 ".equals(page.getHtml()
                    .xpath("/html/body/table/tbody/tr[1]/td/table/tbody/tr[" + i + "]/td[4]/text()").toString())) {

                this.borrowDay = page.getHtml()
                        .xpath("/html/body/table/tbody/tr[1]/td/table/tbody/tr[" + i + "]/td[5]/text()").toString();
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }



    public String getBorrowDay(String bookCode) {

        this.bookCode = bookCode;


        Request request = new Request(BOOK_BORROWED_URL);
        request.setMethod(HttpConstant.Method.GET);

        request.setCharset("GBK")
                .addHeader("Host", BOOK_HOST)
                .addHeader("Cookie", this.sessionId)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36\n")
                .addHeader("Referer", "http://222.24.3.7:8080/opac_two/reader/infoList.jsp")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Upgrade-Insecure-Requests", "1");

        BookStatusPipeline bookStatusPipeline = new BookStatusPipeline();

        Spider.create(this)
                .addRequest(request)
                .addPipeline(bookStatusPipeline)
                .thread(5)
                .run();

        return this.borrowDay;
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

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getBorrowDay() {
        return borrowDay;
    }

    public void setBorrowDay(String borrowDay) {
        this.borrowDay = borrowDay;
    }
}
