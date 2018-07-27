package com.xiyou3g.xiyouhelper.processor;
import com.xiyou3g.xiyouhelper.model.User;
import com.xiyou3g.xiyouhelper.pipeline.UserMessagePipeline;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private UserMessagePipeline pipeline;

    private boolean simpleFlag = true;
    User user = new User();
    private Site site;

    @Override
    public void process(Page page) {
        if (simpleFlag) {
            Html html = new Html(page.getRawText());
            String simpleUserMessageUrl = html.xpath("/html/body/div/div[1]/ul/li[5]/ul/li[1]/a/@href").get();
            System.out.println(simpleUserMessageUrl);
            System.out.println();
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
    }


    @Override
    public Site getSite() {
        return site;
    }



    public void execute(String studentNum, String sessionId) {
        this.simpleFlag = true;
        this.user = new User();
        site = Site.me()
                .setDomain(XYE_HOST)
                .addHeader("Host", XYE_HOST)
                .addHeader("Referer", "http://222.24.62.120/xskbcx.aspx")
                .addCookie("ASP.NET_SessionId", sessionId);
        System.out.println(sessionId);
        String url = String.format(XYE_HOME_URL, studentNum);
        Spider.create(this).addPipeline(pipeline).addUrl(url).thread(10).run();

    }
}