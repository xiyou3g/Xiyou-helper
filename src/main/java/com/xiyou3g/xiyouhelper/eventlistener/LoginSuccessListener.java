package com.xiyou3g.xiyouhelper.eventlistener;

import com.xiyou3g.xiyouhelper.processor.UserMessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


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

    @Override
    public void onApplicationEvent(LoginSuccessEvent loginSuccessEvent) {
        logger.info("登录成功了！");
        loginSuccessEvent.loginSuccess(processor);
    }
}
