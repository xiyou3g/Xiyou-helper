package com.xiyou3g.xiyouhelper.eventlistener;

import com.xiyou3g.xiyouhelper.model.TrainPlanMessage;
import com.xiyou3g.xiyouhelper.model.User;
import com.xiyou3g.xiyouhelper.okhttp.TrainPlanParse;
import com.xiyou3g.xiyouhelper.processor.UserMessageProcessor;
import com.xiyou3g.xiyouhelper.web.service.IAchievementService;
import com.xiyou3g.xiyouhelper.web.service.ITrainPlanService;
import com.xiyou3g.xiyouhelper.web.service.IUserService;
import org.springframework.context.ApplicationEvent;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 18-7-24 下午8:33
 *
 * @author mengchen
 */
public class LoginSuccessEvent extends ApplicationEvent {

    private String sessionId;
    private String studentNum;
    private String name;
    private String major;
    private String level;

    public LoginSuccessEvent(String studentNum, String sessionId) {
        super("登录成功");
        this.sessionId = sessionId;
        this.studentNum = studentNum;
    }


    public void handlerUserMessage(UserMessageProcessor processor) {
        processor.execute(studentNum, sessionId);
    }

    public void handlerAchievement(IAchievementService service) throws IOException {
        service.getAchievement(name, studentNum, sessionId);
    }

    public void hanlderParseTrainPlan(ITrainPlanService service) throws IOException {
        TrainPlanParse parse = new TrainPlanParse();
        List<TrainPlanMessage> messages = parse.parseTrainPlanMessages(studentNum, name, sessionId);
        messages.stream().forEach((message) -> {
            message.setMajor(major);
            message.setLevel(level);
        });

        service.saveTrainPlanService(messages);
        service.saveStatus(major, level);
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}