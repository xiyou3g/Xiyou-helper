package com.xiyou3g.xiyouhelper.processor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import us.codecraft.webmagic.selector.Html;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import static com.xiyou3g.xiyouhelper.util.constant.AchievementConstant.*;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_SESSION_KEY;

/**
 * 爬取隐藏参数3
 */
@Component
public class HiddenProcessor{

    private OkHttpClient okHttpClient;

    public HiddenProcessor(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    private String achievementUrl;
    public String start(String name,String num,String sessionId) throws IOException {
        achievementUrl = String.format(XYE_ACH_URL, num, name);
        Request request =  new Request.Builder().url(achievementUrl)
                .addHeader("Cookie",XYE_SESSION_KEY+sessionId)
                .addHeader("Referer",ACH_REFER)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        InputStream inputStream = response.body().byteStream();
        String htmlStr = StreamUtils.copyToString(inputStream,Charset.forName("GBK"));
        Html html = new Html(htmlStr);
        String result = html.xpath("//*[@id=\"Form1\"]/input[3]/@value").get();
        return result;
    }
}
