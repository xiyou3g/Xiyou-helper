package com.xiyou3g.xiyouhelper.web.service;

import com.xiyou3g.xiyouhelper.model.Achievement;

import java.util.List;

public interface IAchievementService {

    /**
     * 成绩查询
     */
    public List<Achievement> getAchievement(String name,String num,String sessionId,String year,String semester);
}
