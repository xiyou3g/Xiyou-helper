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

@RestController
@RequestMapping("/achievement")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private SessionUtil sessionUtil;

    @PostMapping(value = "getNewAchievement")
    public ServerResponse<NewAchievement> getNewAchievement(HttpSession session) throws IOException {

        Long start = System.currentTimeMillis();
        SimpleUser user = (SimpleUser) session.getAttribute("user");
        String sessionId = sessionUtil.getSessionId(PrefixEnum.XYEDU.getDesc(), user.getSid());
        ThreadLocal<String> sessionID = new ThreadLocal<>();
        sessionID.set(sessionId);
        if (sessionId.equals("")|| user == null){
            return ServerResponse.createByErrorMsg("登陆失败，请重新登陆!");
        }else {
            ServerResponse<NewAchievement> achievement = achievementService.getNewAchievement(user.getName(), user.getSid(), sessionID.get());
            System.out.println("耗时："+(System.currentTimeMillis()-start));
            return achievement;
        }
    }

    @PostMapping(value = "getTotal")
    public ServerResponse<Total> getTotal(HttpSession session) throws IOException {
        Long start = System.currentTimeMillis();
        SimpleUser user = (SimpleUser) session.getAttribute("user");
        String sessionId = sessionUtil.getSessionId(PrefixEnum.XYEDU.getDesc(), user.getSid());
        ThreadLocal<String> sessionID = new ThreadLocal<>();
        sessionID.set(sessionId);
        if (sessionId.equals("") || user == null){
            return ServerResponse.createByErrorMsg("登陆失败，请重新登陆！");
        }
        else {
            ServerResponse<Total> totalServerResponse = achievementService.getTotal(user.getName(),user.getSid(),sessionID.get());
            System.out.println("耗时："+(System.currentTimeMillis()-start));
            return totalServerResponse;
        }

    }

    @PostMapping(value = "getAchievement")
    public ServerResponse<List<Achievement>> selectAchievement(String num,String school_year,String semester){
        return achievementService.selectAchievement(num,school_year,semester);

    }



}
