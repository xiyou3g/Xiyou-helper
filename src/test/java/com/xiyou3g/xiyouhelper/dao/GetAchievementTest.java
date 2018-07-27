package com.xiyou3g.xiyouhelper.dao;


import com.xiyou3g.xiyouhelper.web.service.impls.AchievementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GetAchievementTest {

    @Autowired
    private AchievementService achievementService;
    @Test
    public void testGetAchievement() throws IOException {

        achievementService.getAchievement("孙晓哲","04162130","pyd25zel02kw4a55eo25myeb");

    }

    @Test
    public void testAchievement(){

    }

}
