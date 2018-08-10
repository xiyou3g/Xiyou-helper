package com.xiyou3g.xiyouhelper.parse;

import com.xiyou3g.xiyouhelper.model.*;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.*;


/**
 * @author zeng
 */
@Component
public class BookParse {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OkHttpClient client;

    public String login(String barcode, String password) {

        if (barcode == null || password == null) {
            return null;
        }

        logger.info("url {}", BOOK_LOGIN_URL + "?login_type=barcode&barcode=" + barcode + "&password=" + password);

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(BOOK_LOGIN_URL + "?login_type=barcode&barcode=" + barcode + "&password=" + password)
                .get()
                .build();


        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String responseBody = new String(response.body().bytes());
            if ("ok".equals(responseBody)) {

                String setCookie = response.headers().get("Set-Cookie");
                String sessionId = setCookie.substring(0, setCookie.indexOf(";"));
                logger.info("sessionId {}", sessionId);
                if (sessionId != null) {
                    return sessionId;
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


    public SearchBookResult searchBook(String suchenType, String suchenWord, int curPage) {

        if (suchenWord == null) {
            return null;
        }

        FormBody formBody = new FormBody.Builder(Charset.forName("GBK"))
                .add("suchen_type", suchenType)
                .add("suchen_word", suchenWord)
                .add("library_id", "all")
                .add("search_no_type", "Y")
                .add("snumber_type", "Y")
                .add("suchen_match", "mh")
                .add("recordtype", "all")
                .add("curpage", String.valueOf(curPage))
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

                Integer hasResultFlag =
                        Integer.valueOf(html.xpath("/html/body/center/form/table[1]/tbody/tr[2]/td/span[1]/strong/text()").toString());
                logger.info("记录数 " + hasResultFlag);

//        检索结果记录为0
                if (hasResultFlag == 0) {
                    return null;
                }

                SearchBookResult searchBookResult = new SearchBookResult();

                searchBookResult.setCurPage(Integer.valueOf(html.xpath("/html/body/center/form/table[2]/tbody/tr[2]/td[1]/span[1]/strong/text()").toString()));

                List<CurPageBookResult> curPageBookResults = new ArrayList<>();

                String tdFlag = null;
//        一页默认20条记录 爬取10条测试
                for (int i = 2; i <= 11; i++) {

//            行信息标记 为null的话停止爬取
                    tdFlag = html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[1]/text()").toString();
                    if (tdFlag == null) {
                        break;
                    }

                    CurPageBookResult curPageBookResult = new CurPageBookResult();

                    curPageBookResult.setLink(html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[2]/a/@href").toString());
                    curPageBookResult.setBookName(html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[2]/a/text()").toString());
                    curPageBookResult.setAuthor(html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[3]/text()").toString());
                    curPageBookResult.setPublishingHouse(html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[4]/text()").toString());
                    curPageBookResult.setStandardNumber(html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[5]/text()").toString());
                    curPageBookResult.setPublishingYear(html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[6]/text()").toString());
                    curPageBookResult.setIndexNumber(html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[7]/text()").toString());

                    String totalLeftStr = html.xpath("//*[@id=\"searchout_tuwen\"]/table/tbody/tr[" + i + "]/td[8]/text()").toString();
                    String[] splitStr = totalLeftStr.split(" ");
                    curPageBookResult.setTotal(Integer.valueOf(splitStr[0].substring(splitStr[0].length() - 1)));
                    curPageBookResult.setLeft(Integer.valueOf(splitStr[1].substring(splitStr[1].length() - 1)));

                    curPageBookResults.add(curPageBookResult);
                }

                searchBookResult.setCurPageBookResults(curPageBookResults);

                return searchBookResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String[] getArgsInJs(String sessionId, String bookcode) {

        if (sessionId == null || bookcode == null) {
            return null;
        }

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36")
                .addHeader("Referer", "http://222.24.3.7:8080/opac_two/reader/infoList.jsp")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("Host", BOOK_HOST)
                .addHeader("Cookie", sessionId)
                .url(BOOK_BORROWED_URL)
                .build();

        Call call = client.newCall(request);
        Response response = null;

        try {
            response = call.execute();
            String responseBody = new String(response.body().bytes(), "GBK");

            Html html = new Html(responseBody);

            String argsInJs = null;

            for (int i = 2; i <= 20; i++) {

//            取出onclick中的js函数参数模拟form请求
                if ((bookcode + " ").equals(html.xpath("/html/body/form[1]/table/tbody/tr[" + i + "]/td[4]/text()").toString())) {
                    argsInJs = html.xpath("/html/body/form[1]/table/tbody/tr[" + i + "]/td[8]/input/@onclick").toString();
                    break;
                }
            }

            if (argsInJs != null) {
                String innerArgs = argsInJs.substring(argsInJs.indexOf("Renew(") + 6, argsInJs.indexOf(");"));
                String[] argsArray = innerArgs.split(",");
                logger.info("获取到js续借参数");
                return argsArray;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String renew(String sessionId, String bookcode) {


        String[] argsArray = this.getArgsInJs(sessionId, bookcode);

        if (argsArray == null) {
            return null;
        }

        for (String arg : argsArray) {
            arg.substring(1, arg.length() - 1);
        }


        FormBody formBody = new FormBody.Builder(Charset.forName("GBK"))
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

    public BookDetail getBookDetail(String url) {

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36")
                .addHeader("Referer", "http://222.24.3.7:8080/opac_two/search2/searchout.jsp")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .url(url)
                .build();

        Call call = client.newCall(request);

        try {
            Response response = call.execute();

            String responseBody = new String(response.body().bytes(), "GBK");
            if (responseBody != null) {
                Html html = new Html(responseBody);

                BookDetail bookDetail = new BookDetail();
//                爬取逻辑
                bookDetail.setImg(html.xpath("//*[@id=\"image\"]/@src").toString());

                for (int i = 1; i <= 15; i++) {

                    String tdKey = html.xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[1]/text()").toString();
                    if (tdKey == null) {
                        break;
                    }

                    if ("题名和责任者说明 : ".equals(tdKey)) {
                        bookDetail.setBookName(html.xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[2]/a/text()") +
                                html.xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[2]/text()").toString());
                    }

                    if ("题名 : ".equals(tdKey)) {
                        bookDetail.setBookName(html.xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[2]/text()").toString());
                    }

                    if ("附注 : ".equals(tdKey)) {
                        bookDetail.setNote(html.xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[2]/text()").toString());
                    }

                    if ("简介 : ".equals(tdKey)) {
                        bookDetail.setIntroduction(html.xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[2]/text()").toString());
                    }

                    if ("载体形态 : ".equals(tdKey)) {
                        bookDetail.setIntroduction(html.xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[2]/text()").toString());
                    }

                    if ("ISBN/ISSN : ".equals(tdKey)) {
                        bookDetail.setIndexNumber(html.xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[2]/text()").toString());
                    }

                    if ("出版 : ".equals(tdKey) || "出版社 : ".equals(tdKey)) {
                        bookDetail.setPublish(html.xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[2]/text()").toString());
                    }

                    if ("中图分类号 : ".equals(tdKey)) {

                        bookDetail.setClassificationNumber(html.xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[9]/td[2]/a/text()").toString());
                    }

                    if ("分类号 : ".equals(tdKey)) {
                        bookDetail.setClassificationNumber(html.xpath("//*[@id=\\\"s_detail_book\\\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[\" + i + \"]/td[2]/text()").toString());
                    }

                    if ("主题 : ".equals(tdKey)) {
                        List<Selectable> themes = html.xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[2]/a").nodes();

                        StringBuilder themeStrBuilder = new StringBuilder();

                        if (themes.size() == 0) {
                            themeStrBuilder.append(html.xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[2]/td[2]/text()").toString());
                        }
                        for (Selectable selectable : themes) {
                            themeStrBuilder.append(selectable.xpath("//a/text()").toString());
                        }
                        if (themeStrBuilder != null) {
                            bookDetail.setTheme(themeStrBuilder.toString());
                        }
                    }

                    if ("责任者 : ".equals(tdKey)) {

                        List<Selectable> aAuthors = html.xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[" + i + "]/td[2]/a").nodes();
                        StringBuilder author = new StringBuilder();

                        if (aAuthors.size() == 0) {
                            author.append(html.xpath("//*[@id=\"s_detail_book\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/center/table/tbody/tr[2]/td[2]/text()").toString());
                        }
                        for (Selectable selectable : aAuthors) {
                            author.append(selectable.xpath("//a/text()").toString());
                        }
                        if (author != null) {
                            bookDetail.setAuthor(author.toString());
                        }
                    }
                }


//                存在馆内流动信息
                String innerFlag = html.xpath("//*[@id=\"example1\"]/li/ul/li/table/tbody/tr[2]").toString();

                if (innerFlag != null) {

                    bookDetail.setBookDistributionInformations(new ArrayList<>());

                    for (int i = 2; i <= 10; i += 2) {

                        String tdFlag = html.xpath("//*[@id=\"example1\"]/li/ul/li/table/tbody/tr[" + i + "]").toString();
                        if (tdFlag == null) {
                            break;
                        }

                        BookDistributionInformation bookDistributionInformation = new BookDistributionInformation();
                        bookDistributionInformation.setCallNumber(html.xpath("//*[@id=\"example1\"]/li/ul/li/table/tbody/tr[" + i + "]/td[3]/text()").toString());
                        bookDistributionInformation.setCollectionDepartment(html.xpath("//*[@id=\"example1\"]/li/ul/li/table/tbody/tr[" + i + "]/td[5]/text()").toString());
                        bookDistributionInformation.setStatus(html.xpath("//*[@id=\"example1\"]/li/ul/li/table/tbody/tr[" + i + "]/td[6]/text()").toString());

                        bookDetail.getBookDistributionInformations().add(bookDistributionInformation);

                    }
                }


                return bookDetail;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<BorrowedBook> getMyBorrowed(String sessionId) {

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36")
                .addHeader("Referer", "http://222.24.3.7:8080/opac_two/reader/infoList.jsp")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Cookie", sessionId)
                .addHeader("Upgrade-Insecure-Requests", "1")
                .url(BOOK_BORROWED_URL)
                .build();

        Call call = client.newCall(request);

        Response response = null;
        try {
            response = call.execute();
            String responseBody = new String(response.body().bytes(), "GBK");
            if (responseBody != null) {

                Html html = new Html(responseBody);

                String noBorrowedImg = html.xpath("//*[@id=\"no_text\"]/img").toString();

//                是否存在没有内容的img
                if (noBorrowedImg == null) {

                    logger.info("有借书信息");

                    List<BorrowedBook> borrowedBooks = new ArrayList<>();

                    for (int i = 2; i <= 10; i++) {

                        String tdFlag = html.xpath("/html/body/form[1]/table/tbody/tr[" + i + "]/td[2]").toString();
                        if (tdFlag == null) {
                            break;
                        }

                        BorrowedBook borrowedBook = new BorrowedBook();

                        borrowedBook.setBookName(html.xpath("/html/body/form[1]/table/tbody/tr[" + i + "]/td[3]/text()").toString());
                        String bookCode = html.xpath("/html/body/form[1]/table/tbody/tr[" + i + "]/td[4]/text()").toString();
                        borrowedBook.setBookCode(bookCode.substring(0, bookCode.length() - 1));
                        borrowedBook.setCollectionDepartment(html.xpath("/html/body/form[1]/table/tbody/tr[" + i + "]/td[5]/text()").toString());
                        borrowedBook.setCirculationStatus(html.xpath("/html/body/form[1]/table/tbody/tr[" + i + "]/td[6]/text()").toString());
                        borrowedBook.setShouldReturnDay(html.xpath("/html/body/form[1]/table/tbody/tr[" + i  + "]/td[7]/text()").toString());

                        borrowedBooks.add(borrowedBook);
                    }

                    logger.info("即将返回有数据的List");
                    return borrowedBooks;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("无借书信息");

        return null;
    }

    public List<BorrowedBookHistory> getMyBorrowedBooksHistory(String sessionId) {

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36")
                .addHeader("Referer", "http://222.24.3.7:8080/opac_two/reader/infoList.jsp")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Cookie", sessionId)
                .addHeader("Upgrade-Insecure-Requests", "1")
                .url(BOOK_BORROWED_HISTORY_URL)
                .build();

        Call call = client.newCall(request);

        Response response = null;

        try {
            response = call.execute();

            String responseBody = new String(response.body().bytes(), "GBK");
            if (responseBody != null) {

                Html html = new Html(responseBody);
                String noHistoryImg = html.xpath("//*[@id=\"no_text\"]/img").toString();
//                存在借书历史
                if (noHistoryImg == null) {

                    List<BorrowedBookHistory> borrowedBookHistories = new ArrayList<>();

                    for (int i = 2; i <=20; i++) {
                        String tdFlag = html.xpath("/html/body/table/tbody/tr[1]/td/table/tbody/tr[" + i + "]").toString();

                        if (tdFlag == null) {
                            break;
                        }

                        BorrowedBookHistory borrowedBookHistory = new BorrowedBookHistory();
                        borrowedBookHistory.setBookName(html.xpath("/html/body/table/tbody/tr[1]/td/table/tbody/tr[" + i + "]/td[2]/text()").toString());
                        borrowedBookHistory.setType(html.xpath("/html/body/table/tbody/tr[1]/td/table/tbody/tr[" + i + "]/td[4]/text()").toString());
                        borrowedBookHistory.setOperationTime(html.xpath("/html/body/table/tbody/tr[1]/td/table/tbody/tr[" + i + "]/td[5]/text()").toString());

                        borrowedBookHistories.add(borrowedBookHistory);
                    }

                    return borrowedBookHistories;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public BookMainInfo getMain(String sessionId) {

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36")
                .addHeader("Referer", "http://222.24.3.7:8080/opac_two/reader/infoList.jsp")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Cookie", sessionId)
                .addHeader("Upgrade-Insecure-Requests", "1")
                .url(BOOK_BORROWED_HISTORY_URL)
                .build();

        Call call = client.newCall(request);

        Response response = null;

        try {
            response = call.execute();
            String responseBody = new String(response.body().bytes(), "GBK");
            if (responseBody != null) {

                Html html = new Html(responseBody);
                String noHistoryImg = html.xpath("//*[@id=\"no_text\"]/img").toString();
//                存在借书历史
                if (noHistoryImg == null) {

                    BookMainInfo bookMainInfo = new BookMainInfo();
                    bookMainInfo.setCount(0);

                    for (int i = 2; i <=20; i++) {
                        String tdFlag = html.xpath("/html/body/table/tbody/tr[1]/td/table/tbody/tr[" + i + "]").toString();

                        if (tdFlag == null) {
                            break;
                        }

//                        统计一年内的借书数量
                        if ("借书 ".equals(html.xpath("/html/body/table/tbody/tr[1]/td/table/tbody/tr[2]/td[4]/text()").toString())) {
                            Integer currentCount = bookMainInfo.getCount();
                            bookMainInfo.setCount(currentCount + 1);
                        }
                    }

                    return bookMainInfo;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
