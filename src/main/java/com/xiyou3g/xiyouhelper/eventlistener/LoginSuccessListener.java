package com.xiyou3g.xiyouhelper.eventlistener;

import com.xiyou3g.xiyouhelper.model.Achievement;
import com.xiyou3g.xiyouhelper.model.TrainPlanMessage;
import com.xiyou3g.xiyouhelper.model.User;
import com.xiyou3g.xiyouhelper.parse.TrainPlanParse;
import com.xiyou3g.xiyouhelper.parse.UserMessageParse;
import com.xiyou3g.xiyouhelper.web.service.IAchievementService;
import com.xiyou3g.xiyouhelper.web.service.ITrainPlanService;
import com.xiyou3g.xiyouhelper.web.service.impls.LoginSuccessService;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


/**
 * 18-7-24 下午8:34
 *
 * @author mengchen
 */
@Component
public class LoginSuccessListener implements ApplicationListener<LoginSuccessEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private IAchievementService achievementService;

    @Autowired
    private TrainPlanParse trainPlanParse;

    @Autowired
    private ITrainPlanService trainPlanService;

    @Autowired
    private LoginSuccessService loginSuccessService;

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private UserMessageParse userMessageParse;

    @Override
    public void onApplicationEvent(LoginSuccessEvent loginSuccessEvent) {
        logger.info("登录成功了！");
        Thread thread = new Thread(() -> {
            try {
                User user = loginSuccessEvent.handlerUserMessage(userMessageParse);
                loginSuccessEvent.setName(user.getName());
                List<Achievement> achievements = loginSuccessEvent.handlerAchievement(achievementService);
                loginSuccessEvent.setMajor(user.getMajor());
                loginSuccessEvent.setLevel(user.getLevel());
                List<TrainPlanMessage> messages = null;
                if (trainPlanService.isExist(user.getMajor(), user.getLevel()) == false) {
                    messages = loginSuccessEvent.hanlderParseTrainPlan(trainPlanParse);
                }
                loginSuccessService.saveSomeMessage(user, achievements, messages);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
