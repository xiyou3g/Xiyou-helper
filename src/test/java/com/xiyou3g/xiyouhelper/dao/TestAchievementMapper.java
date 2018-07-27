package com.xiyou3g.xiyouhelper.dao;

import com.xiyou3g.xiyouhelper.model.Achievement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAchievementMapper {

    @Resource
    private AchievementMapper achievementMapper;

    @Test
    public void testAchievementMapper(){
        Achievement achievement = new Achievement();
        achievement.setNum("1111");
        achievement.setSemester("2");
        achievement.setSchool_year("333");
        achievement.setClassname("444444444");
        achievementMapper.insertAchievement(achievement);
    }
}
