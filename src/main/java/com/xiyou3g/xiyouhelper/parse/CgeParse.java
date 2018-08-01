package com.xiyou3g.xiyouhelper.parse;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mengchen
 * @time 18-7-26 上午8:27
 */
@Component
public class CgeParse {

    @Autowired
    private OkHttpClient client;


    public Map<String, String> parseCGETimeMap() throws IOException {
        Map<String, String> map = new HashMap<>();
        Request request = new Request.Builder()
                .url("http://search.neea.edu.cn/QueryMarkUpAction.do?act=doQueryCond&pram=results&community=Home&sid=300")
                .addHeader("Referer", "http://search.neea.edu.cn/QueryMarkUpAction.do?act=doQueryCond&pram=results&community=Home&sid=300")
                .build();
        Response response = client.newCall(request).execute();
        InputStream inputStream = response.body().byteStream();
        String htmlStr = StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
        Html html = new Html(htmlStr);
        System.out.println(html);
        List<Selectable> nodes = html.xpath("//*[@id=\"examselect\"]/select/option").nodes();
        nodes.stream().forEach((node) -> {
            String key = node.xpath("/option/text()").get();
            String value = node.xpath("/option/@value").get();
            map.put(key, value);
        });
        return map;
    }

}
