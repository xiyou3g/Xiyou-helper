package com.xiyou3g.xiyouhelper.web.controller;

import com.mysql.cj.x.protobuf.Mysqlx;
import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.CGEMessage;
import com.xiyou3g.xiyouhelper.util.okhttp.MyOkHttp;
import com.xiyou3g.xiyouhelper.web.service.ICGEService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author mengchen
 * @time 18-7-26 上午10:45
 */
@RestController
public class CGEController {

    @Autowired
    ICGEService service;

    @GetMapping("/cge/validate_code")
    public void sendValidateCode(HttpSession session, HttpServletResponse response) throws IOException {
        OkHttpClient okHttpClient = (OkHttpClient) session.getAttribute("okHttpClient");
        if (okHttpClient == null) {
            okHttpClient = MyOkHttp.getAutoManagerOkHttpClient();
            session.setAttribute("okHttpClient", okHttpClient);
        }
        Request request = new Request.Builder().url("http://search.neea.edu.cn/Imgs.do?act=verify&t=0.4293967792772906")
                .addHeader("Referer", "http://search.neea.edu.cn/QueryMarkUpAction.do?act=doQueryCond&pram=results&community=Home&sid=300")
                .build();
        Response validateResponse = okHttpClient.newCall(request).execute();
        InputStream inputStream = validateResponse.body().byteStream();
        StreamUtils.copy(inputStream, response.getOutputStream());
    }

    @PostMapping("/cge/query")
    public ServerResponse<CGEMessage> query(String time, String type, String validateCode,
                                            String zjh, String name, HttpSession session) throws IOException {
        OkHttpClient okHttpClient = (OkHttpClient) session.getAttribute("okHttpClient");
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("pram", "results");
        formBodyBuilder.add("ksxm", "300");
        formBodyBuilder.add("ksnf", time);
        formBodyBuilder.add("bkjb", type);
        formBodyBuilder.add("sfzh", zjh);
        formBodyBuilder.add("name", name);
        formBodyBuilder.add("verify", validateCode);
        RequestBody body = formBodyBuilder.build();
        Request request = new Request.Builder()
                .url("http://search.neea.edu.cn/QueryMarkUpAction.do?act=doQueryResults")
                .post(body)
                .addHeader("Referer", "http://search.neea.edu.cn/QueryMarkUpAction.do?act=doQueryCond&pram=results&community=Home&sid=300")
                .build();
        Arrays.stream(new Headers[]{request.headers()}).forEach((header) -> {
            System.out.println(header);
        });
        Response response = okHttpClient.newCall(request).execute();
        InputStream inputStream = response.body().byteStream();
        String htmlStr = StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
        if (htmlStr.startsWith("<script")) {
            return ServerResponse.createBySuccessMsg("验证码错误");
        } else {
            CGEMessage cgeMessage = service.parseCGEMessage(htmlStr);
            return ServerResponse.createBySuccess(cgeMessage);
        }
    }

}
