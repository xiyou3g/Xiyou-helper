package com.xiyou3g.xiyouhelper.eventlistener;

import com.xiyou3g.xiyouhelper.model.Achievement;
import com.xiyou3g.xiyouhelper.model.TrainPlanMessage;
import com.xiyou3g.xiyouhelper.model.User;
import com.xiyou3g.xiyouhelper.parse.TrainPlanParse;
import com.xiyou3g.xiyouhelper.processor.UserMessageParse;
import com.xiyou3g.xiyouhelper.web.service.IAchievementService;
import com.xiyou3g.xiyouhelper.web.service.ITrainPlanService;
import okhttp3.OkHttpClient;
import org.springframework.context.ApplicationEvent;

import java.io.IOException;
import java.util.List;

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

    private OkHttpClient okHttpClient;


    public LoginSuccessEvent(String studentNum, String sessionId) {
        super("登录成功");
        this.sessionId = sessionId;
        this.studentNum = studentNum;
    }


    public User handlerUserMessage() throws IOException {
        return new UserMessageParse(okHttpClient).parseUserMessage(studentNum, sessionId);
    }

    public List<Achievement> handlerAchievement(IAchievementService service) throws IOException {
         return service.getAchievement(name, studentNum, sessionId);
    }

    public List<TrainPlanMessage> hanlderParseTrainPlan(ITrainPlanService service) throws IOException {
        TrainPlanParse parse = new TrainPlanParse(okHttpClient);
        List<TrainPlanMessage> messages = parse.parseTrainPlanMessages(studentNum, name, sessionId);
        messages.stream().forEach((message) -> {
            message.setMajor(major);
            message.setLevel(level);
        });
        return messages;
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

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }
}
