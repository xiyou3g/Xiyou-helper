package com.xiyou3g.xiyouhelper.dao;

import com.xiyou3g.xiyouhelper.model.Achievement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AchievementMapper {

    //存储成绩信息
    public void insertAchievement(Achievement achievement);

    int insertAchievements(List<Achievement> achievements);

    //查询成绩
    public List<Achievement> selectAchievement(@Param("num") String num, @Param("school_year") String school_year, @Param("semester") String semester);
}
