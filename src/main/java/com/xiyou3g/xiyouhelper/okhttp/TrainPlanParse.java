package com.xiyou3g.xiyouhelper.okhttp;

import com.xiyou3g.xiyouhelper.model.TrainPlanMessage;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_SESSION_KEY;
import static com.xiyou3g.xiyouhelper.util.constant.EduConstant.TRAINPLAN_URL;

/**
 * @author mengchen
 * @time 18-7-26 下午8:40
 */
@Component
public class TrainPlanParse {

    private OkHttpClient client;

    @Autowired
    public TrainPlanParse(OkHttpClient client) {
        this.client = client;
    }

    public String getHidden(String studentNum, String name, String sessionId) throws IOException {
        String url = String.format(TRAINPLAN_URL, studentNum, name);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", XYE_SESSION_KEY + sessionId)
                .addHeader("Referer", "http://222.24.62.120/xs_main.aspx?xh=04161031")
                .build();
        Response response = client.newCall(request).execute();
        InputStream inputStream = response.body().byteStream();
        String htmlStr = StreamUtils.copyToString(inputStream, Charset.forName("GBK"));
        Html html = new Html(htmlStr);
        String hidden = html.xpath("//*[@id=\"Form1\"]/input[3]/@value").get();
        return hidden;
    }

    public List<TrainPlanMessage> parseTrainPlanMessages(String studentNum, String name, String sessionId) throws IOException {
        List<TrainPlanMessage> list = new ArrayList<>();
        String url = String.format(TRAINPLAN_URL, studentNum, name);

        for (int i = 1; i <= 8; i++) {
            String hidden = getHidden(studentNum, name, sessionId);
            FormBody.Builder form = new FormBody.Builder();
            form.add("__EVENTTARGET", "dpDBGrid:txtPageSize");
            form.add("__EVENTARGUMENT", "");
            form.add("__VIEWSTATE", hidden);

            form.add("kcxz", "全部");
            form.add("dpDBGrid:txtChoosePage", "1");
            form.add("dpDBGrid:txtPageSize", "200");
            form.add("xq", String.valueOf(i));
            Request request = new Request.Builder().url(url)
                    .addHeader("Referer", "http://222.24.62.120/pyjh.aspx?xh=04161031&xm=%C3%CF%B3%BF&gnmkdm=N121607")
                    .addHeader("Cookie", XYE_SESSION_KEY + sessionId)
                    .post(form.build())
                    .build();
            Response response = client.newCall(request).execute();
            InputStream inputStream = response.body().byteStream();
            String htmlStr = StreamUtils.copyToString(inputStream, Charset.forName("GBK"));
            Html html = new Html(htmlStr);
            List<Selectable> nodes = html.xpath("//*[@id=\"DBGrid\"]/tbody/tr").nodes();
            // 略过第一个tr
            boolean flag = true;
            for (Selectable node : nodes) {
                if (flag) {
                    flag = false;
                    continue;
                }
                TrainPlanMessage message = new TrainPlanMessage();
                message.setTerm(i);
                List<Selectable> tdNodes = node.xpath("/tr/td").nodes();
                message.setClassCode(tdNodes.get(0).xpath("/td/text()").get());
                message.setClassName(tdNodes.get(1).xpath("/td/text()").get());
                message.setCredit(Float.parseFloat(tdNodes.get(2).xpath("/td/text()").get()));
                message.setExamType(tdNodes.get(4).xpath("/td/text()").get());
                message.setClassCharacter(tdNodes.get(5).xpath("/td/text()").get());
                System.out.println(message);
                list.add(message);
            }
        }
        return list;
    }
}
