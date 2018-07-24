package com.xiyou3g.xiyouhelper.dao;

import com.xiyou3g.xiyouhelper.model.Achievement;
import com.xiyou3g.xiyouhelper.processor.AchievementProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetAchievementTest {

    @Autowired
    private AchievementProcessor achievementProcessor;


    @Test
    public void testGetAchievement(){

        List<Achievement> lists =  achievementProcessor.start("孙晓哲","04162130","31egaw55hdruff55auxhs455","2017-2018","2");
        for (Achievement list : lists){
            System.out.println(list);
        }

    }
}
