package com.xiyou3g.xiyouhelper.processor;

import com.xiyou3g.xiyouhelper.model.Course;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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

import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_HOST;
import static com.xiyou3g.xiyouhelper.util.constant.CurriculumConstant.*;

/**
 * mengchen
 * 18-7-20 上午9:30
 */
public class CurriculumProcessor implements PageProcessor {

    private String curriculumUrl = "http://222.24.62.120/xskbcx.aspx?xh=04161031&xm=%C3%CF%B3%BF&gnmkdm=N121603";
    private String sessionId = "m0k0yx45gcgy3gv0opy1r12n";
    private String year = "2017-2018";
    private String term = "1";


    private Site site = Site.me()
            .setDomain(XYE_HOST)
            .addHeader("Host", XYE_HOST)
            .addHeader("Referer", "http://222.24.62.120/xskbcx.aspx")
            .addCookie("ASP.NET_SessionId", sessionId);

    @Override
    public void process(Page page) {
        Html html = new Html(page.getRawText(), "http://222.24.62.120/xskbcx.aspx?xh=04161031&xm=%C3%CF%B3%BF&gnmkdm=N121603");
        String courseNode = html.xpath("/html/body/form/div/div/span/table[2]/tbody/tr[3]/td[3]").toString();

    }

    public Course handler


    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        CurriculumProcessor processor = new CurriculumProcessor();
        Map<String, Object> map = new HashMap<>();
        map.put(HIDDEN_NAME1, HIDDEN_VALUE1);
        map.put(HIDDEN_NAME2, HIDDEN_VALUE2);
        map.put(HIDDEN_NAME3, HIDDEN_VALUE3);
        map.put(YEAR, processor.year);
        map.put(TERM, processor.term);
        String url = processor.curriculumUrl;
        Request request = new Request(url);
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.form(map, "GBK"));
        Spider.create(new CurriculumProcessor()).addRequest(request).run();
    }

}
