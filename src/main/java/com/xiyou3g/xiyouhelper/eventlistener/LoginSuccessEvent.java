package com.xiyou3g.xiyouhelper.eventlistener;

import com.xiyou3g.xiyouhelper.model.User;
import com.xiyou3g.xiyouhelper.processor.UserMessageProcessor;
import com.xiyou3g.xiyouhelper.web.service.IAchievementService;
import com.xiyou3g.xiyouhelper.web.service.IUserService;
import org.springframework.context.ApplicationEvent;

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

    public LoginSuccessEvent(String studentNum, String sessionId) {
        super("登录成功");
        this.sessionId = sessionId;
        this.studentNum = studentNum;
    }


    public void handlerUserMessage(UserMessageProcessor processor) {
        processor.execute(studentNum, sessionId);
    }

    public void handlerAchievement(IAchievementService service) {
        service.getAchievement(name, studentNum, sessionId);
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
}
