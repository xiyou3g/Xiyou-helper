package com.xiyou3g.xiyouhelper.web.controller;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.User;
import com.xiyou3g.xiyouhelper.model.dto.SimpleUser;
import com.xiyou3g.xiyouhelper.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author mengchen
 * @time 18-8-3 下午4:33
 */
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/xiyou_edu_sys/me")
    public ServerResponse<User> getUserMessage(HttpSession session) {
        SimpleUser simpleUser = (SimpleUser) session.getAttribute("user");
        if (simpleUser == null) {
            return ServerResponse.createByErrorMsg("未登录");
        }
        User user = userService.getUserBySid(simpleUser.getSid());
        return ServerResponse.createBySuccess(user);
    }
}
