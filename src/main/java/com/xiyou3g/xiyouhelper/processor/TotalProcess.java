package com.xiyou3g.xiyouhelper.processor;

import com.xiyou3g.xiyouhelper.model.Choice;
import com.xiyou3g.xiyouhelper.model.Essential;
import com.xiyou3g.xiyouhelper.model.Total;
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

import static com.xiyou3g.xiyouhelper.util.constant.AchievementConstant.*;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_HOST;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_SESSION_KEY;

@Component
public class TotalProcess implements PageProcessor {


    public String achievementUrl;
    public Total total = new Total();
    private Site site;

    @Override
    public void process(Page page) {

        Html html = new Html(page.getRawText(),achievementUrl);
        Essential essential = new Essential();
        Choice choice = new Choice();
        String src1 = html.xpath("//*[@id=\"Datagrid2\"]/tbody/tr[2]/td[2]").get();
        essential.setTotal(src1.substring(4,src1.length()-5));
        String src2 = html.xpath("//*[@id=\"Datagrid2\"]/tbody/tr[2]/td[3]").get();
        essential.setPass(src2.substring(4,src2.length()-5));
        String src3 = html.xpath("//*[@id=\"Datagrid2\"]/tbody/tr[2]/td[4]").get();
        essential.setNopass(src3.substring(4,src3.length()-5));
        System.out.println(essential);
        String src4 = html.xpath("//*[@id=\"Datagrid2\"]/tbody/tr[3]/td[2]").get();
        choice.setTotal(src4.substring(4,src4.length()-5));
        String src5 = html.xpath("//*[@id=\"Datagrid2\"]/tbody/tr[3]/td[3]").get();
        choice.setPass(src5.substring(4,src5.length()-5));
        String src6 = html.xpath("//*[@id=\"Datagrid2\"]/tbody/tr[3]/td[4]").get();
        choice.setNopass(src6.substring(4,src6.length()-5));

        String src7 = html.xpath("//*[@id=\"pjxfjd\"]/b").get();
        total.setAverageGPA(src7.substring(src7.indexOf("：")+1,src7.length()-4));

        String src8 = html.xpath("//*[@id=\"xfjdzh\"]/b").get();
        total.setAllGPA(src8.substring(src8.indexOf("：")+1,src8.length()-4));
        total.setChoice(choice);
        total.setEssential(essential);

        System.out.println(total);
        /*for (int i = 2; i<= 3; i++){
            for (int j= 2; j < 5; j++){
                if (j == 3){
                    j+=1;
                }
                String result = html.xpath("//*[@id=\"Datagrid2\"]/tbody/tr["+i+"]/td["+j+"]").get();
            }
        }*/
    }

    @Override
    public Site getSite() {
        return site;
    }


    public Total start(String name, String num, String sessionId, String value3){
        achievementUrl = String.format(XYE_ACH_URL, num, name);
        site = Site.me()
                .setDomain(XYE_HOST)
                .addHeader("Host", XYE_HOST)
                .addHeader("Referer", ACH_REFER)
                .addCookie(XYE_SESSION_KEY, sessionId);
        Map<String, Object> map = new HashMap<>();
        map.put(NAME1, VALUE1);
        map.put(NAME2, VALUE2);
        map.put(NAME3,value3);
        map.put(NAME5,VALUE5);
        String url = this.achievementUrl;
        Request request = new Request(url);
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.form(map, "GBK"));
        Spider.create( this).addRequest(request).run();
        return total;
    }
}
