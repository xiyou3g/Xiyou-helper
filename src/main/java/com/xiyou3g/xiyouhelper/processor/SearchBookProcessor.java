package com.xiyou3g.xiyouhelper.processor;

import com.xiyou3g.xiyouhelper.model.Book;
import com.xiyou3g.xiyouhelper.pipeline.BookPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.BOOK_HELP_URL_REGEX;
import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.BOOK_HOST;
import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.BOOK_TARGET_URL_REGEX;


/**
 * @Author: zeng
 * @Date: 2018/7/20 20:04
 */
public class SearchBookProcessor implements PageProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String sessionId;
    private String suchenType;
    private String suchenWord;
    private String libraryId;


    public SearchBookProcessor(String sessionId, String suchenType, String suchenWord, String libraryId) {
        this.sessionId = sessionId;
        this.suchenType = suchenType;
        this.suchenWord = suchenWord;
        this.libraryId = libraryId;
    }

    private Site site = Site.me().setRetryTimes(3).setTimeOut(10000);


    @Override
    public void process(Page page) {


//        爬取逻辑
        if (page.getUrl().regex(BOOK_HELP_URL_REGEX).match()) {
            page.addTargetRequests(page.getHtml().xpath("//*[@id=\"searchout_tuwen\"]/table/tbody").
                    links().regex(BOOK_TARGET_URL_REGEX).all());
        } else {

            page.putField("shelf", page.getHtml().xpath("//*[@id=\"example1\"]/li/ul/li/table/tbody/tr[2]/td[5]/text()").toString());
            page.putField("indexNumber", page.getHtml().xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[1]/td[2]/text()").toString());
            page.putField("publishingHouse", page.getHtml().xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[2]/td[2]/text()").toString());
            page.putField("leftNumber", (long) page.getHtml().xpath("//*[@id=\"example1\"]/li/ul/li/table/tbody/tr").all().size() / 2);

//            解析书名作者
            for (int i = 2; i <= 10; i++) {

                String h5Key = page.getHtml().xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[1]/text()").toString();

                if (h5Key.equals("题名和责任者说明 : ")) {
                    page.putField("bookName", page.getHtml().xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[2]/a/text()") +
                            page.getHtml().xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[2]/text()").toString());
                }

                if (h5Key.equals("责任者 : ")) {

                    List<Selectable> aAuthors = page.getHtml().xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[2]/a").nodes();

                    String author = "";

                    for (Selectable selectable : aAuthors) {

                        author += " " + selectable.xpath("//a/text()").toString();

                    }

                    if (author != null) {
                        page.putField("author", author);
                    }
                }
            }
        }


    }

    @Override
    public Site getSite() {
        return site;
    }


    public List<Book> searchBooks() {

        Request request = new Request("http://222.24.3.7:8080/opac_two/search2/searchout.jsp");
        request.setMethod(HttpConstant.Method.POST);

        request.setCharset("GBK")
                .addHeader("Host", BOOK_HOST)
                .addHeader("Cookie", this.sessionId)
                .addHeader("Referer", "http://222.24.3.7:8080/opac_two/search2/search_simple.jsp?search_no_type=Y&snumber_type=Y&show_type=Z")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");

        Map<String,Object> params = new HashMap<>();
        logger.info("suchen type " + this.suchenType);
        logger.info("sessionId " + this.sessionId);
        params.put("suchen_type", this.suchenType);
        params.put("suchen_word", this.suchenWord);
        params.put("suchen_match", "mh");
        params.put("recordtype", "all");
        params.put("library_id", this.libraryId);

        request.setRequestBody(HttpRequestBody.form(params, "GBK"));

        BookPipeline bookPipeline = new BookPipeline();
        Spider.create(this)
                .addPipeline(bookPipeline)
                .addRequest(request).thread(5).run();

        return bookPipeline.getBooks();
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
