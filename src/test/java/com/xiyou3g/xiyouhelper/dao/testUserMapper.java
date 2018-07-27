package com.xiyou3g.xiyouhelper.dao;

import com.xiyou3g.xiyouhelper.model.Achievement;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testUserMapper {

    @Resource
    private AchievementMapper achievementMapper;


    @Test
    public void testAchievement(){
        List<Achievement> lists = achievementMapper.selectAchievement("04162130","2017-2018","2");
        for (Achievement list : lists){
            System.out.println(list);
        }
    }

    @Test
    public void testMonth(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        System.out.println(month);
    }
}
