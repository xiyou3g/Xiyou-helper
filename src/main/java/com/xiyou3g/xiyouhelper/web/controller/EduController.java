package com.xiyou3g.xiyouhelper.web.controller;

import com.xiyou3g.xiyouhelper.okhttp.EduOkHttp;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.xiyou3g.xiyouhelper.util.constant.EduConstant.VALIDATE_URL;


/**
 * mengchen
 * 18-7-20 下午7:40
 */
@RestController
public class EduController {

    @Autowired
    private EduOkHttp eduOkHttp;

    @GetMapping("/xiyou_edu_sys/validate_code")
    public void sendValidateCode(HttpServletResponse response) {
        response.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9," +
                "image/webp,image/apng,*/*;q=0.8");
        Request request = new Request.Builder().url(VALIDATE_URL).build();
        Call call = eduOkHttp.getClient().newCall(request);
        try {
            Response validateCodeResponse = call.execute();
            InputStream is = validateCodeResponse.body().byteStream();
            StreamUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
