package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.dao.AchievementMapper;
import com.xiyou3g.xiyouhelper.model.Achievement;
import com.xiyou3g.xiyouhelper.model.NewAchievement;
import com.xiyou3g.xiyouhelper.model.Total;
import com.xiyou3g.xiyouhelper.processor.AchievementProcessor;
import com.xiyou3g.xiyouhelper.processor.BoxProcess;
import com.xiyou3g.xiyouhelper.processor.HiddenProcessor;
import com.xiyou3g.xiyouhelper.processor.TotalProcess;
import com.xiyou3g.xiyouhelper.web.service.IAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service
public class AchievementService implements IAchievementService {

    @Autowired
    private AchievementProcessor achievementProcessor;

    @Autowired
    private BoxProcess process;

    @Autowired
    private HiddenProcessor hiddenProcessor;

    @Autowired
    private TotalProcess totalProcess;

    @Resource
    private AchievementMapper achievementMapper;


    @Override
    public void getAchievement(String name, String num, String sessionId) throws IOException {

        String value3 = hiddenProcessor.start(name,num,sessionId);
        System.out.println(value3);
        //爬取所有学年
        List<String> lists = process.getBox(name,num,sessionId);
        List<Achievement> achievementList = Collections.synchronizedList(new ArrayList<>());
        for (String list : lists){
            for (int i = 1;i<=2;i++){
                List<Achievement> achievements = achievementProcessor.start(name,num,sessionId,list, String.valueOf(i),value3);
                for (Achievement achievement : achievements){
                    achievementList.add(achievement);
                }
            }
        }
        try {
            for (Achievement achievement : achievementList){
                achievementMapper.insertAchievement(achievement);
            }
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServerResponse<NewAchievement> getNewAchievement(String name, String num, String sessionId) throws IOException {
        String value3 = hiddenProcessor.start(name,num,sessionId);
        //爬取所有学年
        List<String> lists = process.getBox(name,num,sessionId);
        //成绩集合
        List<Achievement> achievements;
        //最新成绩和列表对象
        NewAchievement newAchievement = new NewAchievement();
        newAchievement.setYear(lists);

        //获取当前月份
        Calendar calendar = Calendar.getInstance();
        int month = 8;
        achievements = achievementProcessor.start(name,num,sessionId,lists.get(0),"2",value3);
        if (achievements.size() == 0){
            achievements = achievementProcessor.start(name,num,sessionId,lists.get(0),"1",value3);
            if (month >= 2){
                try {
                    for (Achievement achievement : achievements){
                        achievementMapper.insertAchievement(achievement);
                    }
                } catch (DuplicateKeyException e) {
                    e.printStackTrace();
                }
            }
            newAchievement.setAchievements(achievements);
            return ServerResponse.createBySuccess(newAchievement);
        }else {
            if (month >= 8){
                try {
                    for (Achievement achievement : achievements){
                        achievementMapper.insertAchievement(achievement);
                    }
                } catch (DuplicateKeyException e) {
                    e.printStackTrace();
                }
            }
            newAchievement.setAchievements(achievements);
            return ServerResponse.createBySuccess(newAchievement);
        }
    }

    @Override
    public ServerResponse<Total> getTotal(String name, String num, String sessionId) throws IOException {
        String value = hiddenProcessor.start(name,num,sessionId);
        return ServerResponse.createBySuccess(totalProcess.start(name,num,sessionId,value));
    }

    @Override
    public ServerResponse<List<Achievement>> selectAchievement(String num, String school_year, String semester) {
        return ServerResponse.createBySuccess(achievementMapper.selectAchievement(num,school_year,semester));
    }

}
