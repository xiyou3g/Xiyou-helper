package com.xiyou3g.xiyouhelper.webmagic.pipeline;

import com.xiyou3g.xiyouhelper.model.User;
import org.apache.catalina.Pipeline;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

/**
 * mengchen
 * 18-7-21 下午4:45
 */
public class UserMessagePipeline implements PageModelPipeline<User> {

    @Override
    public void process(User user, Task task) {

    }
}
