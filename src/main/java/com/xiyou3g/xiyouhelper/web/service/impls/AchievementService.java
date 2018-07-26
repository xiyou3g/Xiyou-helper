package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.model.Achievement;
import com.xiyou3g.xiyouhelper.processor.AchievementProcessor;
import com.xiyou3g.xiyouhelper.web.service.IAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchievementService implements IAchievementService {

    @Autowired
    private AchievementProcessor achievementProcessor;

    @Override
    public List<Achievement> getAchievement(String name,String num,String sessionId,String year,String semester) {
        return achievementProcessor.start(name,num,sessionId,year,semester);
    }
}
