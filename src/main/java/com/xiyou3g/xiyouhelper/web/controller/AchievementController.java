package com.xiyou3g.xiyouhelper.web.controller;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.Achievement;
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
import java.io.IOException;
import java.util.List;

/**
 * @author sunxiaozhe
 * @time 2018/8/1 9:43
 */
@RestController
@RequestMapping("/achievement")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private SessionUtil sessionUtil;

    @PostMapping(value = "get_new_achievement")
    public ServerResponse<NewAchievement> getNewAchievement(HttpSession session) throws IOException {

        SimpleUser user = (SimpleUser) session.getAttribute("user");
        String sessionId = sessionUtil.getSessionId(PrefixEnum.XYEDU.getDesc(), user.getSid());
        ThreadLocal<String> sessionID = new ThreadLocal<>();
        sessionID.set(sessionId);
        if (sessionId.equals("")|| user == null){
            return ServerResponse.createByErrorMsg("登陆过期，请重新登陆!");
        }else {
            ServerResponse<NewAchievement> achievement = achievementService.getNewAchievement(user.getName(), user.getSid(), sessionID.get());
            return achievement;
        }
    }

    @PostMapping(value = "get_total")
    public ServerResponse<Total> getTotal(HttpSession session) throws IOException {
        SimpleUser user = (SimpleUser) session.getAttribute("user");
        String sessionId = sessionUtil.getSessionId(PrefixEnum.XYEDU.getDesc(), user.getSid());
        ThreadLocal<String> sessionID = new ThreadLocal<>();
        sessionID.set(sessionId);
        if (sessionId.equals("") || user == null){
            return ServerResponse.createByErrorMsg("登陆过期，请重新登陆！");
        }
        else {
            ServerResponse<Total> totalServerResponse = achievementService.getTotal(user.getName(),user.getSid(),sessionID.get());
            return totalServerResponse;
        }

    }

    @PostMapping(value = "get_achievement")
    public ServerResponse<List<Achievement>> selectAchievement(String num,String school_year,String semester){
        return achievementService.selectAchievement(num,school_year,semester);

    }



}
