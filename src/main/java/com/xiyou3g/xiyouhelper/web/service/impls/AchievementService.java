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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public void getAchievement(String name, String num, String sessionId) {

        String value3 = hiddenProcessor.start(name,num,sessionId);
        System.out.println(value3);
        //爬取所有学年
        List<String> lists = process.getBox(name,num,sessionId);


        for (String list : lists){
            for (int i = 1;i<=2;i++){
                List<Achievement> achievements = achievementProcessor.start(name,num,sessionId,list, String.valueOf(i),value3);
                for (Achievement achievement : achievements){
                    achievementMapper.insertAchievement(achievement);
                }
            }
        }

    }

    @Override
    public ServerResponse<NewAchievement> getNewAchievement(String name, String num, String sessionId) {
        String value3 = hiddenProcessor.start(name,num,sessionId);
        //爬取所有学年
        List<String> lists = process.getBox(name,num,sessionId);
        List<Achievement> achievements;
        NewAchievement newAchievement = new NewAchievement();
        newAchievement.setYear(lists);
        achievements = achievementProcessor.start(name,num,sessionId,lists.get(0),"2",value3);
        if (achievements.size() == 0){
            achievements = achievementProcessor.start(name,num,sessionId,lists.get(0),"1",value3);
            newAchievement.setAchievements(achievements);
            return ServerResponse.createBySuccess(newAchievement);
        }else {
            newAchievement.setAchievements(achievements);
            return ServerResponse.createBySuccess(newAchievement);
        }
    }

    @Override
    public ServerResponse<Total> getTotal(String name, String num, String sessionId) {
        String value = hiddenProcessor.start(name,num,sessionId);
        return ServerResponse.createBySuccess(totalProcess.start(name,num,sessionId,value));
    }

}
