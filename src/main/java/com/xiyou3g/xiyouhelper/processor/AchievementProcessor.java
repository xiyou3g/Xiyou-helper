package com.xiyou3g.xiyouhelper.processor;

import com.xiyou3g.xiyouhelper.model.Achievement;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.*;

import static com.xiyou3g.xiyouhelper.util.constant.AchievementConstant.*;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_SESSION_KEY;

/**
 * 爬取成绩
 */
@Component
public class AchievementProcessor{

    @Autowired
    private OkHttpClient okHttpClient;
    //成绩集合
    private List<Achievement> achievements = Collections.synchronizedList(new ArrayList());

    public List<Achievement> start(String name,String num,String sessionId,String year,String semester,String value3) throws IOException {
        achievements.clear();
       String achievementUrl = String.format(XYE_ACH_URL, num, name);
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add(NAME1, VALUE1);
        formBodyBuilder.add(NAME2, VALUE2);
        formBodyBuilder.add(NAME3, value3);
        formBodyBuilder.add(YEAR, year);
        formBodyBuilder.add(TERM, semester);
        formBodyBuilder.add(CLASS,CLASS_VALUE);
        formBodyBuilder.add(NAME4,VALUE4);
        Request request = new Request.Builder().url(achievementUrl)
                            .addHeader("Cookie",XYE_SESSION_KEY+sessionId)
                            .addHeader("Referer",ACH_REFER)
                            .post(formBodyBuilder.build())
                            .build();

        Response response = okHttpClient.newCall(request).execute();

        InputStream inputStream = response.body().byteStream();
        String htmlStr = StreamUtils.copyToString(inputStream,Charset.forName("GBK"));
        Html html = new Html(htmlStr);

        System.out.println(htmlStr);

        //XPath解析
        String sec = html.xpath("//*[@id=\"Form1\"]/input[3]/@value").get();

        try {
            //BASE664解码
            String result = new String(Base64.getDecoder().decode(sec));

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

}
