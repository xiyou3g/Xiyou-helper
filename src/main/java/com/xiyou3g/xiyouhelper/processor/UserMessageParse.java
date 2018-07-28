package com.xiyou3g.xiyouhelper.processor;

import com.xiyou3g.xiyouhelper.model.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.*;
import static com.xiyou3g.xiyouhelper.util.constant.EduConstant.EDU_REFERER;
import static com.xiyou3g.xiyouhelper.util.constant.EduConstant.REFERER;

/**
 * 18-7-21 下午12:07
 *
 * @author mengchen
 */
public class UserMessageParse {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private OkHttpClient client;

    public UserMessageParse(OkHttpClient client) {
        this.client = client;
    }

    public User parseUserMessage(String studentNum, String sessionId) throws IOException {
        User user = new User();
        String url = String.format(XYE_HOME_URL, studentNum);
        Request request1 = new Request.Builder()
                .url(url)
                .addHeader(REFERER, EDU_REFERER)
                .addHeader("Cookie", XYE_SESSION_KEY + sessionId)
                .build();
        Response response1 = client.newCall(request1).execute();
        String htmlStr1 = StreamUtils.copyToString(response1.body().byteStream(), Charset.forName("GBK"));
        Html html1 = new Html(htmlStr1);
        String simpleUserMessageUrl = html1.xpath("/html/body/div/div[1]/ul/li[5]/ul/li[1]/a/@href").get();
        logger.info(simpleUserMessageUrl);
        handlerSimpleUserMessage(user, simpleUserMessageUrl);

        Request request2 = new Request.Builder()
                .url(XYE_BASEURL + simpleUserMessageUrl)
                .addHeader(REFERER, EDU_REFERER)
                .addHeader("Cookie", XYE_SESSION_KEY + sessionId)
                .build();
        Response response2 = client.newCall(request2).execute();
        String htmlStr2 = StreamUtils.copyToString(response2.body().byteStream(), Charset.forName("GBK"));
        Html html2 = new Html(htmlStr2);
        String gender = html2.xpath("//*[@id=\"lbl_xb\"]/text()").get();
        if (StringUtils.equals(gender, "男")) {
            user.setGender(1);
        } else {
            user.setGender(0);
        }
        String college = html2.xpath("//*[@id=\"lbl_xy\"]/text()").get();
        user.setCollege(college);
        String major = html2.xpath("//*[@id=\"lbl_zymc\"]/text()").get();
        user.setMajor(major);
        String adClass = html2.xpath("//*[@id=\"lbl_xzb\"]/text()").get();
        user.setAdclass(adClass);
        String level = html2.xpath("//*[@id=\"lbl_dqszj\"]/text()").get();
        user.setLevel(level);
        String education = html2.xpath("//*[@id=\"lbl_CC\"]/text()").get();
        user.setEducation(education);

        return user;
    }


    private void handlerSimpleUserMessage(User user, String simpleUserMessage) {
        String temp = simpleUserMessage.substring(simpleUserMessage.indexOf('?') + 1);
        String[] messages = temp.split("&");
        user.setSid(messages[0].substring(messages[0].indexOf('=') + 1));
        user.setName(messages[1].substring(messages[1].indexOf('=') + 1));
    }




}