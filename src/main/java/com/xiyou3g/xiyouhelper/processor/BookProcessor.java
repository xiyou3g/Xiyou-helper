package com.xiyou3g.xiyouhelper.processor;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.Map;

import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYBOOK_HOST;

/**
 * @Author: zeng
 * @Date: 2018/7/20 20:04
 */
@Component
public class BookProcessor implements PageProcessor {

    private String sessionId;
    private String suchenType;
    private String suchenWord;
    private String libraryId;


    public BookProcessor(String sessionId, String suchenType, String suchenWord, String libraryId) {
        this.sessionId = sessionId;
        this.suchenType = suchenType;
        this.suchenWord = suchenWord;
        this.libraryId = libraryId;
    }

    private Site site = Site.me()
            .setDomain(XYBOOK_HOST)
            .addHeader("Host", XYBOOK_HOST)
            .addHeader("Cookie", this.sessionId)
            .addHeader("Referer", "http://222.24.3.7:8080/opac_two/search2/search_simpl");

    @Override
    public void process(Page page) {

        Html html = new Html(page.getRawText());
        System.out.println(html);
        System.out.println(html.xpath("//*[@id=\"searchout_tuwen\"]"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Request request = new Request("http://222.24.3.7:8080/opac_two/search2/searchout.jsp#");
        request.setMethod(HttpConstant.Method.GET);

        BookProcessor bookProcessor = new BookProcessor(
                "JSESSIONID=5D8124C0785793125EC7A80B7FBFC", "2",
                "高等数学", "all");

        Map<String,Object> params = new HashMap<>();
        params.put("search_no_type", "Y");
        params.put("snumber_type", "Y");
        params.put("suchen_type", bookProcessor.suchenType);
        params.put("suchen_word", bookProcessor.suchenWord);
        params.put("suchen_match", "mh");
        params.put("recordtype", "all");
        params.put("library_id", bookProcessor.libraryId);
        params.put("show_type", "wenzi");

        request.setRequestBody(HttpRequestBody.form(params, "UTF-8"));

        Spider.create(bookProcessor).addRequest(request).run();
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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
