package com.xiyou3g.xiyouhelper.processor;

import com.xiyou3g.xiyouhelper.model.Achievement;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static com.xiyou3g.xiyouhelper.util.constant.AchievementConstant.*;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_HOST;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_SESSION_KEY;

/**
 * 爬取成绩
 */
@Component
public class AchievementProcessor implements PageProcessor {

    //URL
    public String achievementUrl;

    //成绩集合
    public List<Achievement> achievements = new ArrayList<>();
    private Site site;

    @Override
    public void process(Page page) {
       Html html = new Html(page.getRawText(),achievementUrl);
       //XPath解析
       String sec = html.xpath("//*[@id=\"Form1\"]/input[3]/@value").get();
        try {
            //BASE664解码
            String result = encoding(sec);

            String[] array = result.split("<Text;>;l<");

            for (int i = 3; i < array.length - array.length%29; i+=29){
                Achievement achievement = new Achievement();
                //设置学年
                achievement.setSchool_year(handelContents(array[i]));
                //设置学期
                achievement.setSemester(handelContents(array[i+1]));
                //设置总成绩
                achievement.setAchievement(handelContents(array[i+12]));
                //设置课程名字
                achievement.setClassname(handelContents(array[i+3]));
                //设置绩点
                achievement.setPoint(handelContents(array[i+7]).trim());
                //设置课程学分
                achievement.setCredit(handelContents(array[i+6]));
                //设置课程性质
                achievement.setNature(handelContents(array[i+4]));
                //设置平时成绩
                if (handelContents(array[i+8]).trim().equals("&nbsp\\;")){
                    achievement.setOrdinary("");
                }else {
                    achievement.setOrdinary(handelContents(array[i+8]));
                }
                //设置期末成绩
                if (handelContents(array[i+10]).equals("&nbsp\\;")){
                    achievement.setFinalexam("");
                }else{
                    achievement.setFinalexam(handelContents(array[i+10]));
                }
                achievements.add(achievement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Site getSite() {
        return site;
    }

    public List<Achievement> start(String name,String num,String sessionId,String year,String semester,String value3){
        achievements.clear();
        achievementUrl = String.format(XYE_ACH_URL, num, name);
        site = Site.me()
                .setDomain(XYE_HOST)
                .addHeader("Host", XYE_HOST)
                .addHeader("Referer", ACH_REFER)
                .addCookie(XYE_SESSION_KEY, sessionId);
        Map<String, Object> map = new HashMap<>();
        map.put(NAME1, VALUE1);
        map.put(NAME2, VALUE2);
        map.put(NAME3, value3);
        map.put(YEAR, year);
        map.put(TERM, semester);
        map.put(CLASS,CLASS_VALUE);
        map.put(NAME4,VALUE4);
        String url = this.achievementUrl;
        Request request = new Request(url);
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.form(map, "GBK"));
        Spider.create( this).addRequest(request).run();

        for (Achievement achievement : achievements){
            achievement.setNum(num);
        }
        return achievements;
    }
    /**
     * 字符串过滤
     * @param res
     * @return
     */
    private static String handelContents(String res){
        return res.split(";>>;>;;>;t<p<p<l")[0];
    }

    /**
     * base64解码
     * @param result
     * @return
     * @throws UnsupportedEncodingException
     */
    public String encoding(String result) throws UnsupportedEncodingException {
        byte[] str = Base64.getDecoder().decode(result);

        String str1 = new String(str,"utf-8");

        return str1;
    }
}
