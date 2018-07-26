package com.xiyou3g.xiyouhelper.web.controller;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.NewAchievement;
import com.xiyou3g.xiyouhelper.model.Total;
import com.xiyou3g.xiyouhelper.model.dto.SimpleUser;
import com.xiyou3g.xiyouhelper.util.redis.PrefixEnum;
import com.xiyou3g.xiyouhelper.util.redis.SessionUtil;
import com.xiyou3g.xiyouhelper.web.service.impls.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/achievement")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private SessionUtil sessionUtil;

    @PostMapping(value = "getNewAchievement")
    public ServerResponse<NewAchievement> getNewAchievement(HttpSession session){

        SimpleUser user = (SimpleUser) session.getAttribute("user");
        String sessionId = sessionUtil.getSessionId(PrefixEnum.XYEDU.getDesc(), user.getSid());
        return achievementService.getNewAchievement(user.getName(),user.getSid(),sessionId);
    }

    @PostMapping(value = "getTotal")
    public ServerResponse<Total> getTotal(HttpSession session){
        SimpleUser user = (SimpleUser) session.getAttribute("user");
        String sessionId = sessionUtil.getSessionId(PrefixEnum.XYEDU.getDesc(), user.getSid());
        return achievementService.getTotal(user.getName(),user.getSid(),sessionId);
    }



}
