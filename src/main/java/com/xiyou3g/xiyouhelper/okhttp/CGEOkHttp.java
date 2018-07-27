package com.xiyou3g.xiyouhelper.okhttp;

import okhttp3.*;
import org.springframework.util.StreamUtils;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author mengchen
 * @time 18-7-26 上午9:05
 */
public class CGEOkHttp {

    private static OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    }).build();
    public static void main(String[] args) throws IOException {

        Request vrequest = new Request.Builder()
                .url("http://search.neea.edu.cn/Imgs.do?act=verify&t=0.4293967792772906")
                .get()
                .addHeader("Referer", "http://search.neea.edu.cn/QueryMarkUpAction.do?act=doQueryCond&pram=results&community=Home&sid=300")
                .build();

        Response vresponse = client.newCall(vrequest).execute();
        Arrays.stream(new Headers[]{vresponse.headers()}).forEach((header) -> {
            System.out.println(header.toString());
        });
        InputStream is = vresponse.body().byteStream();
        File file = new File("cgeValidate.png");
        StreamUtils.copy(is, new FileOutputStream(file));
        Scanner sc = new Scanner(System.in);
        String validation = sc.nextLine();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("pram", "results");
        formBodyBuilder.add("ksxm", "300");
        formBodyBuilder.add("ksnf", "3S6F6bZfhddXBBt0D8K8qW");
        formBodyBuilder.add("bkjb", "65");
        formBodyBuilder.add("sfzh", "610321199703234229");
        formBodyBuilder.add("name", "郑杨");
        formBodyBuilder.add("verify", validation);
        RequestBody body = formBodyBuilder.build();
        Request request = new Request.Builder()
                .url("http://search.neea.edu.cn/QueryMarkUpAction.do?act=doQueryResults")
                .post(body)
                .addHeader("Referer", "http://search.neea.edu.cn/QueryMarkUpAction.do?act=doQueryCond&pram=results&community=Home&sid=300")
                .addHeader("Cookie", "esessionid=4044D6874BD2337771FF7684873D95B0")
                .build();
        Arrays.stream(new Headers[]{request.headers()}).forEach((header) -> {
            System.out.println(header);
        });
        Response response = client.newCall(request).execute();
        InputStream inputStream = response.body().byteStream();
        String htmlStr = StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
        Html html = new Html(htmlStr);
        List<Selectable> nodes = html.xpath("/html/body/div[1]/table/tbody/tr").nodes();

        nodes.stream().forEach((node) -> {
            node.xpath("/tr/td").nodes().stream().forEach((childnode) -> {
                System.out.println(childnode.xpath("/td/text()"));
            });
        });
    }
}


