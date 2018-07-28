package com.xiyou3g.xiyouhelper.okhttp;

import com.xiyou3g.xiyouhelper.web.service.IUserService;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_SESSION_KEY;
import static com.xiyou3g.xiyouhelper.util.constant.EduConstant.*;

/**
 * 18-7-20 下午7:37
 * @author mengchen
 */

public class EduLoginParse {

    private OkHttpClient okHttpClient;

    public EduLoginParse(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }


    public int handlerSimulationLogin(String studentNum, String password, String validateCode,String sessionId) {
        int loginStatus;
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add(LOGIN_HIDDEN_NAME, LOGIN_HIDDEN_VALUE);
        formBodyBuilder.add(LOGIN_USERNAME, studentNum);
        formBodyBuilder.add(LOGIN_OTHER, sessionId);
        formBodyBuilder.add(LOGIN_PASSWORD, password);
        formBodyBuilder.add(LOGIN_VALIDATE_CODE, validateCode);
        formBodyBuilder.add(LOGIN_TYPE, LOGIN_TYPE_STUDENT);
        formBodyBuilder.add(LOGIN_HIDDEN_NAME2, LOGIN_HIDDEN_VALUE2);
        RequestBody requestBody = formBodyBuilder.build();
        Request loginRequest = new Request
                .Builder()
                .url(LOGIN_URL)
                .post(requestBody)
                .addHeader("Cookie",XYE_SESSION_KEY + "=" +  sessionId)
                .build();
        try {
            StringBuffer stringBuffer = new StringBuffer();
            Response response = okHttpClient.newCall(loginRequest).execute();
            byte[] bytes = new byte[1024];
            InputStream is = response.body().byteStream();
            while (is.read(bytes) != -1) {
                stringBuffer.append(new String(bytes, "GBK"));
            }
            String htmlStr = stringBuffer.toString();
            loginStatus = testLogin(htmlStr);
        } catch (IOException e) {
            loginStatus = -1;
            e.printStackTrace();
        }
        return loginStatus;
    }

    /**
     * 检测登录结果
     * - 0 登录成功
     * - 1 密码错误
     * - 2 用户名不存在
     * - 3 验证码错误
     *
     * -1 未知情况
     * @param htmlStr
     * @return
     */
    public int testLogin(String htmlStr) {
        Html html = new Html(htmlStr);
        String loginMessage = html.xpath("/html/body/form/script").regex("[\\u4e00-\\u9fa5]+").get();
        if (loginMessage == null) {
            return 0;
        }
        if (StringUtils.equals(loginMessage, LOGIN_PASS_ERROR)) {
            return 1;
        } else if (StringUtils.equals(loginMessage, LOGIN_USER_NOTFOUND)) {
            return 2;
        } else if (StringUtils.equals(loginMessage, LOGIN_VALIDATE_CODE_ERROR)) {
            return 3;
        }
        return -1;
    }

}
