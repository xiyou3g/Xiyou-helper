package com.xiyou3g.xiyouhelper.processor;
import com.xiyou3g.xiyouhelper.model.Choice;
import com.xiyou3g.xiyouhelper.model.Essential;
import com.xiyou3g.xiyouhelper.model.Total;
import okhttp3.FormBody;
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

@Component
public class TotalProcess {

    public String achievementUrl;
    public Total total = new Total();
    public Total start(String name, String num, String sessionId, String value3) throws IOException {
        achievementUrl = String.format(XYE_ACH_URL, num, name);

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add(NAME1, VALUE1);
        formBodyBuilder.add(NAME2, VALUE2);
        formBodyBuilder.add(NAME3, value3);
        formBodyBuilder.add(NAME5,VALUE5);

        Request request = new Request.Builder().url(achievementUrl)
                .addHeader("Cookie",XYE_SESSION_KEY+sessionId)
                .addHeader("Referer",ACH_REFER)
                .post(formBodyBuilder.build())
                .build();
        Response response = okHttpClient.newCall(request).execute();

        InputStream inputStream = response.body().byteStream();
        String htmlStr = StreamUtils.copyToString(inputStream,Charset.forName("GBK"));
        Html html = new Html(htmlStr);

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
        return total;
    }
}
