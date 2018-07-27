package com.xiyou3g.xiyouhelper.web.controller;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.TrainPlanMessage;
import com.xiyou3g.xiyouhelper.model.User;
import com.xiyou3g.xiyouhelper.model.dto.SimpleUser;
import com.xiyou3g.xiyouhelper.web.service.ITrainPlanService;
import com.xiyou3g.xiyouhelper.web.service.IUserService;
import com.xiyou3g.xiyouhelper.web.service.impls.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author mengchen
 * @time 18-7-27 下午2:23
 */
@RestController
public class TrainPlanController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ITrainPlanService trainPlanService;

    @PostMapping("/xiyou_edu_sys/train_plan/{term:\\d+}")
    public ServerResponse<List<TrainPlanMessage>> getTrainPlanByTern(@PathVariable int term,
                                                                     HttpSession session,
                                                                     HttpServletResponse response) {
        if (term > 8 || term < 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return ServerResponse.createByErrorMsg("学期必须在1到8之间");
        }
        SimpleUser simpleUser = (SimpleUser) session.getAttribute("user");
        if (simpleUser == null) {
            return ServerResponse.createByErrorMsg("登录过期");
        }
        User user = userService.getUserBySid(simpleUser.getSid());
        String major = user.getMajor();
        String level = user.getLevel();
        List<TrainPlanMessage> trainPlanMessages = trainPlanService.getTrainPlanMessages(major, level, term);
        return ServerResponse.createBySuccess(trainPlanMessages);
    }
}
