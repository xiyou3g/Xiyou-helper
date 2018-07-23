package com.xiyou3g.xiyouhelper.webmagic.processor;

import com.xiyou3g.xiyouhelper.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_BASEURL;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_HOME_URL;
import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_HOST;

/**
 * 18-7-21 下午12:07
 * @author mengchen
 */
@Component
public class UserMessageProcessor implements PageProcessor {

    private String sessionId;
    private String studentNum;

    private boolean simpleFlag = true;
    private User user;

    private Site site = Site.me()
            .setDomain(XYE_HOST)
            .addHeader("Host", XYE_HOST)
            .addHeader("Referer", "http://222.24.62.120/xskbcx.aspx")
            .addCookie("ASP.NET_SessionId", "b23wm255nsvilq45sktezp45");

    @Override
    public void process(Page page) {
        if (simpleFlag) {
            Html html = new Html(page.getRawText());
            String simpleUserMessageUrl = html.xpath("/html/body/div/div[1]/ul/li[5]/ul/li[1]/a/@href").get();
            handlerSimpleUserMessage(user, simpleUserMessageUrl);
            page.addTargetRequest(XYE_BASEURL + simpleUserMessageUrl);
            simpleFlag = false;
        } else {
            Html html = new Html(page.getRawText());
            String gender = html.xpath("//*[@id=\"lbl_xb\"]/text()").get();
            if (StringUtils.equals(gender, "男")) {
                user.setGender(1);
            } else {
                user.setGender(0);
            }
            String college = html.xpath("//*[@id=\"lbl_xy\"]/text()").get();
            user.setCollege(college);
            String major = html.xpath("//*[@id=\"lbl_zymc\"]/text()").get();
            user.setMajor(major);
            String adClass = html.xpath("//*[@id=\"lbl_xzb\"]/text()").get();
            user.setAdclass(adClass);
            String level = html.xpath("//*[@id=\"lbl_dqszj\"]/text()").get();
            user.setLevel(level);
            String education = html.xpath("//*[@id=\"lbl_CC\"]/text()").get();
            user.setEducation(education);
            page.putField("user", user);
        }

    }

    private void handlerSimpleUserMessage(User user, String simpleUserMessage) {
        String temp = simpleUserMessage.substring(simpleUserMessage.indexOf('?') + 1);
        String[] messages = temp.split("&");
        user.setSid(messages[0].substring(messages[0].indexOf('=') + 1));
        user.setName(messages[1].substring(messages[1].indexOf('=') + 1));
        user.setGnmkdm(messages[2].substring(messages[2].indexOf('=') + 1));
    }


    @Override
    public Site getSite() {
        return site;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static void main(String[] args) {
        String url = String.format(XYE_HOME_URL, "04161031");
        UserMessageProcessor messageProcessor = new UserMessageProcessor();
        messageProcessor.setUser(new User());
        System.out.println(url);
        Spider.create(messageProcessor).addUrl(url).run();
        System.out.println(messageProcessor.user);
    }
}
