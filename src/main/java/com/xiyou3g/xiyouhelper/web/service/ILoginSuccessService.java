package com.xiyou3g.xiyouhelper.web.service;

import com.xiyou3g.xiyouhelper.model.Achievement;
import com.xiyou3g.xiyouhelper.model.TrainPlanMessage;
import com.xiyou3g.xiyouhelper.model.User;

import java.util.List;

/**
 * @author mengchen
 * @time 18-7-27 下午9:21
 */
public interface ILoginSuccessService {
    void saveSomeMessage(User user, List<Achievement> achievements,
                         List<TrainPlanMessage> trainPlanMessages);
}
