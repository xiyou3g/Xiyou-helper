package com.xiyou3g.xiyouhelper.eventlistener;

import com.xiyou3g.xiyouhelper.processor.UserMessageProcessor;
import com.xiyou3g.xiyouhelper.model.User;
import com.xiyou3g.xiyouhelper.processor.UserMessageProcessor;
import com.xiyou3g.xiyouhelper.web.service.IAchievementService;
import com.xiyou3g.xiyouhelper.web.service.ITrainPlanService;
import com.xiyou3g.xiyouhelper.web.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 18-7-24 下午8:34
 *
 * @author mengchen
 */
@Component
public class LoginSuccessListener implements ApplicationListener<LoginSuccessEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMessageProcessor processor;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAchievementService achievementService;

    @Autowired
    private ITrainPlanService trainPlanService;

    @Override
    public void onApplicationEvent(LoginSuccessEvent loginSuccessEvent) {
        logger.info("登录成功了！");
        Thread thread = new Thread(() -> {
            loginSuccessEvent.handlerUserMessage(processor);
            User user = userService.getUserBySid(loginSuccessEvent.getStudentNum());
            loginSuccessEvent.setName(user.getName());
            try {
                loginSuccessEvent.handlerAchievement(achievementService);
            } catch (IOException e) {
                e.printStackTrace();
            }
            loginSuccessEvent.setMajor(user.getMajor());
            loginSuccessEvent.setLevel(user.getLevel());
            if (trainPlanService.isExist(user.getMajor(), user.getLevel()) == false) {
                try {
                    loginSuccessEvent.hanlderParseTrainPlan(trainPlanService);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
