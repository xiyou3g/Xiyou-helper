package com.xiyou3g.xiyouhelper.web.service;


import com.xiyou3g.xiyouhelper.model.NewAchievement;

public interface IAchievementService {

    /**
     * 爬取所有成绩存入数据库
     */
    void getAchievement(String name,String num,String sessionId);

    /**
     * 获取最新一学期的成绩
     */
    NewAchievement getNewAchievement(String name, String num, String sessionId);

}
