package com.xiyou3g.xiyouhelper.processor;

import com.xiyou3g.xiyouhelper.model.SearchBookResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.BOOK_HOST;


/**
 * @Author: zeng
 * @Date: 2018/7/20 20:04
 */
@Component
public class SearchBookProcessor implements PageProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private List<SearchBookResult> searchBookResultList;
    private String suchenType;
    private String suchenWord;
    private String libraryId;


    private Site site = Site.me().setRetryTimes(3).setTimeOut(10000).setCharset("GBK");


    @Override
    public void process(Page page) {

//        Integer hasResultFlag =
//                Integer.valueOf(page.getHtml().xpath("/html/body/center/form/table[1]/tbody/tr[2]/td/span[1]/strong/text()").toString());
//
////        检索结果记录为0
//        if (hasResultFlag == 0) {
//            return;
//        }
//
//        this.searchBookResultList = new ArrayList<>();
//
//        String tdFlag = null;
////        一页默认20条记录 爬取10条测试
//        for (int i = 2; i <= 11; i ++) {
//
////            行信息标记 为null的话停止爬取
//            tdFlag = page.getHtml().xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[1]/text()").toString();
//            if (tdFlag == null) {
//                return;
//            }
//
//
//            SearchBookResult searchBookResult = new SearchBookResult();
//
////            searchBookResult.setLink(page.getHtml().xpath(""));
//            searchBookResult.setBookName(page.getHtml().xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[2]/a/text()").toString());
//            searchBookResult.setAuthor(page.getHtml().xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[3]/text()").toString());
//            searchBookResult.setPublishingHouse(page.getHtml().xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[4]/text()").toString());
//            searchBookResult.setStandardNumber(page.getHtml().xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[5]/text()").toString());
//            searchBookResult.setPublishingYear(page.getHtml().xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[6]/text()").toString());
//            searchBookResult.setIndexNumber(page.getHtml().xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[7]/text()").toString());
//
//            String totalLeftStr = page.getHtml().xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[8]/text()").toString();
//            String[] splitStr = totalLeftStr.split(" ");
//            searchBookResult.setTotal(Integer.valueOf(splitStr[0].substring(splitStr[0].length() - 1)));
//            searchBookResult.setLeft(Integer.valueOf(splitStr[1].substring(splitStr[1].length() - 1)));
//
//            System.out.println(searchBookResult.getAuthor());
//            System.out.println(searchBookResult.getBookName());
//
//            this.searchBookResultList.add(searchBookResult);
        }


    @Override
    public Site getSite() {
        return site;
    }


    public List<SearchBookResult> searchBook() {

//        Request request = new Request("http://222.24.3.7:8080/opac_two/search2/searchout.jsp");
//        request.setMethod(HttpConstant.Method.POST);
//
//        request.setCharset("GBK")
//                .addHeader("Host", BOOK_HOST)
//                .addHeader("Referer", "http://222.24.3.7:8080/opac_two/search2/search_simple.jsp?search_no_type=Y&snumber_type=Y&show_type=Z")
//                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36")
//                .addHeader("Origin", BOOK_HOST)
//                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
//                .addHeader("Cookie", "JSESSIONID=65EA0321795B7EE89FC3F69DFC421516");
//
//        Map<String,Object> params = new HashMap<>();
//        params.put("search_no_type", "Y");
//        params.put("snumber_type", "Y");
//        params.put("suchen_type", this.suchenType);
//        params.put("suchen_word", this.suchenWord);
//        params.put("suchen_match", "mh");
//        params.put("recordtype", "all");
//        params.put("library_id", this.libraryId);
//
//        request.setRequestBody(HttpRequestBody.form(params, "GBK"));
//
//        Spider.create(this)
//                .addRequest(request).thread(5).run();

        return this.searchBookResultList;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public List<SearchBookResult> getSearchBookResultList() {
        return searchBookResultList;
    }

    public void setSearchBookResultList(List<SearchBookResult> searchBookResultList) {
        this.searchBookResultList = searchBookResultList;
    }

    public String getSuchenType() {
        return suchenType;
    }

    public void setSuchenType(String suchenType) {
        this.suchenType = suchenType;
    }

    public String getSuchenWord() {
        return suchenWord;
    }

    public void setSuchenWord(String suchenWord) {
        this.suchenWord = suchenWord;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
