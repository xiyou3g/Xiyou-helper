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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xiyou3g.xiyouhelper.util.constant.AchievementConstant.ACH_REFER;
import static com.xiyou3g.xiyouhelper.util.constant.AchievementConstant.XYE_ACH_URL;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_HOST;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_SESSION_KEY;

/**
 * 下拉框爬取
 */
@Component
public class BoxProcess implements PageProcessor {
    //URL
    public String achievementUrl;

    //下拉框内容
    public List<String> boxs = new ArrayList<>();

    private Site site;

    @Override
    public void process(Page page) {
        Html html = new Html(page.getRawText(),achievementUrl);
        String box = html.xpath("//*[@id=\"ddlXN\"]").get();
        String[] array = box.split("\">");
        for (int i = 3; i < array.length; i++){
            boxs.add(array[i].substring(0,9));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public List<String> getBox(String name, String num,String sessionId) {
        achievementUrl = String.format(XYE_ACH_URL, num, name);
        site = Site.me()
                .setDomain(XYE_HOST)
                .addHeader("Host", XYE_HOST)
                .addHeader("Referer", ACH_REFER)
                .addCookie(XYE_SESSION_KEY, sessionId);
        Map<String, Object> map = new HashMap<>();
        String url = this.achievementUrl;
        Request request = new Request(url);
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.form(map, "GBK"));
        Spider.create(this).addRequest(request).run();
        return boxs;
    }
}
