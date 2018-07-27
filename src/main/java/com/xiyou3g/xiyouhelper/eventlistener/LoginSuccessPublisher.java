package com.xiyou3g.xiyouhelper.eventlistener;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * 18-7-24 下午8:40
 * @author mengchen
 */
@Component
public class LoginSuccessPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(LoginSuccessEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
