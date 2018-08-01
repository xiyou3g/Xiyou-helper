package com.xiyou3g.xiyouhelper.parse;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

import static com.xiyou3g.xiyouhelper.util.constant.AchievementConstant.ACH_REFER;
import static com.xiyou3g.xiyouhelper.util.constant.AchievementConstant.XYE_ACH_URL;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_SESSION_KEY;

/**
 * @author sunxiaozhe
 * @time 2018/8/1 9:43
 */
@Component
public class BoxParse
{

    @Autowired
    private OkHttpClient okHttpClient;

    public List<String> getBox(String name, String num, String sessionId) throws IOException {
        List<String> boxs = new ArrayList<>();
        String achievementUrl = String.format(XYE_ACH_URL, num, name);
        okhttp3.Request request =  new okhttp3.Request.Builder().url(achievementUrl)
                .addHeader("Cookie",XYE_SESSION_KEY+sessionId)
                .addHeader("Referer",ACH_REFER)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        InputStream inputStream = response.body().byteStream();
        String htmlStr = StreamUtils.copyToString(inputStream,Charset.forName("GBK"));
        Html html = new Html(htmlStr);
        String box = html.xpath("//*[@id=\"ddlXN\"]").get();
        String[] array = box.split("\">");
        for (int i = 3; i < array.length; i++){
            boxs.add(array[i].substring(0,9));
        }
        return boxs;
    }

}
