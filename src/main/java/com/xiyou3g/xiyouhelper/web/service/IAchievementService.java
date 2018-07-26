package com.xiyou3g.xiyouhelper.web.service;


import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.NewAchievement;
import com.xiyou3g.xiyouhelper.model.Total;

public interface IAchievementService {

    /**
     * 爬取所有成绩存入数据库
     */
    void getAchievement(String name,String num,String sessionId);

    /**
     * 获取最新一学期的成绩
     */
    ServerResponse<NewAchievement> getNewAchievement(String name, String num, String sessionId);

    /**
     * 获取成绩统计信息
     */
    ServerResponse<Total> getTotal(String name,String num,String sessionId);

}
