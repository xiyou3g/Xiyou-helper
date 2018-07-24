package com.xiyou3g.xiyouhelper.pipeline;

import com.xiyou3g.xiyouhelper.model.User;
import com.xiyou3g.xiyouhelper.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 18-7-21 下午4:45
 * @author mengchen
 */
@Component
public class UserMessagePipeline implements Pipeline {

    @Autowired
    private IUserService userService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        User user = resultItems.get("user");
        if (user != null && user.getSid() != null && user.getAdclass() != null) {
            userService.saveUserMessage(user);
        }
    }
}
