package com.xiyou3g.xiyouhelper.processor;

import com.xiyou3g.xiyouhelper.model.Achievement;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static com.xiyou3g.xiyouhelper.util.constant.AchievementConstant.*;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_HOST;

/**
 * 爬取成绩
 */
public class AchievementProcessor implements PageProcessor {

    private String achievementUrl = "http://222.24.62.120/xscjcx.aspx?xh=04162130&xm=%CB%EF%CF%FE%D5%DC&gnmkdm=N121605";
    private String sessionId = "0g4tfq45bbokvtjewlhjlhud";
    private String year = "2017-2018";
    private String term = "2";


    private static List<Achievement> achievements = new ArrayList<>();

    private Site site = Site.me()
            .setDomain(XYE_HOST)
            .addHeader("Host", XYE_HOST)
            .addHeader("Referer", "http://222.24.62.120/xs_main.aspx?xh=04162130")
            .addCookie("ASP.NET_SessionId", sessionId);


    @Override
    public void process(Page page) {
       Html html = new Html(page.getRawText(),"http://222.24.62.120/xscjcx.aspx?xh=04162130&xm=%CB%EF%CF%FE%D5%DC&gnmkdm=N121605");
       String sec = html.xpath("//*[@id=\"Form1\"]/input[3]/@value").get();
        try {
            //BASE664解码
            String result = encoding(sec);

            String[] array = result.split("<Text;>;l<");

            for (int i = 3; i < array.length - array.length%29; i+=29){

                Achievement achievement = new Achievement();
                //设置总成绩
                achievement.setAchievement(handelContents(array[i+12]));
                //设置课程名字
                achievement.setClassName(handelContents(array[i+3]));
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
        println();
    }

    /**
     * 过滤无用的字符串
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


    @Override
    public Site getSite() {
        return site;
    }

    private void println(){
        for (Achievement achievement : achievements){
            System.out.println(achievement);
        }
    }

    public static void main(String[] args) {
        AchievementProcessor processor = new AchievementProcessor();
        Map<String, Object> map = new HashMap<>();
        map.put(NAME1, VALUE1);
        map.put(NAME2, VALUE2);
        map.put(NAME3, VALUE3);
        map.put(YEAR, processor.year);
        map.put(TERM, processor.term);
        map.put(CLASS,CLASS_VALUE);
        map.put(NAME4,VALUE4);
        String url = processor.achievementUrl;
        System.out.println(url);
        Request request = new Request(url);
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.form(map, "GBK"));
        Spider.create(new AchievementProcessor()).addRequest(request).run();

    }
}
