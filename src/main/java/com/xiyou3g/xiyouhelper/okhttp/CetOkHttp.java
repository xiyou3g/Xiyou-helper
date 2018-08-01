package com.xiyou3g.xiyouhelper.okhttp;


import com.xiyou3g.xiyouhelper.web.service.ICetService;
import com.xiyou3g.xiyouhelper.web.service.impls.CetService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import static com.xiyou3g.xiyouhelper.util.constant.CetConstant.*;

/**
 * @author du
 */
public class CetOkHttp {


    private OkHttpClient okHttpClient;

    public CetOkHttp(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public Html handlerSimulationLogin(String zkzh, String name, String validateCode, String sessionId) {
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
            Response response = okHttpClient.newCall(loginRequest).execute();
            byte[] bytes = new byte[1024];
            InputStream is = response.body().byteStream();
            while (is.read(bytes) != -1) {
                stringBuffer.append(new String(bytes, "UTF-8"));
            }
            Html html = new Html(stringBuffer.toString());
            //System.out.println(html);
            return html;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
