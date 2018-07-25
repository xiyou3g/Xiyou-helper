package com.xiyou3g.xiyouhelper.dao;

import com.xiyou3g.xiyouhelper.model.Achievement;
import com.xiyou3g.xiyouhelper.model.NewAchievement;
import com.xiyou3g.xiyouhelper.web.service.impls.AchievementService;
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
    private AchievementService achievementService;


    @Test
    public void testGetAchievement(){

       achievementService.getAchievement("孙晓哲","04162130","ibcvgn45uwr41v2plpb5a2v1");
    }
}
