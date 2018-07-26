package com.xiyou3g.xiyouhelper.eventlistener;

import com.xiyou3g.xiyouhelper.model.User;
import com.xiyou3g.xiyouhelper.processor.UserMessageProcessor;
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

    public LoginSuccessEvent(String studentNum, String sessionId) {
        super("登录成功");
        this.sessionId = sessionId;
        this.studentNum = studentNum;
    }

    public void loginSuccess(UserMessageProcessor processor) {
        Thread thread = new Thread(() -> {
            processor.execute(studentNum, sessionId);
        });
        thread.start();
    }
}
