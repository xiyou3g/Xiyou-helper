package com.xiyou3g.xiyouhelper.web.service;


import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.Achievement;
import com.xiyou3g.xiyouhelper.model.NewAchievement;
import com.xiyou3g.xiyouhelper.model.Total;

import java.io.IOException;
import java.util.List;


/**
 * @author sunxiaozhe
 * @time 2018/8/1 9:43
 */
public interface IAchievementService {

    /**
     *
     * @param name 姓名
     * @param num   学号
     * @param sessionId sessionId
     * @return
     * @throws IOException
     */
    List<Achievement> getAchievement(String name,String num,String sessionId) throws IOException;

    /**
     *
     * @param name 姓名
     * @param num   学号
     * @param sessionId  sessionId
     * @return
     * @throws IOException
     */
    ServerResponse<NewAchievement> getNewAchievement(String name, String num, String sessionId) throws IOException;

    /**
     *
     * @param name   姓名
     * @param num     学号
     * @param sessionId   sessionId
     * @return
     * @throws IOException
     */
    ServerResponse<Total> getTotal(String name,String num,String sessionId) throws IOException;

    /**
     *
     * @param num    学号
     * @param school_year   学年
     * @param semester   学期
     * @return
     */
    ServerResponse<List<Achievement>> selectAchievement(String num,String school_year,String semester);

}
