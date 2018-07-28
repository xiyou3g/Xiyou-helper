package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.dao.AchievementMapper;
import com.xiyou3g.xiyouhelper.dao.TrainPlanMapper;
import com.xiyou3g.xiyouhelper.dao.TrainPlanStatusMapper;
import com.xiyou3g.xiyouhelper.dao.UserMapper;
import com.xiyou3g.xiyouhelper.model.Achievement;
import com.xiyou3g.xiyouhelper.model.TrainPlanMessage;
import com.xiyou3g.xiyouhelper.model.User;
import com.xiyou3g.xiyouhelper.web.service.ILoginSuccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mengchen
 * @time 18-7-27 下午9:23
 */
@Service
public class LoginSuccessService implements ILoginSuccessService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AchievementMapper achievementMapper;

    @Autowired
    private TrainPlanMapper trainPlanMapper;

    @Autowired
    private TrainPlanStatusMapper trainPlanStatusMapper;
    @Override
    @Transactional
    public void saveSomeMessage(User user, List<Achievement> achievements, List<TrainPlanMessage> trainPlanMessages) {
        userMapper.insertUserMessage(user);
        achievementMapper.insertAchievements(achievements);
        if (trainPlanMessages != null) {
            trainPlanMapper.insertTrainPlans(trainPlanMessages);
            trainPlanStatusMapper.save(user.getMajor(), user.getLevel());
        }
    }
}
