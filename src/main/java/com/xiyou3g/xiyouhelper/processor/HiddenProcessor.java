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

import static com.xiyou3g.xiyouhelper.util.constant.AchievementConstant.ACH_REFER;
import static com.xiyou3g.xiyouhelper.util.constant.AchievementConstant.XYE_ACH_URL;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_HOST;

/**
 * 爬取隐藏参数3
 */
@Component
public class HiddenProcessor implements PageProcessor {

    private String achievementUrl;
    public String result;
    private Site site;

    @Override
    public void process(Page page) {
        Html html = new Html(page.getRawText(),achievementUrl);
        result = String.valueOf(html.xpath("//*[@id=\"Form1\"]/input[3]/@value"));
        System.out.println(result);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void start(String name,String num,String sessionId) {

        achievementUrl = String.format(XYE_ACH_URL, num, name);
        site = Site.me()
                .setDomain(XYE_HOST)
                .addHeader("Host", XYE_HOST)
                .addHeader("Referer", ACH_REFER)
                .addCookie("ASP.NET_SessionId", sessionId);
        Map<String, Object> map = new HashMap<>();
        String url = achievementUrl;
        Request request = new Request(url);
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.form(map, "GBK"));
        Spider.create(this).addRequest(request).run();

    }
}
