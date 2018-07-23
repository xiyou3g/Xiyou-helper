package com.xiyou3g.xiyouhelper.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


/**
 * @Author: zeng
 * @Date: 2018/7/23 23:14
 */
public class ReNewProcessor implements PageProcessor {

    private String argsInJs;

    private String BookBarCode;

    private String sessionId;

    private Site site = Site.me().setRetryTimes(3).setTimeOut(10000).setCharset("GBK");

    public ReNewProcessor(String sessionId, String bookCode) {
        this.sessionId = sessionId;
        this.BookBarCode = bookCode;
    }

    @Override
    public void process(Page page) {

        String flag = page.getHtml().xpath("/html/body/form[1]/table/tbody/tr[1]").toString();

        if (flag == null) {
            return;
        }


        for (int i = 2; i <= 12; i++) {

//            是否有借书结点
            String innerFlag = page.getHtml().xpath("/html/body/form[1]/table/tbody/tr[" + i + "]").toString();

            if (innerFlag == null) {
                break;
            }

//            取出onclick中的js函数参数模拟form请求
            if (this.BookBarCode.equals(page.getHtml().xpath("/html/body/form[1]/table/tbody/tr[" + i + "]/td[4]/text()").toString())) {
                this.setArgsInJs(page.getHtml().xpath("/html/body/form[1]/table/tbody/tr[" + i + "]/td[8]/input/@onclick").toString());
            }
        }
    }

    public String[] getArgsInJsArray() {


        String argsLine = this.getArgsInJs();
        String innerArgs = argsLine.substring(argsLine.indexOf("Renew(") + 6, argsLine.indexOf(");"));
        String[] argsArray = innerArgs.split(",");


        return argsArray;

    }


//    public static void main(String[] args) {
//
//        ReNewProcessor reNewProcessor = new ReNewProcessor(
//                "JSESSIONID=AB9FC951EE7056566C691DADAEE01D07");
//
//        reNewProcessor.setBookBarCode("03248396 ");
//
//        Request request = new Request("http://222.24.3.7:8080/opac_two/reader/jieshuxinxi.jsp");
//        request.setMethod(HttpConstant.Method.GET);
//        request.setCharset("GBK")
//                .addHeader("Host", BOOK_HOST)
//                .addHeader("Cookie", reNewProcessor.sessionId)
//                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36\n")
//                .addHeader("Referer", "http://222.24.3.7:8080/opac_two/reader/infoList.jsp")
//                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
//                .addHeader("Upgrade-Insecure-Requests", "1");
//
//        Spider.create(reNewProcessor)
//                .addRequest(request)
//                .run();
//
//        String[] argsArray = reNewProcessor.getArgsInJsArray();
//
//        for (String arg : argsArray) {
//            System.out.println(arg);
//        }
//
//    }

    @Override
    public Site getSite() {
        return site;
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

    public String getArgsInJs() {
        return argsInJs;
    }

    public void setArgsInJs(String argsInJs) {
        this.argsInJs = argsInJs;
    }

    public String getBookBarCode() {
        return BookBarCode;
    }

    public void setBookBarCode(String bookBarCode) {
        this.BookBarCode = bookBarCode;
    }
}
