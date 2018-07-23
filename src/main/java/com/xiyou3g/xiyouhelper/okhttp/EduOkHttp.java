package com.xiyou3g.xiyouhelper.okhttp;

import com.xiyou3g.xiyouhelper.web.service.IUserService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_SESSION_KEY;
import static com.xiyou3g.xiyouhelper.util.constant.EduConstant.*;

/**
 * 18-7-20 下午7:37
 * @author mengchen
 */
@Component
public class EduOkHttp {


    @Autowired
    private IUserService userService;

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

    public OkHttpClient getClient() {
        return client;
    }

    public int handlerSimulationLogin(String studentNum, String password, String validateCode,String sessionId) {
        int loginStatus = 0;
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add(LOGIN_HIDDEN_NAME, LOGIN_HIDDEN_VALUE);
        formBodyBuilder.add(LOGIN_USERNAME, studentNum);
        formBodyBuilder.add(LOGIN_OTHER, sessionId);
        formBodyBuilder.add(LOGIN_PASSWORD, password);
        formBodyBuilder.add(LOGIN_VALIDATE_CODE, validateCode);
        formBodyBuilder.add(LOGIN_TYPE, LOGIN_TYPE_STUDENT);
        formBodyBuilder.add(LOGIN_HIDDEN_NAME2, LOGIN_HIDDEN_VALUE2);
        RequestBody requestBody = formBodyBuilder.build();
        Request loginRequest = new Request.Builder().url(LOGIN_URL).post(requestBody).
                addHeader("Cookie",XYE_SESSION_KEY + "=" +  sessionId).build();
        try {
            StringBuffer stringBuffer = new StringBuffer();
            Response response = client.newCall(loginRequest).execute();
            byte[] bytes = new byte[1024];
            InputStream is = response.body().byteStream();
            while (is.read(bytes) != -1) {
                stringBuffer.append(new String(bytes, "GBK"));
            }
            String htmlStr = stringBuffer.toString();
            loginStatus = userService.testLogin(htmlStr);
        } catch (IOException e) {
            loginStatus = -1;
            e.printStackTrace();
        }
        return loginStatus;
    }

}
