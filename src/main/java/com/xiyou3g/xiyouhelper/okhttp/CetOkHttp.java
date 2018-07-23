package com.xiyou3g.xiyouhelper.okhttp;


import com.xiyou3g.xiyouhelper.web.service.ICetService;
import com.xiyou3g.xiyouhelper.web.service.impls.CetService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import static com.xiyou3g.xiyouhelper.util.constant.CetConstant.*;

/**
 * @author du
 */
@Component
public class CetOkHttp {

    @Autowired
    private ICetService cetService;

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

    public OkHttpClient getClient() {
        return client;
    }

    public int handlerSimulationLogin(String zkzh, String name, String validateCode,String sessionId) {
        int loginStatus = 0;
//        FormBody.Builder formBodyBuilder = new FormBody.Builder();
//        formBodyBuilder.add(LOGIN_ZKZH, zkzh);
//        formBodyBuilder.add(LOGIN_NAME,name);
//        formBodyBuilder.add(LOGIN_VALIDATE_CODE,validateCode);
//        RequestBody requestBody = formBodyBuilder.build();
        Request loginRequest = new Request
                .Builder()
                .url(CET_BASEURL + LOGIN_ZKZH + "=" + zkzh
                        + "&" + LOGIN_NAME + "=" + name
                        + "&" + LOGIN_VALIDATE_CODE + "=" + validateCode)
                .addHeader("Cookie",CET_SESSION_KEY + "=" +  sessionId)
                .addHeader("Referer",CET_REFERE)
                .build();
        try {
            StringBuffer stringBuffer = new StringBuffer();
            Response response = client.newCall(loginRequest).execute();
            byte[] bytes = new byte[1024];
            InputStream is = response.body().byteStream();
            while (is.read(bytes) != -1) {
                stringBuffer.append(new String(bytes, "GBK"));
            }
            String htmlStr = stringBuffer.toString();
            loginStatus = cetService.testLogin(htmlStr);
        } catch (IOException e) {
            loginStatus = -1;
            e.printStackTrace();
        }
        return loginStatus;

    }

}
