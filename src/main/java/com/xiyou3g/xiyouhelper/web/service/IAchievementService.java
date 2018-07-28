package com.xiyou3g.xiyouhelper.web.service;


import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.Achievement;
import com.xiyou3g.xiyouhelper.model.NewAchievement;
import com.xiyou3g.xiyouhelper.model.Total;

import java.io.IOException;
import java.util.List;

public interface IAchievementService {

    /**
     * 爬取所有成绩存入
     */
    List<Achievement> getAchievement(String name,String num,String sessionId) throws IOException;

    /**
     * 获取最新一学期的成绩
     */
    ServerResponse<NewAchievement> getNewAchievement(String name, String num, String sessionId) throws IOException;

    /**
     * 获取成绩统计信息
     */
    ServerResponse<Total> getTotal(String name,String num,String sessionId) throws IOException;

    /**
     * 查询某一学期成绩
     */
    ServerResponse<List<Achievement>> selectAchievement(String num,String school_year,String semester);

}
